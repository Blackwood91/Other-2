package com.giustizia.mediazionecivile.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.Sede;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.ComuneRepository;
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
import com.giustizia.mediazionecivile.security.UtenteLoggato;
import com.giustizia.mediazionecivile.utility.Moduli;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {
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
	ModalitaCostituzioneOrganismoRepository costituzioneOrganismoRepository;
	@Autowired
	ApiFileService apiFileService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public PdfDto getFileModulo(Long idModFiglio) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloOdm = statoModuliRichiestaFigliRepository.findById(idModFiglio);
			return new PdfDto(moduloOdm.get().getNomeAllegato(), 
							  apiFileService.getFile(moduloOdm.get().getDocumentIdClient(), moduloOdm.get().getContentId()));
	}
	
	public PdfDto getFileModuloDomanda(Long idRichiesta) throws Exception {
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
				.findByIdModuloAndIdRichiesta(Moduli.SEPESE_DI_MEDIAZIONE.idModulo, richiesta.get().getIdRichiesta());
		
		// IL MODULO IN UN PRIMO MOMENTO POTREBEE NON ESISTERE O ESSERE VALORIZZATO
		if(moduloSpeseMed.isPresent()) {
			if( moduloSpeseMed.get().getIdRiferimento() == Long.valueOf(1)) {
				addTextPdf(pdfStamper, 1, baseFont, 121, 313, "X");
			}
			else {
				addTextPdf(pdfStamper, 1, baseFont, 121, 295, "X");
			}
		}
		
		String dataDocumento = dateFormat.format(new Date());
		addTextPdf(pdfStamper, 2, baseFont, 100, 294, dataDocumento);
		
		pdfStamper.close();
		
		return new PdfDto("domanda_di_iscrizione", byteArrayOutputStream.toByteArray());
	}

	public PdfDto getFileSedePlanimetria(Long idRichiesta, Long idSede) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloPlanimetria = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 69, idRichiesta, idSede);

		return new PdfDto(moduloPlanimetria.get().getNomeAllegato(), 
				  apiFileService.getFile(moduloPlanimetria.get().getDocumentIdClient(), moduloPlanimetria.get().getContentId()));
	}

	public PdfDto getFileSedeCopiaContratto(Long idRichiesta, Long idSede) throws Exception {
		Optional<StatoModuliRichiestaFigli> moduloCopiaContratto = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdRiferimento((long) 70, idRichiesta, idSede);

		return new PdfDto(moduloCopiaContratto.get().getNomeAllegato(), 
				  apiFileService.getFile(moduloCopiaContratto.get().getDocumentIdClient(), moduloCopiaContratto.get().getContentId()));
	}

	public List<PdfDto> getFileRappresentante(Long idRichiesta, Long idAnagrafica) throws Exception {
		List<PdfDto> allFile =  new ArrayList<PdfDto>();
		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoQuaificaMed = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, idAnagrafica);

		if(moduloAttoDocumento.isPresent() && apiFileService.existFile(moduloAttoDocumento.get().getDocumentIdClient(),
											   moduloAttoDocumento.get().getContentId())) {
			allFile.add(new PdfDto(moduloAttoDocumento.get().getNomeAllegato(), apiFileService.getFile(moduloAttoDocumento.get().getDocumentIdClient(),
																				moduloAttoDocumento.get().getContentId())));
			// Al contrario del documento questo file non è obbligatorio
			if(moduloAttoQuaificaMed.isPresent() && apiFileService.existFile(moduloAttoQuaificaMed.get().getDocumentIdClient(),
													moduloAttoQuaificaMed.get().getContentId())) {
				allFile.add(new PdfDto(moduloAttoQuaificaMed.get().getNomeAllegato(), apiFileService.getFile(moduloAttoQuaificaMed.get().getDocumentIdClient(),
																					  moduloAttoQuaificaMed.get().getContentId())));
			}
		} 
		
		return allFile;
	}
	
	public List<PdfDto> getFileCompOrgAm(Long idRichiesta, Long idAnagrafica) throws Exception {
		List<PdfDto> allFile =  new ArrayList<PdfDto>();
		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, idAnagrafica);

		if(moduloAttoDocumento.isPresent()) {
			allFile.add(new PdfDto(moduloAttoDocumento.get().getNomeAllegato(), apiFileService.getFile(moduloAttoDocumento.get().getDocumentIdClient(),
																				moduloAttoDocumento.get().getContentId())));
		} 
		
		return allFile;
	}
	
	public List<PdfDto> getFilePrestatore(Long idRichiesta, Long idAnagrafica) throws Exception {
		List<PdfDto> allFile =  new ArrayList<PdfDto>();
		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = statoModuliRichiestaFigliRepository
				.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, idAnagrafica);

		if(moduloAttoDocumento.isPresent() && apiFileService.existFile(moduloAttoDocumento.get().getDocumentIdClient(),
											   moduloAttoDocumento.get().getContentId())) {
			allFile.add(new PdfDto(moduloAttoDocumento.get().getNomeAllegato(), apiFileService.getFile(moduloAttoDocumento.get().getDocumentIdClient(),
				moduloAttoDocumento.get().getContentId())));
			} 
		
		return allFile;
	}
	
	public List<PdfDto> getFileMediatore(Long idRichiesta, Long idAnagrafica) throws Exception {		
		List<PdfDto> allFile =  new ArrayList<PdfDto>();
		Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
		Optional<StatoModuliRichiestaFigli> moduloAttoDocumento = Optional.empty();
		
		if(anagrafica.get().getIdTipoAnagrafica() == 4) {
			moduloAttoDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, idAnagrafica);
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 5) {
			moduloAttoDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, idAnagrafica);
		}
		if(anagrafica.get().getIdTipoAnagrafica() == 6) {
			moduloAttoDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, idAnagrafica);
		}

		if(moduloAttoDocumento.isPresent() && apiFileService.existFile(moduloAttoDocumento.get().getDocumentIdClient(),
				   moduloAttoDocumento.get().getContentId())) {
			allFile.add(new PdfDto(moduloAttoDocumento.get().getNomeAllegato(), apiFileService.getFile(moduloAttoDocumento.get().getDocumentIdClient(),
					moduloAttoDocumento.get().getContentId())));
		} 
		return allFile;
	}

	public PdfDto getAnteprimaFileAttoRiepOdm(Long idRichiesta) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);

			if (richiesta.isPresent()) {
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

				return new PdfDto("Anteprima_domanda_di_iscrizione", byteArrayOutputStream.toByteArray());
			} else {
				throw new RuntimeException("-ErrorInfo Non è stata trovata nessuna richiesta associata");
			}

		} catch (Exception e) {
			throw new RuntimeException("-ErrorInfo Si è verificato un errore non previsto");
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
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 340, 629, richiesta.getModalitaGestioneContabile());
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
	
	public PdfDto getAnteprimaFileSchedeRapLegRespOrg(Long idRichiesta) throws Exception {
		try {
			List<AnagraficaOdm> anagraficheRespOrg = anagraficaOdmRepository.findAllByIdRichiestaAndIdQualifica(idRichiesta, (long)2);
			Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
			List<AnagraficaOdm> anagrafiche = anagraficheRespOrg;
			Optional<AnagraficaOdm> anagraficaClone = Optional.empty();	
			boolean existClone = false;
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 			
			String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio_con_intestazione.pdf";
			PdfReader pdfReader = new PdfReader(inputFile);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);	
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);	
			
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
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			for (int a = 0; a < anagrafiche.size(); a++) {		
				AnagraficaOdm anagrafica = anagrafiche.get(a);
				
				Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
				Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
	
				if(a != 0) {
					inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio.pdf";
					PdfReader pdfReaderNewScheda = new PdfReader(inputFile);
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							pdfReaderNewScheda.getPageSizeWithRotation(1));
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderNewScheda, 1), 0, 0);
				}
				
				if(anagrafica.getIdQualifica() == 1 || (anagraficaClone.isPresent() &&
														anagraficaClone.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) ) {
					// RAPPRESENTANTE LEGALE
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 204, 595, "X");		
				}
				if(anagrafica.getIdQualifica() == 2 || (anagraficaClone.isPresent() &&
														anagraficaClone.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) ) {
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
				
				inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(),
									moduloDocumento.get().getContentId()), pdfStamper);
				
				if(anagrafica.getIdQualifica() == 2) {
					inserimentoFileByte(apiFileService.getFile(moduloQualificaMed.get().getDocumentIdClient(), 
										moduloQualificaMed.get().getContentId()), pdfStamper);
				}
			}
			
			pdfStamper.close();

			return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore non previsto nella compilazione del file");
		}
	}
	
	public PdfDto getAnteprimaFileSchedeCompOrgAm(Long idRichiesta) throws Exception {
		try {
			List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaForCompOrgAm(idRichiesta);
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 			
			String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio_con_intestazione.pdf";
			PdfReader pdfReader = new PdfReader(inputFile);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);	
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);	
			
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			for (int a = 0; a < anagrafiche.size(); a++) {		
				AnagraficaOdm anagrafica = anagrafiche.get(a);
				
				Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 33, idRichiesta, anagrafica.getIdAnagrafica());
	
				if(a != 0) {
					inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio.pdf";
					PdfReader pdfReaderNewScheda = new PdfReader(inputFile);
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							pdfReaderNewScheda.getPageSizeWithRotation(1));
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderNewScheda, 1), 0, 0);
				}
				

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
				
				inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(),
									moduloDocumento.get().getContentId()), pdfStamper);
			}
			
			pdfStamper.close();

			return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore non previsto nella compilazione del file");
		}
	}
	
	public PdfDto getAnteprimaFileAutocertificazione(Long idRichiesta, Long idAnagrafica, Long idModulo) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			if(moduloAttoReqOnora.isPresent()) {
				if(moduloAttoReqOnora.get().getCompletato() != null && moduloAttoReqOnora.get().getCompletato() == 1) {
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
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.get().getNome() + " " + anagrafica.get().getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.get().getDataNascita()));
					
					if(anagrafica.get().getIdComuneNascita() != null && anagrafica.get().getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.get().getComuneNascitaEstero() + 
								" (" + anagrafica.get().getStatoNascita() + ")");
					}
					
					if(anagrafica.get().getIdComuneResidenza() != null && anagrafica.get().getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getIndirizzo() + " " +
								anagrafica.get().getNumeroCivico() + " " +
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getComuneResidenzaEstero() + 
								" (" + anagrafica.get().getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.get().getCodiceFiscale());

					if(anagrafica.get().getIdQualifica() == null || anagrafica.get().getIdQualifica() == 0) {
						String tipoAnagrafica = "";
						switch(anagrafica.get().getIdTipoAnagrafica().intValue()) {
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
					}
					else {
						Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
						// IN CASO DI STESSO RAP.LEGALE PER RESP ORGANISMO
						if(anagrafica.get().getIdAnagrafica() != anagraficaRapLeg.get().getIdAnagrafica() &&
						   anagraficaRapLeg.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.get().getCodiceFiscale()))
							addTextSmallPdf(pdfStamper, 1, baseFont, 350, 615, 
									qualificaRepository.findById((long) 1).get().getQualifica() + " e " + 
									qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica());
						else
							addTextSmallPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica());
					}
					
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
				//	addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					pdfStamper.close();
					return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());
				}
			}
			
			return null;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file dell'autocertificazione");
		}
	}
	
	public PdfDto getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(Long idRichiesta, Long idAnagrafica) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 32, idRichiesta, idAnagrafica);
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			if(moduloAttoReqOnora.isPresent()) {
				if(moduloAttoReqOnora.get().getCompletato() != null && moduloAttoReqOnora.get().getCompletato() == 1) {
					Document document = new Document();
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
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.get().getNome() + " " + anagrafica.get().getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.get().getDataNascita()));
					
					if(anagrafica.get().getIdComuneNascita() != null && anagrafica.get().getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.get().getComuneNascitaEstero() + 
								" (" + anagrafica.get().getStatoNascita() + ")");
					}
					
					if(anagrafica.get().getIdComuneResidenza() != null && anagrafica.get().getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getIndirizzo() + " " +
								anagrafica.get().getNumeroCivico() + " " +
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getComuneResidenzaEstero() + 
								" (" + anagrafica.get().getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.get().getCodiceFiscale());
					if(anagrafica.get().getDescQualifica() == null || anagrafica.get().getDescQualifica().isEmpty()) {
						addTextPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica() );
					}
					else {
						addTextPdf(pdfStamper, 1, baseFont, 354, 615, anagrafica.get().getDescQualifica());
					}
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
		
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					pdfStamper.close();
					return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());
				}
			}
			
			return null;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file dell'autocertificazione");
		}
	}
	
	public PdfDto getAnteprimaFileAutocertificazioneAppe(Long idRichiesta, Long idAnagrafica, long idModulo) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
			Optional<StatoModuliRichiestaFigli> moduloAttoReqOnora = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica(idModulo, idRichiesta, idAnagrafica);
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			if(moduloAttoReqOnora.isPresent()) {
				if(moduloAttoReqOnora.get().getCompletato() != null && moduloAttoReqOnora.get().getCompletato() == 1) {
					Document document = new Document();
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
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.get().getNome() + " " + anagrafica.get().getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.get().getDataNascita()));
					
					if(anagrafica.get().getIdComuneNascita() != null && anagrafica.get().getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.get().getComuneNascitaEstero() + 
								" (" + anagrafica.get().getStatoNascita() + ")");
					}
					
					if(anagrafica.get().getIdComuneResidenza() != null && anagrafica.get().getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getIndirizzo() + " " +
								anagrafica.get().getNumeroCivico() + " " +
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getComuneResidenzaEstero() + 
								" (" + anagrafica.get().getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.get().getCodiceFiscale());
					addTextPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica() );
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
		
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					pdfStamper.close();
					return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());
				}
			}
			
			return null;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file dell'autocertificazione");
		}
	}
	
	public PdfDto getAnteprimaFileDisponibilitaAppe(Long idRichiesta, Long idAnagrafica, Long idModulo) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
			Optional<StatoModuliRichiestaFigli> moduloDisponi = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) idModulo, idRichiesta, idAnagrafica);
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			if(moduloDisponi.isPresent()) {
				if(moduloDisponi.get().getCompletato() != null && moduloDisponi.get().getCompletato() == 1) {
					Document document = new Document();
					// PATH DOCUMENTO IN SISTEMA
					String inputFile = "documentale_locale/modulistica_domanda/allegato_2.pdf";
					// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
					// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
					PdfReader pdfReader = new PdfReader(inputFile);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			
					// SET FONT DA INSERIRE
					BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
					// Aggiungi testo sotoscritto
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.get().getNome() + " " + anagrafica.get().getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.get().getDataNascita()));
					
					if(anagrafica.get().getIdComuneNascita() != null && anagrafica.get().getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.get().getComuneNascitaEstero() + 
								" (" + anagrafica.get().getStatoNascita() + ")");
					}
					
					if(anagrafica.get().getIdComuneResidenza() != null && anagrafica.get().getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getIndirizzo() + " " +		
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getComuneResidenzaEstero() + 
								" (" + anagrafica.get().getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.get().getCodiceFiscale());
					addTextPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica() );
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
		
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					pdfStamper.close();
					return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());
				}
			}
			
			return null;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file dell'autocertificazione");
		}
	}
	
	public PdfDto getAnteprimaFileDichiaSostAppe(Long idRichiesta, Long idAnagrafica, Long idModulo) throws Exception {
		try {
			Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
			Optional<AnagraficaOdm> anagrafica = anagraficaOdmRepository.findByidAnagrafica(idAnagrafica);
			Optional<StatoModuliRichiestaFigli> moduloDisponi = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) idModulo, idRichiesta, idAnagrafica);
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			if(moduloDisponi.isPresent()) {
				if(moduloDisponi.get().getCompletato() != null && moduloDisponi.get().getCompletato() == 1) {
					Document document = new Document();
					// PATH DOCUMENTO IN SISTEMA
					String inputFile = "documentale_locale/modulistica_domanda/allegato_3.pdf";
					// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
					// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
					PdfReader pdfReader = new PdfReader(inputFile);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
			
					// SET FONT DA INSERIRE
					BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
					// Aggiungi testo sotoscritto
					addTextPdf(pdfStamper, 1, baseFont, 145, 654, anagrafica.get().getNome() + " " + anagrafica.get().getCognome());
					addTextPdf(pdfStamper, 1, baseFont, 98, 641, dateFormat.format(anagrafica.get().getDataNascita()));
					
					if(anagrafica.get().getIdComuneNascita() != null && anagrafica.get().getIdComuneNascita() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneNascita());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641,
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 210, 641, anagrafica.get().getComuneNascitaEstero() + 
								" (" + anagrafica.get().getStatoNascita() + ")");
					}
					
					if(anagrafica.get().getIdComuneResidenza() != null && anagrafica.get().getIdComuneResidenza() != 0) {
						Optional<Comune> comune = comuneRepository.findById(anagrafica.get().getIdComuneResidenza());
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getIndirizzo() + " " +		
								comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")");
					}
					else {
						addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 122, 628, anagrafica.get().getComuneResidenzaEstero() + 
								" (" + anagrafica.get().getStatoResidenza() + ")");
					}
						
					addTextPdf(pdfStamper, 1, baseFont, 85, 615, anagrafica.get().getCodiceFiscale());
					addTextPdf(pdfStamper, 1, baseFont, 354, 615, qualificaRepository.findById(anagrafica.get().getIdQualifica()).get().getQualifica() );
					addTextPdf(pdfStamper, 1, baseFont, 194, 603, richiesta.get().getDenominazioneOdm() != null ? richiesta.get().getDenominazioneOdm() : "");
		
					//addTextPdf(pdfStamper, 1, baseFont, 95, 237, "Roma");
					String dataDocumento = dateFormat.format(new Date());
					addTextPdf(pdfStamper, 1, baseFont, 230, 237, dataDocumento);
					pdfStamper.close();
					return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());
				}
			}
			
			return null;

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore nella compilazione del file dell'autocertificazione");
		}
	}
	
	public PdfDto getAnteprimaFileElencoMediatori(Long idRichiesta) throws Exception {
		try {
			List<AnagraficaOdm> mediatoriGenerici = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);
			List<AnagraficaOdm> mediatoriInter = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);
			List<AnagraficaOdm> mediatoriMatCons = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);
			
	        // Creazione del documento PDF
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A3);
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            
            // ELENCO MEDIATORI GENERICI
            // AGGIUNGE TITOLO TABELLA
            Font fontTitolo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Paragraph paragraphTitolo = new Paragraph("ELENCO MEDIATORI GENERICI", fontTitolo);
            paragraphTitolo.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitolo);

            // AGGIUNGE SPZIO BOTTOM
            document.add(new Paragraph("\n"));

            // CREAZIONE DELLA TABELLA CON NUMERO COLONNE
            PdfPTable table = new PdfPTable(10); 
            // LARGHEZZA DELLA PAGINA
            table.setWidthPercentage(100); 
            
            // AGGIUNGI INTESTAZIONE COLONNE
            table.addCell(createCell("Cognome", true));
            table.addCell(createCell("Nome", true));
            table.addCell(createCell("Codice fiscale", true));
            table.addCell(createCell("Sesso", true));
            table.addCell(createCell("Data di nascita", true));
            table.addCell(createCell("Comune di nascita", true));
            table.addCell(createCell("Comune di residenza", true));
            table.addCell(createCell("Indirizzo Email", true));
            table.addCell(createCell("Pec", true));
            table.addCell(createCell("Num. Organismi", true));

            // Aggiungi dati alla tabella
            for (AnagraficaOdm anagrafica : mediatoriGenerici) {
                table.addCell(createCell(anagrafica.getCognome(), false));
                table.addCell(createCell(anagrafica.getNome(), false));
                table.addCell(createCell(anagrafica.getCodiceFiscale(), false));
                table.addCell(createCell(anagrafica.getSesso(), false));
                table.addCell(createCell(anagrafica.getDataNascita() != null ? dateFormat.format(anagrafica.getDataNascita()) : "", false));
                
    			if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneNascitaEstero() + " (" + anagrafica.getStatoNascita() + ")", false));
    			}
                
       			if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneResidenzaEstero() + " (" + anagrafica.getStatoResidenza() + ")", false));
    			}
                
                table.addCell(createCell(anagrafica.getIndirizzoEmail(), false));
                table.addCell(createCell(anagrafica.getIndirizzoPec(), false));
                table.addCell(createCell(anagrafica.getMedNumeroOrganismiDisp() != null ? anagrafica.getMedNumeroOrganismiDisp().toString() : "", false));

            }
            // AGGIUNTA TABELLA MEDIATORI GENERICI NEL DOCUMENTO
            document.add(table);
            
            // ELENCO MEDIATORI INTERNAZIONALI
            fontTitolo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            paragraphTitolo = new Paragraph("ELENCO MEDIATORI INTERNAZIONALI", fontTitolo);
            paragraphTitolo.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitolo);
            // AGGIUNGE SPZIO BOTTOM
            document.add(new Paragraph("\n"));
            // CREAZIONE DELLA TABELLA CON NUMERO COLONNE
            table = new PdfPTable(10); 
            // LARGHEZZA DELLA PAGINA
            table.setWidthPercentage(100); 
            
            // AGGIUNGI INTESTAZIONE COLONNE
            table.addCell(createCell("Cognome", true));
            table.addCell(createCell("Nome", true));
            table.addCell(createCell("Codice fiscale", true));
            table.addCell(createCell("Sesso", true));
            table.addCell(createCell("Data di nascita", true));
            table.addCell(createCell("Comune di nascita", true));
            table.addCell(createCell("Comune di residenza", true));
            table.addCell(createCell("Indirizzo Email", true));
            table.addCell(createCell("Pec", true));
            table.addCell(createCell("Num. Organismi", true));

            // Aggiungi dati alla tabella
            for (AnagraficaOdm anagrafica : mediatoriInter) {
                table.addCell(createCell(anagrafica.getCognome(), false));
                table.addCell(createCell(anagrafica.getNome(), false));
                table.addCell(createCell(anagrafica.getCodiceFiscale(), false));
                table.addCell(createCell(anagrafica.getSesso(), false));
                table.addCell(createCell(anagrafica.getDataNascita() != null ? dateFormat.format(anagrafica.getDataNascita()) : "", false));
                
    			if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneNascitaEstero() + " (" + anagrafica.getStatoNascita() + ")", false));
    			}
                
       			if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneResidenzaEstero() + " (" + anagrafica.getStatoResidenza() + ")", false));
    			}
                
                table.addCell(createCell(anagrafica.getIndirizzoEmail(), false));
                table.addCell(createCell(anagrafica.getIndirizzoPec(), false));
                table.addCell(createCell(anagrafica.getMedNumeroOrganismiDisp() != null ? anagrafica.getMedNumeroOrganismiDisp().toString() : "", false));

            }

            // AGGIUNTA TABELLA COMPLETA NEL DOCUMENTO
            document.add(table);
            
            // ELENCO MEDIATORI MATERIA DI CONSUMO
            fontTitolo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            paragraphTitolo = new Paragraph("ELENCO MEDIATORI INTERNAZIONALI", fontTitolo);
            paragraphTitolo.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTitolo);
            // AGGIUNGE SPZIO BOTTOM
            document.add(new Paragraph("\n"));
            // CREAZIONE DELLA TABELLA CON NUMERO COLONNE
            table = new PdfPTable(10); 
            // LARGHEZZA DELLA PAGINA
            table.setWidthPercentage(100); 
            
            // AGGIUNGI INTESTAZIONE COLONNE
            table.addCell(createCell("Cognome", true));
            table.addCell(createCell("Nome", true));
            table.addCell(createCell("Codice fiscale", true));
            table.addCell(createCell("Sesso", true));
            table.addCell(createCell("Data di nascita", true));
            table.addCell(createCell("Comune di nascita", true));
            table.addCell(createCell("Comune di residenza", true));
            table.addCell(createCell("Indirizzo Email", true));
            table.addCell(createCell("Pec", true));
            table.addCell(createCell("Num. Organismi", true));

            // Aggiungi dati alla tabella
            for (AnagraficaOdm anagrafica : mediatoriMatCons) {
                table.addCell(createCell(anagrafica.getCognome(), false));
                table.addCell(createCell(anagrafica.getNome(), false));
                table.addCell(createCell(anagrafica.getCodiceFiscale(), false));
                table.addCell(createCell(anagrafica.getSesso(), false));
                table.addCell(createCell(anagrafica.getDataNascita() != null ? dateFormat.format(anagrafica.getDataNascita()) : "", false));
                
    			if(anagrafica.getIdComuneNascita() != null && anagrafica.getIdComuneNascita() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneNascita());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneNascitaEstero() + " (" + anagrafica.getStatoNascita() + ")", false));
    			}
                
       			if(anagrafica.getIdComuneResidenza() != null && anagrafica.getIdComuneResidenza() != 0) {
    				Optional<Comune> comune = comuneRepository.findById(anagrafica.getIdComuneResidenza());
                    table.addCell(createCell(comune.get().getNomeComune() + " (" + comune.get().getRegioneProvince().getSiglaProvincia() + ")", false));
    			}
    			else {
                    table.addCell(createCell(anagrafica.getComuneResidenzaEstero() + " (" + anagrafica.getStatoResidenza() + ")", false));
    			}
                
                table.addCell(createCell(anagrafica.getIndirizzoEmail(), false));
                table.addCell(createCell(anagrafica.getIndirizzoPec(), false));
                table.addCell(createCell(anagrafica.getMedNumeroOrganismiDisp() != null ? anagrafica.getMedNumeroOrganismiDisp().toString() : "", false));

            }

            // AGGIUNTA TABELLA COMPLETA NEL DOCUMENTO
            document.add(table);

            // CHIUSURE A FINE OPERAZIONI
            document.close();
            byteArrayOutputStream.close();

			return new PdfDto("Anteprima_elenco_mediatori", byteArrayOutputStream.toByteArray());
			 
		} catch (Exception e) {
			throw new RuntimeException("-ErrorInfo Si è verificato un errore non previsto");
		}

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

	public void checkValidLoadDocument(byte[] pdfData) throws Exception {
		PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfData));
		PDFRenderer renderer = new PDFRenderer(document);

		for (int i = 0; i < document.getNumberOfPages(); i++) {
			BufferedImage image = renderer.renderImageWithDPI(i, 300);
			// Farà i controlli su file prima di essere caricato, in caso di esito negativo
			// generà errore
			isValidDocument(image);
		}

		document.close();
	}

	public static boolean isValidDocument(BufferedImage image) throws Exception {
		int width = image.getWidth();
		int height = image.getHeight();

		// Calcola la risoluzione DPI
		double dpiX = (double) width / (double) image.getWidth() * 25.4; // Converti da pixel per millimetro a pixel per
																			// pollice (25.4 millimetri per pollice)
		double dpiY = (double) height / (double) image.getHeight() * 25.4; // Converti da pixel per millimetro a pixel
																			// per pollice (25.4 millimetri per pollice)

		// Verifica se la risoluzione è inferiore a 201 DPI
		boolean isUnder200DPI = dpiX < 201 || dpiY < 201;
		if (isUnder200DPI == false) {
			throw new Exception("-ErrorInfo Il documento è maggiore 200 DPI");
		}

		// Iterare su ogni pixel dell'immagine
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Ottenere il colore del pixel
				int color = image.getRGB(x, y);

				// Separare i componenti rosso, verde e blu del colore
				int red = (color >> 16) & 0xFF;
				int green = (color >> 8) & 0xFF;
				int blue = color & 0xFF;

				// Se i componenti rosso, verde e blu sono diversi, l'immagine non è in bianco e
				// nero
				if (red != green || red != blue || green != blue) {
					throw new Exception("-ErrorInfo Il documento non è totalmente in bianco e nero");
				}
			}
		}
		// SE E' STATO PROCESSATO FINO A QUESTO PUNTO IL DOCUMENTO E' VALIDO
		return true;
	}
	
	public PdfDto getAnteprimaFilePolizzaAss(Long idRichiesta) throws Exception {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);

		// PATH DOCUMENTO IN SISTEMA
		String inputFile = "documentale_locale/modulistica_domanda/polizza_assicurativa.pdf";
		// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
		PdfReader pdfReader = new PdfReader(inputFile);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);

		// DATI POLIZZA
		addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 130, 741,
				   richiesta.get().getDataStipulaPoliz() != null ? dateFormat.format(richiesta.get().getDataStipulaPoliz()) : "");
		addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 50, 727,
				   richiesta.get().getCompagniaAss() != null ? richiesta.get().getCompagniaAss() : "");
		addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 250, 712, 
				   richiesta.get().getMassimaleAssic() != null ? richiesta.get().getMassimaleAssic().toString().replace(".", ",") : ""); 
		addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 153, 683,
				   dateFormat.format(richiesta.get().getScadenzaPoliza()));
		
		pdfStamper.close();
		
		return new PdfDto("polizza_assicurativa", byteArrayOutputStream.toByteArray());
	}
	
	public PdfDto getAnteprimaFilePrestaSerOpe(Long idRichiesta) throws Exception {
	    List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta,
	            (long) 3);

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
        
	    for (AnagraficaOdm anagrafica : anagrafiche) {
	        Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
	                .findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 37, idRichiesta, anagrafica.getIdAnagrafica());

	        byte[] pdfBytes = apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(),
	                moduloDocumento.get().getContentId());
	        
	        inserimentoFileByte(pdfBytes, pdfStamper);
	    }
        
		pdfStamper.close();

	    // Restituzione del documento PDF finale
	    return new PdfDto("Anteprima_elenco_prestatori", byteArrayOutputStream.toByteArray());
	}
	
	public PdfDto getAnteprimaFileSchedeMediatori(Long idRichiesta) throws Exception {
		try {
			List<AnagraficaOdm> anagraficheRespOrg = anagraficaOdmRepository.findAllByIdRichiestaAndIdQualifica(idRichiesta, (long)2);
			Optional<AnagraficaOdm> anagraficaRapLeg = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)1);	
			List<AnagraficaOdm> anagrafiche = anagraficheRespOrg;
			Optional<AnagraficaOdm> anagraficaClone = Optional.empty();	
			boolean existClone = false;
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 			
			String inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio_con_intestazione.pdf";
			PdfReader pdfReader = new PdfReader(inputFile);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);	
			BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);	
			
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
			
			// SOLO SE CONVALIDATO POTRA' ESSERE VISTA L'ANTEPRIMA DELLA STAMPA
			for (int a = 0; a < anagrafiche.size(); a++) {		
				AnagraficaOdm anagrafica = anagrafiche.get(a);
				
				Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 30, idRichiesta, anagrafica.getIdAnagrafica());
				Optional<StatoModuliRichiestaFigli> moduloQualificaMed = statoModuliRichiestaFigliRepository
						.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 84, idRichiesta, anagrafica.getIdAnagrafica());
	
				if(a != 0) {
					inputFile = "documentale_locale/modulistica_domanda/appendice_d/scheda_socio.pdf";
					PdfReader pdfReaderNewScheda = new PdfReader(inputFile);
					pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
							pdfReaderNewScheda.getPageSizeWithRotation(1));
					PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
					pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderNewScheda, 1), 0, 0);
				}
				
				if(anagrafica.getIdQualifica() == 1 || (anagraficaClone.isPresent() &&
														anagraficaClone.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) ) {
					// RAPPRESENTANTE LEGALE
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 204, 595, "X");		
				}
				if(anagrafica.getIdQualifica() == 2 || (anagraficaClone.isPresent() &&
														anagraficaClone.get().getCodiceFiscale().equalsIgnoreCase(anagrafica.getCodiceFiscale())) ) {
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
				
				inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(),
									moduloDocumento.get().getContentId()), pdfStamper);
				
				if(anagrafica.getIdQualifica() == 2) {
					inserimentoFileByte(apiFileService.getFile(moduloQualificaMed.get().getDocumentIdClient(), 
										moduloQualificaMed.get().getContentId()), pdfStamper);
				}
			}
			
			pdfStamper.close();

			return new PdfDto("Anteprima_autocertificazione_requisiti_onorabilità", byteArrayOutputStream.toByteArray());

		} catch (Exception e) {
			throw new Exception("Si è verificato un errore non previsto nella compilazione del file");
		}
	}

	public PdfDto getAnteprimaFileAppeA(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 4);

		// PATH DOCUMENTO IN SISTEMA
		String inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a_con_intestazione.pdf";
		// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
		PdfReader pdfReader = new PdfReader(inputFile);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			if(a != 0) {
				inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a.pdf"; 
				PdfReader pdfReaderNewScheda = new PdfReader(inputFile);
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						pdfReaderNewScheda.getPageSizeWithRotation(1));
				PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderNewScheda, 1), 0, 0);
			}
		
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
			
			// INSERIMENTO PDF DOCUMENTO D'IDENTITA'
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 42, idRichiesta, anagrafica.getIdAnagrafica());
			inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(), moduloDocumento.get().getContentId())
													   , pdfStamper);
		}
		
		pdfStamper.close();
		
		return new PdfDto("polizza_assicurativa", byteArrayOutputStream.toByteArray());
	}
	
	public PdfDto getAnteprimaFileAppeB(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 5);

		// PATH DOCUMENTO IN SISTEMA
		String inputFile = "documentale_locale/modulistica_domanda/appendice_b/scheda_b_con_intestazione.pdf";
		// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
		PdfReader pdfReader = new PdfReader(inputFile);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);
			
			if(a != 0) {
				inputFile = "documentale_locale/modulistica_domanda/appendice_a/scheda_a.pdf"; 
				pdfReader = new PdfReader(inputFile);
			}
			
			pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
					pdfReader.getPageSizeWithRotation(1));
			PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
			pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReader, 1), 0, 0);
		
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
					addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 150, 485, anagrafica.getStatoDomicilio());
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
			
			// INSERIMENTO PDF DOCUMENTO D'IDENTITA'
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 47, idRichiesta, anagrafica.getIdAnagrafica());
			inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(), moduloDocumento.get().getContentId())
													   , pdfStamper);
			
		}
		
		pdfStamper.close();
		
		return new PdfDto("polizza_assicurativa", byteArrayOutputStream.toByteArray());
	}
	
	public PdfDto getAnteprimaFileAppeC(Long idRichiesta) throws Exception {
		List<AnagraficaOdm> anagrafiche = anagraficaOdmRepository.findAllByIdRichiestaAndIdTipoAnagrafica(idRichiesta, (long) 6);

		// PATH DOCUMENTO IN SISTEMA
		String inputFile = "documentale_locale/modulistica_domanda/appendice_c/scheda_c_con_intestazione.pdf";
		// ARRAY DI BYTE NECESSARIO PER LA CREAZIONE DEL DOCUMENTO
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// LETTURA DEL DOCUMENTO ESISTENTE IN SISTEMA
		PdfReader pdfReader = new PdfReader(inputFile);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);

		// SET FONT DA INSERIRE
		BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		
		for (int a = 0; a < anagrafiche.size(); a++) {		
			AnagraficaOdm anagrafica = anagrafiche.get(a);

			if(a != 0) {
				inputFile = "documentale_locale/modulistica_domanda/appendice_c/scheda_c.pdf"; 
				PdfReader pdfReaderNewScheda = new PdfReader(inputFile);
				pdfStamper.insertPage(pdfStamper.getReader().getNumberOfPages() + 1,
						pdfReaderNewScheda.getPageSizeWithRotation(1));
				PdfContentByte pdfContentByte = pdfStamper.getOverContent(pdfStamper.getReader().getNumberOfPages());
				pdfContentByte.addTemplate(pdfStamper.getImportedPage(pdfReaderNewScheda, 1), 0, 0);
			}
		
			addTextPdf(pdfStamper, pdfStamper.getReader().getNumberOfPages(), baseFont, 110, 601, anagrafica.getCognome());
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
			
			// INSERIMENTO PDF DOCUMENTO D'IDENTITA'
			Optional<StatoModuliRichiestaFigli> moduloDocumento = statoModuliRichiestaFigliRepository
					.findByIdModuloAndIdRichiestaAndIdAnagrafica((long) 56, idRichiesta, anagrafica.getIdAnagrafica());
			inserimentoFileByte(apiFileService.getFile(moduloDocumento.get().getDocumentIdClient(), moduloDocumento.get().getContentId())
													   , pdfStamper);
		}
		
		pdfStamper.close();
		
		return new PdfDto("polizza_assicurativa", byteArrayOutputStream.toByteArray());
	}

	// METODI PRIVATI
    public boolean isPdfFrimato(byte[] pdfBytes) throws IOException {
        // Crea un PdfDocument utilizzando un ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pdfBytes);
    	PdfReader reader = new PdfReader(byteArrayInputStream);
    	AcroFields acroFields = reader.getAcroFields();
    	List<String> signatureNames = acroFields.getSignatureNames();
        
        // SE NEI FILE NON CE N'è NEANCHE UNO FIRMATO L'ESITO SARA FALSE
        if(signatureNames.isEmpty()) {
        	return false;
        }
        else {
        	return true;
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
	
	private Optional<AnagraficaOdm> getRappresentateLegale(Long idRichiesta) {
		// PER TROVARE SOLO IL RAPPRESENTATE LEGALE NELL'ANAGRAFICHE
		return anagraficaOdmRepository.getAnagraficaRapLegaleByIdRichiesta(idRichiesta);
	}

}
