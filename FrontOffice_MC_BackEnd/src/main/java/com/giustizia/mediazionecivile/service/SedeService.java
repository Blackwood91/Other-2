package com.giustizia.mediazionecivile.service;
//com.giustizia.mediazionecivile.service

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.SedeDto;
import com.giustizia.mediazionecivile.dto.SedeOperativaTab;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.Sede;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.ModuloRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class SedeService {
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
	ModuloRepository moduloRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	ApiFileService apiFileService;
	@Autowired
	PdfService pdfService;

	public HashMap<String, Object> getAllSediByIdRichiesta(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage = sedeRepository.findByidRichiestaForElenco(pageable, idRichiesta);;
		List<Object[]> resultList = resultPage.getContent();

		List<SedeOperativaTab> sediDto = new ArrayList<>();
		for (Object[] obj : resultList) {
			SedeOperativaTab sedeDto = new SedeOperativaTab();
			sedeDto.setIdSede(obj[0]);
			sedeDto.setSedeLegale((char) obj[2]);
			sedeDto.setIndirizzo((String) obj[3]);
			sedeDto.setNumeroCivico((String) obj[4]);
			sedeDto.setIdComune(obj[6]);
			sedeDto.setNomeComune((String) obj[21]);
			sedeDto.setSiglaProvincia((String) obj[30]);
			sedeDto.setIdTitoloDetenzione(obj[11]);
			sedeDto.setDataContratto((Date) obj[13]);
			sedeDto.setDataInserimentoSede((Date) obj[17]);
			sedeDto.setDetenzioneTitolo((String) obj[31]);
			sedeDto.setCompletato((Integer) obj[32]);
			sedeDto.setValidato((Integer) obj[33]);
			sedeDto.setAnnullato((Integer) obj[34]);
			sediDto.add(sedeDto);
		}

		response.put("result", sediDto);
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public Sede getSedeById(Long idSede) {
		return sedeRepository.findById(idSede).get();
	}

	public boolean existSedeLegale(Long idProvvisorio) {
		return sedeRepository.existsByIdRichiestaAndSedeLegale(idProvvisorio, '1');
	}

	@Transactional(rollbackFor = Exception.class)
	public Sede insertSede(SedeDto sedeDto) {
		Sede sede = new Sede();
		boolean existSedeLegale = sedeRepository.existsByIdRichiestaAndSedeLegale(sedeDto.getIdRichiesta(), '1');

		if (existSedeLegale) {
			// Verifica se il dato passato dal front end è coerente con quello in db
			if (sedeDto.getIsSedeLegale().equalsIgnoreCase("0")) {
				sede.setSedeLegale(sedeDto.getIsSedeLegale().charAt(0));
			} else {
				throw new RuntimeException("La sede passata deve essere operativa");
			}
		} else {
			// Verifica se il dato passato dal front end è coerente con quello in db
			if (sedeDto.getIsSedeLegale().equalsIgnoreCase("1")) {
				sede.setSedeLegale(sedeDto.getIsSedeLegale().charAt(0));
			} else {
				throw new RuntimeException("La sede passata deve essere legale");
			}
		}

		sede.setIdRichiesta(sedeDto.getIdRichiesta());
		sede.setIndirizzo(sedeDto.getIndirizzo());
		sede.setNumeroCivico(sedeDto.getNumeroCivico());
		sede.setCap(sedeDto.getCap());
		sede.setIdComune(sedeDto.getIdComune());
		sede.setTelefono(sedeDto.getTelefono());
		sede.setFax(sedeDto.getFax());
		sede.setPec(sedeDto.getPec());
		sede.setEmail(sedeDto.getEmail());
		sede.setIdTitoloDefinizione(sedeDto.getIdTitoloDetenzione());
		sede.setDurataContratto(sedeDto.getDurataContratto());
		sede.setDataContratto(sedeDto.getDataContratto());
		sede.setStrutturaOrgSegreteria(sedeDto.getStrutOrgSeg());
		sede.setSitoWebSede(sedeDto.getSitoWeb());
		sede.setRegistrazione(sedeDto.getRegistrazione());
		sede.setDataInserimentoSede(new Date());

		Sede sedeUpdate = sedeRepository.save(sede);
		
		// Logica per il caricamento del file in simulazione locale
		Optional<StatoModuliRichiestaFigli> schedaSede = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 68, sedeUpdate.getIdRichiesta(),
						sedeUpdate.getIdSede());

		// Logica per il caricamento del file in simulazione locale
		Optional<StatoModuliRichiestaFigli> moduloPlanimetria = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 69, sedeUpdate.getIdRichiesta(),
						sedeUpdate.getIdSede());

		Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 70, sedeUpdate.getIdRichiesta(),
						sedeUpdate.getIdSede());
		
		if (schedaSede.isPresent() == false) {
			StatoModuliRichiestaFigli newSchedaSede = new StatoModuliRichiestaFigli();
			newSchedaSede.setIdModulo((long) 68);
			newSchedaSede.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newSchedaSede.setIdRiferimento(sedeUpdate.getIdSede());
			newSchedaSede.setDataInserimento(new Date());
			schedaSede = Optional.of(statoModuliRichiestaFigliRepository.save(newSchedaSede));
		}
		if (moduloPlanimetria.isPresent() == false) {
			StatoModuliRichiestaFigli newModuloPlanimetria = new StatoModuliRichiestaFigli();
			newModuloPlanimetria.setIdModulo((long) 69);
			newModuloPlanimetria.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newModuloPlanimetria.setIdRiferimento(sedeUpdate.getIdSede());
			newModuloPlanimetria.setNomeAllegato(sedeDto.getNomeFilePlanimetria());
			newModuloPlanimetria.setDataInserimento(new Date());
			moduloPlanimetria = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloPlanimetria));
		}
		if (moduloCopiaContratto.isPresent() == false) {
			StatoModuliRichiestaFigli newModuloCopiaContratto = new StatoModuliRichiestaFigli();
			newModuloCopiaContratto.setIdModulo((long) 70);
			newModuloCopiaContratto.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newModuloCopiaContratto.setIdRiferimento(sedeUpdate.getIdSede());
			newModuloCopiaContratto.setNomeAllegato(sedeDto.getNomeFileCopContratto());
			newModuloCopiaContratto.setDataInserimento(new Date());
			moduloCopiaContratto = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloCopiaContratto));
		}

		try {
			pdfService.checkValidLoadDocument(sedeDto.getAllegatoPlanimetria());
			pdfService.checkValidLoadDocument(sedeDto.getAllegatoCopContratto());

			String pathPlanimetria = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloPlanimetria.get().getIdModulo();
			MercurioFile infoFile = apiFileService.insertFile(pathPlanimetria, 
									Long.toString(moduloPlanimetria.get().getId()), sedeDto.getAllegatoPlanimetria());
			moduloPlanimetria.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloPlanimetria.get().setContentId(infoFile.getContentId());
			
			String pathCopiaContratto = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloCopiaContratto.get().getIdModulo();
			MercurioFile infoFile2 = apiFileService.insertFile(pathCopiaContratto, 
									 Long.toString(moduloCopiaContratto.get().getId()), sedeDto.getAllegatoCopContratto());
			moduloCopiaContratto.get().setDocumentIdClient(infoFile2.getDocumentIdClient());
			moduloCopiaContratto.get().setContentId(infoFile2.getContentId());
			
			statoModuliRichiestaFigliRepository.save(moduloPlanimetria.get());						
			statoModuliRichiestaFigliRepository.save(moduloCopiaContratto.get());						
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage() : "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}

		// clone ma operativa
		if (sedeDto.getLegaleIsOperativa()) {
			Sede sedeOperativa = new Sede();
			// Copia tutti gli attributi da 'sede' a 'sedeOperativa' per evitare il
			// sovraccaricamento del primo inserimento
			BeanUtils.copyProperties(sede, sedeOperativa);
			sedeOperativa.setIdSede(null); // Imposta l'id su null
			sedeOperativa.setSedeLegale('0');
			Sede sedeOperativaUpdate = sedeRepository.save(sedeOperativa);

			// VERRA' RIFATTO ANCHE L'INSERIMENTO DEGLI ALLEGATI
			// Logica per il caricamento del file in simulazione locale
			Optional<StatoModuliRichiestaFigli> schedaSedeOperativa = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdRiferimento(
					(long) 68, sedeUpdate.getIdRichiesta(), sedeOperativaUpdate.getIdSede());
			moduloPlanimetria = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdRiferimento(
					(long) 69, sedeOperativaUpdate.getIdRichiesta(), sedeOperativaUpdate.getIdSede());

			moduloCopiaContratto = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdRiferimento(
					(long) 70, sedeOperativaUpdate.getIdRichiesta(), sedeOperativaUpdate.getIdSede());

			if (moduloPlanimetria.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloPlanimetria = new StatoModuliRichiestaFigli();
				newModuloPlanimetria.setIdModulo((long) 69);
				newModuloPlanimetria.setIdRichiesta(sedeOperativaUpdate.getIdRichiesta());
				newModuloPlanimetria.setIdRiferimento(sedeOperativaUpdate.getIdSede());
				newModuloPlanimetria.setNomeAllegato(sedeDto.getNomeFilePlanimetria());
				newModuloPlanimetria.setDataInserimento(new Date());
				moduloPlanimetria = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloPlanimetria));
			}
			if (moduloCopiaContratto.isPresent() == false) {
				StatoModuliRichiestaFigli newModuloCopiaContratto = new StatoModuliRichiestaFigli();
				newModuloCopiaContratto.setIdModulo((long) 70);
				newModuloCopiaContratto.setIdRichiesta(sedeOperativaUpdate.getIdRichiesta());
				newModuloCopiaContratto.setIdRiferimento(sedeOperativaUpdate.getIdSede());
				newModuloCopiaContratto.setNomeAllegato(sedeDto.getNomeFileCopContratto());
				newModuloCopiaContratto.setDataInserimento(new Date());
				moduloCopiaContratto = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloCopiaContratto));
			}
			if (schedaSedeOperativa.isPresent() == false) {
				StatoModuliRichiestaFigli newSchedaSedeOperativa = new StatoModuliRichiestaFigli();
				newSchedaSedeOperativa.setIdModulo((long) 68);
				newSchedaSedeOperativa.setIdRichiesta(sedeOperativaUpdate.getIdRichiesta());
				newSchedaSedeOperativa.setIdRiferimento(sedeOperativaUpdate.getIdSede());
				newSchedaSedeOperativa.setDataInserimento(new Date());
				schedaSede = Optional.of(statoModuliRichiestaFigliRepository.save(newSchedaSedeOperativa));
			}

			try {
				pdfService.checkValidLoadDocument(sedeDto.getAllegatoPlanimetria());
				pdfService.checkValidLoadDocument(sedeDto.getAllegatoCopContratto());

				String pathPlanimetria = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloPlanimetria.get().getIdModulo();
				MercurioFile infoFile = apiFileService.insertFile(pathPlanimetria, 
										Long.toString(moduloPlanimetria.get().getId()), sedeDto.getAllegatoPlanimetria());
				moduloPlanimetria.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloPlanimetria.get().setContentId(infoFile.getContentId());
				
				String pathCopiaContratto = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloCopiaContratto.get().getIdModulo();
				MercurioFile infoFile2 = apiFileService.insertFile(pathCopiaContratto, 
										 Long.toString(moduloCopiaContratto.get().getId()), sedeDto.getAllegatoCopContratto());
				moduloCopiaContratto.get().setDocumentIdClient(infoFile2.getDocumentIdClient());
				moduloCopiaContratto.get().setContentId(infoFile2.getContentId());
				
				statoModuliRichiestaFigliRepository.save(moduloPlanimetria.get());						
				statoModuliRichiestaFigliRepository.save(moduloCopiaContratto.get());
			} catch (Exception e) {
				throw new RuntimeException("Si è verificato un errore con il caricamento del file nel documentale");
			}

		}

		return sedeUpdate;
	}

	@Transactional(rollbackFor = Exception.class)
	public Sede updateSede(SedeDto sedeDto) {
		Optional<Sede> sedeExisting = sedeRepository.findById(sedeDto.getIdSede());

		if (sedeExisting.isPresent()) {
			sedeExisting.get().setIndirizzo(sedeDto.getIndirizzo());
			sedeExisting.get().setNumeroCivico(sedeDto.getNumeroCivico());
			sedeExisting.get().setCap(sedeDto.getCap());
			sedeExisting.get().setIdComune(sedeDto.getIdComune());
			sedeExisting.get().setTelefono(sedeDto.getTelefono());
			sedeExisting.get().setFax(sedeDto.getFax());
			sedeExisting.get().setPec(sedeDto.getPec());
			sedeExisting.get().setEmail(sedeDto.getEmail());
			sedeExisting.get().setIdTitoloDefinizione(sedeDto.getIdTitoloDetenzione());
			sedeExisting.get().setDataContratto(sedeDto.getDataContratto());
			sedeExisting.get().setDurataContratto(sedeDto.getDurataContratto());
			sedeExisting.get().setStrutturaOrgSegreteria(sedeDto.getStrutOrgSeg());
			sedeExisting.get().setSitoWebSede(sedeDto.getSitoWeb());
			sedeExisting.get().setRegistrazione(sedeDto.getRegistrazione());

			Sede sedeUpdate = sedeRepository.save(sedeExisting.get());

			// AGGIORNAMENTO DEGLI ALLEGATI SE CAMBIATI
			if (sedeDto.getAllegatoCopContratto() != null) {
				// Logica per il caricamento del file in simulazione locale
				Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 70, sedeUpdate.getIdRichiesta(),
								sedeUpdate.getIdSede());

				if (moduloCopiaContratto.isPresent() == false) {
					StatoModuliRichiestaFigli newModuloCopiaContratto = new StatoModuliRichiestaFigli();
					newModuloCopiaContratto.setIdModulo((long) 70);
					newModuloCopiaContratto.setIdRichiesta(sedeUpdate.getIdRichiesta());
					newModuloCopiaContratto.setIdRiferimento(sedeUpdate.getIdSede());
					newModuloCopiaContratto.setNomeAllegato(sedeDto.getNomeFileCopContratto());
					newModuloCopiaContratto.setDataInserimento(new Date());
					moduloCopiaContratto = Optional
							.of(statoModuliRichiestaFigliRepository.save(newModuloCopiaContratto));
				}

				try {
					// Se uno dei due file non è valido per l'inserimento verrà generato un errore
					pdfService.checkValidLoadDocument(sedeDto.getAllegatoCopContratto());
					
					apiFileService.deleteFile(moduloCopiaContratto.get().getDocumentIdClient());
					String pathCopiaContratto = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloCopiaContratto.get().getIdModulo();
					MercurioFile infoFile = apiFileService.insertFile(pathCopiaContratto, 
											 Long.toString(moduloCopiaContratto.get().getId()), sedeDto.getAllegatoCopContratto());
					moduloCopiaContratto.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloCopiaContratto.get().setContentId(infoFile.getContentId());
					
					statoModuliRichiestaFigliRepository.save(moduloCopiaContratto.get());
				} catch (Exception e) {
					String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
							? e.getMessage() : "Si è verificato un errore con il caricamento del file nel documentale";
					throw new RuntimeException(message);
				}
			}
			if (sedeDto.getAllegatoPlanimetria() != null) {
				// Logica per il caricamento del file in simulazione locale
				Optional<StatoModuliRichiestaFigli> moduloPlanimetria = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 69, sedeUpdate.getIdRichiesta(),
								sedeUpdate.getIdSede());

				if (moduloPlanimetria.isPresent() == false) {
					StatoModuliRichiestaFigli newModuloPlanimetria = new StatoModuliRichiestaFigli();
					newModuloPlanimetria.setIdModulo((long) 69);
					newModuloPlanimetria.setIdRichiesta(sedeUpdate.getIdRichiesta());
					newModuloPlanimetria.setIdRiferimento(sedeUpdate.getIdSede());
					newModuloPlanimetria.setNomeAllegato(sedeDto.getNomeFilePlanimetria());
					newModuloPlanimetria.setDataInserimento(new Date());
					moduloPlanimetria = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloPlanimetria));
				}

				try {
					pdfService.checkValidLoadDocument(sedeDto.getAllegatoPlanimetria());

					apiFileService.deleteFile(moduloPlanimetria.get().getDocumentIdClient());
					String pathPlanimetria = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloPlanimetria.get().getIdModulo();
					MercurioFile infoFile = apiFileService.insertFile(pathPlanimetria, 
											Long.toString(moduloPlanimetria.get().getId()), sedeDto.getAllegatoPlanimetria());
					moduloPlanimetria.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloPlanimetria.get().setContentId(infoFile.getContentId());					
					statoModuliRichiestaFigliRepository.save(moduloPlanimetria.get());
				} catch (Exception e) {
					String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
							? e.getMessage() : "Si è verificato un errore con il caricamento del file nel documentale";
					throw new RuntimeException(message);
				}
				
			}
			
			// ANNULLA CONVALIDAZIONE DELLA SEDE AGGIORNATA E ANCHE DELL'ATTO RIEP (long) 3
			List<StatoModuliRichiestaFigli> moduliSedi = statoModuliRichiestaFigliRepository
					.findAllByFor3IdModuliAndIdRichiestaAndIdRiferimento((long) 68, (long) 69, (long) 70, sedeDto.getIdRichiesta(), sedeDto.getIdSede());
			Optional<StatoModuliRichiestaFigli> moduloAtto = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 3, 
					 sedeDto.getIdRichiesta());
			
			for (StatoModuliRichiestaFigli modulo : moduliSedi) {
				// SOLO SE CONVALIDATO VERRA' ANNULLATO IL MODULO
				if(modulo.getCompletato() == (Integer) 1 || modulo.getValidato() == (Integer) 1) {
					modulo.setValidato(0);
					modulo.setCompletato(0);
					statoModuliRichiestaFigliRepository.save(modulo);
				}
			}
			
			
			if(moduloAtto.isPresent() && (moduloAtto.get().getCompletato() == (Integer) 1 || moduloAtto.get().getValidato() == (Integer) 1)) {
				moduloAtto.get().setValidato(0);
				moduloAtto.get().setCompletato(0);
				statoModuliRichiestaFigliRepository.save(moduloAtto.get());
			}

			
			return sedeUpdate;
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna sede corrispondente a quell'id");
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public String deleteSede(Long idSede) {
		Optional<Sede> sede = sedeRepository.findById(idSede);
		try {
			if (sede.isPresent()) {
				if (sede.get().getSedeLegale() == 1) {
					throw new RuntimeException("-ErrorInfo La sede è legale e non può essere cancellata");
				}

				// Cancellazione dei file e della cartella associata
				Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 70, sede.get().getIdRichiesta(),
								sede.get().getIdSede());

				Optional<StatoModuliRichiestaFigli> moduloPlanimetria = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 69, sede.get().getIdRichiesta(),
								sede.get().getIdSede());
				
				sedeRepository.delete(sede.get());
				statoModuliRichiestaFigliRepository.delete(moduloCopiaContratto.get());
				statoModuliRichiestaFigliRepository.delete(moduloPlanimetria.get());

				if(moduloCopiaContratto.isPresent()) {
					apiFileService.deleteFile(moduloCopiaContratto.get().getDocumentIdClient());
				}
				if(moduloPlanimetria.isPresent()) {
					apiFileService.deleteFile(moduloCopiaContratto.get().getDocumentIdClient());
				}
				
				return "Successo";
			} else {
				throw new RuntimeException("-ErrorInfo La sede non è presente in banca dati");
			}

		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo"))
					? e.getMessage() : "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}

		// IN ATTESA DI SIMULAZIONE DI PERCORSO DEL CARICAMENTO DEI FILE NEL
		// DOCUMENTALE...
	}

}
