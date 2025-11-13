package com.giustizia.mediazionecivile.service;

import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.RegistrazioneUtenteDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.model.ModalitaCostituzioneOrganismo;
import com.giustizia.mediazionecivile.model.NaturaGiuridica;
import com.giustizia.mediazionecivile.model.NaturaSocietaria;
import com.giustizia.mediazionecivile.model.OrdiniCollegi;
import com.giustizia.mediazionecivile.model.Qualifica;
import com.giustizia.mediazionecivile.model.RegioneProvince;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.RichiestaRegistrazioneUtente;
import com.giustizia.mediazionecivile.model.Sede;
import com.giustizia.mediazionecivile.model.SedeDetenzioneTitolo;
import com.giustizia.mediazionecivile.model.Societa;
import com.giustizia.mediazionecivile.model.SoggettoRichiedente;
import com.giustizia.mediazionecivile.model.SoggettoRichiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.model.TipoRichiedente;
import com.giustizia.mediazionecivile.model.TitoloAnagrafiche;
import com.giustizia.mediazionecivile.model.UserLogin;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.ComuneRepository;
import com.giustizia.mediazionecivile.repository.ModalitaCostituzioneOrganismoRepository;
import com.giustizia.mediazionecivile.repository.NaturaGiuridicaRepository;
import com.giustizia.mediazionecivile.repository.NaturaSocietariaRepository;
import com.giustizia.mediazionecivile.repository.OrdiniCollegiRepository;
import com.giustizia.mediazionecivile.repository.QualificaRepository;
import com.giustizia.mediazionecivile.repository.RegioniProvinceRepository;
import com.giustizia.mediazionecivile.repository.RichiestaRegistrazioneUtenteRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SedeDetenzioneTitoloRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.SocietaRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiedenteRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;
import com.giustizia.mediazionecivile.repository.TipoRichiedenteRepository;
import com.giustizia.mediazionecivile.repository.TitoloAnagraficheRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;

