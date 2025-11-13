package giustiziariparativa.frontoffice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import giustiziariparativa.backoffice.service.MediatoreGiustiziaRiparativaService;
import giustiziariparativa.configurations.ParametersConfigurations;

@RestController
@RequestMapping("mediatori-frontoffice")
//@CrossOrigin
public class MediatoreGiustiziaRiparativaControllerFront {
	@Autowired
	ParametersConfigurations parametersConfigurations;
    @Autowired
    MediatoreGiustiziaRiparativaService mediatoreGiustiziaRiparativaService;
    
    @GetMapping("/getElencoPubblico")
    public ResponseEntity<Object> getElencoPubblico(@RequestParam(value = "indexPage", required = false, defaultValue = "0") String indexPage,
            										@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            										@RequestParam(value = "colonna", required = false, defaultValue = "") String colonna) {
	    Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(Integer.parseInt(indexPage), Integer.parseInt(parametersConfigurations.getRowsTable()));
	    Map<String, Object> mediatoriElencoPubblico = mediatoreGiustiziaRiparativaService.getElencoPubblico(pageable, searchText, colonna);

	    response.put("result", mediatoriElencoPubblico.get("result"));
	    response.put("totalResult", mediatoriElencoPubblico.get("totalResult"));

		return ResponseEntity.ok(response);
    }
}
