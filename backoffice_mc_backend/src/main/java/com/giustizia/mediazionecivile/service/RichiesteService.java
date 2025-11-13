package com.giustizia.mediazionecivile.service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giustizia.mediazionecivile.dto.DomandaIscrizioneOdmDto;
import com.giustizia.mediazionecivile.dto.PolizzaAssicurativaDto;
import com.giustizia.mediazionecivile.dto.RichiestaIntegrazioneDto;
import com.giustizia.mediazionecivile.dto.RichiestaIscrForElencoDto;
import com.giustizia.mediazionecivile.dto.SezionePrimaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneQuartaDomOdmDto;
import com.giustizia.mediazionecivile.dto.SezioneSecondaDomOdmDto;
import com.giustizia.mediazionecivile.model.AnagraficaOdm;
import com.giustizia.mediazionecivile.model.Richiesta;
import com.giustizia.mediazionecivile.model.SoggettoRichiesta;
import com.giustizia.mediazionecivile.model.StatoModuliRichiestaFigli;
import com.giustizia.mediazionecivile.projection.SezioneSecDomOdmProjection;
import com.giustizia.mediazionecivile.projection.PolizzaAssicurativaProjection;
import com.giustizia.mediazionecivile.projection.RichiestaDomIscProjection;
import com.giustizia.mediazionecivile.projection.RichiestaProjection;
import com.giustizia.mediazionecivile.projection.SezionePrimaDOMODMProjection;
import com.giustizia.mediazionecivile.projection.SezioneQuartaDomOdmProjection;
import com.giustizia.mediazionecivile.repository.AnagraficaOdmRepository;
import com.giustizia.mediazionecivile.repository.RichiesteRepository;
import com.giustizia.mediazionecivile.repository.SedeRepository;
import com.giustizia.mediazionecivile.repository.SoggettoRichiestaRepository;
import com.giustizia.mediazionecivile.repository.StatoModuliRichiestaFigliRepository;

@Service
public class RichiesteService {
	@Autowired
	RichiesteRepository richiesteRepository;
	@Autowired
	SedeRepository sedeRepository;
	@Autowired
	SoggettoRichiestaRepository soggettoRRepository;
	@Autowired
	AnagraficaOdmRepository anagraficaOdmRepository;
	@Autowired
	StatoModuliRichiestaFigliRepository statoModuliRichiestaFigliRepository;
	@Autowired
	StatusService convalidazioneService;

	public RichiestaProjection getRichiestaForSocieta(Long idRichiesta) {
		return richiesteRepository.findProjectionByIdRichiesta(idRichiesta);
	}

	public RichiestaDomIscProjection getRichiestaDomIscr(Long idRichiesta) {
		return richiesteRepository.findProjectiondDomByIdRichiesta(idRichiesta);
	}

	public SezionePrimaDOMODMProjection getSezionePrimaDOMODMP(Long idRichiesta) {
		return richiesteRepository.findSezionePrimaDOMODMByIdRichiesta(idRichiesta);
	}

	public SezioneSecDomOdmProjection getSezioneSecondaDOMODMP(Long idRichiesta) {
		// PER TROVARE SOLO IL RAPPRESENTATE LEGALE NELL'ANAGRAFICHE
		return anagraficaOdmRepository.findByIdProjectionSezSecOdm(idRichiesta);
	}

	public SezioneQuartaDomOdmDto getSezioneQuartaDOMODMP(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);

		SezioneQuartaDomOdmDto sezione4 = new SezioneQuartaDomOdmDto();
		sezione4.setIdRichiesta(richiesta.get().getIdRichiesta());
		sezione4.setNumCompoOrgAmm(richiesta.get().getNumCompoOrgAmm());
		sezione4.setNumCompoCompSoc(richiesta.get().getNumCompoCompSoc());
		sezione4.setDurataCarica(richiesta.get().getDurataCarica());
		sezione4.setIdModalitaCostOrgani(richiesta.get().getIdModalitaCostOrgani());
		sezione4.setDataCostituOrg(richiesta.get().getDataCostituOrg());
		sezione4.setAutonomo(richiesta.get().getAutonomo());
		sezione4.setNumPersonaleAdetto(richiesta.get().getNumPersonaleAdetto());
		sezione4.setFontiDiFinanziamento(richiesta.get().getFontiDiFinanziamento());
		sezione4.setDurataCostituzioneOrganismo(richiesta.get().getDurataCostituzioneOrganismo());
		sezione4.setModalitaGestioneContabile(richiesta.get().getModalitaGestioneContabile());
		sezione4.setNumMediatori(richiesta.get().getNumMediatori());
		sezione4.setNumMediatoriInter(richiesta.get().getNumMediatoriInter());
		sezione4.setNumMediatoriCons(richiesta.get().getNumMediatoriCons());
		
		Optional<AnagraficaOdm> respOrganismo = anagraficaOdmRepository.findByIdRichiestaAndIdQualifica(idRichiesta, (long)2);
		if(respOrganismo.isPresent()) {
			sezione4.setRespOrganismo(respOrganismo.get().getCognome() + " " + respOrganismo.get().getNome());
		}
		
		return sezione4;
	}
	
	public boolean richiestaNaturaIsAutonomo(Long idRichiesta) {
		Optional<Richiesta> richiesta = richiesteRepository.findById(idRichiesta);
		if(richiesta.get().getAutonomo() == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public PolizzaAssicurativaProjection getPolizzaAssicurativa(Long idRichiesta) {
		Optional<PolizzaAssicurativaProjection> polizza = richiesteRepository
				.findPolizzaAssicurativaDOMODMByIdRichiesta(idRichiesta);
		if (polizza.isPresent() == false) {
			return null;
		} else {
			return polizza.get();
		}
	}

	public HashMap<String, Object> getAllRichiestePaged(Pageable pageable, Long idStatoRichiesta, Long idTipoRichiesta) {
        HashMap<String, Object> response = new HashMap<>();
        Page<Object[]> resultPage = richiesteRepository.findAllForElencoRicIsc(pageable, idStatoRichiesta, idTipoRichiesta);

    /*    if (searchText.isEmpty()) {
            resultPage = societaRepository.findAllForSocietaAndRichiesta(pageable);
        } else {
            resultPage = societaRepository.findAllForSocietaAndRichiestaByText(searchText, pageable);
        }*/

        List<Object[]> resultList = resultPage.getContent();

        List<RichiestaIscrForElencoDto> richieste = new ArrayList<RichiestaIscrForElencoDto>();
        for (Object[] obj : resultList) {
        	RichiestaIscrForElencoDto richiestaRow = new RichiestaIscrForElencoDto();
        	richiestaRow.setIdRichiesta((Long) obj[0]);
        	richiestaRow.setNumIscrAlbo((Long) obj[1]);
        	richiestaRow.setDataRichiesta((Date) obj[2]);
        	richiestaRow.setRagioneSociale((String) obj[3]);
        	richiestaRow.setPIva((String) obj[4]);
        	richiestaRow.setCodFiscSocieta((String) obj[5]);
        	richiestaRow.setStatoRichiesta((String) obj[6]);
        	richiestaRow.setIdTipoRichiesta((Integer) obj[7]);
        	
        	richieste.add(richiestaRow);
        }
        response.put("result", richieste);
        response.put("totalResult", resultPage.getTotalElements());

        return response;        
    }
	
}
