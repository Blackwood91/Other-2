package com.giustizia.mediazionecivile.service;

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
import com.giustizia.mediazionecivile.dto.EmissionePdgOdmDto;
import com.giustizia.mediazionecivile.dto.PdfDto;
import com.giustizia.mediazionecivile.mercurio.ApiFileService;
import com.giustizia.mediazionecivile.mercurio.MercurioFile;
import com.giustizia.mediazionecivile.model.EmissionePdgOdm;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.EmissionePdgOdmRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;

@Service
public class EmissionePdgOdmService {
	@Autowired
	EmissionePdgOdmRepository emissionePdgOdmRepository;
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired
	ApiFileService apiFileService;
	@Autowired
	PdfService pdfService;

	public HashMap<String, Object>  getAllEmissionePdg(Pageable pageable, Long idRichiesta) {
		HashMap<String, Object> response = new HashMap<>();        
        Page<Object[]> resultPage = emissionePdgOdmRepository.findAllForElenco(idRichiesta, pageable);
        List<Object[]> resultList = resultPage.getContent();
		List<EmissionePdgOdmDto> emissioni = new ArrayList<EmissionePdgOdmDto>();
		
		for (Object[] obj : resultList) {
			EmissionePdgOdmDto emissione = new EmissionePdgOdmDto();
			emissione.setIdEmissionePdg((Long) obj[0]);
			emissione.setTipoPdg((String) obj[1]);
			emissione.setDataEmissione((Date) obj[2]);
			
			emissioni.add(emissione);
		}

        response.put("totalResult", resultPage.getTotalElements());
		response.put("result", emissioni);
		return response;		
	}
	
//	@Transactional(rollbackFor = Exception.class)
//	public void  emissionePdg(EmissionePdgOdmDto emissionePdgOdmDto) throws Exception {
//		Optional<Richiesta> richiesta = richiesteRepository.findById(emissionePdgOdmDto.getIdRichiesta());
//		EmissionePdgOdm emissionePdgOdm = new EmissionePdgOdm();
//		emissionePdgOdm.setIdRichiesta(richiesta.get().getIdRichiesta());
//		emissionePdgOdm.setIdTipoPdg(emissionePdgOdmDto.getIdTipoPdg());
//		
//		EmissionePdgOdm emissionePdgOdmSave = emissionePdgOdmRepository.save(emissionePdgOdm);
//
//		//pdfService.checkValidLoadDocument(emissionePdgOdmDto.getFile());
//		
//		String path = "/" + emissionePdgOdmSave.getIdRichiesta() + "/odm/" + emissionePdgOdmSave.getIdEmissionePdg();
//		MercurioFile infoFile = apiFileService.insertFile(path, "pdg", emissionePdgOdmDto.getFile());
//		emissionePdgOdmSave.setDocumentIdClient(infoFile.getDocumentIdClient());
//		emissionePdgOdmSave.setContentId(infoFile.getContentId());
//		emissionePdgOdmRepository.save(emissionePdgOdmSave);
//		
//		emissionePdgOdm.setDataEmissione(new Date());
//		emissionePdgOdmRepository.save(emissionePdgOdmSave);
//
//		richiesta.get().setIdStato((long) 4);
//		richiesteRepository.save(richiesta.get());
//	}
	
	@Transactional(rollbackFor = Exception.class)
	public PdfDto  getFilePdg(Long idEmissionePdg) throws Exception {
		Optional<EmissionePdgOdm> emissionePdgOdm = emissionePdgOdmRepository.findById(idEmissionePdg);
		return new PdfDto("pdg", 
						  apiFileService.getFile(emissionePdgOdm.get().getDocumentIdClient(), emissionePdgOdm.get().getContentId()));
		
	}
	
}