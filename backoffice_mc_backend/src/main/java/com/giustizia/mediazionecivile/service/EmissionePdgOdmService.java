package com.giustizia.mediazionecivile.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
import com.giustizia.mediazionecivile.model.JobRichiesta;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.repository.EmissionePdgOdmRepository;
import com.giustizia.mediazionecivile.repository.JobRichiestaRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.UserLoginRepository;
import com.giustizia.mediazionecivile.security.UtenteLoggato;

import jakarta.persistence.EntityManager;

@Service
public class EmissionePdgOdmService {
	@Autowired
	EmissionePdgOdmRepository emissionePdgOdmRepository;
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired
	JobRichiestaRepository jobRichiestaRepository;
	@Autowired
	UserLoginRepository userLoginRepository;
	@Autowired
	StatusService statusService;
	@Autowired
	ApiFileService apiFileService;
	@Autowired
	PdfService pdfService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private EntityManager entityManager;
	
	@Transactional(rollbackFor = Exception.class)
	public boolean accessEmettiPdg(Long idRichiesta) {
		return statusService.checkAccessoEmettiPdg(idRichiesta);
	}

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
	
	@Transactional(rollbackFor = Exception.class)
	public void  emissionePdg(EmissionePdgOdmDto emissionePdgOdmDto) throws Exception {
		
		if(pdfService.isPdfFrimato(emissionePdgOdmDto.getFile()) == false) {
			throw new RuntimeException("-ErrorInfo Per proseguire il file deve essere firmato");
		}
		
		Optional<Richiesta> richiesta = richiesteRepository.findById(emissionePdgOdmDto.getIdRichiesta());
		EmissionePdgOdm emissionePdgOdm = new EmissionePdgOdm();
		emissionePdgOdm.setIdRichiesta(richiesta.get().getIdRichiesta());
		emissionePdgOdm.setIdTipoPdg(emissionePdgOdmDto.getIdTipoPdg());
		
		EmissionePdgOdm emissionePdgOdmSave = emissionePdgOdmRepository.save(emissionePdgOdm);
		
		String path = "/" + emissionePdgOdmSave.getIdRichiesta() + "/odm/" + emissionePdgOdmSave.getIdEmissionePdg();
		MercurioFile infoFile = apiFileService.insertFile(path, "pdg", emissionePdgOdmDto.getFile());
		emissionePdgOdmSave.setDocumentIdClient(infoFile.getDocumentIdClient());
		emissionePdgOdmSave.setContentId(infoFile.getContentId());
		emissionePdgOdmSave.setNomeAllegato(emissionePdgOdmDto.getNomeFile());
		emissionePdgOdmRepository.save(emissionePdgOdmSave);
		
		Long numIscrAlbo;
		richiesta.get().setIdStato((long) 4);
		if(richiesta.get().getNumIscrAlbo() == null) {
			// SOLO SE NON E' STATO MAI VALORIZZATO VERRA' INSERITTO IL SEQ. DEL NUMERO ISCRIZIONE ALBO
			numIscrAlbo = ((Number) entityManager.createNativeQuery("SELECT NUMERO_ISCRIZIONE_ALBO_SEQ.NEXTVAL FROM DUAL").getSingleResult()).longValue();
			richiesta.get().setNumIscrAlbo(numIscrAlbo);
			richiesta.get().setIscrittoAlbo(1);
			richiesta.get().setDataIscrAlbo(new Date());
		}
		else {
			numIscrAlbo = richiesta.get().getNumIscrAlbo();
		}
		
		richiesteRepository.save(richiesta.get());
		
		emissionePdgOdm.setDataEmissione(new Date());
		emissionePdgOdm.setRom(numIscrAlbo);
		emissionePdgOdmRepository.save(emissionePdgOdmSave);
		
		// SALVATAGGIO IN UN PRIMO MOMENTE SENZA GLI ID DI RIFERIMENTO A MERCURIO	
		Optional<JobRichiesta> jobRichiesta = Optional.of(new JobRichiesta());
		jobRichiesta.get().setIdRichiesta(richiesta.get().getIdRichiesta());
		jobRichiesta.get().setTipoJob("EMISSIONE PDG");
		jobRichiesta.get().setStatoJob(null);
		jobRichiesta.get().setTipoRichiesta("ODM");
		// VALORIZZATO TRAMITE L'UTENTE LOGGATO
				
		UtenteLoggato user = (UtenteLoggato) cacheManager.getCache("cacheVE").get("utenteIAMG").get();
		jobRichiesta.get().setIdUtenteRichiedente(userLoginRepository.findByCodiceFiscale(user.getCodiceFiscale()).getId());
				
		jobRichiesta.get().setDataOraRichiesta(new Date());		

		jobRichiestaRepository.save(jobRichiesta.get());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public PdfDto  getFilePdg(Long idEmissionePdg) throws Exception {
		Optional<EmissionePdgOdm> emissionePdgOdm = emissionePdgOdmRepository.findById(idEmissionePdg);
		return new PdfDto("pdg", 
						  apiFileService.getFile(emissionePdgOdm.get().getDocumentIdClient(), emissionePdgOdm.get().getContentId()));
		
	}
	
}
