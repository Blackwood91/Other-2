package giustiziariparativa.backoffice.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import giustiziariparativa.backoffice.DTO.MediatoreGiustiziaDto;
import giustiziariparativa.backoffice.DTO.MediatoreGiustiziaRiparativaTAB;
import giustiziariparativa.backoffice.model.EnteAttestato;
import giustiziariparativa.backoffice.model.MediatoreGiustiziaRiparativa;
import giustiziariparativa.backoffice.model.Provvedimento;
import giustiziariparativa.backoffice.model.StatoIscrizione;
import giustiziariparativa.backoffice.model.StoricoStatoMediarore;
import giustiziariparativa.backoffice.model.UtenteAbilitato;
import giustiziariparativa.backoffice.repository.MediatoreGiustiziaRiparativaRepository;
import giustiziariparativa.backoffice.repository.EnteAttestatoRepository;
import giustiziariparativa.backoffice.repository.ProvvedimentoRepository;
import giustiziariparativa.backoffice.repository.StatoIscrizioneRepository;
import giustiziariparativa.backoffice.repository.StoricoStatoMediatoreRepository;
import giustiziariparativa.util.EstraiCsv;
import giustiziariparativa.util.TipoOperazione;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class MediatoreGiustiziaRiparativaService {

	@Autowired
	MediatoreGiustiziaRiparativaRepository mediatoreGiustiziaRiparativaRepository;

	@Autowired
	RegistroOperazioneMediatoreService registroOperazioneMediatoreService;

	@Autowired
	StatoIscrizioneRepository statoIscrizioneRepository;

	@Autowired
	StoricoStatoMediatoreRepository storicoStatoMediatoreRepository;

	@Autowired
	ProvvedimentoRepository provvedimentoRepository;

	@Autowired
	EnteAttestatoService enteAttestatoService;

	@Autowired
	EnteAttestatoRepository enteAttestatoRepository;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

	public List<MediatoreGiustiziaRiparativa> getAllMediatori() {
		return mediatoreGiustiziaRiparativaRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
    //@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void UpdateMediatori(MediatoreGiustiziaDto mediatoreDto) throws Exception {
    	
    	Optional<MediatoreGiustiziaRiparativa> mediatore = mediatoreGiustiziaRiparativaRepository.findById(mediatoreDto.getIdMediatore());
		Optional<StoricoStatoMediarore> storicoStatoMediatore = storicoStatoMediatoreRepository.findById(mediatoreDto.getStatoIscrizioneId()); 
    	Optional<Provvedimento> provvedimento = Optional.of(new Provvedimento());
    	Optional<EnteAttestato> enteAttestato = Optional.of(new EnteAttestato());
    	
    	// Prende lo stato prima di modificarlo
    	Long statoOld = storicoStatoMediatore.get().getIdStato();

    	// Aggiorna nuovo stato nella tabella StatoIscrizione  
    	//mi sono presa getStato perchè se devo aggiornare prendo il campo del form sennò id_stato che c'era prima è sempre 1
    	/*StatoIscrizione statoIscrizione = new StatoIscrizione(mediatoreDto.getIdStatoMediatore(), mediatoreDto.getStato());
    	statoIscrizioneRepository.save(statoIscrizione);*/

    	try {
    		// Solo se il cambiamento dello stato non vine fatto 
	    	if(statoOld == mediatoreDto.getIdStatoMediatore()) {
	    			
	    		if(storicoStatoMediatore.get().getIdProvvedimento() != null){
	    			provvedimento = provvedimentoRepository.findById(storicoStatoMediatore.get().getIdProvvedimento());
	    			provvedimento.get().setProvvedimentoPdf(mediatoreDto.getProvvedimento());	
	    			provvedimento.get().setDataEmissioneProvvedimento(mediatoreDto.getDataProvvedimento());
	    			provvedimento.get().setDataModificaProvvedimento(new Date());

	    			provvedimentoRepository.save(provvedimento.get());
	    		}
	    		else{
	    			// Set con dati del dto
	    			provvedimento.get().setProvvedimentoPdf(mediatoreDto.getProvvedimento());
	    			provvedimento.get().setDataEmissioneProvvedimento(mediatoreDto.getDataProvvedimento());
	    			provvedimento.get().setDataInsertProvvedimento(new Date());

	    			// Passaggio ultimo idProvvedimento alla tabella di STORICO STATO MEDIATORE
	    			Provvedimento provvedimentoUpdate = provvedimentoRepository.save(provvedimento.get());
					storicoStatoMediatore.get().setIdProvvedimento(provvedimentoUpdate.getIdProvvedimento());
	    		}
	
				storicoStatoMediatore.get().setDataModificaStato(new Date());
				storicoStatoMediatore.get().setDataStato(checkDateFormat(mediatoreDto.getDataStato()));
				storicoStatoMediatore.get().setMotivazioneStatoIscrizione(mediatoreDto.getMotivazione());
				storicoStatoMediatore.get().setTipologiaStato(mediatoreDto.getTipologia());
	    	}
	    	// Condizione cambiamento stato
	    	else if(mediatoreDto.getIdStatoMediatore() != statoOld) {				
				// Creazione del  nuovo provvedimento
    			//provvedimento = provvedimentoRepository.findById(storicoStatoMediatore.get().getIdProvvedimento());
    			provvedimento.get().setProvvedimentoPdf(mediatoreDto.getProvvedimento());	
    			provvedimento.get().setDataEmissioneProvvedimento(mediatoreDto.getDataProvvedimento());
    			provvedimento.get().setDataInsertProvvedimento(new Date());
		
				// Creazione del nuovo storicoStatoMediatore
    			storicoStatoMediatore = Optional.of(new StoricoStatoMediarore());
				storicoStatoMediatore.get().setTipologiaStato(mediatoreDto.getTipologia());
				storicoStatoMediatore.get().setDataStato(checkDateFormat(mediatoreDto.getDataStato()));
				storicoStatoMediatore.get().setMotivazioneStatoIscrizione(mediatoreDto.getMotivazione());
				storicoStatoMediatore.get().setDataFine(checkDateFormat(mediatoreDto.getDataFine()));
				storicoStatoMediatore.get().setIdMediatore(mediatoreDto.getIdMediatore());
				storicoStatoMediatore.get().setIdStato(mediatoreDto.getIdStatoMediatore());
				storicoStatoMediatore.get().setDataInserimentoStato(new Date());
				// Collegamento al nuovo provvedimento con il nuovo storicoStatoMediatore
		        Provvedimento provvedimentoUpdate = provvedimentoRepository.save(provvedimento.get());
				storicoStatoMediatore.get().setIdProvvedimento(provvedimentoUpdate.getIdProvvedimento());	

				// Collegamento al nuovo storicoStatoMediatore con il mediatore esistente
				StoricoStatoMediarore storicoStatoMediatoreUpdate = storicoStatoMediatoreRepository.save(storicoStatoMediatore.get());
				mediatore.get().setDescrizioneStatoIscrizione(storicoStatoMediatoreUpdate.getIdStatoStorico());
	    	}
	    	
			// Solo se qualifica è si
			if(mediatoreDto.getIsFormatore() == 1) {
				if (mediatoreDto.getTipologiaEnteFormatore()==1) {
				
					
					 mediatore.get().setEnteAttestato((long)1);
				}	
			
				else {
					// In un primo momento viene impostata, se soddisfa le seguenti condizioni verrà ricambiato
					mediatore.get().setEnteAttestato(mediatoreDto.getEnteAttestatoId());
						
					// In caso di nuovo ente 
					// In entrambe le casistiche
					enteAttestato.get().setEnteAttestato(mediatoreDto.getNomeEnteAttestato());
					enteAttestato.get().setIsConvenzionato(mediatoreDto.getIsConvenzionato());
					enteAttestato.get().setTipologiaEnte(mediatoreDto.getTipologiaEnte());
					enteAttestato.get().setIdTipologiaEnteFormatore(mediatoreDto.getTipologiaEnteFormatore());

				    // Nell'universitario possibilità di casistica gia esisteste e non
					if(mediatoreDto.getTipologiaEnteFormatore() != 1) {
			    	// In caso di nuovo ente universitario
						if(mediatoreDto.getEnteAttestatoId() == 1) {
							enteAttestato.get().setDataInserimentoEnte(new Date());		
							EnteAttestato enteUpdate = enteAttestatoRepository.save(enteAttestato.get());
							mediatore.get().setEnteAttestato(enteUpdate.getIdEnteAttestato());
						}
						else {
							mediatore.get().setEnteAttestato(mediatoreDto.getEnteAttestatoId());
							enteAttestato.get().setDataModificaEnte(new Date());
						}	
					}				

				}
			}
	    	// Inserimento dati per mediatore
	    	mediatore.get().setNomeMediatore(mediatoreDto.getNomeMediatore());
	    	mediatore.get().setCognomeMediatore(mediatoreDto.getCognomeMediatore());
	    	mediatore.get().setCodiceFiscale(mediatoreDto.getCodiceFiscale());
	    	mediatore.get().setNumeroIscrizioneElenco(mediatoreDto.getNumeroIscrizioneElenco());
	    	mediatore.get().setIsFormatore(mediatoreDto.getIsFormatore());
	    	mediatore.get().setLuogoDiNascita(mediatoreDto.getLuogoDiNascita());
	    	mediatore.get().setDataDiNascita(mediatoreDto.getDataDiNascita());
	    	//cambiati 
	    	mediatore.get().setCittaDiResidenza(mediatoreDto.getCittaDiResidenza());
	    	mediatore.get().setProvinciaDiResidenza(mediatoreDto.getProvinciaDiResidenza());
	    	// Contrallare se corretto perche non trovo corrispondenza con indirizzo 1
	    	mediatore.get().setIndirizzo(mediatoreDto.getIndirizzo());
	    	mediatore.get().setDataIscrizioneElenco(mediatoreDto.getDataIscrizioneElenco());
	    	// Contrallare se corretto perche doppio campo in dto chiamato numero civico
	    	mediatore.get().setNumeroCivico(mediatoreDto.getNumeroCivico());
	    	mediatore.get().setCap(mediatoreDto.getCap());
	    	mediatore.get().setIndirizzoPec(mediatoreDto.getIndirizzoPec());
	    	mediatore.get().setRequisitiIscrizioneElenco(mediatoreDto.getRequisitiIscrizioneElenco());
	    	mediatore.get().setDataModificaMediatore(new Date());
	    	
	    	// Salvataggi o inserimento a seconda dei parametri passati tramite dto
			mediatoreGiustiziaRiparativaRepository.save(mediatore.get());
    	}
    	catch(Exception e) {
    		throw new Exception();
    	}
    }

	public Map<String, Object> getElencoPubblico(Pageable pageable, String searchText, String colonna) {
		Map<String, Object> response = new HashMap<>();
		Page<Object[]> resultPage;

		if (searchText.isEmpty()) {
			resultPage = mediatoreGiustiziaRiparativaRepository.findCustomFields(searchText, pageable);
		} else {
			resultPage = mediatoreGiustiziaRiparativaRepository.findCustomFieldsByText(searchText, pageable);
		}

		List<Object[]> resultList = resultPage.getContent();

		List<MediatoreGiustiziaRiparativaTAB> mediatoreGiustiziaRiparativaTAB = new ArrayList<>();

		for (Object[] obj : resultList) {
			MediatoreGiustiziaRiparativaTAB mediatoreGiustiziaRiparativaROW = new MediatoreGiustiziaRiparativaTAB(obj);
			mediatoreGiustiziaRiparativaTAB.add(mediatoreGiustiziaRiparativaROW);
		}

		response.put("result", mediatoreGiustiziaRiparativaTAB);
		response.put("totalResult", resultPage.getTotalElements());

		return response;
	}

   public Map<String, Object> getElencoPubblicoIscritti(Pageable pageable, String searchText, String colonna) {	
	    Map<String, Object> response = new HashMap<>();
	    
	     Page<Object[]> resultPage = mediatoreGiustiziaRiparativaRepository.findMediatoriIscritti(searchText, pageable);

	    List<Object[]> resultList = resultPage.getContent();  
		List<MediatoreGiustiziaRiparativaTAB> mediatoreGiustiziaRiparativaTAB = new ArrayList<>();
 
		for (Object[] obj : resultList) {
			MediatoreGiustiziaRiparativaTAB mediatoreGiustiziaRiparativaROW = new MediatoreGiustiziaRiparativaTAB(obj);
			mediatoreGiustiziaRiparativaTAB.add(mediatoreGiustiziaRiparativaROW);
		}
  
		response.put("result", mediatoreGiustiziaRiparativaTAB);
		response.put("totalResult", resultPage.getTotalElements());

		return response;
    }

	@Transactional(rollbackFor = Exception.class)
	public MediatoreGiustiziaRiparativa createNuovoMediatore(MediatoreGiustiziaDto mediatoreDto) {

		MediatoreGiustiziaRiparativa mediatoreGiustiziaRiparativa = new MediatoreGiustiziaRiparativa();
		UtenteAbilitato utente = new UtenteAbilitato();
		Provvedimento provvedimento = new Provvedimento();
		StoricoStatoMediarore storicoStatoMediatore = new StoricoStatoMediarore();

		EnteAttestato ente = new EnteAttestato();

		// Solo se qualifica è si
		if (mediatoreDto.getIsFormatore() == 1) {
			if (mediatoreDto.getTipologiaEnteFormatore()==1) {
				
				mediatoreGiustiziaRiparativa.setEnteAttestato((long)1);
			}	
			else {
				// In un primo momento viene impostata, se soddisfa le seguenti condizioni verrà ricambiato
				mediatoreGiustiziaRiparativa.setEnteAttestato(mediatoreDto.getEnteAttestatoId());
					
				// In caso di nuovo ente 
				// In entrambe le casistiche
				ente.setEnteAttestato(mediatoreDto.getNomeEnteAttestato());
				ente.setIsConvenzionato(mediatoreDto.getIsConvenzionato());
				ente.setTipologiaEnte(mediatoreDto.getTipologiaEnte());
				ente.setIdTipologiaEnteFormatore(mediatoreDto.getTipologiaEnteFormatore());

				// Nell'universitario possibilità di casistica gia esisteste e non
				if(mediatoreDto.getTipologiaEnteFormatore() != 1) {
				// In caso di nuovo ente universitario
					if(mediatoreDto.getEnteAttestatoId() == 1) {
						ente.setDataInserimentoEnte(new Date());		
						EnteAttestato enteUpdate = enteAttestatoRepository.save(ente);
						mediatoreGiustiziaRiparativa.setEnteAttestato(enteUpdate.getIdEnteAttestato());
					}
					else {
						mediatoreGiustiziaRiparativa.setEnteAttestato(mediatoreDto.getEnteAttestatoId());
						ente.setDataModificaEnte(new Date());
					}	
				}
				
			}
		}

		mediatoreGiustiziaRiparativa.setNomeMediatore(mediatoreDto.getNomeMediatore());
		mediatoreGiustiziaRiparativa.setCognomeMediatore(mediatoreDto.getCognomeMediatore());
		mediatoreGiustiziaRiparativa.setCodiceFiscale(mediatoreDto.getCodiceFiscale());
		mediatoreGiustiziaRiparativa.setNumeroIscrizioneElenco(mediatoreDto.getNumeroIscrizioneElenco());
		mediatoreGiustiziaRiparativa.setDataIscrizioneElenco(mediatoreDto.getDataIscrizioneElenco());
		mediatoreGiustiziaRiparativa.setIsFormatore(mediatoreDto.getIsFormatore());
		mediatoreGiustiziaRiparativa.setLuogoDiNascita(mediatoreDto.getLuogoDiNascita());
		mediatoreGiustiziaRiparativa.setDataDiNascita(mediatoreDto.getDataDiNascita());
		mediatoreGiustiziaRiparativa.setCittaDiResidenza(mediatoreDto.getCittaDiResidenza());
		mediatoreGiustiziaRiparativa.setProvinciaDiResidenza(mediatoreDto.getProvinciaDiResidenza());
		mediatoreGiustiziaRiparativa.setIndirizzo(mediatoreDto.getIndirizzo());
		mediatoreGiustiziaRiparativa.setNumeroCivico(mediatoreDto.getNumeroCivico());
		mediatoreGiustiziaRiparativa.setCap(mediatoreDto.getCap());
		mediatoreGiustiziaRiparativa.setIndirizzoPec(mediatoreDto.getIndirizzoPec());
		mediatoreGiustiziaRiparativa.setRequisitiIscrizioneElenco(mediatoreDto.getRequisitiIscrizioneElenco());
		mediatoreGiustiziaRiparativa.setDataInserimentoMediatore(new Date());
		mediatoreGiustiziaRiparativa.setDataModificaMediatore(new Date());

		// Solo se messo verrà creato il record del provvedimento
		if (mediatoreDto.getDataProvvedimento() != null || mediatoreDto.getProvvedimento() != null) {
			Provvedimento provvedimentoSave = new Provvedimento();
			
			provvedimento.setDataInsertProvvedimento(new Date());
			provvedimento.setProvvedimentoPdf(mediatoreDto.getProvvedimento());
			provvedimento.setDataEmissioneProvvedimento(mediatoreDto.getDataProvvedimento());

			provvedimentoSave = provvedimentoRepository.save(provvedimento);
			storicoStatoMediatore.setIdProvvedimento(provvedimentoSave.getIdProvvedimento());
		}

		storicoStatoMediatore.setTipologiaStato(mediatoreDto.getTipologia());
		storicoStatoMediatore.setDataStato(checkDateFormat(mediatoreDto.getDataStato()));
		storicoStatoMediatore.setDataFine(checkDateFormat(mediatoreDto.getDataFine()));
		// storicoStatoMediatore.setMotivazioneStatoIscrizione(mediatoreDto.getMotivazione());
		storicoStatoMediatore.setMotivazioneStatoIscrizione(mediatoreDto.getMotivazione());
		storicoStatoMediatore.setIdStato(mediatoreDto.getIdStatoMediatore());
		storicoStatoMediatore.setDataInserimentoStato(new Date());

		mediatoreGiustiziaRiparativa.setDescrizioneStatoIscrizione(2L); //valore fittizzio per non fare andare in errore la riga seguente
		
		MediatoreGiustiziaRiparativa mediatoreUpdate = mediatoreGiustiziaRiparativaRepository.save(mediatoreGiustiziaRiparativa);
		storicoStatoMediatore.setIdMediatore(mediatoreUpdate.getIdMediatore());
		StoricoStatoMediarore storicoStatoUpdate = storicoStatoMediatoreRepository.save(storicoStatoMediatore);

		mediatoreGiustiziaRiparativa.setDescrizioneStatoIscrizione(storicoStatoUpdate.getIdStatoStorico());
		// Passaggio obbligatorio per riaggiornare l'id dell'inserimento appena fatto dello storicoStao
		mediatoreGiustiziaRiparativaRepository.save(mediatoreGiustiziaRiparativa);

		return mediatoreGiustiziaRiparativa;
	}

	@Transactional(rollbackFor = Exception.class)
	public String saveMediatoriFileCSV(MultipartFile fileCSV) throws IOException {    	
		int currentRow = 0;
		// Contantori che serviranno per le righe non valide
		int contaNotValidRows = 0;
		int contaNotValidParamsRows = 0;
		int contaNotValidRowsCodFisc = 0;
		int contaNotValidRowsExistCodFisc = 0;

		try {
			EstraiCsv file = new EstraiCsv();
			ArrayList<String[]> ListaCSV = file.ReadCsv(fileCSV);	
	    	
			for (String[] row : ListaCSV) {
				if(currentRow > 0) {
					if(checkRowCsv(row[0])) {
					    // Stampa ogni colonna della riga
					    for (String column : row) {
					    	// -1 per forzare la considerazione anche degli split vuoti
					    	String[] celleRow = column.split(";", -1);
					        MediatoreGiustiziaRiparativa mediatoreGiustiziaRiparativa = new MediatoreGiustiziaRiparativa();
					        StoricoStatoMediarore storicoStatoMediatore = new StoricoStatoMediarore();
							EnteAttestato ente = new EnteAttestato();
					        Provvedimento provvedimento = new Provvedimento();
					        UtenteAbilitato utente = new UtenteAbilitato();

					        // CONTROLLI PER TUTTI I PARAMETRI DELLA RIGA DEL CSV
					        String outcomeCheckParams = checkValidParamsCsvMediatore(celleRow); 
							if(outcomeCheckParams.equalsIgnoreCase("mancanza_campi_obbligatori")) {
								contaNotValidRows++;
								continue;
							}
					        else if(outcomeCheckParams.equalsIgnoreCase("codice_fiscale_non_valido")) {
								contaNotValidRowsCodFisc++;
								continue;
							}
							else if(outcomeCheckParams.equalsIgnoreCase("codice_fiscale_esistente")) {
								contaNotValidRowsExistCodFisc++;
								continue;
							}
							else if(outcomeCheckParams.equalsIgnoreCase("campi_inseriti_non_validi")) {
								contaNotValidParamsRows++;
								continue;
							}
					        
							// Solo se qualifica è si
							int isFormatore = celleRow[11].equalsIgnoreCase("si") ? 1 : 0 ;
					        mediatoreGiustiziaRiparativa.setIsFormatore(isFormatore);
					        
							if(isFormatore == 1) {		
								int enteConvezionato = celleRow[19].equalsIgnoreCase("si") ? 1 : 0;
								EnteAttestato enteExisting = enteAttestatoRepository.findFirstByEnteAttestatoAndTipologiaEnteAndIsConvenzionato(celleRow[18], 
															 celleRow[17], enteConvezionato);
								
							    if(enteExisting == null) {
							    	// In entrambe le casistiche
									ente.setEnteAttestato(celleRow[18]);
									ente.setIsConvenzionato(enteConvezionato);
									ente.setTipologiaEnte(celleRow[17]);
								}
								
								// Nell'universitario possibilità di casistica gia esisteste e non
								Long tipologiaEnteFormatore = (long) (celleRow[16].equalsIgnoreCase("università") ? 2 : 1);
								if(tipologiaEnteFormatore != 1) {
							    	// In caso di nuovo ente universitario
							    	if(enteExisting == null) {
							    		ente.setDataInserimentoEnte(new Date());	
							    		ente.setIdTipologiaEnteFormatore(tipologiaEnteFormatore);
							    		EnteAttestato enteUpdate = enteAttestatoRepository.save(ente);
							    		mediatoreGiustiziaRiparativa.setEnteAttestato(enteUpdate.getIdEnteAttestato());
							    	}
							    	else {
								    	// In caso di ente universitario gia esistente
							    		mediatoreGiustiziaRiparativa.setEnteAttestato(enteExisting.getIdEnteAttestato());
							    	}	
								}
								else {
									mediatoreGiustiziaRiparativa.setEnteAttestato(1L);
								}	
							}
					        
					        mediatoreGiustiziaRiparativa.setNomeMediatore(celleRow[0]);
					        mediatoreGiustiziaRiparativa.setCognomeMediatore(celleRow[1]);
					        mediatoreGiustiziaRiparativa.setCodiceFiscale(celleRow[2]);
					        mediatoreGiustiziaRiparativa.setNumeroIscrizioneElenco(celleRow[12]);
					        mediatoreGiustiziaRiparativa.setDataIscrizioneElenco(formatDateCsv(celleRow[14]));
					        mediatoreGiustiziaRiparativa.setLuogoDiNascita(celleRow[4]);
					        mediatoreGiustiziaRiparativa.setDataDiNascita(formatDateCsv(celleRow[5]));
					        mediatoreGiustiziaRiparativa.setCittaDiResidenza(celleRow[8]);
					        mediatoreGiustiziaRiparativa.setProvinciaDiResidenza(celleRow[9]);
					        mediatoreGiustiziaRiparativa.setIndirizzo(celleRow[6]);
					        mediatoreGiustiziaRiparativa.setNumeroCivico(celleRow[7]);
					        mediatoreGiustiziaRiparativa.setCap(celleRow[10]);
					        mediatoreGiustiziaRiparativa.setIndirizzoPec(celleRow[3]);
					        mediatoreGiustiziaRiparativa.setRequisitiIscrizioneElenco(celleRow[13]);
							mediatoreGiustiziaRiparativa.setDataInserimentoMediatore(new Date());
							mediatoreGiustiziaRiparativa.setDataModificaMediatore(new Date());
					        
					        // Solo se messo verrà creato il record del provvedimento
							provvedimento.setDataInsertProvvedimento(new Date());
							provvedimento.setDataEmissioneProvvedimento(formatDateCsv(celleRow[15]));								
							
							Provvedimento provvedimentoUpdate = provvedimentoRepository.save(provvedimento);
					        MediatoreGiustiziaRiparativa mediatoreUpdate = mediatoreGiustiziaRiparativaRepository.save(mediatoreGiustiziaRiparativa);
					        
							storicoStatoMediatore.setIdProvvedimento(provvedimentoUpdate.getIdProvvedimento());
					        storicoStatoMediatore.setIdMediatore(mediatoreUpdate.getIdMediatore());
					        storicoStatoMediatore.setIdStato((long)1);
							storicoStatoMediatore.setDataInserimentoStato(new Date());

							StoricoStatoMediarore storicoStatoUpdate = storicoStatoMediatoreRepository.save(storicoStatoMediatore);
							mediatoreGiustiziaRiparativa.setDescrizioneStatoIscrizione(storicoStatoUpdate.getIdStatoStorico());
                            mediatoreGiustiziaRiparativaRepository.save(mediatoreGiustiziaRiparativa);

					    }
					}
				}
			    else {
			    	currentRow ++;
			    }
				
			}

      } catch(Exception e) {
    	    // In caso di errore generato dal service preventivato
    	  	if(e.getMessage().contains("-ErrorService")) {
    	  		throw new RuntimeException("-ErrorController " + e.getMessage().replace("-ErrorService", ""));	
    	  	}
      		throw new RuntimeException("-ErrorController Si è verificato un errore non previsto");
      }
      
	
	  String rowsNotValid = "";
	  if(contaNotValidRows != 0) {
		  rowsNotValid = "<br>-Non è stato possibile inserire determinati mediatori perchè non tutti i campi contengono i valori obbligatori, in " + contaNotValidRows + 
				  		 " righe del csv inserito";
	  }
	  if(contaNotValidParamsRows != 0) {
		  rowsNotValid = "<br>-Non è stato possibile inserire determinati mediatori perchè non tutti i campi contengono i valori accettati, in " + contaNotValidParamsRows + 
				  		 " righe del csv inserito";
	  }
	  if(contaNotValidRowsCodFisc != 0) {
		  rowsNotValid = "<br>-Non è stato possibile inserire determinati mediatori perchè Il Codice Fiscale non è valido in " + contaNotValidRowsCodFisc + 
				  		 " righe del csv inserito";
	  }
	  if(contaNotValidRowsExistCodFisc != 0) {
		  rowsNotValid = "<br>-Non è stato possibile inserire determinati mediatori perchè Il Codice Fiscale è già esistente in " + contaNotValidRowsExistCodFisc + 
				  		 " righe del csv inserito";
	  }
	  
	  return rowsNotValid;	
	}

	public Object inserisciMediatore(MediatoreGiustiziaRiparativa mediatore) {
		return mediatoreGiustiziaRiparativaRepository.save(mediatore);
	}

	public MediatoreGiustiziaRiparativa updateStatoIscrizioneElenco(Long idMediatore, Long idStato,
			StoricoStatoMediarore storicoStatoMediarore, UtenteAbilitato utente) {
		MediatoreGiustiziaRiparativa mediatoreGiustiziaRiparativa = mediatoreGiustiziaRiparativaRepository
				.findById(idMediatore)
				.orElseThrow(() -> new EntityNotFoundException("Mediatore non trovato con ID: " + idMediatore));

		mediatoreGiustiziaRiparativa.setDescrizioneStatoIscrizione(idStato);

		if (idStato != 1L) {
			mediatoreGiustiziaRiparativa = mediatoreGiustiziaRiparativaRepository.save(mediatoreGiustiziaRiparativa);
			// il salvataggio avviene già nel metodo
			createStoricoMediatore(mediatoreGiustiziaRiparativa.getIdMediatore(), idStato, 0L, "", "",
					mediatoreGiustiziaRiparativa.getDescrizioneStatoIscrizione());
			registroOperazioneMediatoreService.registraOperazioneMediatore(utente, TipoOperazione.AGGIORNA_STATO);
		}
		return mediatoreGiustiziaRiparativa;
	}

	public StoricoStatoMediarore createStoricoMediatore(Long idMediatore, Long idStato, Long idProvvedimento,
			String tipologiaStato, String motivazioneStatoIscrizione, Long idStatoMediatore) {
		StoricoStatoMediarore storicoStatoMediarore = new StoricoStatoMediarore();

		storicoStatoMediarore.setIdMediatore(idMediatore);
		storicoStatoMediarore.setIdStato(idStatoMediatore);
		storicoStatoMediarore.setIdProvvedimento(idProvvedimento);
		storicoStatoMediarore.setTipologiaStato(tipologiaStato);
		storicoStatoMediarore.setMotivazioneStatoIscrizione(motivazioneStatoIscrizione);
		storicoStatoMediarore.setDataStato(new Date());
		// storicoStatoMediarore.setDataFine(new Date());

		return storicoStatoMediatoreRepository.save(storicoStatoMediarore);

	}

	public String getDataUltimaModificaMax() {
		return mediatoreGiustiziaRiparativaRepository.getDataUltimaModificaMax();
	}

     // Controllo su formattazione data prima di inserimento nella entity
    private Date formatDateCsv(String date) {
    	try { 
    	    SimpleDateFormat dateFormatCsv = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    		return dateFormatCsv.parse(date);
    	}
    	catch(Exception e) {
    		return null;
    	}
    }

	// Esclude le righe vuote che potrebbero uscire in seguito a delle cancellazioni
	// nel file di caricamento di csv
	private boolean checkRowCsv(String row) {
		// (esclude questa casistica "[;;;;;;]")
		return !Pattern.matches("^[;\\[\\]]*$", row);
	}

	public void uploadProvvedimentoPdf(byte[] pdfData) {
//    	 MediatoreGiustiziaRiparativa mediatoreGiustiziaRiparativa =
//	     mediatoreGiustiziaRiparativaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mediatore non trovato con ID:" + id));
		Provvedimento provvedimento = new Provvedimento();
		provvedimento.setProvvedimentoPdf(pdfData);
		provvedimentoRepository.caricaPdf(pdfData);
		// statoIscrizioneRepository.save(mediatoreGiustiziaRiparativa.getStatoIscrizioneElenco());
		// registroOperazioneMediatoreService.registraOperazioneMediatore(utente,
		// TipoOperazione.AGGIORNA_STATO);
	}

	public String findCodiceFiscale(String codiceFiscale) {
		return mediatoreGiustiziaRiparativaRepository.findCodiceFiscale(codiceFiscale);
	}

	// Controllo su formattazione data prima di inserimento nella entity
	private Date checkDateFormat(String date) {
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

     // Controllo su formattazione data prima di inserimento nella entity
    private String checkValidParamsCsvMediatore(String[] row) {
    	if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty() && row[4].isEmpty() && row[5].isEmpty() && 
    	   row[6].isEmpty() && row[7].isEmpty() && row[8].isEmpty() && row[9].isEmpty() && row[10].isEmpty() && row[11].isEmpty() && 
    	   row[12].isEmpty() && row[13].isEmpty() && row[14].isEmpty() && row[15].isEmpty()) {
    		return "mancanza_campi_obbligatori";
    	}
    	else {    	
    		// ALTRI CONTROLLI
    		String qualificaFormatore = row[11];
    		if(qualificaFormatore.equalsIgnoreCase("si")) {
    			// cont ente fomator
    			String enteFormatore = row[16];
    			if(enteFormatore.equalsIgnoreCase("università")) {
    				if(row[17].isEmpty() || row[18].isEmpty() || row[19].isEmpty()) {
    		    		return "mancanza_campi_obbligatori";
    				}
    				else {
    					String tipologiaEnte = row[17];
    					String enteConvezionato = row[19];
    					if(tipologiaEnte.equalsIgnoreCase("pubblico") == false && tipologiaEnte.equalsIgnoreCase("privato") == false) {
    		        		return "campi_inseriti_non_validi";
    					}
    					else if(enteConvezionato.equalsIgnoreCase("si") == false && enteConvezionato.equalsIgnoreCase("no") == false) {
    		        		return "campi_inseriti_non_validi";
    					}
    				}
    			}
    			else if(enteFormatore.equalsIgnoreCase("centro per la giustizia riparativa") == false) {
	        		return "campi_inseriti_non_validi";
    			}	
    		} 
    		else if(qualificaFormatore.equalsIgnoreCase("no") == false) {
        		return "campi_inseriti_non_validi";
			}
    		
    		String cap = row[10];
			if(cap.length() != 5) {
        		return "campi_inseriti_non_validi";
			}
    		
			// CONTROLLI PER VALIDITA' DEL CODICE FISCALE
		    String codiceFiscale = row[2].toUpperCase();
		    
		    if(mediatoreGiustiziaRiparativaRepository.findCodiceFiscale(codiceFiscale) != null) {
	            return "codice_fiscale_esistente";
		    }
		    else if (codiceFiscale == null || codiceFiscale.length() != 16) {
		        // Verifica che il codice fiscale abbia una lunghezza di 16 caratteri
	            return "codice_fiscale_non_valido";
	        }
		    else if (!Pattern.matches("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$", codiceFiscale)) {
		        // Verifica che il codice fiscale contenga solo caratteri alfanumerici
	            return "codice_fiscale_non_valido";
	        }

	        return "parametri_validi";
    	}
     }	

}
