package com.giustizia.mediazionecivile.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.model.JobRichiesta;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.Sede;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.ComuneRepository;
import com.giustizia.mediazionecivile.repository.JobRichiestaRepository;
import com.giustizia.mediazionecivile.repository.ModalitaCostituzioneOrganismoRepository;
import com.giustizia.mediazionecivile.repository.ModuloRepository;
import com.giustizia.mediazionecivile.repository.NaturaGiuridicaRepository;
import com.giustizia.mediazionecivile.repository.NaturaSocietariaRepository;
import com.giustizia.mediazionecivile.repository.OrdiniCollegiRepository;
import com.giustizia.mediazionecivile.repository.QualificaRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;
import com.giustizia.mediazionecivile.security.UtenteLoggato;
import com.giustizia.mediazionecivile.utility.Moduli;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class FinalizzaService {
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
	ModuloRepository moduloRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	ComuneRepository comuneRepository;
	@Autowired
	NaturaGiuridicaRepository naturaGiuridicaRepository;
	@Autowired
	NaturaSocietariaRepository naturaSocietariaRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRichiestaRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	QualificaRepository qualificaRepository;
	@Autowired
	OrdiniCollegiRepository ordcollegiRepository;
	@Autowired
	JobRichiestaRepository jobRichiestaRepository;
	@Autowired
	UserLoginRepository userLoginRepository; 
	@Autowired
	ModalitaCostituzioneOrganismoRepository costituzioneOrganismoRepository;
	@Autowired
	ApiFileService apiFileService;
	@Autowired
	StatusService statusService;
	@Autowired
	PdfService pdfService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Transactional(rollbackFor = Exception.class)
	public void finalizzazioneRichiestaODM(Long idRichiesta) throws Exception  {
		
			if (statusService.checkFinalizza(idRichiesta) == false) {
				throw new RuntimeException("-ErrorInfo Per proseguire convalidare tutti i moduli");
			}
			
			saveFileModuloDomanda(idRichiesta);
			saveFileSchedeSediOperative(idRichiesta);
			saveFileAttoRiepOdm(idRichiesta);
			saveFileSchedeRapLegRspOrg(idRichiesta);
			saveFileAutocertificazioneRapLegRespOrg(idRichiesta);
			saveFileSchedeCompOrgAm(idRichiesta);
			saveFileAutocertificazioneReqOnoCompOrgAm(idRichiesta);
			saveFilePrestaSerOpe(idRichiesta);
			saveFileSchedeAppeA(idRichiesta);
			saveFileAutocertificazioneAppeA(idRichiesta);
			saveFileSchedeAppeB(idRichiesta);
			saveFileAutocertificazioneAppeB(idRichiesta);
			saveFileSchedeAppeC(idRichiesta);
			saveFileAutocertificazioneAppeC(idRichiesta);
			
			// AGGIORNAMENTO STATO RICHIESTA IN INVIATO SE NON ISCRITTA
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);

			if(richiesta.get().getIdStato() != 4) {				
				insertRichiestaJob(idRichiesta, "GENERAZIONE PDF", "ODM");
			}
			else {				
				insertRichiestaJob(idRichiesta, "RICHIESTA INTEGRAZIONE", "ODM");	
			}
	}
	
	public PdfDto downloadRichiestaODM(Long idRichiesta) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// INSERIMENTO DEL PRIMO DOCUMENTO OBBLIGATORIO PER NON FAR SCATENARE ERRORE ALL'INIZIALIZZAZIONE, VERRA RIMOSSA ALLA FINE
		PdfReader pdfReader = new PdfReader("documentale_locale/modulistica_domanda/blank_pdf.pdf");
		PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
		
		// INSERIMENTO PARTE DEL MODULO DOMANDA
		Optional<StatoModuliRichiestaFigli> moduloDomanda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 1, idRichiesta);
		
		if(moduloDomanda.get().getValidato() != (Integer) 1) {
			inserimentoFileByte(apiFileService.getFile(moduloDomanda.get().getDocumentIdClient(), moduloDomanda.get().getContentId()), pdfStamper);
		}
		
		// INSERIMENTO PARTE ATTO RIEPILOGATIVO
		Optional<StatoModuliRichiestaFigli> moduloAttoRiep = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		
		if(moduloAttoRiep.get().getValidato() != (Integer) 1) {
			inserimentoFileByte(apiFileService.getFile(moduloAttoRiep.get().getDocumentIdClient(), moduloAttoRiep.get().getContentId()), pdfStamper);
		}
		
		// INSERIMENTO FILE DOMANDA DI ISCRIZIONE
		ArrayList<Long> moduliFileDI = new ArrayList<Long>(
						Arrays.asList((long) 4, (long) 71, (long) 72, (long) 5, (long) 16, (long) 17, (long) 23));
		
		for(Long modulo : moduliFileDI) {
			Optional<StatoModuliRichiestaFigli> moduloFile = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta(modulo, idRichiesta);
			
			if(moduloFile.isEmpty() == false) {
				if(richiesta.get().getAutonomo() == (Integer) 0 && (modulo == 71 || modulo == 72)) {
					if(moduloFile.get().getValidato() != (Integer) 1) {
						// POTREBBE NON ESISTERE IL FILE SE AUTONOMO E' TRUE
						if(apiFileService.existFile(moduloFile.get().getDocumentIdClient(), moduloFile.get().getContentId()))
							inserimentoFileByte(apiFileService.getFile(moduloFile.get().getDocumentIdClient(), 
									moduloFile.get().getContentId()), pdfStamper);
					}
				}
				else {
					if(moduloFile.get().getValidato() != (Integer) 1) {
						inserimentoFileByte(apiFileService.getFile(moduloFile.get().getDocumentIdClient(), 
								moduloFile.get().getContentId()), pdfStamper);
					}
				}
			}
		}
		
		// INSERIMENTO PARTE RAP. LEGALE E RESPONSABILE DELL'ORGANISMO
		List<AnagraficaOdm> anagraficheRespOrg = anagraficaOdmRepository.findAllByIdRichiestaAndIdQualifica(idRichiesta, (long)2);
		Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
		List<AnagraficaOdm> anagrafiche = anagraficheRespOrg;
		Optional<AnagraficaOdm> anagraficaClone = Optional.empty();	
		boolean existClone = false;
				
		// CICLO PER LA GESTIONE DELLA LISTA A SECONDA DELL'ESISTENZA O NO DEL CLONE 
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			// CONDIZIONE SOLO PER IL RESPONSABILE DELL'ORGANISMO
			if(anagrafica.getIdQualifica() == 2) {
				// CONDIZIONE PER TROVARE LA SCHEDA CLONATA SE ESISTENTE 
				if(anagraficaRapLeg.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) {
					anagraficaClone = Optional.of(anagrafica);	
					existClone = true;
				}
			}
		}
		// SE NON ESISTE IL CLONE VERRA INSERITO IL RAP.LEGALE DENTRO LA LISTA DELLE ANAGRAFICHE
		if(existClone == false) {
			// VERRA' AGGIUNTO IL RAP. LEGALE CON IL RESP.ORGANISMO CLONE COME PRIMO RISULTATO DELLA LITA
			anagrafiche.add(0, anagraficaRapLeg.get());
		}
		else {
		    // SE ESISTE VERRA' SPOSTATO IL RAP. LEGALE CON IL RESP.ORGANISMO CLONE, COME PRIMO RISULTATO DELLA LITA
			anagrafiche.remove(anagraficaClone.get());
			anagrafiche.add(0, anagraficaClone.get());
		}
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			
			Optional<StatoModuliRichiestaFigli> moduloSchedaRapLeRespOr = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloSchedaRapLeRespOr.get().getValidato() != (Integer) 1) {
				// INSERIMENTO SCHEDA DEL RAP LEGALE O RESP ORGANISMO
				inserimentoFileByte(apiFileService.getFile(moduloSchedaRapLeRespOr.get().getDocumentIdClient(), 
									moduloSchedaRapLeRespOr.get().getContentId()), pdfStamper);
				// INSERIMENTO DEL DOCUMENTO
				Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
				inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(), 
									moduloDocumento.get().getContentId()), pdfStamper);
				// INSERIMENTO DELLA QUALIFICA DEL MEDIATORE
				if(anagrafica.getIdQualifica() != 1) { 
					Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
							.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
					inserimentoFileByte(apiFileService.getFile(moduloQualificaMed.get().getDocumentIdClient(), 
										moduloQualificaMed.get().getContentId()), pdfStamper);
				}
			}
			
		}
		// INSERIMENTO PARTE AUTOCERTIFICAZIONE DEL RAP LEGALE E DEL RESPONSABILE DELL'ORGANISMO
		// UTILIZZERA LA STESSA LISTA DELLE ANAGRAFICHE UTILIZZATA PER LA SCHEDA E I DOCUMENTI ASSOCIATI
		for (int a = 0; a < anagrafiche.size(); a++) {	
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloAttoReqOnora.get().getDocumentIdClient(), 
									moduloAttoReqOnora.get().getContentId()), pdfStamper);
			}
		}
		
		// INSERIMENTO PARTE SCHEDE COMPONENTI ORGANO DI AMMINISTRAZIONE
		List<AnagraficaOdm> anagraficheOrgAmm = anagraficaOdmRepository.getAllAnagraficaByIdRichiestaForEleCompOrgAm(idRichiesta);
		for (int a = 0; a < anagraficheOrgAmm.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 31, idRichiesta);
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagraficheOrgAmm.get(a).getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloScheda.get().getDocumentIdClient(), 
									moduloScheda.get().getContentId()), pdfStamper);
			}
			
			if(moduloDocumento.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(), 
									moduloDocumento.get().getContentId()), pdfStamper);
			}
			
	
		}
		
		for (int a = 0; a < anagraficheOrgAmm.size(); a++) {	
			// INSERIMENTO AUTOCERTIFICAZIONE APPENDICE A
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagraficheOrgAmm.get(a).getIdAnagrafica());
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloAutocertificazione.get().getDocumentIdClient(), 
									moduloAutocertificazione.get().getContentId()), pdfStamper);
			}
		}
		
		// INSERIMENTO PARTE APPENDICE PRESTATORI DI SERVIZIO
		Optional<StatoModuliRichiestaFigli> moduloElencoPDS = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 35, 
				   idRichiesta);

		if(moduloElencoPDS.get().getValidato() != (Integer) 1) {
			inserimentoFileByte(apiFileService.getFile(moduloElencoPDS.get().getDocumentIdClient(), 
								moduloElencoPDS.get().getContentId()), pdfStamper);
			
		    List<AnagraficaOdm> anagrafichePDS = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 3);
			for (int a = 0; a < anagrafichePDS.size(); a++) {	
				Optional<StatoModuliRichiestaFigli> moduloDocumentoPDS = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, anagrafichePDS.get(a).getIdAnagrafica());
				inserimentoFileByte(apiFileService.getFile(moduloDocumentoPDS.get().getDocumentIdClient(), 
									moduloDocumentoPDS.get().getContentId()), pdfStamper);
			}
		}

		// INSERIMENTO PARTE SCHEDE APPENDICE A
		List<AnagraficaOdm> anagraficheAppA = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);
		for (int a = 0; a < anagraficheAppA.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 38, idRichiesta, anagraficheAppA.get(a).getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloScheda.get().getDocumentIdClient(), 
									moduloScheda.get().getContentId()), pdfStamper);
			}
		}
		
		for (int a = 0; a < anagraficheAppA.size(); a++) {	
			// INSERIMENTO AUTOCERTIFICAZIONE APPENDICE A
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, anagraficheAppA.get(a).getIdAnagrafica());
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloAutocertificazione.get().getDocumentIdClient(), 
									moduloAutocertificazione.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO DISPONIBILITA'
			Optional<StatoModuliRichiestaFigli> moduloDisponibilita = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 40, idRichiesta, anagraficheAppA.get(a).getIdAnagrafica());
			if(moduloDisponibilita.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloDisponibilita.get().getDocumentIdClient(), 
									moduloDisponibilita.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO FORMAZIONE
			Optional<StatoModuliRichiestaFigli> moduloFormazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 41, idRichiesta, anagraficheAppA.get(a).getIdAnagrafica());
			if(moduloFormazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloFormazione.get().getDocumentIdClient(), 
									moduloFormazione.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO CERTIFICAZIONE ULTERIORE REQUISITI
			Optional<StatoModuliRichiestaFigli> moduloCertUltReq = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 75, idRichiesta, anagraficheAppA.get(a).getIdAnagrafica());
			if(moduloCertUltReq.isPresent() == true && moduloCertUltReq.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloCertUltReq.get().getDocumentIdClient(), 
									moduloCertUltReq.get().getContentId()), pdfStamper);	
			}
		}
		
		// INSERIMENTO PARTE SCHEDE APPENDICE B
		List<AnagraficaOdm> anagraficheAppB = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);
		for (int a = 0; a < anagraficheAppB.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
															   (long) 43, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloScheda.get().getDocumentIdClient(), 
									moduloScheda.get().getContentId()), pdfStamper);
			}
		}
		// INSERIMENTO AUTOCERTIFICAZIONE APPENDICE B
		for (int a = 0; a < anagraficheAppB.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloAutocertificazione.get().getDocumentIdClient(), 
									moduloAutocertificazione.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO DISPONIBILITA'
			Optional<StatoModuliRichiestaFigli> moduloDisponibilita = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 45, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			if(moduloDisponibilita.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloDisponibilita.get().getDocumentIdClient(), 
									moduloDisponibilita.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO FORMAZIONE
			Optional<StatoModuliRichiestaFigli> moduloFormazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 46, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			if(moduloFormazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloFormazione.get().getDocumentIdClient(), 
									moduloFormazione.get().getContentId()), pdfStamper);
			}
			// INSERIMENTO PARTE MODULO CERTIFICAZIONE ULTERIORE REQUISITI
			Optional<StatoModuliRichiestaFigli> moduloCertUltReq = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 78, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			if(moduloCertUltReq.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloCertUltReq.get().getDocumentIdClient(), 
									moduloCertUltReq.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULI CERTIFICAZIONI LINGUISTICHE
			List<StatoModuliRichiestaFigli> moduliCertLing = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 50, idRichiesta, anagraficheAppB.get(a).getIdAnagrafica());
			
			for(StatoModuliRichiestaFigli moduloCertLing : moduliCertLing) {
				if(moduloCertLing.getValidato() != (Integer) 1) {
					inserimentoFileByte(apiFileService.getFile(moduloCertLing.getDocumentIdClient(), 
										moduloCertLing.getContentId()), pdfStamper);
				}
			}
		
		}
		
		// INSERIMENTO PARTE SCHEDE APPENDICE C
		List<AnagraficaOdm> anagraficheAppC = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);
		for (int a = 0; a < anagraficheAppC.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
															   (long) 52, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());

			if(moduloScheda.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloScheda.get().getDocumentIdClient(), 
									moduloScheda.get().getContentId()), pdfStamper);
			}
		}
		// INSERIMENTO AUTOCERTIFICAZIONE APPENDICE C
		for (int a = 0; a < anagraficheAppC.size(); a++) {	
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());		
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloAutocertificazione.get().getDocumentIdClient(), 
									moduloAutocertificazione.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO DISPONIBILITA'
			Optional<StatoModuliRichiestaFigli> moduloDisponibilita = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 54, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());
			if(moduloDisponibilita.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloDisponibilita.get().getDocumentIdClient(), 
									moduloDisponibilita.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO FORMAZIONE
			Optional<StatoModuliRichiestaFigli> moduloFormazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 55, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());
			if(moduloFormazione.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloFormazione.get().getDocumentIdClient(), 
									moduloFormazione.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULO CERTIFICAZIONE ULTERIORE REQUISITI
			Optional<StatoModuliRichiestaFigli> moduloCertUltReq = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 81, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());
			if(moduloCertUltReq.get().getValidato() != (Integer) 1) {
				inserimentoFileByte(apiFileService.getFile(moduloCertUltReq.get().getDocumentIdClient(), 
									moduloCertUltReq.get().getContentId()), pdfStamper);
			}
			
			// INSERIMENTO PARTE MODULI CERTIFICAZIONI LINGUISTICHE
			List<StatoModuliRichiestaFigli> moduliCertLing = statoModuliRichiestaFigliRepository
					.findAllByIdModuloAndIdRichiestaAndIdAnagrafica((long) 82, idRichiesta, anagraficheAppC.get(a).getIdAnagrafica());
			
			for(StatoModuliRichiestaFigli moduloCertLing : moduliCertLing) {
				if(moduloCertLing.getValidato() != (Integer) 1) {
					inserimentoFileByte(apiFileService.getFile(moduloCertLing.getDocumentIdClient(), 
										moduloCertLing.getContentId()), pdfStamper);
				}
			}
		}
		
		pdfStamper.close();

		// RIMOZIONE DELLA PRIMA PAGINE DEL DOCUMENTO LAVORATO
		com.itextpdf.kernel.pdf.PdfReader pdfReaderNew = new com.itextpdf.kernel.pdf.PdfReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));        
        ByteArrayOutputStream byteArrayOutputStreamNew = new ByteArrayOutputStream();        
        com.itextpdf.kernel.pdf.PdfWriter pdfWriterNew = new com.itextpdf.kernel.pdf.PdfWriter(byteArrayOutputStreamNew);        
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(pdfReaderNew, pdfWriterNew);
        // RIMOZIONE DELLA PAGINA BIANCA
        pdfDocument.removePage(1);
        
        pdfDocument.close();
        
		return new PdfDto("richiesta", byteArrayOutputStreamNew.toByteArray());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void inviaRichiestaODM(Long idRichiesta, byte[] filePdf, String nomeFile) throws IOException {
		
		if(pdfService.isPdfFrimato(filePdf) == false) {
			throw new RuntimeException("-ErrorInfo Per proseguire il file deve essere firmato");
		}
		
		// SALVATAGGIO IN UN PRIMO MOMENTE SENZA GLI ID DI RIFERIMENTO A MERCURIO	
		Optional<StatoModuliRichiestaFigli> moduloRicInviata = Optional.of(new StatoModuliRichiestaFigli());
		moduloRicInviata.get().setIdModulo((long) 85);
		moduloRicInviata.get().setIdRichiesta(idRichiesta);
		moduloRicInviata.get().setDataInserimento(new Date());	
		StatoModuliRichiestaFigli saveModuloRicInviata = statoModuliRichiestaFigliRepository.save(moduloRicInviata.get());

		String path = "/" + idRichiesta + "/odm/" + saveModuloRicInviata.getIdModulo() + "/" + saveModuloRicInviata.getId();
		MercurioFile infoFile = apiFileService.insertFile(path, saveModuloRicInviata.getNomeAllegato(), filePdf);
		saveModuloRicInviata.setDocumentIdClient(infoFile.getDocumentIdClient());
		saveModuloRicInviata.setContentId(infoFile.getContentId());
		saveModuloRicInviata.setNomeAllegato(nomeFile);
		statoModuliRichiestaFigliRepository.save(saveModuloRicInviata);
		
		// AGGIORNAMENTE RICHIESTA JOB STATO RICHIESTA E DATA_ULTIMO_STATO
		Optional<JobRichiesta> jobRichiesta = jobRichiestaRepository.
				findFirstByIdRichiestaAndTipoRichiestaOrderByDataOraRichiestaDesc(idRichiesta, "ODM");
		jobRichiesta.get().setStatoJob("chiusa");
		jobRichiesta.get().setDataUltimoStato(new Date());
		jobRichiestaRepository.save(jobRichiesta.get());
		
		// AGGIORNAMENTO STATO RICHIESTA IN INVIATO
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);	
		richiesta.get().setIdStato((long) 2);
		richiesteRepository.save(richiesta.get());
	}
	
	public void saveFileModuloDomanda(Long idRichiesta) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloDomanda = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 1, idRichiesta);
		
		if(moduloDomanda.get().getValidato() != (Integer) 1) {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);	
			// PATH DOCUMENTO IN SISTEMA
			String inputFile = "documentale_locale/modulistica_domanda/domanda_di_iscrizione.pdf";
			// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
			PdfReader pdfReader = new PdfReader(inputFile);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	
			// SET FONT DA INSERIRE
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			// Aggiungi testo sotoscritto
			addTextPdf(pdfStamper, 1, baseFont, 125, 560, richiesta.get().getCognome() + " " + richiesta.get().getNome());
			// Aggiungi testo denominazione o ragione sociale)
			addTextPdf(pdfStamper, 1, baseFont, 374, 509, richiesta.get().getDenominazioneOdm());
			
			// INSERIMENTO CHECK BASATOSI SU SPESE DI MEDIAZIONE
			Optional<StatoModuliRichiestaFigli> moduloSpeseMed = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiesta(Moduli.SEPESE_DI_MEDIAZIONE.idModulo, idRichiesta);
			
			// IL MODULO IN UN PRIMO MOMENTO POTREBEE NON ESISTERE O ESSERE VALORIZZATO
			if( moduloSpeseMed.get().getIdRiferimento() == Long.valueOf(1)) {
				addTextPdf(pdfStamper, 1, baseFont, 121, 313, "X");
			}
			else {
				addTextPdf(pdfStamper, 1, baseFont, 121, 295, "X");
			}
			
			
			String dataDocumento = dateFormat.format(new Date());
			addTextPdf(pdfStamper, 2, baseFont, 100, 294, dataDocumento);
			
			pdfStamper.close();
			
			apiFileService.deleteFile(moduloDomanda.get().getDocumentIdClient());
			
			String path = "/" + idRichiesta + "/odm/" + moduloDomanda.get().getIdModulo() 
					    + "/" + moduloDomanda.get().getId();
			MercurioFile infoFile = apiFileService.insertFile(path, moduloDomanda.get().getNomeAllegato(), 
									byteArrayOutputStream.toByteArray());
			moduloDomanda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloDomanda.get().setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(moduloDomanda.get());
		}
	}
	
	private void saveFileSchedeSediOperative(Long idRichiesta) throws Exception {
		List<Sede> sedi = sedeRepository.findByIdRichiestaAndSedeLegale(idRichiesta, '0');
		
		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

		for (int s = 0; s < sedi.size(); s++) {
			Optional<StatoModuliRichiestaFigli> schedaSede = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 68, sedi.get(s).getIdRichiesta(),
							sedi.get(s).getIdSede());
			
			if(schedaSede.get().getValidato() != (Integer) 1) {
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(s == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/sezione_3/altre_sedi_operative_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/sezione_3/altre_sedi_operative.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

				// Inserimento dati db al file per posizione
				Optional<Comune> comune = comuneRepository.findById(sedi.get(s).getIdComune());
				int indexTextPagination = pdfStamper.getReader().getNumberOfPages();
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 180, 674, comune.get().getNomeComune());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 447, 673,
						comune.get().getRegioneProvince().getSiglaProvincia());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 500, 673, sedi.get(s).getCap());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 130, 655, sedi.get(s).getIndirizzo());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 510, 655, sedi.get(s).getNumeroCivico());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 123, 635, sedi.get(s).getTelefono());
				if(sedi.get(s).getFax() != null)
					addTextPdf(pdfStamper, (indexTextPagination), baseFont, 360, 635, sedi.get(s).getFax());
				
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 160, 618, sedi.get(s).getIndirizzo());

				pdfStamper.close();

				apiFileService.deleteFile(schedaSede.get().getDocumentIdClient());
				String path = "/" + idRichiesta + "/odm/" + schedaSede.get().getIdModulo() + "/" + schedaSede.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, schedaSede.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				schedaSede.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				schedaSede.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(schedaSede.get());	
			}
		}			

	}
	
	public void saveFileAttoRiepOdm(Long idRichiesta) throws Exception {
		Optional<StatoModuliRichiestaFigli> statoModulo = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiesta((long) 3, idRichiesta);
		
		if(statoModulo.get().getValidato() != (Integer) 1) {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			PdfReader pdfReader = null;
			PdfStamper pdfStamper = null;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	
			// Meto per la composizione del documento
			HashMap<String, Object> inizializerValueObj = getAnteprimaSezione1Inizializer(pdfReader, pdfStamper, 
														  byteArrayOutputStream, richiesta.get());
			// VERRA' VALORIZZATO DAL RITORNO DEL PRIMO METODO
			pdfReader = (PdfReader) inizializerValueObj.get("pdfReader");
			pdfStamper = (PdfStamper) inizializerValueObj.get("pdfStamper");
	
			getAnteprimaSezione2(pdfReader, pdfStamper, byteArrayOutputStream, richiesta.get());
			getAnteprimaFileSediOperative(pdfReader, pdfStamper, byteArrayOutputStream, idRichiesta);
			getAnteprimaFileSezione4(pdfReader, pdfStamper, byteArrayOutputStream, richiesta.get());
			insertFileSpeseDiMediazione(pdfStamper, richiesta.get());
	
			pdfStamper.close();
			
			apiFileService.deleteFile(statoModulo.get().getDocumentIdClient());
			
			String path = "/" + idRichiesta + "/odm/" + statoModulo.get().getIdModulo() + "/" + statoModulo.get().getId();
			MercurioFile infoFile = apiFileService.insertFile(path, statoModulo.get().getNomeAllegato(), 
									byteArrayOutputStream.toByteArray());
			statoModulo.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			statoModulo.get().setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(statoModulo.get());
		}
	}
	
	public void saveFileSchedeRapLegRspOrg(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findByIdRichiestaAndTwoIdQualifica(idRichiesta, (long) 1, (long) 2);
		Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
		
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);	
		 
		for (int a = 0; a < anagrafiche.size(); a++) {				
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 28, idRichiesta, anagrafica.getIdAnagrafica());

			if(moduloScheda.get().getValidato() != (Integer) 1) {
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(a == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

				if(anagrafica.getIdQualifica() == 1 || anagrafica.getCodiceFiscale().equalsIgnoreCase(anagraficaRapLeg.get().getCodiceFiscale())) {
					// RAPPRESENTANTE LEGALE
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 204, 595, "X");		
				}

				if(anagrafica.getIdQualifica() == 2) {
					// RESPONSABILE DELL'ORGANISMO
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 57, 595, "X");	
				}

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 107, 538, anagrafica.getCognome());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 91, 524, anagrafica.getNome());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 127, 512, dateFormat.format(anagrafica.getDataNascita()));	

				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 307, 512, 
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");	
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 500, "Italia");	

				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 307, 512, anagrafica.getComuneNascitaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 500, anagrafica.getStatoNascita());	
				}

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 85, 487, anagrafica.getCodiceFiscale());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 121, 474, "Italiana");

				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 104, 441, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 440, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 152, 428, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 104, 441, anagrafica.getComuneResidenzaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 152, 428, anagrafica.getStatoResidenza());
				}
				// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 164, 454, anagrafica.getIndirizzo());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 482, 454, anagrafica.getNumeroCivico());
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 428, anagrafica.getCap());

				if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
					if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 390, comune.get().getNomeComune());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 390, comune.get().getRegioneProvince().getSiglaProvincia());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 185, 378, "Italia");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 390, anagrafica.getComuneDomicilioEstero());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 185, 378, anagrafica.getStatoDomicilio());
					}
					// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 290, 403, anagrafica.getIndirizzoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 390, anagrafica.getNumeroCivicoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 378, anagrafica.getCapDomicilio());
				}

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 134, 353, anagrafica.getIndirizzoPec());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 218, 339, anagrafica.getIndirizzoEmail());

				pdfStamper.close();

				apiFileService.deleteFile(moduloScheda.get().getDocumentIdClient());

				String path = "/" + idRichiesta + "/odm/" + moduloScheda.get().getIdModulo() + "/" + moduloScheda.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloScheda.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloScheda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloScheda.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			}
		}
		
	}
	
	public void saveFileAutocertificazioneRapLegRespOrg(Long idRichiesta) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForAntScheMedLegAndOrg(idRichiesta);
		Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);		
			
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 29, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
				// PATH DOCUMENTO IN SISTEMA
				String inputFile = "documentale_locale/modulistica_domanda/allegato_1.pdf";
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader = new PdfReader(inputFile);
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
		
				// SET FONT DA INSERIRE
				BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
				// Aggiungi testo sotoscritto
				addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.getNome() + " " + anagrafica.getCognome());
				addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.getDataNascita()));
				
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.getComuneNascitaEstero() + 
							" (" + anagrafica.getStatoNascita() + ")");
				}
				
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getIndirizzo() + " " +	
							anagrafica.getNumeroCivico() + " " +
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getComuneResidenzaEstero() + 
							" (" + anagrafica.getStatoResidenza() + ")");
				}
					
				addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.getCodiceFiscale());
	
				// IN CASO DI STESSO RAP.LEGALE PER RESP ORGANISMO
				if(anagrafica.getIdAnagrafica() != anagraficaRapLeg.get().getIdAnagrafica() &&
				   anagraficaRapLeg.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale()))
					addTextSmallPdf(pdfStamper, 1, baseFont, 350, 615, 
							qualificaRepository.findById((long) 1).get().getQualifica() + " e " + 
							qualificaRepository.findById(anagrafica.getIdQualifica()).get().getQualifica());
				else
					addTextSmallPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.getIdQualifica()).get().getQualifica());
	
				addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
			//	addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
				String dataDocumento = dateFormat.format(new Date());
				addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
				
				pdfStamper.close();

				apiFileService.deleteFile(moduloAttoReqOnora.get().getDocumentIdClient());

				String path = "/" + idRichiesta + "/odm/" + moduloAttoReqOnora.get().getIdModulo() + "/" + moduloAttoReqOnora.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloAttoReqOnora.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloAttoReqOnora.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloAttoReqOnora.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());	
			}
		}

	}
	
	public void saveFileSchedeCompOrgAm(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);
		
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);	
		
		// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 31, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(a == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	
				if(anagrafica.getIdQualifica() == 3) {
					// COMPONENTE DELL'ORGANO
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 318, 595, "X");	
				}
				else if(anagrafica.getIdQualifica() == 4) {
					// SOCIO
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 99, 576, "X");	
				}
				else if(anagrafica.getIdQualifica() == 5) {
					// ASSOCIATO
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 57, 576, "X");	
				}
				else if(anagrafica.getIdQualifica() == 6) {
					// ALTRO
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 158, 576, "X");	
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 198, 576, anagrafica.getDescQualifica());	
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 107, 538, anagrafica.getCognome());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 91, 524, anagrafica.getNome());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 127, 512, dateFormat.format(anagrafica.getDataNascita()));	
				
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 307, 512, 
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");	
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 500, "Italia");	
	
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 307, 512, anagrafica.getComuneNascitaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 500, anagrafica.getStatoNascita());	
				}
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 85, 487, anagrafica.getCodiceFiscale());	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 121, 474, "Italiana");
	
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 104, 441, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 440, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 152, 428, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 104, 441, anagrafica.getComuneResidenzaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 152, 428, anagrafica.getStatoResidenza());
				}
				// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 164, 454, anagrafica.getIndirizzo());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 482, 454, anagrafica.getNumeroCivico());
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 428, anagrafica.getCap());
	
				if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
					if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 390, comune.get().getNomeComune());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 390, comune.get().getRegioneProvince().getSiglaProvincia());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 185, 378, "Italia");
					}
					else {
						if(anagrafica.getComuneDomicilioEstero().isEmpty() == false && anagrafica.getComuneDomicilioEstero().isEmpty() == false) {
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 390, anagrafica.getComuneDomicilioEstero());
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 185, 378, anagrafica.getStatoDomicilio());
						}
					}
					// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 290, 403, anagrafica.getIndirizzoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 390, anagrafica.getNumeroCivicoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 378, anagrafica.getCapDomicilio());
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 134, 353, anagrafica.getIndirizzoPec());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 218, 339, anagrafica.getIndirizzoEmail());
				
				pdfStamper.close();
	
				apiFileService.deleteFile(moduloScheda.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloScheda.get().getIdModulo() + "/" + moduloScheda.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloScheda.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloScheda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloScheda.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());
			}
		}

	}
	
	public void saveFileAutocertificazioneReqOnoCompOrgAm(Long idRichiesta) throws Exception {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.getAllAnagraficaByIdRichiestaCompOrgAm(idRichiesta);

			for (int a = 0; a < anagrafiche.size(); a++) {		
				AnagraficaOdm anagrafica = anagrafiche.get(a);		
				
				Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, anagrafica.getIdAnagrafica());
				
				if(moduloAttoReqOnora.get().getValidato() != (Integer) 1) {
					// PATH DOCUMENTO IN SISTEMA
					String inputFile = "documentale_locale/modulistica_domanda/allegato_1.pdf";
					// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
					// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
					PdfReader pdfReader = new PdfReader(inputFile);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			
					// SET FONT DA INSERIRE
					BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
					// Aggiungi testo sotoscritto
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.getNome() + " " + anagrafica.getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.getDataNascita()));
					
					if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.getComuneNascitaEstero() + 
								" (" + anagrafica.getStatoNascita() + ")");
					}
					
					if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getIndirizzo() + " " +	
								anagrafica.getNumeroCivico() + " " +
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getComuneResidenzaEstero() + 
								" (" + anagrafica.getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.getCodiceFiscale());
					if(anagrafica.getDescQualifica() == null || anagrafica.getDescQualifica().isEmpty()) {
						addTextPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.getIdQualifica()).get().getQualifica() );
					}
					else {
						addTextPdf(pdfStamper, 1, baseFont, 354, 615, anagrafica.getDescQualifica());
					}
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
			
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					
					pdfStamper.close();
					
					apiFileService.deleteFile(moduloAttoReqOnora.get().getDocumentIdClient());
					 
					String path = "/" + idRichiesta + "/odm/" + moduloAttoReqOnora.get().getIdModulo() + "/" + moduloAttoReqOnora.get().getId();
					MercurioFile infoFile = apiFileService.insertFile(path, moduloAttoReqOnora.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
					moduloAttoReqOnora.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloAttoReqOnora.get().setContentId(infoFile.getContentId());
					statoModuliRichiestaFigliRepository.save(moduloAttoReqOnora.get());
				}
			}
			
	}
	
	public void saveFilePrestaSerOpe(Long idRichiesta) throws Exception {
	    List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 3);

		Optional<StatoModuliRichiestaFigli> moduloElenco = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiesta((long) 35, 
				   idRichiesta);
		
		if(moduloElenco.get().getValidato() != (Integer) 1) {
		    // Creazione del documento PDF
		    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    Document document = new Document(PageSize.A3);
		    PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
		    document.open();
	
		    // ELENCO MEDIATORI GENERICI
		    // AGGIUNGE TITOLO TABELLA
		    Font fontTitolo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		    Paragraph paragraphTitolo = new Paragraph("ELENCO PRESTATORI DI SERVIZIO O OPERA", fontTitolo);
		    paragraphTitolo.setAlignment(Element.ALIGN_CENTER);
		    document.add(paragraphTitolo);
	
		    // AGGIUNGE SPZIO BOTTOM
		    document.add(new Paragraph("\n"));
	
		    // CREAZIONE DELLA TABELLA CON NUMERO COLONNE
		    PdfPTable table = new PdfPTable(7);
		    // LARGHEZZA DELLA PAGINA
		    table.setWidthPercentage(100);
	
		    // AGGIUNGI INTESTAZIONE COLONNE
		    table.addCell(createCell("Cognome", true));
		    table.addCell(createCell("Nome", true));
		    table.addCell(createCell("Codice fiscale", true));
		    table.addCell(createCell("Sesso", true));
		    table.addCell(createCell("Data di nascita", true));
		    table.addCell(createCell("Rapporto Organismo", true));
		    table.addCell(createCell("Data assunzione", true));
	
		    for (AnagraficaOdm anagrafica : anagrafiche) {
		        table.addCell(createCell(anagrafica.getCognome(), false));
		        table.addCell(createCell(anagrafica.getNome(), false));
		        table.addCell(createCell(anagrafica.getCodiceFiscale(), false));
		        table.addCell(createCell(anagrafica.getSesso(), false));
		        table.addCell(createCell(dateFormat.format(anagrafica.getDataNascita()), false));
		        table.addCell(createCell(anagrafica.getPoTipoRappOdm(), false));
		        table.addCell(createCell(dateFormat.format(anagrafica.getPoDataAssunzione()), false));
		    }
	
		    // AGGIUNTA TABELLA MEDIATORI GENERICI NEL DOCUMENTO
		    document.add(table);
	
		    // CHIUSURE A FINE OPERAZIONI
		    document.close();
	    
		    ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	        PdfReader pdfReader = new PdfReader(inputStream);
	        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	        
			pdfStamper.close();
			
			apiFileService.deleteFile(moduloElenco.get().getDocumentIdClient());
			
			String path = "/" + idRichiesta + "/odm/" + moduloElenco.get().getIdModulo() + "/" + moduloElenco.get().getId();
			MercurioFile infoFile = apiFileService.insertFile(path, moduloElenco.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
			moduloElenco.get().setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloElenco.get().setContentId(infoFile.getContentId());
			statoModuliRichiestaFigliRepository.save(moduloElenco.get());		
		}
	}
	
	public void saveFileSchedeAppeA(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 38, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(a == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 562, anagrafica.getCognome());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 549, anagrafica.getNome());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 126, 536, dateFormat.format(anagrafica.getDataNascita()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 83, 511, anagrafica.getCodiceFiscale());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 324, 511, anagrafica.getMedPiva());
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 125, 499, anagrafica.getCittadinanza());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 162, 486, anagrafica.getIndirizzo());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 480, 486, anagrafica.getNumeroCivico());
	
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 536, comune.get().getNomeComune() +
							   " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 524, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 536, anagrafica.getComuneNascitaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 524, anagrafica.getStatoNascita());
				}
	
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 473, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 473, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 473, anagrafica.getComuneResidenzaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460, anagrafica.getStatoResidenza());
				}
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 461, anagrafica.getCap());
	
				if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
					if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 435, comune.get().getNomeComune());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 435, comune.get().getRegioneProvince().getSiglaProvincia());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 190, 422, "Italia");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 435, anagrafica.getComuneDomicilioEstero());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460, anagrafica.getStatoDomicilio());
					}
					addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 422, anagrafica.getCapDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 448, anagrafica.getIndirizzoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 67, 436, anagrafica.getNumeroCivicoDomicilio());
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 410, anagrafica.getIndirizzoPec());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 220, 397, anagrafica.getIndirizzoEmail());
				
				// SOLO SE L'ORDINE COLLEGIO E' STATO VALORIZZATO VERRANNO INSERITI I SEGUENTI PARAMETRI NEL DOCUMENTO
				if(anagrafica.getIdOrdiniCollegi() != null && anagrafica.getIdOrdiniCollegi() != 0) {
					addTextSmall2Pdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 282, 385, ordcollegiRepository.findById(
																					  anagrafica.getIdOrdiniCollegi()).get().getDescrizione()); 
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 478, 385, dateFormat.format(anagrafica.getMedDataOrdineCollegioProfess()));
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 321, anagrafica.getMedRappGiuridicoEconomico());
				
				pdfStamper.close();
				
				apiFileService.deleteFile(moduloScheda.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloScheda.get().getIdModulo() + "/" + moduloScheda.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloScheda.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloScheda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloScheda.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());	
			}
		}

	}
	
	public void saveFileAutocertificazioneAppeA(Long idRichiesta) throws Exception {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);

			for (int a = 0; a < anagrafiche.size(); a++) {		
				AnagraficaOdm anagrafica = anagrafiche.get(a);	
				Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 39, idRichiesta, anagrafica.getIdAnagrafica());
				
				if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
					// PATH DOCUMENTO IN SISTEMA
					String inputFile = "documentale_locale/modulistica_domanda/allegato_1.pdf";
					// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
					// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
					PdfReader pdfReader = new PdfReader(inputFile);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			
					// SET FONT DA INSERIRE
					BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
					// Aggiungi testo sotoscritto
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.getNome() + " " + anagrafica.getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.getDataNascita()));
					
					if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.getComuneNascitaEstero() + 
								" (" + anagrafica.getStatoNascita() + ")");
					}
					
					if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getIndirizzo() + " " +
								anagrafica.getNumeroCivico() + " " +
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getComuneResidenzaEstero() + 
								" (" + anagrafica.getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.getCodiceFiscale());
					
					String tipoAnagrafica = "";
					switch(anagrafica.getIdTipoAnagrafica().intValue()) {
						case 4: 
							tipoAnagrafica = "Mediatore Generico";
							break;
						case 5: 
							tipoAnagrafica = "Mediatore esperto in materia Internazionale";
							break;
						case 6: 
							tipoAnagrafica = "Mediatore esperto in materia di Consumo";
							break;
					}
					addTextSmallPdf(pdfStamper, 1, baseFont, 354, 615, tipoAnagrafica);
					
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");	
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					
					pdfStamper.close();
					
					apiFileService.deleteFile(moduloAutocertificazione.get().getDocumentIdClient());
					
					String path = "/" + idRichiesta + "/odm/" + moduloAutocertificazione.get().getIdModulo() + "/" + moduloAutocertificazione.get().getId();
					MercurioFile infoFile = apiFileService.insertFile(path, moduloAutocertificazione.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
					moduloAutocertificazione.get().setDocumentIdClient(infoFile.getDocumentIdClient());
					moduloAutocertificazione.get().setContentId(infoFile.getContentId());
					statoModuliRichiestaFigliRepository.save(moduloAutocertificazione.get());	
				}
			}
			
	}
	
	public void saveFileAutocertificazioneAppeB(Long idRichiesta) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);

		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 44, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {
				// PATH DOCUMENTO IN SISTEMA
				String inputFile = "documentale_locale/modulistica_domanda/allegato_1.pdf";
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader = new PdfReader(inputFile);
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	
				// SET FONT DA INSERIRE
				BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
				// Aggiungi testo sotoscritto
				addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.getNome() + " " + anagrafica.getCognome());
				addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.getDataNascita()));
	
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.getComuneNascitaEstero() + 
							" (" + anagrafica.getStatoNascita() + ")");
				}
	
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getIndirizzo() + " " +	
							anagrafica.getNumeroCivico() + " " +
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getComuneResidenzaEstero() + 
							" (" + anagrafica.getStatoResidenza() + ")");
				}
	
				addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.getCodiceFiscale());
	
				String tipoAnagrafica = "";
				switch(anagrafica.getIdTipoAnagrafica().intValue()) {
					case 4: 
						tipoAnagrafica = "Mediatore Generico";
						break;
					case 5: 
						tipoAnagrafica = "Mediatore esperto in materia Internazionale";
						break;
					case 6: 
						tipoAnagrafica = "Mediatore esperto in materia di Consumo";
						break;
				}
				addTextSmallPdf(pdfStamper, 1, baseFont, 354, 615, tipoAnagrafica);
				
				addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
				//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
				String dataDocumento = dateFormat.format(new Date());
				addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
	
				pdfStamper.close();
	
				apiFileService.deleteFile(moduloAutocertificazione.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloAutocertificazione.get().getIdModulo() + "/" + moduloAutocertificazione.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloAutocertificazione.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloAutocertificazione.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloAutocertificazione.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloAutocertificazione.get());
			}
		}

	}

	public void saveFileAutocertificazioneAppeC(Long idRichiesta) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);

		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			
			Optional<StatoModuliRichiestaFigli> moduloAutocertificazione = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 53, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloAutocertificazione.get().getValidato() != (Integer) 1) {	
				// PATH DOCUMENTO IN SISTEMA
				String inputFile = "documentale_locale/modulistica_domanda/allegato_1.pdf";
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader = new PdfReader(inputFile);
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
	
				// SET FONT DA INSERIRE
				BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
				// Aggiungi testo sotoscritto
				addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.getNome() + " " + anagrafica.getCognome());
				addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.getDataNascita()));
	
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.getComuneNascitaEstero() + 
							" (" + anagrafica.getStatoNascita() + ")");
				}
	
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getIndirizzo() + " " +	
							anagrafica.getNumeroCivico() + " " +
							comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.getComuneResidenzaEstero() + 
							" (" + anagrafica.getStatoResidenza() + ")");
				}
	
				addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.getCodiceFiscale());
	
				String tipoAnagrafica = "";
				switch(anagrafica.getIdTipoAnagrafica().intValue()) {
					case 4: 
						tipoAnagrafica = "Mediatore Generico";
						break;
					case 5: 
						tipoAnagrafica = "Mediatore esperto in materia Internazionale";
						break;
					case 6: 
						tipoAnagrafica = "Mediatore esperto in materia di Consumo";
						break;
				}
				addTextSmallPdf(pdfStamper, 1, baseFont, 354, 615, tipoAnagrafica);
				
				addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
				//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
				String dataDocumento = dateFormat.format(new Date());
				addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
	
				pdfStamper.close();
	
				apiFileService.deleteFile(moduloAutocertificazione.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloAutocertificazione.get().getIdModulo() + "/" + moduloAutocertificazione.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloAutocertificazione.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloAutocertificazione.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloAutocertificazione.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloAutocertificazione.get());
			}
		}
		
	}
	
	public void saveFileSchedeAppeB(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);
		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {					
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 43, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(a == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_b/scheda_b_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_b/scheda_b.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 587, anagrafica.getCognome());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 574, anagrafica.getNome());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 126, 561, dateFormat.format(anagrafica.getDataNascita()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 83, 536, anagrafica.getCodiceFiscale());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 324, 536, anagrafica.getMedPiva());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 125, 524, anagrafica.getCittadinanza());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 162, 511, anagrafica.getIndirizzo());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 480, 511, anagrafica.getNumeroCivico());
	
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 561, comune.get().getNomeComune() +
							   " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 549, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 561, anagrafica.getComuneNascitaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 549, anagrafica.getStatoNascita());
				}

				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 498, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 498, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 485, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 498, anagrafica.getComuneResidenzaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 485, anagrafica.getStatoResidenza());
				}
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 486, anagrafica.getCap());

				if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
					if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 460, comune.get().getNomeComune());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 460, comune.get().getRegioneProvince().getSiglaProvincia());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 190, 447, "Italia");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 460, anagrafica.getComuneDomicilioEstero());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 190, 447, anagrafica.getStatoDomicilio());
					}
					addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 447, anagrafica.getCapDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 473, anagrafica.getIndirizzoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 67, 461, anagrafica.getNumeroCivicoDomicilio());
				}

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 435, anagrafica.getIndirizzoPec());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 220, 422, anagrafica.getIndirizzoEmail());
				
				// SOLO SE L'ORDINE COLLEGIO E' STATO VALORIZZATO VERRANNO INSERITI I SEGUENTI PARAMETRI NEL DOCUMENTO
				if(anagrafica.getIdOrdiniCollegi() != null && anagrafica.getIdOrdiniCollegi() != 0) {
					addTextSmall2Pdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 282, 410, ordcollegiRepository.findById(
																					  anagrafica.getIdOrdiniCollegi()).get().getDescrizione()); 
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 478, 410, dateFormat.format(anagrafica.getMedDataOrdineCollegioProfess()));
				}

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 346, anagrafica.getMedRappGiuridicoEconomico());
				
				pdfStamper.close();
								
				apiFileService.deleteFile(moduloScheda.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloScheda.get().getIdModulo() + "/" + moduloScheda.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloScheda.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloScheda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloScheda.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());	
			}
		}		
	}
	
	public void saveFileSchedeAppeC(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);
		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {					
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			Optional<StatoModuliRichiestaFigli> moduloScheda = statoModuliRichiestaFigliRepository.findByIdModuloAndIdRichiestaAndIdAnagrafica(
					(long) 52, idRichiesta, anagrafica.getIdAnagrafica());
			
			if(moduloScheda.get().getValidato() != (Integer) 1) {	
				// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
				PdfReader pdfReader;
				if(a == 0) {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_c/scheda_c_con_intestazione.pdf";
					pdfReader = new PdfReader(inputFile);
				}
				else {
					String inputFile = "documentale_locale/modulistica_domanda/appendice_c/scheda_c.pdf"; 
					pdfReader = new PdfReader(inputFile);
				}
				PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 601, anagrafica.getCognome()); //587
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 588, anagrafica.getNome());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 126, 575, dateFormat.format(anagrafica.getDataNascita()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 83, 550, anagrafica.getCodiceFiscale());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 324, 550, anagrafica.getMedPiva());
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 125, 538, anagrafica.getCittadinanza());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 162, 525, anagrafica.getIndirizzo());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 480, 525, anagrafica.getNumeroCivico());
	
				if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 575, comune.get().getNomeComune() +
							   " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 563, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 575, anagrafica.getComuneNascitaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 563, anagrafica.getStatoNascita());
				}
	
				if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 512, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 512, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 499, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 512, anagrafica.getComuneResidenzaEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 499, anagrafica.getStatoResidenza());
				}
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 500, anagrafica.getCap());
	
				if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
					if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 474, comune.get().getNomeComune());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 474, comune.get().getRegioneProvince().getSiglaProvincia());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 190, 461, "Italia");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 474, anagrafica.getComuneDomicilioEstero());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 499, anagrafica.getStatoDomicilio());
					}
					addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 461, anagrafica.getCapDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 487, anagrafica.getIndirizzoDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 67, 475, anagrafica.getNumeroCivicoDomicilio());
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 449, anagrafica.getIndirizzoPec());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 220, 436, anagrafica.getIndirizzoEmail());
				
				// SOLO SE L'ORDINE COLLEGIO E' STATO VALORIZZATO VERRANNO INSERITI I SEGUENTI PARAMETRI NEL DOCUMENTO
				if(anagrafica.getIdOrdiniCollegi() != null && anagrafica.getIdOrdiniCollegi() != 0) {
					addTextSmall2Pdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 282, 424, ordcollegiRepository.findById(
																					  anagrafica.getIdOrdiniCollegi()).get().getDescrizione()); 
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 478, 424, dateFormat.format(anagrafica.getMedDataOrdineCollegioProfess()));
				}
	
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 360, anagrafica.getMedRappGiuridicoEconomico());
				
				pdfStamper.close();
				
				apiFileService.deleteFile(moduloScheda.get().getDocumentIdClient());
				
				String path = "/" + idRichiesta + "/odm/" + moduloScheda.get().getIdModulo() + "/" + moduloScheda.get().getId();
				MercurioFile infoFile = apiFileService.insertFile(path, moduloScheda.get().getNomeAllegato(), byteArrayOutputStream.toByteArray());
				moduloScheda.get().setDocumentIdClient(infoFile.getDocumentIdClient());
				moduloScheda.get().setContentId(infoFile.getContentId());
				statoModuliRichiestaFigliRepository.save(moduloScheda.get());	
			}			

		}

	}
	
	public boolean anteprimaFileAppeAFinalizza(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta,
				(long) 4);

		// PATH DOCUMENTO IN SISTEMA
		String inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a_con_intestazione.pdf";
		PdfReader pdfReaderAppeA = new PdfReader(inputFile);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

		for (int a = 0; a < anagrafiche.size(); a++) {
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			if (a != 0) {
				inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a.pdf";
				pdfReaderAppeA = new PdfReader(inputFile);
			}

			pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
					pdfReaderAppeA.getPageSizeWithRotation(1));
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
			pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderAppeA, 1), 0, 0);
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 562, anagrafica.getCognome());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 95, 549, anagrafica.getNome());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 126, 536, dateFormat.format(anagrafica.getDataNascita()));
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 83, 511, anagrafica.getCodiceFiscale());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 324, 511, anagrafica.getMedPiva());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 125, 499, anagrafica.getCittadinanza());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 162, 486, anagrafica.getIndirizzo());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 480, 486, anagrafica.getNumeroCivico());

			if (anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 536,
						comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia()
						+ ")");
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 524, "Italia");
			} else {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 320, 536,
						anagrafica.getComuneNascitaEstero());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 132, 524,
						anagrafica.getStatoNascita());
			}

			if (anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 473,
						comune.get().getNomeComune());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 473,
						comune.get().getRegioneProvince().getSiglaProvincia());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460, "Italia");
			} else {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 105, 473,
						anagrafica.getComuneResidenzaEstero());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460,
						anagrafica.getStatoResidenza());
			}
			addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 461,
					anagrafica.getCap());

			if (anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
				if (anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 435,
							comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 508, 435,
							comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 190, 422, "Italia");
				} else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 148, 435,
							anagrafica.getComuneDomicilioEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 460,
							anagrafica.getStatoDomicilio());
				}
				addTextSmallPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 422,
						anagrafica.getCapDomicilio());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 448,
						anagrafica.getIndirizzoDomicilio());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 67, 436,
						anagrafica.getNumeroCivicoDomicilio());
			}

			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 410,
					anagrafica.getIndirizzoPec());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 220, 397,
					anagrafica.getIndirizzoEmail());

			// SOLO SE L'ORDINE COLLEGIO E' STATO VALORIZZATO VERRANNO INSERITI I SEGUENTI PARAMETRI NEL DOCUMENTO
			if (anagrafica.getIdOrdiniCollegi() != null && anagrafica.getIdOrdiniCollegi() != 0) {
				addTextSmall2Pdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 282, 385,
						ordcollegiRepository.findById(anagrafica.getIdOrdiniCollegi()).get().getDescrizione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 478, 385,
						dateFormat.format(anagrafica.getMedDataOrdineCollegioProfess()));
			}

			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 70, 321,
					anagrafica.getMedRappGiuridicoEconomico());

			/*
			 * // INSERIMENTO PDF DOCUMENTO D'IDENTITA' Optional<StatoModuliRichiestaFigli>
			 * moduloDocumento = statoModuliRichiestaFigliRepository
			 * .findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta,
			 * anagrafica.getIdAnagrafica());
			 * inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().
			 * getDocumentIdClient(), moduloDocumento.get().getContentId()) , pdfStamper);
			 */
		}

		return true;
		//return new PdfDto("polizza_assicurativa", byteArrayOutputStream.toByteArray());
	}
		
	private void insertRichiestaJob(Long idRichiesta, String tipoJob, String tipoRichiesta) throws Exception {
		// SALVATAGGIO IN UN PRIMO MOMENTE SENZA GLI ID DI RIFERIMENTO A MERCURIO	
		Optional<JobRichiesta> jobRichiesta = Optional.of(new JobRichiesta());
		jobRichiesta.get().setIdRichiesta(idRichiesta);
		jobRichiesta.get().setTipoJob("GENERAZIONE PDF");
		jobRichiesta.get().setTipoRichiesta(tipoRichiesta);
		jobRichiesta.get().setStatoJob("aperta");

		// VALORIZZATO TRAMITE L'UTENTE LOGGATO
		UtenteLoggato user = (UtenteLoggato) cacheManager.getCache("cacheVE").get("utenteIAMG").get();
		jobRichiesta.get().setIdUtenteRichiedente(userLoginRepository.findByCodiceFiscale(user.getCodiceFiscale()).getId());
		
		jobRichiesta.get().setDataOraRichiesta(new Date());		

		jobRichiestaRepository.save(jobRichiesta.get());
	}
	
	// METODI PRIVATI
	private ByteArrayOutputStream getAnteprimaSezione1(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Richiesta richiesta) throws Exception {
		try {
			// Essendo legale uscira solo una sede associata
			List<Sede> sedi = sedeRepository.findByIdRichiestaAndSedeLegale(richiesta.getIdRichiesta(), '1');
			Optional<Comune> comune = comuneRepository.findById(sedi.get(0).getIdComune());

			// Inserimento sedi pdf
			int actualPage = 0;
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			PdfReader pdfReaderSezione1 = new PdfReader("documentale_locale/modulistica_domanda/sezione_1.pdf");
			PdfReader allegatoPlanimetriaReader = null;
			PdfReader allegatoCopContrattoReader = null;

			pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
					pdfReaderSezione1.getPageSizeWithRotation(1));
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
			pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderSezione1, 1), 0, 0);

			addTextPdf(pdfStamper, (3), baseFont, 154, 628, dateFormat.format(richiesta.getDataAttoCosti()));
			addTextPdf(pdfStamper, (3), baseFont, 154, 608, dateFormat.format(richiesta.getDataStatutoVig()));
			addTextPdf(pdfStamper, (3), baseFont, 86, 589, richiesta.getCodFiscSocieta());
			addTextPdf(pdfStamper, (3), baseFont, 96, 570, richiesta.getPIva());
			addTextPdf(pdfStamper, (3), baseFont, 135, 551,
					   naturaGiuridicaRepository.findById(richiesta.getIdNaturaGiu()).get().getDescrizione()); // Fix
			addTextPdf(pdfStamper, (3), baseFont, 118, 493, sedi.get(0).getIndirizzo());
			addTextPdf(pdfStamper, (3), baseFont, 494, 493, sedi.get(0).getNumeroCivico());
			addTextPdf(pdfStamper, (3), baseFont, 88, 474, sedi.get(0).getCap());
			addTextPdf(pdfStamper, (3), baseFont, 223, 474, comune.get().getNomeComune());
			addTextPdf(pdfStamper, (3), baseFont, 494, 474, comune.get().getRegioneProvince().getSiglaProvincia());
			addTextPdf(pdfStamper, (3), baseFont, 88, 456, "palermo"); // ??
			addTextPdf(pdfStamper, (3), baseFont, 104, 417, sedi.get(0).getTelefono());
			if(sedi.get(0).getFax() != null)
				addTextPdf(pdfStamper, (3), baseFont, 80, 398, sedi.get(0).getFax());
			
			addTextPdf(pdfStamper, (3), baseFont, 184, 381, sedi.get(0).getPec());
			addTextPdf(pdfStamper, (3), baseFont, 220, 361, sedi.get(0).getEmail());
			addTextPdf(pdfStamper, (3), baseFont, 104, 343, sedi.get(0).getSitoWebSede());

			// Da capire condizione per check e società...
			if (richiesta.getIdNaturaSoc() != null) {
				addTextPdf(pdfStamper, (3), baseFont, 57, 202, "X");
				addTextPdf(pdfStamper, (3), baseFont, 213, 177, "Prova nome");
				addTextPdf(pdfStamper, (3), baseFont, 137, 163, "Prova nome");
			}

			// Inserimenti degli allegati riguardanti la sede legale
			allegatoCopContrattoReader = new PdfReader(new ByteArrayInputStream(
					getFileSedeCopiaContratto(sedi.get(0).getIdRichiesta(), sedi.get(0).getIdSede()).getFile()));
			// Per tornare il numero totale delle pagine dell'allegato
			actualPage = actualPage + allegatoCopContrattoReader.getNumberOfPages();
			for (int i = 1; i <= allegatoCopContrattoReader.getNumberOfPages(); i++) {
				// Per aggiungere alla fine del documento le pagine del file ricevuto
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						allegatoCopContrattoReader.getPageSizeWithRotation(i));
				PdfContentByte pdfContentByteCopCont = pdfStamper
						.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentByteCopCont.addTemplate(pdfStamper.getImportedPage(allegatoCopContrattoReader, i), 0, 0);
			}

			allegatoPlanimetriaReader = new PdfReader(new ByteArrayInputStream(
					getFileSedePlanimetria(sedi.get(0).getIdRichiesta(), sedi.get(0).getIdSede()).getFile()));
			// Per tornare il numero totale delle pagine dell'allegato
			actualPage = actualPage + allegatoPlanimetriaReader.getNumberOfPages();
			for (int i = 1; i <= allegatoPlanimetriaReader.getNumberOfPages(); i++) {
				// Per aggiungere alla fine del documento le pagine del file ricevuto
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						allegatoPlanimetriaReader.getPageSizeWithRotation(i));
				PdfContentByte pdfContentBytePlanimetria = pdfStamper
						.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentBytePlanimetria.addTemplate(pdfStamper.getImportedPage(allegatoPlanimetriaReader, i), 0, 0);
			}

			return byteArrayOutputStream;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file della sezione 1");
		}

	}
	
	private HashMap<String, Object> getAnteprimaSezione1Inizializer(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Richiesta richiesta) throws Exception {
		try {
			HashMap<String, Object> response = new HashMap<>();

			// Essendo legale uscira solo una sede associata
			List<Sede> sedi = sedeRepository.findByIdRichiestaAndSedeLegale(richiesta.getIdRichiesta(), '1');
			Optional<Comune> comune = comuneRepository.findById(sedi.get(0).getIdComune());

			// Inserimento sedi pdf
			int actualPage = 0;
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			pdfReader = new PdfReader("documentale_locale/modulistica_domanda/sezione_1.pdf");
			pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

			addTextPdf(pdfStamper, 1, baseFont, 154, 628, dateFormat.format(richiesta.getDataAttoCosti()));
			addTextPdf(pdfStamper, 1, baseFont, 154, 608, dateFormat.format(richiesta.getDataStatutoVig()));
			addTextPdf(pdfStamper, 1, baseFont, 86, 589, richiesta.getCodFiscSocieta());
			addTextPdf(pdfStamper, 1, baseFont, 96, 570, richiesta.getPIva());
			if(richiesta.getIdNaturaSoc() != null && richiesta.getIdNaturaSoc() != 0) {
				addTextPdf(pdfStamper, 1, baseFont, 135, 551,
						   naturaSocietariaRepository.findById(richiesta.getIdNaturaSoc()).get().getDescrizione()); 
			}
			addTextPdf(pdfStamper, 1, baseFont, 118, 493, sedi.get(0).getIndirizzo());
			addTextPdf(pdfStamper, 1, baseFont, 494, 493, sedi.get(0).getNumeroCivico());
			addTextPdf(pdfStamper, 1, baseFont, 88, 474, sedi.get(0).getCap());
			addTextPdf(pdfStamper, 1, baseFont, 223, 474, comune.get().getNomeComune()); 
			addTextPdf(pdfStamper, 1, baseFont, 494, 474, comune.get().getRegioneProvince().getSiglaProvincia());
			addTextPdf(pdfStamper, 1, baseFont, 88, 456, "Italia"); 
			addTextPdf(pdfStamper, 1, baseFont, 104, 417, sedi.get(0).getTelefono());
			if(sedi.get(0).getFax() != null)
				addTextPdf(pdfStamper, 1, baseFont, 80, 398, sedi.get(0).getFax());
			
			addTextPdf(pdfStamper, 1, baseFont, 184, 381, sedi.get(0).getPec());
			addTextPdf(pdfStamper, 1, baseFont, 220, 361, sedi.get(0).getEmail());
			addTextPdf(pdfStamper, 1, baseFont, 104, 343, sedi.get(0).getSitoWebSede());

			// Da capire condizione per check e società...
			if (richiesta.getIstitutoEntePub() != null && richiesta.getIstitutoEntePub() == 1) {				
				addTextPdf(pdfStamper, 1, baseFont, 57, 202, "X");
				addTextPdf(pdfStamper, 1, baseFont, 213, 177, richiesta.getDenominaOdmPub());
				addTextPdf(pdfStamper, 1, baseFont, 137, 163, naturaGiuridicaRepository.findById(richiesta.getIdNaturaGiu()).get().getDescrizione());
			}
			
			PdfReader allegatoPlanimetriaReader = null;
			PdfReader allegatoCopContrattoReader = null;

			// Inserimenti degli allegati riguardanti la sede legale
			allegatoCopContrattoReader = new PdfReader(new ByteArrayInputStream(
					getFileSedeCopiaContratto(sedi.get(0).getIdRichiesta(), sedi.get(0).getIdSede()).getFile()));
			// Per tornare il numero totale delle pagine dell'allegato
			actualPage = actualPage + allegatoCopContrattoReader.getNumberOfPages();
			for (int i = 1; i <= allegatoCopContrattoReader.getNumberOfPages(); i++) {
				// Per aggiungere alla fine del documento le pagine del file ricevuto
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						allegatoCopContrattoReader.getPageSizeWithRotation(i));
				PdfContentByte pdfContentByteCopCont = pdfStamper
						.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentByteCopCont.addTemplate(pdfStamper.getImportedPage(allegatoCopContrattoReader, i), 0, 0);
			}

			allegatoPlanimetriaReader = new PdfReader(new ByteArrayInputStream(
					getFileSedePlanimetria(sedi.get(0).getIdRichiesta(), sedi.get(0).getIdSede()).getFile()));
			// Per tornare il numero totale delle pagine dell'allegato
			actualPage = actualPage + allegatoPlanimetriaReader.getNumberOfPages();
			for (int i = 1; i <= allegatoPlanimetriaReader.getNumberOfPages(); i++) {
				// Per aggiungere alla fine del documento le pagine del file ricevuto
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						allegatoPlanimetriaReader.getPageSizeWithRotation(i));
				PdfContentByte pdfContentBytePlanimetria = pdfStamper
						.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentBytePlanimetria.addTemplate(pdfStamper.getImportedPage(allegatoPlanimetriaReader, i), 0, 0);
			}

			// RITORNERA' GLI OGGETTI VALORIZZATI 
			response.put("pdfReader", pdfReader);
			response.put("pdfStamper", pdfStamper);
			return response;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file della sezione 1");
		}

	}

	private ByteArrayOutputStream getAnteprimaSezione2(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Richiesta richiesta) throws Exception {
		try {
			AnagraficaOdm anagrafica = getRappresentateLegale(richiesta.getIdRichiesta()).get();

			inserimentoFile("documentale_locale/modulistica_domanda/sezione_2.pdf", pdfStamper);
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 682,
					anagrafica.getCognome());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 662, anagrafica.getNome());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 644,
					dateFormat.format(anagrafica.getDataNascita()));
				
			if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 325, 644,
						comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 624, "Italia");
			}
			else {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 325, 644, anagrafica.getComuneNascitaEstero());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 624, anagrafica.getStatoNascita());	
			}
			// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 318, 624, anagrafica.getCodiceFiscale());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 125, 605, anagrafica.getCittadinanza());
			
			if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 547, comune.get().getNomeComune());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 397, 546, comune.get().getRegioneProvince().getSiglaProvincia());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 93, 528, "Italia");
			}
			else {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 547, anagrafica.getComuneResidenzaEstero());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 93, 528, anagrafica.getStatoResidenza());
			}
			// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 567, anagrafica.getIndirizzo());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 471, 567, anagrafica.getNumeroCivico());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 456, 548, anagrafica.getCap());
		
			if(anagrafica.getIndirizzoDomicilio() != null && anagrafica.getIndirizzoDomicilio().isEmpty() == false) {
				if(anagrafica.getIdComuneDomicilio() != null && anagrafica.getIdComuneDomicilio() != 0) {
					Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneDomicilio());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 471, comune.get().getNomeComune());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 398, 471, comune.get().getRegioneProvince().getSiglaProvincia());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 93, 452, "Italia");
				}
				else {
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 471, anagrafica.getComuneDomicilioEstero());
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 93, 452, anagrafica.getStatoDomicilio());
				}
				// DATI IN COMUNE A PRESCINDERE DALL'ESITO DELLA CONDIZIONE
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 491, anagrafica.getIndirizzoDomicilio());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 471, 491, anagrafica.getNumeroCivicoDomicilio());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 456, 472, anagrafica.getCapDomicilio());
			}
			
			return byteArrayOutputStream;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file della sezione 2");
		}

	}
	
	private ByteArrayOutputStream getAnteprimaFileSediOperative(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Long idRichiesta) throws Exception {
		try {
			List<Sede> sedi = sedeRepository.findByIdRichiestaAndSedeLegale(idRichiesta, '0');
			// Inserimento sedi pdf
			int actualPage = 0;
			String inputFileSedeOpe = "";
			PdfReader allegatoPlanimetriaReader = null;
			PdfReader allegatoCopContrattoReader = null;
			// SET FONT DA INSERIRE
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

			for (int s = 0; s < sedi.size(); s++) {
				// Percorso del documento PDF per l'inserimento della sede principale
				if (s == 0) {
					inputFileSedeOpe = "documentale_locale/modulistica_domanda/sezione_3/altre_sedi_operative_con_intestazione.pdf";
				} else {
					inputFileSedeOpe = "documentale_locale/modulistica_domanda/sezione_3/altre_sedi_operative.pdf";
				}

				PdfReader pdfReaderSedeOpe = new PdfReader(inputFileSedeOpe);
				// Per leggere il documento dell'inserimento della sede
				actualPage = actualPage + pdfReaderSedeOpe.getNumberOfPages();
				// Aggiungi ogni pagina del secondo documento alla fine del PDF risultante
				for (int i = 1; i <= pdfReaderSedeOpe.getNumberOfPages(); i++) {
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							pdfReaderSedeOpe.getPageSizeWithRotation(i));
					PdfContentByte pdfContentByte = pdfStamper
							.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderSedeOpe, i), 0, 0);
				}
				// Inserimento dati db al file per posizione
				Optional<Comune> comune = comuneRepository.findById(sedi.get(s).getIdComune());
				int indexTextPagination = pdfStamper.getReader().getNumberOfPages();
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 180, 674, comune.get().getNomeComune());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 447, 673,
						comune.get().getRegioneProvince().getSiglaProvincia());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 500, 673, sedi.get(s).getCap());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 130, 655, sedi.get(s).getIndirizzo());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 510, 655, sedi.get(s).getNumeroCivico());
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 123, 635, sedi.get(s).getTelefono());
				if(sedi.get(s).getFax() != null)
					addTextPdf(pdfStamper, (indexTextPagination), baseFont, 360, 635, sedi.get(s).getFax());
				
				addTextPdf(pdfStamper, (indexTextPagination), baseFont, 160, 618, sedi.get(s).getIndirizzo());

				// Inserimenti degli allegati riguardanti la sede operativa
				allegatoCopContrattoReader = new PdfReader(new ByteArrayInputStream(
						getFileSedeCopiaContratto(sedi.get(s).getIdRichiesta(), sedi.get(s).getIdSede()).getFile()));
				// Per tornare il numero totale delle pagine dell'allegato
				actualPage = actualPage + allegatoCopContrattoReader.getNumberOfPages();
				for (int i = 1; i <= allegatoCopContrattoReader.getNumberOfPages(); i++) {
					// Per aggiungere alla fine del documento le pagine del file ricevuto
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							allegatoCopContrattoReader.getPageSizeWithRotation(i));
					PdfContentByte pdfContentByte = pdfStamper
							.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(allegatoCopContrattoReader, i), 0, 0);
				}

				allegatoPlanimetriaReader = new PdfReader(new ByteArrayInputStream(
						getFileSedePlanimetria(sedi.get(s).getIdRichiesta(), sedi.get(s).getIdSede()).getFile()));
				// Per tornare il numero totale delle pagine dell'allegato
				actualPage = actualPage + allegatoPlanimetriaReader.getNumberOfPages();
				for (int i = 1; i <= allegatoPlanimetriaReader.getNumberOfPages(); i++) {
					// Per aggiungere alla fine del documento le pagine del file ricevuto
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							allegatoPlanimetriaReader.getPageSizeWithRotation(i));
					PdfContentByte pdfContentByte = pdfStamper
							.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(allegatoPlanimetriaReader, i), 0, 0);
				}

			}

			return byteArrayOutputStream;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file della sezione 3");
		}
	}

	private ByteArrayOutputStream getAnteprimaFileSezione4(PdfReader pdfReader, PdfStamper pdfStamper,
			ByteArrayOutputStream byteArrayOutputStream, Richiesta richiesta) throws Exception {
		try {
			// PRENDERA' SOLO LE SEDI OPERATIVE
			// PRENDERA' SOLO LE SEDI OPERATIVE
			List<Sede> sedi = sedeRepository.findByIdRichiestaAndSedeLegale(richiesta.getIdRichiesta(), '0');

			// Inserimento sedi pdf
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

			inserimentoFile("documentale_locale/modulistica_domanda/sezione_4/sezione_4.pdf", pdfStamper);
			// INSERIMENTO DEI DATI DELLA SEZIONE 4
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 370, 593, richiesta.getNumCompoOrgAmm().toString());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 212, 567, richiesta.getNumCompoCompSoc().toString());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 184, 542, richiesta.getDurataCarica());

			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 276, 518, 
			costituzioneOrganismoRepository.findById(richiesta.getIdModalitaCostOrgani()).get().getDescrizione());

			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 264, 491,
					   dateFormat.format(richiesta.getDataCostituOrg()));
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 440,
					   dateFormat.format(richiesta.getDataCostituOrg()));
			
			// DATI POLIZZA POSSIBILITA CHE NON SIANO VALORIZZATI IN UN PRIMO MOMENTO
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 184, 415,
			           richiesta.getDataStipulaPoliz() != null ? dateFormat.format(richiesta.getDataStipulaPoliz()) : "");
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 402,
					   richiesta.getCompagniaAss() != null ? richiesta.getCompagniaAss() : "");
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 290, 389, 
					   richiesta.getMassimaleAssic() != null ? richiesta.getMassimaleAssic().toString().replace(".", ",") : ""); 																								
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 194, 364,
					   dateFormat.format(richiesta.getScadenzaPoliza()));
			
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 216, 326, richiesta.getDurataCostituzioneOrganismo());
			// Nonme del responsabile dell'organismo
			Optional<AnagraficaOdm> anagraficaRespOrg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(richiesta.getIdRichiesta(), (long) 2);	
			if(anagraficaRespOrg.isPresent()) {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 131, 289, anagraficaRespOrg.get().getNome() 
						   + " " + anagraficaRespOrg.get().getCognome());
			}
			
			Sede sedeLegale = sedeRepository.findSedeLegale(richiesta.getIdRichiesta(), '1');
			if(sedeLegale.getDurataContratto() != null)
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 270, 161, sedeLegale.getDurataContratto());
			
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 184, 149, sedeLegale.getRegistrazione());
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 90, 136, dateFormat.format(sedeLegale.getDataContratto()));
			//addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 474, 111, "???");
			if(sedeLegale.getStrutturaOrgSegreteria() != null) {
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 100, 98, sedeLegale.getStrutturaOrgSegreteria());
			}

			// INSERIMENTO SPECIFICO A SECONDA DEL FILE DI RIFERIMENTO PER IL TOTALE DELLE
			// SEDI
			if (sedi.size() == 1) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_1.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");
			} else if (sedi.size() == 2) {
				inserimentoFile("documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_2.pdf", pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 3) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_3.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 4) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_4.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 5) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_5.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
						comuneRepository.findById(sedi.get(4).getIdComune()).get().getNomeComune());
				if(sedi.get(4).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
							sedi.get(4).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
						sedi.get(4).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
						dateFormat.format(sedi.get(4).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
						sedi.get(4).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 6) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_6.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						   sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "" != null ? sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "" : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
						comuneRepository.findById(sedi.get(4).getIdComune()).get().getNomeComune());
				if(sedi.get(4).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
							sedi.get(4).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
						sedi.get(4).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
						dateFormat.format(sedi.get(4).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
						sedi.get(4).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
						comuneRepository.findById(sedi.get(5).getIdComune()).get().getNomeComune());
				if(sedi.get(5).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
							sedi.get(5).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
						sedi.get(5).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
						dateFormat.format(sedi.get(5).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
						sedi.get(5).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 7) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_7.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
						comuneRepository.findById(sedi.get(4).getIdComune()).get().getNomeComune());
				if(sedi.get(4).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
							sedi.get(4).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
						sedi.get(4).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
						dateFormat.format(sedi.get(4).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
						sedi.get(4).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
						comuneRepository.findById(sedi.get(5).getIdComune()).get().getNomeComune());
				if(sedi.get(5).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
							sedi.get(5).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
						sedi.get(5).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
						dateFormat.format(sedi.get(5).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
						sedi.get(5).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 238,
						comuneRepository.findById(sedi.get(6).getIdComune()).get().getNomeComune());
				if(sedi.get(6).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 222,
							sedi.get(6).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 212,
						sedi.get(6).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 211,
						dateFormat.format(sedi.get(6).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 198,
						sedi.get(6).getStrutturaOrgSegreteria());
			} else if (sedi.size() == 8) {
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_8.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
						comuneRepository.findById(sedi.get(4).getIdComune()).get().getNomeComune());
				if(sedi.get(4).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
							sedi.get(4).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
						sedi.get(4).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
						dateFormat.format(sedi.get(4).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
						sedi.get(4).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
						comuneRepository.findById(sedi.get(5).getIdComune()).get().getNomeComune());
				if(sedi.get(5).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
							sedi.get(5).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
						sedi.get(5).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
						dateFormat.format(sedi.get(5).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
						sedi.get(5).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 238,
						comuneRepository.findById(sedi.get(6).getIdComune()).get().getNomeComune());
				if(sedi.get(6).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 222,
							sedi.get(6).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 212,
						sedi.get(6).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 211,
						dateFormat.format(sedi.get(6).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 198,
						sedi.get(6).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 157,
						comuneRepository.findById(sedi.get(7).getIdComune()).get().getNomeComune());
				if(sedi.get(7).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 143,
							sedi.get(7).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 131,
						sedi.get(7).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 130,
						dateFormat.format(sedi.get(7).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 118,
						sedi.get(7).getStrutturaOrgSegreteria());
			} else {
				// PER TUTTE LE SEDI IN CASO SUPERINO IL TOTALE DI 8
				inserimentoFile(
						"documentale_locale/modulistica_domanda/sezione_4/sedi_operative_con_intestazione/sedi_operative_8.pdf",
						pdfStamper);

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
						comuneRepository.findById(sedi.get(0).getIdComune()).get().getNomeComune());
				if(sedi.get(0).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
							sedi.get(0).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
						sedi.get(0).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
						dateFormat.format(sedi.get(0).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
						sedi.get(0).getStrutturaOrgSegreteria() != null ? sedi.get(0).getStrutturaOrgSegreteria() : "");

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
						comuneRepository.findById(sedi.get(1).getIdComune()).get().getNomeComune());
				if(sedi.get(1).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
							sedi.get(1).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
						sedi.get(1).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
						dateFormat.format(sedi.get(1).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
						sedi.get(1).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
						comuneRepository.findById(sedi.get(2).getIdComune()).get().getNomeComune());
				if(sedi.get(2).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
							sedi.get(2).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
						sedi.get(2).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
						dateFormat.format(sedi.get(2).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
						sedi.get(2).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
						comuneRepository.findById(sedi.get(3).getIdComune()).get().getNomeComune());
				if(sedi.get(3).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
							sedi.get(3).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
						sedi.get(3).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
						dateFormat.format(sedi.get(3).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
						sedi.get(3).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
						comuneRepository.findById(sedi.get(4).getIdComune()).get().getNomeComune());
				if(sedi.get(4).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
							sedi.get(4).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
						sedi.get(4).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
						dateFormat.format(sedi.get(4).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
						sedi.get(4).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
						comuneRepository.findById(sedi.get(5).getIdComune()).get().getNomeComune());
				if(sedi.get(5).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
							sedi.get(5).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
						sedi.get(5).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
						dateFormat.format(sedi.get(5).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
						sedi.get(5).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 238,
						comuneRepository.findById(sedi.get(6).getIdComune()).get().getNomeComune());
				if(sedi.get(6).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 222,
							sedi.get(6).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 212,
						sedi.get(6).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 211,
						dateFormat.format(sedi.get(6).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 198,
						sedi.get(6).getStrutturaOrgSegreteria());

				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 157,
						comuneRepository.findById(sedi.get(7).getIdComune()).get().getNomeComune());
				if(sedi.get(7).getDurataContratto() != null)
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 143,
							sedi.get(7).getDurataContratto());
				
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 131,
						sedi.get(7).getRegistrazione());
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 130,
						dateFormat.format(sedi.get(7).getDataContratto()));
				addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 118,
						sedi.get(7).getStrutturaOrgSegreteria());

				// STRUTTURA PER L'INSERIMENTO DI ALTRE SEDI DOPO LA PRIMA PAGINA
				Map<Integer, Integer> divisoriSediFile = divisoriSediFile(sedi.size() - 8);
				for (int s = 8; s < sedi.size(); s++) {
					// INSERIMENTO SPECIFICO A SECONDA DEL FILE DI RIFERIMENTO PER IL TOTALE DELLE
					// SEDI DOPO L'OTTAVA
					if (divisoriSediFile.get(8) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_8.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
								comuneRepository.findById(sedi.get(s + 3).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 3).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
									sedi.get(s + 3).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
								sedi.get(s + 3).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
								dateFormat.format(sedi.get(s + 3).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
								sedi.get(s + 3).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
								comuneRepository.findById(sedi.get(s + 4).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 4).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
									sedi.get(s + 4).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
								sedi.get(s + 4).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
								dateFormat.format(sedi.get(s + 4).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
								sedi.get(s + 4).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
								comuneRepository.findById(sedi.get(s + 5).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 5).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
									sedi.get(s + 5).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
								sedi.get(s + 5).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
								dateFormat.format(sedi.get(s + 5).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
								sedi.get(s + 5).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 238,
								comuneRepository.findById(sedi.get(s + 6).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 6).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 222,
									sedi.get(s + 6).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 212,
								sedi.get(s + 6).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 211,
								dateFormat.format(sedi.get(s + 6).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 198,
								sedi.get(s + 6).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 157,
								comuneRepository.findById(sedi.get(s + 7).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 7).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 143,
									sedi.get(s + 7).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 131,
								sedi.get(s + 7).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 130,
								dateFormat.format(sedi.get(s + 7).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 118,
								sedi.get(s + 7).getStrutturaOrgSegreteria());

						// Aggiornamento del divisore una volta utilizzato per la struttura del file con
						// le sedi di riferimento
						divisoriSediFile.put(8, divisoriSediFile.get(8) - 1);
						// Aggiornamento del indice del ciclo calcolando gli indici della lista
						// utilizzati
						s = s + 6;

					} else if (divisoriSediFile.get(7) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_7.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
								comuneRepository.findById(sedi.get(s + 3).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 3).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
									sedi.get(s + 3).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
								sedi.get(s + 3).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
								dateFormat.format(sedi.get(s + 3).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
								sedi.get(s + 3).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
								comuneRepository.findById(sedi.get(s + 4).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 4).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
									sedi.get(s + 4).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
								sedi.get(s + 4).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
								dateFormat.format(sedi.get(s + 4).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
								sedi.get(s + 4).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
								comuneRepository.findById(sedi.get(s + 5).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 5).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
									sedi.get(s + 5).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
								sedi.get(s + 5).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
								dateFormat.format(sedi.get(s + 5).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
								sedi.get(s + 5).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 238,
								comuneRepository.findById(sedi.get(s + 6).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 6).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 222,
									sedi.get(s + 6).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 212,
								sedi.get(s + 6).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 211,
								dateFormat.format(sedi.get(s + 6).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 198,
								sedi.get(s + 6).getStrutturaOrgSegreteria());

						divisoriSediFile.put(7, divisoriSediFile.get(7) - 1);
						s = s + 5;
					} else if (divisoriSediFile.get(6) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_6.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
								comuneRepository.findById(sedi.get(s + 3).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 3).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
									sedi.get(s + 3).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
								sedi.get(s + 3).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
								dateFormat.format(sedi.get(s + 3).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
								sedi.get(s + 3).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
								comuneRepository.findById(sedi.get(s + 4).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 4).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
									sedi.get(s + 4).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
								sedi.get(s + 4).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
								dateFormat.format(sedi.get(s + 4).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
								sedi.get(s + 4).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
								comuneRepository.findById(sedi.get(s + 5).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 5).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
									sedi.get(s + 5).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 293,
								sedi.get(s + 5).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 291,
								dateFormat.format(sedi.get(s + 5).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 279,
								sedi.get(s + 5).getStrutturaOrgSegreteria());

						divisoriSediFile.put(6, divisoriSediFile.get(6) - 1);
						s = s + 4;
					} else if (divisoriSediFile.get(5) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_5.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
								comuneRepository.findById(sedi.get(s + 3).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 3).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
									sedi.get(s + 3).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
								sedi.get(s + 3).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
								dateFormat.format(sedi.get(s + 3).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
								sedi.get(s + 3).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 398,
								comuneRepository.findById(sedi.get(s + 4).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 4).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 386,
									sedi.get(s + 4).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 373,
								sedi.get(s + 4).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 371,
								dateFormat.format(sedi.get(s + 4).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 359,
								sedi.get(s + 4).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 316,
								comuneRepository.findById(sedi.get(s + 5).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 5).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 304,
									sedi.get(s + 5).getDurataContratto());

						divisoriSediFile.put(5, divisoriSediFile.get(5) - 1);
						s = s + 4;
					} else if (divisoriSediFile.get(4) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_4.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 478,
								comuneRepository.findById(sedi.get(s + 3).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 3).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 464,
									sedi.get(s + 3).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 451,
								sedi.get(s + 3).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 451,
								dateFormat.format(sedi.get(s + 3).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 437,
								sedi.get(s + 3).getStrutturaOrgSegreteria());

						divisoriSediFile.put(4, divisoriSediFile.get(4) - 1);
						s = s + 3;
					} else if (divisoriSediFile.get(3) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_3.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 554,
								comuneRepository.findById(sedi.get(s + 2).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 2).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 540,
									sedi.get(s + 2).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 527,
								sedi.get(s + 2).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 527,
								dateFormat.format(sedi.get(s + 2).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 292, 513,
								sedi.get(s + 2).getStrutturaOrgSegreteria());

						divisoriSediFile.put(3, divisoriSediFile.get(3) - 1);
						s = s + 2;
					} else if (divisoriSediFile.get(2) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_2.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 625,
								comuneRepository.findById(sedi.get(s + 1).getIdComune()).get().getNomeComune());
						if(sedi.get(s + 1).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 612,
									sedi.get(s + 1).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 600,
								sedi.get(s + 1).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 600,
								dateFormat.format(sedi.get(s + 1).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 586,
								sedi.get(s + 1).getStrutturaOrgSegreteria());

						divisoriSediFile.put(2, divisoriSediFile.get(2) - 1);
						s = s + 1;
					} else if (divisoriSediFile.get(1) > 0) {
						inserimentoFile(
								"documentale_locale/modulistica_domanda/sezione_4/sedi_operative/sedi_operative_1.pdf",
								pdfStamper);

						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 154, 700,
								comuneRepository.findById(sedi.get(s).getIdComune()).get().getNomeComune());
						if(sedi.get(s).getDurataContratto() != null)
							addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 200, 685,
									sedi.get(s).getDurataContratto());
						
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 120, 673,
								sedi.get(s).getRegistrazione());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 323, 673,
								dateFormat.format(sedi.get(s).getDataContratto()));
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 294, 660,
								sedi.get(s).getStrutturaOrgSegreteria());

						divisoriSediFile.put(1, divisoriSediFile.get(1) - 1);
					}

				}

			}

			// INSERIMENTO PARTE FINALE SEZIONE 4
			inserimentoFile("documentale_locale/modulistica_domanda/sezione_4/sezione_4_fine.pdf", pdfStamper);
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 340, 629,
					richiesta.getModalitaGestioneContabile());
			// DATA DOCUMENTO
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 123, 134, dateFormat.format(new Date()));
			// INSERIMENTO DOCUMENTO POLIZZA
			Optional<StatoModuliRichiestaFigli> moduloPolizza = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiesta((long) 59, richiesta.getIdRichiesta());
			inserimentoFileByte(apiFileService.getFile(moduloPolizza.get().getDocumentIdClient(), 
					moduloPolizza.get().getContentId()), pdfStamper);
	
			return byteArrayOutputStream;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file della sezione 4");
		}
	}
	
	private boolean insertFileSpeseDiMediazione(PdfStamper pdfStamper, Richiesta richiesta) throws Exception {
		try {
			// INSERIMENTO DOCUMENTO SPESE DI MEDIAZIONE
			Optional<StatoModuliRichiestaFigli> moduloSpeseMed = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiesta((long) 59, richiesta.getIdRichiesta());
			
			// L'INSERIMENTO DEL FILE VERRA' FATTO SOLO SE LA SCELTA DI SPESE DI MEDIAZIONE E' TABELLA DI SPESA
			if(moduloSpeseMed.get().getIdRiferimento() == Long.valueOf(1)) {
				inserimentoFileByte(apiFileService.getFile(moduloSpeseMed.get().getDocumentIdClient(), 
						moduloSpeseMed.get().getContentId()), pdfStamper);
				return true;
			}
			
			return false;
		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nell'inserimento del file di spese di mediazione");
		}
		
	}
	
	private void inserimentoFile(String pathFile, PdfStamper pdfStamper) throws IOException {
		PdfReader pdfReader = new PdfReader(pathFile);
		// Per leggere il documento dell'inserimento
		for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
			pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
					pdfReader.getPageSizeWithRotation(i));
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
			pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReader, i), 0, 0);
		}
	}
	
	private void inserimentoFileByte(byte[] file, PdfStamper pdfStamper) throws IOException {
  		PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(file));
		// Per leggere il documento dell'inserimento
		for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
			pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
					pdfReader.getPageSizeWithRotation(i));
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
			pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReader, i), 0, 0);
		}
	}
	
	private void addTextPdf(PdfStamper pdfStamper, int pagina, BaseFont baseFont, float x, float y, String testo)
			throws DocumentException {
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(pagina);
		pdfContentByte.beginText();
		// Imposta il font e la dimensione
		pdfContentByte.setFontAndSize(baseFont, 12);

		// Aggiungi il nuovo testo alla posizione specificata
		pdfContentByte.moveText(x, y);
		pdfContentByte.showText(testo);

		pdfContentByte.endText();
	}
	
	
	private void addTextSmall2Pdf(PdfStamper pdfStamper, int pagina, BaseFont baseFont, float x, float y, String testo)
			throws DocumentException {
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(pagina);
		pdfContentByte.beginText();
		// Imposta il font e la dimensione
		pdfContentByte.setFontAndSize(baseFont, 6);

		// Aggiungi il nuovo testo alla posizione specificata
		pdfContentByte.moveText(x, y);
		pdfContentByte.showText(testo);

		pdfContentByte.endText();
	}
	
	private void addTextSmallPdf(PdfStamper pdfStamper, int pagina, BaseFont baseFont, float x, float y, String testo)
			throws DocumentException {
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(pagina);
		pdfContentByte.beginText();
		// Imposta il font e la dimensione
		pdfContentByte.setFontAndSize(baseFont, 9);

		// Aggiungi il nuovo testo alla posizione specificata
		pdfContentByte.moveText(x, y);
		pdfContentByte.showText(testo);

		pdfContentByte.endText();
	}
	
	// SET GRAFICA E TESTO PROGETTATO PER TABELLA A 10 COLONNE 
    private PdfPCell createCell(String contenuto, boolean isHeader) {
        // Crea un'istanza del Font con dimensioni più piccole per il testo
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 8, isHeader ? Font.BOLD : Font.NORMAL);
        PdfPCell cella = new PdfPCell(new com.itextpdf.text.Paragraph(contenuto, font));
        cella.setPadding(2);
        cella.setHorizontalAlignment(Element.ALIGN_CENTER); // Imposta l'allineamento del testo
        return cella;
    }
	
	private Map<Integer, Integer> divisoriSediFile(int numero) {
		int[] divisori = { 8, 7, 6, 5, 4, 3, 2, 1 }; // Divisori da 8 a 1
		Map<Integer, Integer> conteggi = new HashMap<>();

		for (int divisore : divisori) {
			if (numero >= divisore) {
				int divisioni = numero / divisore;
				conteggi.put(divisore, divisioni);
				numero %= divisore;
			} else {
				conteggi.put(divisore, 0);
			}
		}

		return conteggi;
	}
	
	private Optional<AnagraficaOdm> getRappresentateLegale(Long idRichiesta) {
		// PER TROVARE SOLO IL RAPPRESENTATE LEGALE NELL'ANAGRAFICHE
		return anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
	}
	
	private PdfDto getFileSedePlanimetria(Long idRichiesta, Long idSede) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloPlanimetria = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 69, idRichiesta, idSede);

		return new PdfDto(moduloPlanimetria.get().getNomeAllegato(), 
				  apiFileService.getFile(moduloPlanimetria.get().getDocumentIdClient(), moduloPlanimetria.get().getContentId()));
	}

	private PdfDto getFileSedeCopiaContratto(Long idRichiesta, Long idSede) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 70, idRichiesta, idSede);

		return new PdfDto(moduloCopiaContratto.get().getNomeAllegato(), 
				  apiFileService.getFile(moduloCopiaContratto.get().getDocumentIdClient(), moduloCopiaContratto.get().getContentId()));
	}
}
