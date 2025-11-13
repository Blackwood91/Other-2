package com.giustizia.mediazionecivile.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.giustizia.mediazionecivile.model.Comune;
import com.giustizia.mediazionecivile.model.RegioneProvince;
import com.giustizia.mediazionecivile.projection.ComuneProjection;
import com.giustizia.mediazionecivile.repository.ComuneRepository;
import com.giustizia.mediazionecivile.repository.RegioniProvinceRepository;
import com.giustizia.mediazionecivile.utility.EstraiCsv;
import com.giustizia.mediazionecivile.utility.TypeUtility;

@Service
public class ComuneService {
	@Autowired
	ComuneRepository comuneRepository; 
	@Autowired
	private TypeUtility typeUtility;
	@Autowired
	RegioniProvinceRepository regioniProvinceRepository;
	
	public List<ComuneProjection> getAllComuneByNome(String nomeComune, Pageable pageable) {
		return comuneRepository.findByNomeComuneIgnoreCaseStartingWithAndStatoOrderByNomeComuneAsc(nomeComune, "A", pageable);
	}
	
	public ComuneProjection getComuneById(Long idComune) {	
		return comuneRepository.findByIdCodComune(idComune);
	}
	
	public List<Comune> getAllComune() {	
		return comuneRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public String insertCSVComuni(MultipartFile fileCSV) throws Exception{
			EstraiCsv file = new EstraiCsv();
			ArrayList<String[]> ListaCSV = file.ReadCsv(fileCSV);
			
			try {
	
	    	// Per skippare la prima riga che è il nome delle colonne
	    	int currentRow = 0;

	    	List<Comune> comuni = new ArrayList<Comune>();
	    	int i = 0;
			for (String[] row : ListaCSV) {
				if(currentRow > 0) {
					if(checkRowCsv(row[0])) {
					    // Stampa ogni colonna della riga
					    for (String column : row) {
					    	// -1 per forzare la considerazione anche degli split vuoti
					    	String[] celleRow = column.split(";", -1);
					    	
					    	System.out.println(i);
					    	i = i + 1;
					    	Comune comune = new Comune();
					    	RegioneProvince regioneProvincia = new RegioneProvince();
					    	
					    	int numColonne = celleRow.length;
					    	
					    	String IDGmap = celleRow[0];
					    	String NomeComune = celleRow[1];
					    	
					        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					      //  String DataIstituzione = dateFormat.format(typeUtility.castWithDate("yyyy-MM-dd HH:mm:ss.SSS", celleRow[2]));
					      //  String DataCessazione = dateFormat.format(typeUtility.castWithDate("yyyy-MM-dd HH:mm:ss.SSS", celleRow[3]));
					    	if(celleRow[2].equalsIgnoreCase("") == false) {
					    		Date DataIstituzione = dateFormat.parse(celleRow[2]);
						    	//System.out.println(DataIstituzione);
					    		
						    	comune.setDataIstituzione(DataIstituzione);
					    	}else {
						    	comune.setDataIstituzione(null);
					    	}
					    	
					    	if(celleRow[3].equalsIgnoreCase("") == false) {
					    		Date DataCessazione = dateFormat.parse(celleRow[3]);
						    	comune.setDataCessazione(DataCessazione);
					    	}else {
						    	comune.setDataCessazione(null);
					    	}

					    	
					    	String Stato = celleRow[4];
					    	String CodiceIstat = celleRow[5];
					    	String CodiceIstat_sbagliato = celleRow[6];
					    	String UfficioId = celleRow[7];
					    	String SiglaProvincia = celleRow[8];
					    	String DescrizioneProvincia = celleRow[9];
					    	String Regione = celleRow[10];
					    	Long CodiceProvincia = Long.parseLong(celleRow[11]);
					    	Long CodiceRegione = Long.parseLong(celleRow[12]);;
					    	String TribunaleCompetenza = celleRow[13];
					    	//String UfficioCivile = celleRow[14];	
					    	//String UfficioPenale = celleRow[15];
					    	
					    	RegioneProvince tabRegioni = regioniProvinceRepository.findByCodiceProvincia(CodiceProvincia);
					    	
					    	comune.setCodiceRegione(CodiceRegione);
					    	//comune.setCodiceProvincia(CodProv);  
					    	comune.setRegioneProvince(tabRegioni);
					    	comune.setNomeComune(NomeComune);
					    	comune.setIstat(CodiceIstat);
					    	
					    	Optional<RegioneProvince> provincia = java.util.Optional.empty();

					    	provincia = regioniProvinceRepository.findByNomeProvincia(NomeComune);
					    	// In caso la provincia letta nel file non esista
					    	if(provincia.isPresent() == false ) {
						    	comune.setCapoluogoProvincia('0');
					    	}else {
						    	comune.setCapoluogoProvincia('1');
					    	}
					    	comune.setCodiceCatastale(null);
					    	comune.setStato(Stato);
					    	
					    	comuni.add(comune);
					    }
					}
				}
			    else {
			    	currentRow ++;
			    }
				
			}
	        // Salvataggio di tutte le righe dell'entità estrapolate
	        if(comuni.isEmpty() == false) {
	        	comuneRepository.saveAll(comuni);
	        }
	        else {
	        	throw new RuntimeException("-ErrorService Nessun seggio è stato salvato in banca dati. File non formalmente corretto o non sono presenti dati all'interno");
	        }
	  } catch(Exception e) {
		    // In caso di errore generato dal service preventivato
		  	if(e.getMessage().contains("-ErrorService")) {
		  		throw new RuntimeException("-ErrorController " + e.getMessage().replace("-ErrorService", ""));	
		  	}
	  		throw new RuntimeException("-ErrorController Si è verificato un errore non previsto");
	  }
		
		
	  String rowsNotValid = "";	
	  

	
	  return rowsNotValid;
	}
	
	// Esclude le righe vuote che potrebbero uscire in seguito a delle cancellazioni nel file di caricamento di csv 
    private boolean checkRowCsv(String row) {
    	//(eclude questa casistica "[;;;;;;]")
        return ! Pattern.matches("^[;\\[\\]]*$", row);
    }
}
