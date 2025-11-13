package giustiziariparativa.backoffice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import giustiziariparativa.backoffice.DTO.MediatoreGiustiziaDto;
import giustiziariparativa.backoffice.model.EnteAttestato;
import giustiziariparativa.backoffice.repository.MediatoreGiustiziaRiparativaRepository;
import giustiziariparativa.backoffice.service.EnteAttestatoService;
import giustiziariparativa.backoffice.service.MediatoreGiustiziaRiparativaService;
import giustiziariparativa.configurations.ParametersConfigurations;


@RestController
@RequestMapping("mediatori")
//@CrossOrigin
public class MediatoreGiustiziaRiparativaController {
	@Autowired
	ParametersConfigurations parametersConfigurations;
	
    @Autowired
    MediatoreGiustiziaRiparativaService mediatoreGiustiziaRiparativaService;

	@Autowired
	MediatoreGiustiziaRiparativaRepository mediatoreGiustiziaRiparativaRepository;

	@Autowired
	EnteAttestatoService enteAttestatoService;

   //metodo che prende tutti i dati per la tab mediatori  
    @GetMapping("/getElencoPubblico")
    public ResponseEntity<Object> getElencoPubblico(@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            										@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            										@RequestParam(value = "colonna", required = false, defaultValue = "") String colonna) {
	    Map<String, Object> response = new HashMap<>();
		try{
			Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
	    	Map<String, Object> mediatoriElencoPubblico = mediatoreGiustiziaRiparativaService.getElencoPubblico(pageable, searchText, colonna);
			response.put("result", mediatoriElencoPubblico.get("result"));
			response.put("totalResult", mediatoriElencoPubblico.get("totalResult"));
		} catch (Exception e){
			return ResponseEntity.badRequest().body(e);
		}

		return ResponseEntity.ok(response);
    }
	 
	@GetMapping("/getUltimaModificaMediatore")
	public ResponseEntity<Object> getUltimaModificaMediatore() {
		try {
			 Map<String, String> response = new HashMap<>();

			String ultimaModifica = mediatoreGiustiziaRiparativaService.getDataUltimaModificaMax();
			response.put("dataUltimaModifica", ultimaModifica);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	
	@PostMapping(value = "/UpdateMediatori", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> UpdateMediatori(@RequestPart("mediatoreGiustiziaRiparativa") MediatoreGiustiziaDto mediatoreGiustiziaDto,
		      									  @RequestPart(value = "file", required = false)  MultipartFile file){
		try {
	        Map<String, String> response = new HashMap<>();
	        // Inserimento file nella DTO
	        if(file != null) {
	        	mediatoreGiustiziaDto.setProvvedimento(file.getBytes());
	        }
	        
	        mediatoreGiustiziaRiparativaService.UpdateMediatori(mediatoreGiustiziaDto);
			response.put("message", "L'aggiornamento del mediatore è avvenuto con successo");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Si è verificato un errore nell'aggiornamento del mediatore");
		}
	}
	 
	@PostMapping("/setInserisciEnteAttestato")
	public ResponseEntity<Object> setInserisciEnteAttestato (@RequestBody EnteAttestato enteAttestato){
		try {
			Map<String, String> response = new HashMap<>();
			enteAttestatoService.inserisciEnteAttestato(enteAttestato);
			response.put("message", "INSERIMENTO RIUSCITO");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE INSERIMENTO");
		}
	}
	
	 @PostMapping("/setCaricaPDF")
	 public ResponseEntity<String> setCaricaPDF(@RequestParam(name="file", required=false) MultipartFile file) throws IOException{//@RequestBody byte[] pdfData) throws IOException  {
		 String message;
		 
		if (file != null) {
		 	try {
			 	 InputStream inputStream = file.getInputStream(); 
			     byte[] pdfData = inputStream.readAllBytes();;
		 		 mediatoreGiustiziaRiparativaService.uploadProvvedimentoPdf(pdfData);    

		         message = "Successfully uploaded!";
		         return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "Failed to upload!";
		        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}else {
	         message = "File vuoto";
	         return ResponseEntity.status(HttpStatus.OK).body(message);			
		}

	}
	 
	//metodo che inserisce i dati in tab mediatori 
	@PostMapping(value = "/setMediatori", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> inserisciMediatore(@RequestPart("mediatoreGiustiziaRiparativa") MediatoreGiustiziaDto mediatoreGiustiziaRiparativaTAB,
		      									     @RequestPart(value = "file", required = false)  MultipartFile file) {
		try {
	        Map<String, String> response = new HashMap<>();
	        // Inserimento file nella DTO
	        if(file != null) {
	        	mediatoreGiustiziaRiparativaTAB.setProvvedimento(file.getBytes());
	        }
	        mediatoreGiustiziaRiparativaService.createNuovoMediatore(mediatoreGiustiziaRiparativaTAB);
			response.put("message", "inserimento RIUSCITO");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("ERRORE NELLA MODIFICA");
		}
	}

	@PostMapping(value = "/saveMediatoriFileCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> saveMediatoriFileCSV(@RequestPart("file")  MultipartFile file) {
	    String message;
		//System.out.println("Il file è "+ file);
	    Map<String, String> response = new HashMap<>();
		if (file != null) {
			try {
				 message = mediatoreGiustiziaRiparativaService.saveMediatoriFileCSV(file);
				 response.put("paramsNotValid", message);
				 return ResponseEntity.ok(response);
			} catch (Exception e) {
				if(e.getMessage().contains("-ErrorController")) {
					return ResponseEntity.badRequest().body(e.getMessage().replace("-ErrorController", ""));
				}
				return ResponseEntity.badRequest().body("Si è verificato un errore non previsto");			
			}
		}else {
	         message = "File vuoto";
	         return ResponseEntity.status(HttpStatus.OK).body(message);			
		}

	}
	 
    @GetMapping("/verificaCodiceFiscale")
    public ResponseEntity<Object> verificaCodiceFiscale(@RequestParam String codiceFiscale) {
        try {
            Map<String, String> response = new HashMap<>();

            // Esegui la verifica del codice fiscale nel tuo database
            boolean codiceFiscaleEsiste = verificaCodiceFiscaleNelDatabase(codiceFiscale);

            if (codiceFiscaleEsiste) {
                response.put("message", "true");
            } else {
                response.put("message", "false");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRORE NELLA VERIFICA DEL CODICE FISCALE");
        }
    }

    private boolean verificaCodiceFiscaleNelDatabase(String codiceFiscale) {
    	
        String codiceFiscaleEsiste = mediatoreGiustiziaRiparativaService.findCodiceFiscale(codiceFiscale);
        if(codiceFiscaleEsiste == "" || codiceFiscaleEsiste == null || codiceFiscaleEsiste.isEmpty()){
        	return false;
        }else {
        	return true;
        }
    	
    }
}
