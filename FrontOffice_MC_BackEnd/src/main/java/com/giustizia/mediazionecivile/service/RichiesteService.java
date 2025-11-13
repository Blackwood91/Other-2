package com.giustizia.mediazionecivile.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.AnagraficaOdmDto;
import com.giustizia.mediazionecivile.dto.DomandaIscrizioneOdmDto;
import com.giustizia.mediazionecivile.dto.PolizzaAssicurativaDto;
import com.giustizia.mediazionecivile.dto.RichiestaSocietaDto;
import com.giustizia.mediazionecivile.dto.SezionePrimaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneQuartaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneSecondaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SocietaDTO;
import com.giustizia.mediazionecivile.model.AlboMediatori;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.SoggettoRichiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;
import com.giustizia.mediazionecivile.projection.PolizzaAssicurativaProjection;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.RichiestaProjection;
import com.giustizia.mediazionecivile.projection.SezionePrimaDOMODMProjection;
import com.giustizia.mediazionecivile.projection.SezioneQuartaDomOdmProjection;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class RichiesteService {
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	StatusService convalidazioneService;

	public List<Richiesta> getAllRichiesta() {
		return richiesteRepository.findAll();
	}
	
	public RichiestaSocietaDto getRichiestaForSocieta(Long idRichiesta) {
        Object[] resultObject = (Object[]) richiesteRepository.findByIdRichiestaForRichiestaSocieta(idRichiesta);
        if(resultObject.length != 0) {
        	Object[] result = (Object[]) resultObject[0];
	        RichiestaSocietaDto richiestaSocieta = new RichiestaSocietaDto();
	        richiestaSocieta.setIdRichiesta((Long) result[0]);
	        richiestaSocieta.setDataRichiesta((Date) result[1]);
	        richiestaSocieta.setDataIscrAlbo((Date) result[2]);
	        richiestaSocieta.setIdTipoRichiesta((Integer) result[3]);
	        richiestaSocieta.setIdTipoRichiedente((Long) result[4]);
	        richiestaSocieta.setDenominazioneOdm((String) result[5]);
	        richiestaSocieta.setIdStato((Long) result[6]);
	        richiestaSocieta.setStatoRichiesta((String) result[7]);
	        
			return richiestaSocieta;
        }
        else {
        	return null;
        }
	}

	public RichiestaDomIscProjection getRichiestaDomIscr(Long idRichiesta) {
		return richiesteRepository.findProjectiondDomByIdRichiesta(idRichiesta);
	}

	public SezionePrimaDOMODMProjection getSezionePrimaDOMODMP(Long idRichiesta) {
		return richiesteRepository.findSezionePrimaDOMODMByIdRichiesta(idRichiesta);
	}

	public SezioneSecDomOdmProjection getSezioneSecondaDOMODMP(Long idRichiesta) {
		// PER TROVARE SOLO IL RAPPRESENTATE LEGALE NELL'ANAGRAFICHE
		return anagraficaOdmRepository.findByIdProjectionSezSecOdm(idRichiesta);
	}
	
	
//	public HashMap<String, Object> getAllRichiestePaged(Pageable pageable) {
//
//		HashMap<String, Object> response = new HashMap<>();        
//        Page<Object[]> resultPage = richiesteRepository.findAllPage(pageable);
//        List<Object[]> resultList = resultPage.getContent();
//		List<Richiesta> richiestaDto = new ArrayList<Richiesta>();
//		
//		for (Object[] obj : resultList) {
//			richiestaDto.add(new AnagraficaOdmDto(obj));
//		}
//
//        response.put("totalResult", resultPage.getTotalElements());
//		response.put("result", anagDto);
//		return response;
//	}

	
	public SezioneQuartaDomOdmDto getSezioneQuartaDOMODMP(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);

		SezioneQuartaDomOdmDto sezione4 = new SezioneQuartaDomOdmDto();
		sezione4.setIdRichiesta(richiesta.get().getIdRichiesta());
		sezione4.setNumCompoOrgAmm(richiesta.get().getNumCompoOrgAmm());
		sezione4.setNumCompoCompSoc(richiesta.get().getNumCompoCompSoc());
		sezione4.setDurataCarica(richiesta.get().getDurataCarica());
		sezione4.setIdModalitaCostOrgani(richiesta.get().getIdModalitaCostOrgani());
		sezione4.setDataCostituOrg(richiesta.get().getDataCostituOrg());
		sezione4.setAutonomo(richiesta.get().getAutonomo());
		sezione4.setNumPersonaleAdetto(richiesta.get().getNumPersonaleAdetto());
		sezione4.setFontiDiFinanziamento(richiesta.get().getFontiDiFinanziamento());
		sezione4.setDurataCostituzioneOrganismo(richiesta.get().getDurataCostituzioneOrganismo());
		sezione4.setModalitaGestioneContabile(richiesta.get().getModalitaGestioneContabile());
		sezione4.setNumMediatori(richiesta.get().getNumMediatori());
		sezione4.setNumMediatoriInter(richiesta.get().getNumMediatoriInter());
		sezione4.setNumMediatoriCons(richiesta.get().getNumMediatoriCons());
		
		Optional<AnagraficaOdm> respOrganismo = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)2);
		if(respOrganismo.isPresent()) {
			sezione4.setRespOrganismo(respOrganismo.get().getCognome() + " " + respOrganismo.get().getNome());
		}
		
		return sezione4;
	}
	
	public boolean richiestaNaturaIsAutonomo(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getAutonomo() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public PolizzaAssicurativaProjection getPolizzaAssicurativa(Long idRichiesta) {
		Optional<PolizzaAssicurativaProjection> polizza = richiesteRepository
				.findPolizzaAssicurativaDOMODMByIdRichiesta(idRichiesta);
		if (polizza.isPresent() == false) {
			return null;
		} else {
			return polizza.get();
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Richiesta updateDomandaIscODM(DomandaIscrizioneOdmDto domandaIscODM) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(domandaIscODM.getIdRichiesta());
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long)1, domandaIscODM.getIdRichiesta());

		if (richiesta.isPresent()) {
			richiesta.get().setDenominazioneOdm(domandaIscODM.getDenominazioneOdm());
			richiesta.get().setCognome(domandaIscODM.getCognome());
			richiesta.get().setNome(domandaIscODM.getNome());
			if(statoModulo.isPresent() == false) {
				StatoModuliRichiestaFigli statoModuloNew = new StatoModuliRichiestaFigli();
				statoModuloNew.setIdModulo((long)1);
				statoModuloNew.setIdRichiesta(domandaIscODM.getIdRichiesta());
				statoModuloNew.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(statoModuloNew);
			}
			else {
				statoModulo.get().setValidato(0);
				statoModulo.get().setCompletato(0);
				statoModuliRichiestaFigliRepository.save(statoModulo.get());
			}
			
			return richiesteRepository.save(richiesta.get());
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna richiesta associata");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Richiesta saveSezionePrima(SezionePrimaDomOdmDto sezionePrimaDto) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(sezionePrimaDto.getIdRichiesta());

		// LA RICHIESTA SARA' SEMPRE UN UPDATE
		if (richiesta.isPresent()) {
			richiesta.get().setDataAttoCosti(sezionePrimaDto.getDataAttoCosti());
			richiesta.get().setDataStatutoVig(sezionePrimaDto.getDataStatutoVig());
			richiesta.get().setCodFiscSocieta(sezionePrimaDto.getCodFiscSocieta());
			richiesta.get().setPIva(sezionePrimaDto.getPIva());
			richiesta.get().setIdNaturaSoc(sezionePrimaDto.getIdNaturaSoc());
			richiesta.get().setIdSoggRichiedente(sezionePrimaDto.getIdSoggettoRichiedente());
			richiesta.get().setAutonomo(sezionePrimaDto.getAutonomo());
			richiesta.get().setOggettoSociale(sezionePrimaDto.getOggettoSociale());
			richiesta.get().setIstitutoEntePub(sezionePrimaDto.getIstitutoEntePub());
			richiesta.get().setDenominaOdmPub(sezionePrimaDto.getDenominaOdmPub());
			richiesta.get().setIdNaturaGiu(sezionePrimaDto.getIdNaturaGiu());
			
			convalidazioneService.annullaConvalidazioneAttoRiepilogativoODM(sezionePrimaDto.getIdRichiesta());
			
			return richiesteRepository.save(richiesta.get());
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna richiesta associata");
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public Richiesta saveSezioneSeconda(SezioneSecondaDomOdmDto sezioneSecondaDto) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(sezioneSecondaDto.getIdRichiesta());
		Optional<AnagraficaOdm> anagraficaOdm;
		
		// LA RICHIESTA SARA' SEMPRE UN UPDATE
		if (richiesta.isPresent()) {
			anagraficaOdm = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(sezioneSecondaDto.getIdRichiesta());
			// IN CASO DI NUOVO INSERIMENTO ANAGRAFICA
			if(anagraficaOdm.isPresent() == false) {				
				anagraficaOdm = Optional.of(new AnagraficaOdm());
			}
			
			anagraficaOdm.get().setIdTitoloAnagrafica(sezioneSecondaDto.getIdTitoloAnagrafica());
			anagraficaOdm.get().setCognome(sezioneSecondaDto.getCognome());
			anagraficaOdm.get().setNome(sezioneSecondaDto.getNome());
			// NASCITA
			anagraficaOdm.get().setSesso(sezioneSecondaDto.getSesso());
			anagraficaOdm.get().setDataNascita(sezioneSecondaDto.getDataNascita());
			anagraficaOdm.get().setStatoNascita(sezioneSecondaDto.getStatoNascita());
			anagraficaOdm.get().setIdComuneNascita(sezioneSecondaDto.getIdComuneNascita());
			anagraficaOdm.get().setCodiceFiscale(sezioneSecondaDto.getCodiceFiscale());
			anagraficaOdm.get().setCittadinanza(sezioneSecondaDto.getCittadinanza());
			anagraficaOdm.get().setComuneNascitaEstero(sezioneSecondaDto.getComuneNascitaEstero());
			// RESIDENZA
			anagraficaOdm.get().setStatoResidenza(sezioneSecondaDto.getStatoResidenza());
			anagraficaOdm.get().setIdComuneResidenza(sezioneSecondaDto.getIdComuneResidenza());
			anagraficaOdm.get().setIndirizzo(sezioneSecondaDto.getIndirizzo());
			anagraficaOdm.get().setNumeroCivico(sezioneSecondaDto.getNumeroCivico());
			anagraficaOdm.get().setCap(sezioneSecondaDto.getCap());
			anagraficaOdm.get().setComuneResidenzaEstero(sezioneSecondaDto.getComuneResidenzaEstero());
			// DOMICILIO
			anagraficaOdm.get().setStatoDomicilio(sezioneSecondaDto.getStatoDomicilio());
			anagraficaOdm.get().setIdComuneDomicilio(sezioneSecondaDto.getIdComuneDomicilio());
			anagraficaOdm.get().setIndirizzoDomicilio(sezioneSecondaDto.getIndirizzoDomicilio());
			anagraficaOdm.get().setNumeroCivicoDomicilio(sezioneSecondaDto.getNumeroCivicoDomicilio());
			anagraficaOdm.get().setCapDomicilio(sezioneSecondaDto.getCapDomicilio());
			anagraficaOdm.get().setComuneDomicilioEstero(sezioneSecondaDto.getComuneDomicilioEstero());
			// CONTATTI
			anagraficaOdm.get().setIndirizzoPec(sezioneSecondaDto.getIndirizzoPec());
			anagraficaOdm.get().setIndirizzoEmail(sezioneSecondaDto.getIndirizzoEmail());
			
			anagraficaOdm.get().setIdQualifica((long)1);;

			// IN CASO DI NUOVO INSERIMENTO ANAGRAFICA
			if (anagraficaOdm.get().getIdAnagrafica() == null || anagraficaOdm.get().getIdAnagrafica() == 0) {
				// Se primo inserimento valorizzazione anche della colonna idAnagrafica nella
				// tabella sog.Richiesta
				AnagraficaOdm anagraficaOdmSave = anagraficaOdmRepository.save(anagraficaOdm.get());
				Optional<SoggettoRichiesta> sogRichiesta = Optional.of(new SoggettoRichiesta());
				sogRichiesta.get().setIdRichiesta(sezioneSecondaDto.getIdRichiesta());
				sogRichiesta.get().setIdAnagrafica(anagraficaOdmSave.getIdAnagrafica());
				soggettoRRepository.save(sogRichiesta.get());
			} else {
				anagraficaOdmRepository.save(anagraficaOdm.get());
			}
			
			convalidazioneService.annullaConvalidazioneAttoRiepilogativoODM(sezioneSecondaDto.getIdRichiesta());

			return richiesteRepository.save(richiesta.get());
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna richiesta associata");
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public Richiesta saveSezioneQuarta(SezioneQuartaDomOdmDto sezioneQuartaDomOdmDto) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(sezioneQuartaDomOdmDto.getIdRichiesta());
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long)3, sezioneQuartaDomOdmDto.getIdRichiesta());

		// LA RICHIESTA SARA' SEMPRE UN UPDATE
		if (richiesta.isPresent()) {
			Richiesta updateRichiesta = richiesta.get();
			
			updateRichiesta.setNumCompoOrgAmm(sezioneQuartaDomOdmDto.getNumCompoOrgAmm());
			updateRichiesta.setNumCompoCompSoc(sezioneQuartaDomOdmDto.getNumCompoCompSoc());
			updateRichiesta.setDurataCarica(sezioneQuartaDomOdmDto.getDurataCarica());
			updateRichiesta.setIdModalitaCostOrgani(sezioneQuartaDomOdmDto.getIdModalitaCostOrgani());
			updateRichiesta.setDataCostituOrg(sezioneQuartaDomOdmDto.getDataCostituOrg());
			updateRichiesta.setAutonomo(sezioneQuartaDomOdmDto.getAutonomo());
			updateRichiesta.setNumPersonaleAdetto(sezioneQuartaDomOdmDto.getNumPersonaleAdetto());
			updateRichiesta.setFontiDiFinanziamento(sezioneQuartaDomOdmDto.getFontiDiFinanziamento());
			updateRichiesta.setDurataCostituzioneOrganismo(sezioneQuartaDomOdmDto.getDurataCostituzioneOrganismo());
			updateRichiesta.setModalitaGestioneContabile(sezioneQuartaDomOdmDto.getModalitaGestioneContabile());
			updateRichiesta.setNumMediatori(sezioneQuartaDomOdmDto.getNumMediatori());
			updateRichiesta.setNumMediatoriInter(sezioneQuartaDomOdmDto.getNumMediatoriInter());
			updateRichiesta.setNumMediatoriCons(sezioneQuartaDomOdmDto.getNumMediatoriCons());
			
			// Nell'utima sezione dell'atto se non esiste lo stato modulo di riferimento di tutte le sezioni, verrà generato
			if(statoModulo.isPresent() == false) {
				StatoModuliRichiestaFigli statoModuloNew = new StatoModuliRichiestaFigli();
				statoModuloNew.setIdRichiesta(sezioneQuartaDomOdmDto.getIdRichiesta());
				statoModuloNew.setIdModulo((long)3);
				statoModuloNew.setDataInserimento(new Date());
				statoModuliRichiestaFigliRepository.save(statoModuloNew);
			}
			
		//	if(updateRichiesta.getNumMediatori() != richiesta.get().getNumMediatori())
		//		convalidazioneService.annullaAppendiceA(updateRichiesta.getIdRichiesta());
		//	if(updateRichiesta.getNumMediatoriInter() != richiesta.get().getNumMediatoriInter())
		//		convalidazioneService.annullaAppendiceB(updateRichiesta.getIdRichiesta());
		//	if(updateRichiesta.getNumMediatoriCons() != richiesta.get().getNumMediatoriCons())
		//		convalidazioneService.annullaAppendiceC(updateRichiesta.getIdRichiesta());
			//IN ATTESA DI FIX 
			convalidazioneService.annullaConvalidazioneAttoRiepilogativoODM(sezioneQuartaDomOdmDto.getIdRichiesta());

			return richiesteRepository.save(updateRichiesta);
		} else {
			throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna richiesta associata");
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public Richiesta saveDichiarazionePolizzaAss(PolizzaAssicurativaDto polizzaAssicurativaDto) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(polizzaAssicurativaDto.getIdRichiesta());
		Optional<StatoModuliRichiestaFigli> statoModuloPolizza = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 58, 
																 polizzaAssicurativaDto.getIdRichiesta());

		richiesta.get().setDataStipulaPoliz(polizzaAssicurativaDto.getDataStipulaPoliz());
		richiesta.get().setScadenzaPoliza(polizzaAssicurativaDto.getScadenzaPoliza());
		richiesta.get().setMassimaleAssic(polizzaAssicurativaDto.getMassimaleAssicurato());
		richiesta.get().setCompagniaAss(polizzaAssicurativaDto.getCompagniaAssicuratrice());
		
		if(statoModuloPolizza.isPresent() == false) {
			statoModuloPolizza = Optional.of(new StatoModuliRichiestaFigli());
			statoModuloPolizza.get().setIdRichiesta(polizzaAssicurativaDto.getIdRichiesta());
			statoModuloPolizza.get().setIdModulo((long) 58);
			statoModuloPolizza.get().setDataInserimento(new Date());
			statoModuliRichiestaFigliRepository.save(statoModuloPolizza.get());
		}
		else {
			statoModuloPolizza.get().setValidato(0);
			statoModuloPolizza.get().setCompletato(0);
			statoModuliRichiestaFigliRepository.save(statoModuloPolizza.get());
		}
		
		// ANNULLA CONVALIDAZIONE ATTO RIEP.
		Optional<StatoModuliRichiestaFigli> moduloAtto = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 3, 
				 polizzaAssicurativaDto.getIdRichiesta());
		if(moduloAtto.isPresent() && moduloAtto.get().getCompletato() == (Integer) 1) {
			moduloAtto.get().setValidato(0);
			moduloAtto.get().setCompletato(0);
			statoModuliRichiestaFigliRepository.save(moduloAtto.get());
		}
		
		return richiesteRepository.save(richiesta.get());
	}

	
	
}
