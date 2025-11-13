package com.giustizia.mediazionecivile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.dto.CertificazioneLingueDto;
import com.giustizia.mediazionecivile.dto.FileAllegatoAppendiciDto;
import com.giustizia.mediazionecivile.dto.FileAllegatoDto;
import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.CertificazioneLingueStraniere;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.CertificazioneLingueStraniereRepository;
import com.giustizia.mediazionecivile.repository.ModuloRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class StatoModuloAllegatoService {
	@Autowired
	ModuloRepository moduloRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	CertificazioneLingueStraniereRepository certificazioneLingueStraniereRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	ApiFileService apiFileService;
	@Autowired
	PdfService pdfService;
	
	public HashMap<String, Object> getAllAttoCostOdm(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 4, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllAttoCostOdmNA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 71, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllCodiceEtico(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 17, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllStatutoOrg(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 5, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllStatutoOrgNA(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 72, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllRegProcedura(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 16, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllSpeseMed(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 73, idRichiesta, (long) 1, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements()); 
		return response;
	}

	public HashMap<String, Object> getAllBilancio(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 23, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllAttoCostNonAutonomo(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 71, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllStatutoOrgNonAutonomo(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 72, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllPolizzaAssicurativa(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 59, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllRichiestaInviata(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaOrderByDataInserimentoDesc((long) 85, idRichiesta, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertAttoCostOdm(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 4);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);
			
			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertCodiceEtico(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 17);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);
			
			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertStatutoOrg(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 5);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertAttoCostOdmNA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 71);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertStatutoOrgNA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 72);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertRegProcedura(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 16);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());
			
			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli saveSpeseMed(FileAllegatoDto fileAllegatoDto) {
		Optional<StatoModuliRichiestaFigli> moduloSpeseMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long)73, fileAllegatoDto.getIdRichiesta());
		
		// per gestire la casistica in caso venga fatto un inserimento con "Dichiarazione di adozione" e in prece
		if(moduloSpeseMed.isPresent() == false) {
			moduloSpeseMed = Optional.of(new StatoModuliRichiestaFigli());
			moduloSpeseMed.get().setIdModulo((long)73);
			moduloSpeseMed.get().setIdRichiesta(fileAllegatoDto.getIdRichiesta());
			moduloSpeseMed.get().setNomeAllegato(fileAllegatoDto.getNomeFile());
			moduloSpeseMed.get().setDataInserimento(new Date());
		}
		else {
			moduloSpeseMed.get().setDataInserimento(new Date());
		}

		// Servirà per capire quale selezione è stata scelta per la tabella
		moduloSpeseMed.get().setIdRiferimento(fileAllegatoDto.getIdRiferimento());
		StatoModuliRichiestaFigli saveSpeseMed = statoModuliRichiestaFigliRepository.save(moduloSpeseMed.get());

		// Solo se la scelta è "Dichiarazione di adozione" verrà caricato il file
		if (saveSpeseMed.getIdRiferimento() == 1) {
			try {
				pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

				String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
						+ saveSpeseMed.getIdModulo();
				MercurioFile infoFile = apiFileService.insertFile(path, 
										Long.toString(saveSpeseMed.getId()), fileAllegatoDto.getFile());
				saveSpeseMed.setDocumentIdClient(infoFile.getDocumentIdClient());
				saveSpeseMed.setContentId(infoFile.getContentId());
			} catch (Exception e) {
				String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
						: "Si è verificato un errore con il caricamento del file nel documentale";
				throw new RuntimeException(message);
			}
		}
		
		// MODULO SPESE DI MEDIAZIONE VERRA RIMOSSO IL CONVALIDA A PRESCINDERE DALLA SELEZIONE FATTA
		if (saveSpeseMed.getCompletato() == (Integer) 1) {
			saveSpeseMed.setValidato(0);
			saveSpeseMed.setCompletato(0);
		} 
		
		statoModuliRichiestaFigliRepository.save(saveSpeseMed);

		// RIMOZIONE CONVALIDAZIONI CON MODULI ASSOCIATI
		Optional<StatoModuliRichiestaFigli> statoModuloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, fileAllegatoDto.getIdRichiesta());

		if (statoModuloAttoRiep.isPresent() && statoModuloAttoRiep.get().getCompletato() == (Integer) 1) {
			statoModuloAttoRiep.get().setValidato(0);
			statoModuloAttoRiep.get().setCompletato(0);
			statoModuliRichiestaFigliRepository.save(statoModuloAttoRiep.get());
		} 

		return saveSpeseMed;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertBilancio(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 23);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFilePolizzaAssicurativa(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 59);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFileAttoCostNonAutonomo(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 71);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFileStatutoOrgNonAutonomo(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 72);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli updateFilePolizza(FileAllegatoDto fileAllegatoDto) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findById(fileAllegatoDto.getId());
		
		modulo.get().setNomeAllegato(fileAllegatoDto.getNomeFile());
		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo.get());
		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			apiFileService.deleteFile(modulo.get().getDocumentIdClient());
			
			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			
			// VERRA' ANNULLATA ANCHE LA CONVALIDAZIONE SE IL MODULO RISULTA CONVALIDATO
			if(saveModulo.getCompletato() == (Integer) 1) {
				saveModulo.setValidato(0);
				saveModulo.setCompletato(0);
			}
			
			statoModuliRichiestaFigliRepository.save(saveModulo);
			
			// RIMOZIONE CONVALIDAZIONE ATTO RIEP.
			Optional<StatoModuliRichiestaFigli> moduloAtto = statoModuliRichiestaFigliRepository
															 .findByIdModuloAndIdRichiesta((long) 3, fileAllegatoDto.getIdRichiesta());
			if(moduloAtto.isPresent() && moduloAtto.get().getCompletato() == (Integer) 1) {
				moduloAtto.get().setValidato(0);
				moduloAtto.get().setCompletato(0);
				statoModuliRichiestaFigliRepository.save(moduloAtto.get());
			}
			
			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli updateFileModulo(FileAllegatoDto fileAllegatoDto) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findById(fileAllegatoDto.getId());
		
		modulo.get().setNomeAllegato(fileAllegatoDto.getNomeFile());
		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo.get());
		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());
			//apiFileService.deleteFile(modulo.get().getDocumentIdClient());
			saveModulo.setDataInserimento(new Date());
			
			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			
			// VERRA' ANNULLATA ANCHE LA CONVALIDAZIONE SE IL MODULO RISULTA CONVALIDATO
			if(saveModulo.getCompletato() == (Integer) 1) {
				saveModulo.setValidato(0);
				saveModulo.setCompletato(0);
			}
			
			statoModuliRichiestaFigliRepository.save(saveModulo);
			
			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli updateFileSpeseDiMediazione(FileAllegatoDto fileAllegatoDto) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findById(fileAllegatoDto.getId());

		// Solo se la scelta è "Dichiarazione di adozione" verrà salvato il nome del
		// file
		if (fileAllegatoDto.getIdRiferimento() == 1) {
			modulo.get().setNomeAllegato(fileAllegatoDto.getNomeFile());
		} else {
			modulo.get().setNomeAllegato(null);
		}

		modulo.get().setIdRiferimento(fileAllegatoDto.getIdRiferimento());
		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo.get());

		// Solo se la scelta è "Dichiarazione di adozione" verrà caricato il file
		if (saveModulo.getIdRiferimento() == 1) {
			try {
				pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());
								
				apiFileService.deleteFile(modulo.get().getDocumentIdClient());
				
				String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
						+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
				MercurioFile infoFile = apiFileService.insertFile(path, 
										saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
				saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
				saveModulo.setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(saveModulo);		
				
			} catch (Exception e) {
				String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
						: "Si è verificato un errore con il caricamento del file nel documentale";
				throw new RuntimeException(message);
			}
		}
		return saveModulo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteFileSpeseDiMediazione(FileAllegatoDto fileAllegatoDto) {
		Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
				.findById(fileAllegatoDto.getId());

		String pathCostOdm = "src/main/resources/documentale_locale/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
				+ modulo.get().getIdModulo();
		File fileCostOdm = new File(pathCostOdm + "/" + modulo.get().getId() + ".pdf");
		try {
			// Verrà cancellato il file esistente
			fileCostOdm.delete();
			statoModuliRichiestaFigliRepository.delete(modulo.get());
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	
	/**********************************************************************************************************************/
	
	public HashMap<String, Object> getAllDisponibilitaA(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllDisponibilitaB(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllDisponibilitaC(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllFormazioneInizialeA(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllFormazioneInizialeB(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllFormazioneInizialeC(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllFormazioneSpecificaB(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 77, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllFormazioneSpecificaC(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 80, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllCertificazioneB(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> result = statoModuliRichiestaFigliRepository
				.getCertificatoByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, idAnagrafica, pageable);

		List<CertificazioneLingueDto> certDto = new ArrayList<CertificazioneLingueDto>();
		for (Object[] obj : result.getContent()) {
			CertificazioneLingueDto certTemp = new CertificazioneLingueDto();
//			certTemp.setFile(apiFileService.getFile((String)obj[12], (Integer)obj[13]));
//			certTemp.setFile(apiFileService.getFile(((MercurioFile) result.get()).getDocumentIdClient(), ((MercurioFile) result.get()).getContentId()));
			certTemp.setIdCertificazioneLingua((Long) obj[1]);
			certTemp.setDataCertificazione((Date) obj[2]);	//76	
			certTemp.setEnteCertificatore((String) obj[3].toString()); //77
			certTemp.setId((Long) obj[6]);
			certTemp.setDataInserimento((Date) obj[5]);
			certTemp.setNomeAllegato((String) obj[4]);
			certTemp.setCompletato((Integer) obj[7]);
			certTemp.setValidato((Integer) obj[8]);
			certTemp.setAnnullato((Integer) obj[9]);
			certDto.add(certTemp);
		}

		response.put("result", certDto);
		response.put("totalResult", result.getTotalElements());
		return response;
	}
	
	

	public HashMap<String, Object> getAllCertificazioneC(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<Object[]> result = statoModuliRichiestaFigliRepository
				.getCertificatoByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, idAnagrafica, pageable);
		
//		response.put("result", resultPage.getContent());
//		response.put("totalResult", resultPage.getTotalElements());
//		return response;
		
		List<CertificazioneLingueDto> certDto = new ArrayList<CertificazioneLingueDto>();
		for (Object[] obj : result.getContent()) {
			CertificazioneLingueDto certTemp = new CertificazioneLingueDto();
//			certTemp.setFile(apiFileService.getFile((String)obj[12], (Integer)obj[13]));
//			certTemp.setFile(apiFileService.getFile(((MercurioFile) result.get()).getDocumentIdClient(), ((MercurioFile) result.get()).getContentId()));
			certTemp.setIdCertificazioneLingua((Long) obj[1]);
			certTemp.setDataCertificazione((Date) obj[2]);	//76	
			certTemp.setEnteCertificatore((String) obj[3].toString()); //77
			certTemp.setId((Long) obj[6]);
			certTemp.setDataInserimento((Date) obj[5]);
			certTemp.setNomeAllegato((String) obj[4]);
			certTemp.setCompletato((Integer) obj[7]);
			certTemp.setValidato((Integer) obj[8]);
			certTemp.setAnnullato((Integer) obj[9]);
			certDto.add(certTemp);
		}

		response.put("result", certDto);
		response.put("totalResult", result.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllUlterioriRequisitiA(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}

	public HashMap<String, Object> getAllUlterioriRequisitiB(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public HashMap<String, Object> getAllUlterioriRequisitiC(Pageable pageable, Long idRichiesta, Long idAnagrafica) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertOnorabilitaA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 39);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);
		return saveModulo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertOnorabilitaB(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 44);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);
		return saveModulo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertOnorabilitaC(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 53);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);
		return saveModulo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertDisponibilitaA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 40);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertDisponibilitaB(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 45);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);
			
			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertDisponibilitaC(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 54);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFormazioneInizialeA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 41);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message); 
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFormazioneInizialeB(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 46);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFormazioneInizialeC(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 55);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFormazioneSpecificaB(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 77);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());

			return statoModuliRichiestaFigliRepository.save(saveModulo);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertFormazioneSpecificaC(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 80);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			
			return statoModuliRichiestaFigliRepository.save(saveModulo);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertCertificazioneB(FileAllegatoAppendiciDto fileAllegatoAppendiciDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		CertificazioneLingueStraniere certificazione = new CertificazioneLingueStraniere();				
		
		certificazione.setIdAnagrafica(fileAllegatoAppendiciDto.getIdAnagrafica());
		certificazione.setDataCertificazione(fileAllegatoAppendiciDto.getDataCertificazione());
		certificazione.setEnteCertificatore(fileAllegatoAppendiciDto.getEnteCertificatore());
		
		CertificazioneLingueStraniere saveCertificazione = certificazioneLingueStraniereRepository.save(certificazione);
		
		modulo.setIdModulo((long) 50);
		modulo.setIdRichiesta(fileAllegatoAppendiciDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoAppendiciDto.getNomeFile());
		modulo.setIdAnagrafica(fileAllegatoAppendiciDto.getIdAnagrafica());
		modulo.setIdRiferimento(saveCertificazione.getIdCertificazione());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);
		

		try {
			pdfService.checkValidLoadDocument(fileAllegatoAppendiciDto.getFile());

			String path = "/" + fileAllegatoAppendiciDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoAppendiciDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertCertificazioneC(FileAllegatoAppendiciDto fileAllegatoAppendiciDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		CertificazioneLingueStraniere certificazione = new CertificazioneLingueStraniere();				
		
		certificazione.setIdAnagrafica(fileAllegatoAppendiciDto.getIdAnagrafica());
		certificazione.setDataCertificazione(fileAllegatoAppendiciDto.getDataCertificazione());
		certificazione.setEnteCertificatore(fileAllegatoAppendiciDto.getEnteCertificatore());
		
		CertificazioneLingueStraniere saveCertificazione = certificazioneLingueStraniereRepository.save(certificazione);
		
		modulo.setIdModulo((long) 82);
		modulo.setIdRichiesta(fileAllegatoAppendiciDto.getIdRichiesta());
		modulo.setNomeAllegato(fileAllegatoAppendiciDto.getNomeFile());
		modulo.setIdAnagrafica(fileAllegatoAppendiciDto.getIdAnagrafica());
		modulo.setIdRiferimento(saveCertificazione.getIdCertificazione());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);
		

		try {
			pdfService.checkValidLoadDocument(fileAllegatoAppendiciDto.getFile());

			String path = "/" + fileAllegatoAppendiciDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoAppendiciDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteCertificatoB(Long idRichiesta, Long idCertificato) {
		Optional<CertificazioneLingueStraniere> certificato = certificazioneLingueStraniereRepository.findById(idCertificato);
		Optional<StatoModuliRichiestaFigli> moduloCertificato = statoModuliRichiestaFigliRepository
			.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 50, idRichiesta, idCertificato);
		
		certificazioneLingueStraniereRepository.delete(certificato.get());
		statoModuliRichiestaFigliRepository.delete(moduloCertificato.get());
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteCertificatoC(Long idRichiesta, Long idCertificato) {
		Optional<CertificazioneLingueStraniere> certificato = certificazioneLingueStraniereRepository.findById(idCertificato);
		Optional<StatoModuliRichiestaFigli> moduloCertificato = statoModuliRichiestaFigliRepository
			.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 82, idRichiesta, idCertificato);
		
		certificazioneLingueStraniereRepository.delete(certificato.get());
		statoModuliRichiestaFigliRepository.delete(moduloCertificato.get());
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertUlterioriRequisitiA(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 75);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			
			return statoModuliRichiestaFigliRepository.save(saveModulo);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertUlterioriRequisitiB(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 78);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			
			return statoModuliRichiestaFigliRepository.save(saveModulo);
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertUlterioriRequisitiC(FileAllegatoDto fileAllegatoDto) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		modulo.setIdModulo((long) 81);
		modulo.setIdRichiesta(fileAllegatoDto.getIdRichiesta());
		modulo.setIdAnagrafica(fileAllegatoDto.getIdAnagrafica());
		modulo.setNomeAllegato(fileAllegatoDto.getNomeFile());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

		try {
			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());

			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
			MercurioFile infoFile = apiFileService.insertFile(path, 
									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveModulo.setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
		} catch (Exception e) {
			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
					: "Si è verificato un errore con il caricamento del file nel documentale";
			throw new RuntimeException(message);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public StatoModuliRichiestaFigli insertSchedaMediatore(AnagraficaOdmDto anagraficaOdmDto, Long tipoMediatore) {
		StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
		switch(tipoMediatore.intValue()) {
			case 4: modulo.setIdModulo((long) 30); break;
			case 5: modulo.setIdModulo((long) 33); break;
			case 6: modulo.setIdModulo((long) 37); break;
			default: return null;
		}
		modulo.setIdRichiesta(anagraficaOdmDto.getIdRichiesta());
		modulo.setNomeAllegato(anagraficaOdmDto.getCodiceFiscale());
		modulo.setDataInserimento(new Date());

		StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo);

//		try {
//			pdfService.checkValidLoadDocument(fileAllegatoDto.getFile());
//
//			String path = "/" + fileAllegatoDto.getIdRichiesta() + "/odm/"
//					+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
//			MercurioFile infoFile = apiFileService.insertFile(path, 
//									saveModulo.getNomeAllegato(), fileAllegatoDto.getFile());
//			saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
//			saveModulo.setContentId(infoFile.getContentId());
//			statoModuliRichiestaFigliRepository.save(saveModulo);

			return saveModulo;
//		} catch (Exception e) {
//			String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
//					: "Si è verificato un errore con il caricamento del file nel documentale";
//			throw new RuntimeException(message);
//		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public CertificazioneLingueStraniere updateFileAppendici(FileAllegatoAppendiciDto fileAllegatoAppendiciDto) {
		CertificazioneLingueStraniere certificazione = certificazioneLingueStraniereRepository
				.findById(fileAllegatoAppendiciDto.getIdCertificazioneLingua()).get();
		
		certificazione.setDataCertificazione(fileAllegatoAppendiciDto.getDataCertificazione());
		certificazione.setEnteCertificatore(fileAllegatoAppendiciDto.getEnteCertificatore());
		
		CertificazioneLingueStraniere savedCertificazione = certificazioneLingueStraniereRepository
				.save(certificazione);
		
		if(fileAllegatoAppendiciDto.getFile() != null) {
			try {
				Optional<StatoModuliRichiestaFigli> modulo = statoModuliRichiestaFigliRepository
						.findById(fileAllegatoAppendiciDto.getId());
				modulo.get().setNomeAllegato(fileAllegatoAppendiciDto.getNomeFile());

				StatoModuliRichiestaFigli saveModulo = statoModuliRichiestaFigliRepository.save(modulo.get());
				
				pdfService.checkValidLoadDocument(fileAllegatoAppendiciDto.getFile());
	
				apiFileService.deleteFile(modulo.get().getDocumentIdClient());
				
				String path = "/" + fileAllegatoAppendiciDto.getIdRichiesta() + "/odm/"
						+ saveModulo.getIdModulo() + "/" + saveModulo.getId();
				MercurioFile infoFile = apiFileService.insertFile(path, 
										saveModulo.getNomeAllegato(), fileAllegatoAppendiciDto.getFile());
				saveModulo.setDocumentIdClient(infoFile.getDocumentIdClient());
				saveModulo.setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(saveModulo);
				
			} catch (Exception e) {
				String message = (e.getMessage() != null && e.getMessage().contains("-ErrorInfo")) ? e.getMessage()
						: "Si è verificato un errore con il caricamento del file nel documentale";
				throw new RuntimeException(message);
			}
		}
		
		return savedCertificazione;
	}

	public HashMap<String, Object> getAutocertificazioneApp(Pageable pageable, Long idRichiesta, Long idAnagrafica, Long idModulo) {
		HashMap<String, Object> response = new HashMap<>();
		Page<StatoModuliRichiestaFigli> resultPage = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica, pageable);
		response.put("result", resultPage.getContent());
		response.put("totalResult", resultPage.getTotalElements());
		return response;
	}
	
	public boolean getModuloIsInserito(Long idModulo, Long idRichiesta, Long idAnagrafica) {
		if(idModulo == 50 || idModulo == 82)
			return false;
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);
	}
	
	public boolean getPolizzaIsInserita(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiesta(idModulo, idRichiesta);
	}
	
	public boolean getSpeseIsInserito(Long idModulo, Long idRichiesta) {
		return statoModuliRichiestaFigliRepository.existsByIdModuloAndIdRichiesta(idModulo, idRichiesta);
	}

}
