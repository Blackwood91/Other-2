package com.giustizia.mediazionecivile.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.ModuloAnnullatoRicInteDto;
import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.dto.RichiestaIntegrazioneDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.JobRichiesta;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.RichiestaIntegrazione;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.JobRichiestaRepository;
import com.giustizia.mediazionecivile.repository.RichiestaIntegrazioneRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;
import com.giustizia.mediazionecivile.security.UtenteLoggato;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class RichiestaInvioService {
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired 
	RichiestaIntegrazioneRepository integrazioneRepository;
	@Autowired 
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	JobRichiestaRepository jobRichiestaRepository;
	@Autowired
	UserLoginRepository userLoginRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	StatusService statusService;
	@Autowired 
	ApiFileService apiFileService;
	@Autowired
	private CacheManager cacheManager;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Transactional(rollbackFor = Exception.class)
	public void invioRichiestaIntegrazione(Long idRichiesta, String motivazione) throws Exception {
		if(statusService.checkPreFinalizza(idRichiesta)) {	

			// INSERIMENTO RICHIESA
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			richiesta.get().setIdStato((long) 1);
			richiesteRepository.save(richiesta.get());
			
			// INSERIMENTO RICHIESTA DI INTEGRAZIONE
			RichiestaIntegrazione richiestaIntegrazione = new RichiestaIntegrazione();
			richiestaIntegrazione.setIdRichiesta(idRichiesta);
			richiestaIntegrazione.setMotivazione(motivazione);
			richiestaIntegrazione.setDenominazioneOdm(richiesta.get().getDenominazioneOdm());
			
			List<Object[]> result = statoModuliRichiestaFigliRepository.findAllForRichiestaIntegrazione(idRichiesta);
			String idModuliAnnullati = "";
	        for (Object[] obj : result) {
	        	idModuliAnnullati = idModuliAnnullati + (Long) obj[1];
	        }
			richiestaIntegrazione.setIdModuliAnnulati(idModuliAnnullati);
			
	        // Ottieni la data e l'ora attuali
	        LocalDateTime oraAttuale = LocalDateTime.now();
	        // Aggiungi 2 ore
	        LocalDateTime oraIncrementata = oraAttuale.plusHours(2);
	        // Converti LocalDateTime in ZonedDateTime usando il fuso orario di sistema
	        ZonedDateTime oraZonata = oraIncrementata.atZone(ZoneId.systemDefault());
	        // Converti ZonedDateTime in Date
	        Date dataIncrementata = Date.from(oraZonata.toInstant());

			richiestaIntegrazione.setDataRichiesta(dataIncrementata);
			
			RichiestaIntegrazione richiestaIntegrazioneSave = integrazioneRepository.save(richiestaIntegrazione);
			
	        // CREAZIONE DEL DOCUMENTO PDF
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        Document document = new Document(PageSize.A3);
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();
	        
	        // HEADER PAGINA
	        Font fontTitolo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	        
	        RichiestaIntegrazioneDto richiestaInte = this.getRichiestaIntegrazione(idRichiesta);
	        
	        Paragraph paragraphNumDatRic = new Paragraph("RICHIESTA NR. " + idRichiesta + " DEL: " + dateFormat.format(richiestaInte.getDataRichiesta()), fontTitolo);
	        paragraphNumDatRic.setAlignment(Element.ALIGN_CENTER);
	        document.add(paragraphNumDatRic);
            document.add(new Paragraph("\n"));
            
            String testTipoRichiesta = "";
            switch(richiestaInte.getIdTipoRichiesta()) {
            	case 1:
            		testTipoRichiesta = "Organismo di Mediazione";
            		break;
            	default:
            		testTipoRichiesta = "Errore";
            		break;
            }
            
	        Paragraph paragraphTipRic = new Paragraph("TIPO RICHIESTA: " + testTipoRichiesta, fontTitolo);
	        paragraphTipRic.setAlignment(Element.ALIGN_CENTER);
	        document.add(paragraphTipRic);
            document.add(new Paragraph("\n"));
            
	        Paragraph paragraphRichieRic = new Paragraph("RICHIEDENTE: " + richiestaInte.getUserRichiesta(), fontTitolo);
	        paragraphRichieRic.setAlignment(Element.ALIGN_CENTER);
	        document.add(paragraphRichieRic);
            document.add(new Paragraph("\n"));
            
	        Paragraph paragraphTitolo = new Paragraph("MOTIVAZIONI RICHIESTA DI INTEGRAZIONE", fontTitolo);
	        paragraphTitolo.setAlignment(Element.ALIGN_LEFT);
	        document.add(paragraphTitolo);
            document.add(new Paragraph("\n"));

	        // MOTIVAZIONE
	        Font font = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
	        Paragraph paragraphMotivazione = new Paragraph(motivazione, font);
	        paragraphTitolo.setAlignment(Element.ALIGN_LEFT);
	        document.add(paragraphMotivazione);

	        // CHIUSURE A FINE OPERAZIONI
	        document.close();
	        byteArrayOutputStream.close();

			// SALVATAGGIO IN UN PRIMO MOMENTE SENZA GLI ID DI RIFERIMENTO A MERCURIO	
			Optional<JobRichiesta> jobRichiesta = Optional.of(new JobRichiesta());
			jobRichiesta.get().setIdRichiesta(idRichiesta);
			jobRichiesta.get().setTipoJob("GENERAZIONE PDF");
			jobRichiesta.get().setStatoJob(null);
			jobRichiesta.get().setTipoRichiesta("ODM");
			// VALORIZZATO TRAMITE L'UTENTE LOGGATO
			UtenteLoggato user = (UtenteLoggato) cacheManager.getCache("cacheVE").get("utenteIAMG").get();
			jobRichiesta.get().setIdUtenteRichiedente(userLoginRepository.findByCodiceFiscale(user.getCodiceFiscale()).getId());
			
			jobRichiesta.get().setDataOraRichiesta(dataIncrementata);		

			jobRichiestaRepository.save(jobRichiesta.get());
	
	        StatoModuliRichiestaFigli modulo = new StatoModuliRichiestaFigli();
	        modulo.setIdModulo((long) 86);
	        modulo.setIdRichiesta(idRichiesta);
	        modulo.setIdRiferimento(richiestaIntegrazioneSave.getId());
	        
	        StatoModuliRichiestaFigli moduloSave = statoModuliRichiestaFigliRepository.save(modulo);
	        
			String pathModulo = "/" + moduloSave.getIdRichiesta() + "/odm/" + moduloSave.getIdModulo();
			MercurioFile infoFile = apiFileService.insertFile(pathModulo, 
									Long.toString(moduloSave.getId()), byteArrayOutputStream.toByteArray());
			moduloSave.setDocumentIdClient(infoFile.getDocumentIdClient());
			moduloSave.setContentId(infoFile.getContentId());
			moduloSave.setDataInserimento(new Date());
    
			statoModuliRichiestaFigliRepository.save(moduloSave);        
		} 
		else {
			throw new RuntimeException("-ErrorInfo Per proseguire bisogna prima validare o rifiutare tutti i moduli");
		}
	
	}
	
	public RichiestaIntegrazioneDto getRichiestaIntegrazione(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		Optional<AnagraficaOdm> anagraficaRapL = anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
		Optional<RichiestaIntegrazione> richistaInte = integrazioneRepository.findFirstByIdRichiestaOrderByDataRichiestaDesc(idRichiesta);
		
		RichiestaIntegrazioneDto richiestaIntegrazioneDto = new RichiestaIntegrazioneDto();
		richiestaIntegrazioneDto.setIdTipoRichiesta(richiesta.get().getIdTipoRichiesta());
		// TORNERA' SOLO SE GIA E' STATA INVIATA UNA RICHIESTA DI INTEGRAZIONE
		if(richistaInte.isPresent()) {
			richiestaIntegrazioneDto.setDataRichiesta(richistaInte.get().getDataRichiesta());
		}
		richiestaIntegrazioneDto.setUserRichiesta(anagraficaRapL.get().getNome() + " " + anagraficaRapL.get().getCognome());
		
		return richiestaIntegrazioneDto;
	}
	
	public boolean activeInviaRic(Long idRichiesta) {
		Optional<JobRichiesta> jobRichiesta =  jobRichiestaRepository.
				findFirstByIdRichiestaAndTipoRichiestaOrderByDataOraRichiestaDesc(idRichiesta, "ODM");
		
		if(jobRichiesta.isPresent() == false) {
			return true;
		}
		else {
			if(jobRichiesta.get().getStatoJob() != null &&
			   jobRichiesta.get().getStatoJob().equalsIgnoreCase("aperta")) {
				return true;
			}
			else {
				return false;
			}
		}
		
	}
	
	public PdfDto dwonloadFileRI(Long idRichiesta) {
		Optional<RichiestaIntegrazione> richiestaIntegrazione = integrazioneRepository.findFirstByIdRichiestaOrderByDataRichiestaDesc(idRichiesta);
		Optional<StatoModuliRichiestaFigli> moduloRicInt = statoModuliRichiestaFigliRepository
				.findFirstByIdModuloAndIdRichiestaAndIdRiferimentoOrderByDataInserimentoDesc((long) 86, idRichiesta, richiestaIntegrazione.get().getId());	
		
		 return new PdfDto("richiesta_integrazione", 
				    apiFileService.getFile(moduloRicInt.get().getDocumentIdClient(), moduloRicInt.get().getContentId()));
	}
	
}