@Service
public class UserService {
	@Autowired
	RichiestaRegistrazioneUtenteRepository richiestaRegistrazioneUtenteRepository;
	@Autowired
	UserLoginRepository userLoginRepository;
	

	
	@Transactional(rollbackFor = Exception.class)
	public RichiestaRegistrazioneUtente registrazioneUtente(RegistrazioneUtenteDto registrazioneUtenteDto) {
		
		if(userLoginRepository.findByCodiceFiscale(registrazioneUtenteDto.getCodiceFiscale()) != null) {
			throw new RuntimeException("-ErrorInfo Il codice fiscale già è stato associata ad un utente o a una richiesta di registrazione");
		}
		
		UserLogin userLogin = new UserLogin();
		userLogin.setCognome(registrazioneUtenteDto.getCognome());
		userLogin.setNome(registrazioneUtenteDto.getNome());
		userLogin.setCodiceFiscale(registrazioneUtenteDto.getCodiceFiscale());
		// Di default sarà Rappresentante Legale
		userLogin.setIdRuolo(6);
		userLogin.setEmail(registrazioneUtenteDto.getEmail());
		userLogin.setIsAbilitato(2);
		userLogin.setDataCreazione(new Date());
		userLogin.setDataModifica(new Date());
		UserLogin userLoginUpdate = userLoginRepository.save(userLogin);
		
		RichiestaRegistrazioneUtente richiestaRegistrazione = new RichiestaRegistrazioneUtente();
		richiestaRegistrazione.setIdUserLogin(userLoginUpdate.getId());
		richiestaRegistrazione.setEmail(registrazioneUtenteDto.getEmail());
		richiestaRegistrazione.setPec(registrazioneUtenteDto.getPec());
		richiestaRegistrazione.setpIva(registrazioneUtenteDto.getpIva());
		richiestaRegistrazione.setIdRuolo(6); 
		richiestaRegistrazione.setRichiestaIscrizione(registrazioneUtenteDto.getTipoRichiesta());
		richiestaRegistrazione.setRagioneSociale(registrazioneUtenteDto.getRagioneSociale());
		richiestaRegistrazione.setDataInserimento(new Date());
		return richiestaRegistrazioneUtenteRepository.save(richiestaRegistrazione);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void temporaneo() {
		try {
	/*	    ClassPathResource resource = new ClassPathResource("documentale_locale/modulistica_domanda/polizza_assicurativa.pdf");
		    InputStream inputStreamProva = resource.getInputStream();
		    
		    MercurioFile infoFile;
		    
		    OrdiniCollegi collegi = new OrdiniCollegi();
		    collegi.setIdOrdiniCollegi(1L);
		    collegi.setDescrizione("Prova collegio");
		    
		    ordiniCollegiRepository.save(collegi);
		    
	        // PASSAGGIO SOCIETA CON CREAZIONE RICHIESTA
	        Societa newSocieta = new Societa();
	        newSocieta.setPartitaIva("50288330942");
	        newSocieta.setCodiceFiscaleSocieta("50288330942");
	        newSocieta.setRagioneSociale("prova");
	        societaRepository.save(newSocieta);
	        
	        TipoRichiedente richiedente = new TipoRichiedente();
	        richiedente.setId(1L);
	        richiedente.setDescrizione("Privato");
	        richiedente.setTipo("Privato");
	        richiedente.setCodice("1");
	        tipoRichiedenteRepository.save(richiedente);
	        
	        Richiesta richiesta = new Richiesta();
	        richiesta.setIdRichiesta(1L);
	        richiesta.setIdTipoRichiedente(1L);
	        richiesta.setIdTipoRichiesta(1);
	        richiesta.setDataRichiesta(new Date());
	        
	        // PASSAGGIO MODULO DOMANDA CON CONVALIDZIONE MODULO
			richiesta.setDenominazioneOdm("Prova denominazione");
	        
			StatoModuliRichiestaFigli statoModuloNew = new StatoModuliRichiestaFigli();
			statoModuloNew.setIdModulo((long)1);
			statoModuloNew.setIdRichiesta(1L);
			statoModuloNew.setDataInserimento(new Date());
			statoModuloNew.setCompletato(1);
			statoModuliRichiestaFigliRepository.save(statoModuloNew);
	        
	        // APRIRE MENU CON QUESTA PATH UNA VOLTA CHE SI STA SULLA PAGINA SOCIETA
	        // https://localhost:4200/organismiDiMediazione?vistaMenu=domanda_di_iscrizione&idRichiesta=1
			
			// PASSAGGIO SEZIONE 1
			SoggettoRichiedente SogRichiedente = new SoggettoRichiedente();
			SogRichiedente.setId(1L);
			SogRichiedente.setSoggettoRichiedente("SCELTA PROVA");
			soggettoRichiedenteRepository.save(SogRichiedente);
			
			NaturaGiuridica naturaGiuridica = new NaturaGiuridica();
			naturaGiuridica.setIdNaturaGiuridica(1L);
			naturaGiuridica.setDescrizione("Prova Giuridica");
			naturaGiuridicaRepository.save(naturaGiuridica);
			
			richiesta.setDataAttoCosti(new Date());
			richiesta.setDataStatutoVig(new Date());
			richiesta.setCodFiscSocieta("PROVACODF");
			richiesta.setPIva("50288330942");
			richiesta.setIdNaturaSoc(1L);
			richiesta.setIdSoggRichiedente(1L);
			richiesta.setAutonomo(0);
			richiesta.setOggettoSociale("Prova oggetto sociale");
			richiesta.setIstitutoEntePub(1);
			richiesta.setDenominaOdmPub("Prova");
			richiesta.setIdNaturaGiu(1L);

			richiesteRepository.save(richiesta);
			
		    NaturaSocietaria naturaSocietaria = new NaturaSocietaria();
		    naturaSocietaria.setIdNaturaSoc(1L);
		    naturaSocietaria.setDescrizione("Società per azioni");
		    naturaSocietaria.setDescrizioneBreve("S.P.A");
		    naturaSocietariaRepository.save(naturaSocietaria);
		    
		    Qualifica qualifica = new Qualifica();
		    qualifica.setIdQualifica(1L);
		    qualifica.setQualifica("Prova qualifica");
		    qualificaRepository.save(qualifica);*/
		    
		/*    Qualifica qualifica2 = new Qualifica();
		    qualifica2.setIdQualifica(2L);
		    qualifica2.setQualifica("Responsabile dell'Organismo");
		    qualificaRepository.save(qualifica2);
		    
		    Qualifica qualifica3 = new Qualifica();
		    qualifica3.setIdQualifica(3L);
		    qualifica3.setQualifica("Componente organo Amministrazione");
		    qualificaRepository.save(qualifica3);
		    
		    Qualifica qualifica4 = new Qualifica();
		    qualifica4.setIdQualifica(4L);
		    qualifica4.setQualifica("Socio");
		    qualificaRepository.save(qualifica4);
		    
		    Qualifica qualifica5 = new Qualifica();
		    qualifica5.setIdQualifica(5L);
		    qualifica5.setQualifica("Associato");
		    qualificaRepository.save(qualifica5);
		    
		    Qualifica qualifica6 = new Qualifica();
		    qualifica6.setIdQualifica(6L);
		    qualifica6.setQualifica("Altro");
		    qualificaRepository.save(qualifica6);
		    


			RegioneProvince regioneProvince = new RegioneProvince();
			regioneProvince.setCodiceProvincia(1);
			regioneProvince.setCodiceRegione(1);
			regioneProvince.setNomeProvincia("Milano");
			regioneProvince.setNomeRegione("Lombardia");
			regioneProvince.setSiglaProvincia("MI");
			provinceRepository.save(regioneProvince); 
		    
			Comune comune = new Comune();

			RegioneProvince regioneProvince = provinceRepository.findById(1L).orElse(null); // Assicurati di trovare la RegioneProvince corretta
			comune.setRegioneProvince(regioneProvince);
			
			comune.setIdCodComune(1L);
			comune.setCodiceRegione(1L);
			comune.setCodiceProvincia(1L);
			comune.setNomeComune("Imola");
			comuneRepository.save(comune);
			
*/
		    
			
		/*	AnagraficaOdm anagraficaOdm = new AnagraficaOdm();
			anagraficaOdm.setIdTitoloAnagrafica(1L);
			anagraficaOdm.setCognome("Prova");
			anagraficaOdm.setNome("Marco");
			// NASCITA
			anagraficaOdm.setSesso("M");
			anagraficaOdm.setDataNascita(new Date());
			anagraficaOdm.setStatoNascita("Italia");
			anagraficaOdm.setIdComuneNascita(1L);
			anagraficaOdm.setCodiceFiscale("MAILSSREMO22");
			anagraficaOdm.setCittadinanza("Italiana");
			anagraficaOdm.setComuneNascitaEstero(null);
			// RESIDENZA
			anagraficaOdm.setStatoResidenza("Germania");
			anagraficaOdm.setIdComuneResidenza(null);
			anagraficaOdm.setIndirizzo("indirizzo prova");
			anagraficaOdm.setNumeroCivico("44S");
			anagraficaOdm.setCap("00121");
			anagraficaOdm.setComuneResidenzaEstero("Berlino");
			// DOMICILIO
			anagraficaOdm.setStatoDomicilio("Germania");
			anagraficaOdm.setIdComuneDomicilio(null);
			anagraficaOdm.setIndirizzoDomicilio("Via della prova");
			anagraficaOdm.setNumeroCivicoDomicilio("33");
			anagraficaOdm.setCapDomicilio("00123");
			anagraficaOdm.setComuneDomicilioEstero("Prova");
			// CONTATTI
			anagraficaOdm.setIndirizzoPec("provaPec@li.it");
			anagraficaOdm.setIndirizzoEmail("email@lini.it");
			
			anagraficaOdm.setIdQualifica((long)1);;
	
			AnagraficaOdm anagraficaOdmSave = anagraficaOdmRepository.save(anagraficaOdm);
			Optional<SoggettoRichiesta> sogRichiesta = Optional.of(new SoggettoRichiesta());
			sogRichiesta.get().setIdRichiesta(1L);
			sogRichiesta.get().setIdAnagrafica(anagraficaOdmSave.getIdAnagrafica());
			soggettoRRepository.save(sogRichiesta.get());
			
			// PASSAGGIO SEDI
			SedeDetenzioneTitolo sedeDetenzioneTitolo = new SedeDetenzioneTitolo();
			sedeDetenzioneTitolo.setIdTitoloDetenzione(1L);
			sedeDetenzioneTitolo.setDescrizione("Prova detenzione");
			sedeDetenzioneTitoloRepository.save(sedeDetenzioneTitolo);
			
			Sede sede = new Sede();
			sede.setSedeLegale('1');
			
			sede.setIdRichiesta(1L);
			sede.setIndirizzo("prova indirizzo");
			sede.setNumeroCivico("34");
			sede.setCap("00222");
			sede.setIdComune(1L);
			sede.setTelefono("349493");
			sede.setFax("prova fax");
			sede.setPec("prova@pec.it");
			sede.setEmail("prova@email.it");
			sede.setIdTitoloDefinizione(1L);
			sede.setDurataContratto("3");
			sede.setDataContratto(new Date());
			sede.setStrutturaOrgSegreteria("prova 55");
			sede.setSitoWebSede("prova.sito.it");
			sede.setRegistrazione("registrazione");
			sede.setDataInserimentoSede(new Date());
	
			Sede sedeUpdate = sedeRepository.save(sede);
			
			StatoModuliRichiestaFigli newModuloSedi = new StatoModuliRichiestaFigli();
			newModuloSedi.setIdModulo((long) 68);
			newModuloSedi.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newModuloSedi.setDataInserimento(new Date());
			newModuloSedi.setCompletato(1);
			Optional<StatoModuliRichiestaFigli> moduloSedi = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloSedi));
		
			StatoModuliRichiestaFigli newModuloPlanimetria = new StatoModuliRichiestaFigli();
			newModuloPlanimetria.setIdModulo((long) 69);
			newModuloPlanimetria.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newModuloPlanimetria.setIdRiferimento(sedeUpdate.getIdSede());
			newModuloPlanimetria.setNomeAllegato("prova_nome");
			newModuloPlanimetria.setDataInserimento(new Date());
			Optional<StatoModuliRichiestaFigli> moduloPlanimetria = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloPlanimetria));
		
			StatoModuliRichiestaFigli newModuloCopiaContratto = new StatoModuliRichiestaFigli();
			newModuloCopiaContratto.setIdModulo((long) 70);
			newModuloCopiaContratto.setIdRichiesta(sedeUpdate.getIdRichiesta());
			newModuloCopiaContratto.setIdRiferimento(sedeUpdate.getIdSede());
			newModuloCopiaContratto.setNomeAllegato("prova_nome");
			newModuloCopiaContratto.setDataInserimento(new Date());
			Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloCopiaContratto));
			
			String pathPlanimetria = "/1/odm/" + moduloPlanimetria.get().getIdModulo();
			MercurioFile infoFile = apiFileService.insertFile(pathPlanimetria, 
									Long.toString(moduloPlanimetria.get().getId()), inputStreamProva.readAllBytes());
			moduloPlanimetria.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloPlanimetria.get().setContentId(infoFile.getContentId());
			moduloPlanimetria.get().setCompletato(1);
			
			String pathCopiaContratto = "/1/odm/" + moduloCopiaContratto.get().getIdModulo();
			MercurioFile infoFile2 = apiFileService.insertFile(pathCopiaContratto, 
									 Long.toString(moduloCopiaContratto.get().getId()), inputStreamProva.readAllBytes());
			moduloCopiaContratto.get().setDocumentIdClient(infoFile2.getDocumentIdClient());
			moduloCopiaContratto.get().setContentId(infoFile2.getContentId());
			moduloCopiaContratto.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloPlanimetria.get());						
			statoModuliRichiestaFigliRepository.save(moduloCopiaContratto.get());						
			
	
			Sede sedeOperativa = new Sede();
			BeanUtils.copyProperties(sede, sedeOperativa);
			sedeOperativa.setIdSede(null); // Imposta l'id su null 
			sedeOperativa.setSedeLegale('0');
			Sede sedeOperativaUpdate = sedeRepository.save(sedeOperativa);
	
			// VERRA' RIFATTO ANCHE L'INSERIMENTO DEGLI ALLEGATI
			moduloPlanimetria = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdRiferimento(
					(long) 69, sedeOperativaUpdate.getIdRichiesta(), sedeOperativaUpdate.getIdSede());
	
			moduloCopiaContratto = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdRiferimento(
					(long) 70, sedeOperativaUpdate.getIdRichiesta(), sedeOperativaUpdate.getIdSede());
	
			newModuloPlanimetria = new StatoModuliRichiestaFigli();
			newModuloPlanimetria.setIdModulo((long) 69);
			newModuloPlanimetria.setIdRichiesta(sedeOperativaUpdate.getIdRichiesta());
			newModuloPlanimetria.setIdRiferimento(sedeOperativaUpdate.getIdSede());
			newModuloPlanimetria.setNomeAllegato("prova_nome");
			newModuloPlanimetria.setDataInserimento(new Date());
			moduloPlanimetria = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloPlanimetria));
		
			newModuloCopiaContratto = new StatoModuliRichiestaFigli();
			newModuloCopiaContratto.setIdModulo((long) 70);
			newModuloCopiaContratto.setIdRichiesta(sedeOperativaUpdate.getIdRichiesta());
			newModuloCopiaContratto.setIdRiferimento(sedeOperativaUpdate.getIdSede());
			newModuloCopiaContratto.setNomeAllegato("prova_nome");
			newModuloCopiaContratto.setDataInserimento(new Date());
			moduloCopiaContratto = Optional.of(statoModuliRichiestaFigliRepository.save(newModuloCopiaContratto));
	
			pathPlanimetria = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloPlanimetria.get().getIdModulo();
			infoFile = apiFileService.insertFile(pathPlanimetria, 
									Long.toString(moduloPlanimetria.get().getId()), inputStreamProva.readAllBytes());
			moduloPlanimetria.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloPlanimetria.get().setContentId(infoFile.getContentId());
			moduloPlanimetria.get().setCompletato(1);
			
			pathCopiaContratto = "/" + sedeUpdate.getIdRichiesta() + "/odm/" + moduloCopiaContratto.get().getIdModulo();
			infoFile2 = apiFileService.insertFile(pathCopiaContratto, 
									 Long.toString(moduloCopiaContratto.get().getId()), inputStreamProva.readAllBytes());
			moduloCopiaContratto.get().setDocumentIdClient(infoFile2.getDocumentIdClient());
			moduloCopiaContratto.get().setContentId(infoFile2.getContentId());
			moduloCopiaContratto.get().setCompletato(1);
			
			statoModuliRichiestaFigliRepository.save(moduloPlanimetria.get());						
			statoModuliRichiestaFigliRepository.save(moduloCopiaContratto.get());
			
			// PASSAGGIO SPESE DI MEDIAZIONE
			Optional<StatoModuliRichiestaFigli> moduloSpeseMed =  Optional.of(new StatoModuliRichiestaFigli());
			moduloSpeseMed.get().setIdModulo((long) 73);
			moduloSpeseMed.get().setIdRichiesta(1L);
			moduloSpeseMed.get().setNomeAllegato("nome_prova");
			moduloSpeseMed.get().setDataInserimento(new Date());
	
			// Servirà per capire quale selezione è stata scelta per la tabella
			moduloSpeseMed.get().setIdRiferimento(1L);
			StatoModuliRichiestaFigli saveSpeseMed = statoModuliRichiestaFigliRepository.save(moduloSpeseMed.get());
	
			// Solo se la scelta è "Dichiarazione di adozione" verrà caricato il file
			String path = "/1/odm/" + saveSpeseMed.getIdModulo();
			infoFile = apiFileService.insertFile(path, 
									Long.toString(saveSpeseMed.getId()), inputStreamProva.readAllBytes());
			saveSpeseMed.setDocumentIdClient(infoFile.getDocumentIdClient());
			saveSpeseMed.setContentId(infoFile.getContentId());
			saveSpeseMed.setCompletato(1);
			 
			statoModuliRichiestaFigliRepository.save(saveSpeseMed);

		
	        richiesteRepository.save(richiesta);
	        
		    // PASSAGGIO SEZIONE 3
		    TitoloAnagrafiche titoloAnagrafiche = new TitoloAnagrafiche();
		    titoloAnagrafiche.setIdTitoloAnagrafiche(1L);
		    titoloAnagrafiche.setDescrizione("descrizione prova");
		    titoloAnagraficheRepository.save(titoloAnagrafiche);
		    
		    //PASSAGGIO SEZIONE 4
			ModalitaCostituzioneOrganismo costituzioneOrganismo = new ModalitaCostituzioneOrganismo();
			costituzioneOrganismo.setId(1L);
			costituzioneOrganismo.setDescrizione("prova costituzione");
			modalitaCostituzioneOrganismoRepository.save(costituzioneOrganismo);*/
		} 
		catch (Exception e) {
			throw new RuntimeException("-ErrorInfo " + e.getMessage());
		}
		

	}
	
}
