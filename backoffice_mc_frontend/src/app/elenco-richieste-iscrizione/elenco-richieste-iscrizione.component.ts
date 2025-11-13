import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmMessageComponent } from '../principal-components/confirm-message/confirm-message.component';
import { ExtraInfoComponent } from '../modals/extra-info/extra-info.component';
import { MediazioneService } from '../mediazione.service';
import { SharedService } from '../shared.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import * as moment from 'moment';

@Component({
  selector: 'app-elenco-richieste-iscrizione',
  templateUrl: './elenco-richieste-iscrizione.component.html',
  styleUrls: ['./elenco-richieste-iscrizione.component.css']
})
export class ElencoRichiesteIscrizioneComponent implements OnInit {
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  isConvalidato: boolean = false;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  idAlbo: number = 0;
  dataRichiesta: string = "";
  ragioneSociale: string = "";
  partitaIva: string = "";
  idStatoRichiesta: number = '' as unknown as number;
  idTipoRichiesta: number = '' as unknown as number;
  isModifica: boolean = false;
  accediPdg: boolean = false;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private router: Router) { }
  
  ngOnInit(): void {
    this.colapsedCustomRicerca();
    this.colapsedCustomSceltaCategoria();
    this.colapsedCustomSceltaStato();
    this.colapsedCustomSceltaTipo();
    this.addCheckboxEventListeners();
    this.loadTable([], []);
  }

  colapsedCustomRicerca() {
    // Seleziona l'elemento con classe 'container-filtro-ricerca-title'
    var titleElement = document.querySelector(".container-filtro-ricerca-title");

    // Aggiungi un listener per il click
    titleElement?.addEventListener("click", function() {
        // Seleziona entrambi gli SVG
        var collapsedIcon = document.querySelector(".container-filtro-ricerca-icon.text-collapsed") as HTMLElement;
        var expandedIcon = document.querySelector(".container-filtro-ricerca-icon.text-expanded") as HTMLElement;

        // Verifica che gli elementi siano stati trovati
        if (collapsedIcon && expandedIcon) {
            // Alterna la visibilità degli SVG
            collapsedIcon.style.display = collapsedIcon.style.display === "none" ? "block" : "none";
            expandedIcon.style.display = expandedIcon.style.display === "none" ? "block" : "none";
        }
    });
  }

  
  colapsedCustomSceltaCategoria() {
    // Seleziona l'elemento con classe 'container-filtro-ricerca-title'
    var titleElement = document.querySelector("#titleScelteCategoria");

    // Aggiungi un listener per il click
    titleElement?.addEventListener("click", function() {
        // Seleziona entrambi gli SVG
        var collapsedIcon = document.querySelector("#svg-collapsed-categoria-collapsed") as HTMLElement;
        var expandedIcon = document.querySelector("#svg-collapsed-categoria-expanded") as HTMLElement;

        // Verifica che gli elementi siano stati trovati
        if (collapsedIcon && expandedIcon) {
            // Alterna la visibilità degli SVG
            collapsedIcon.style.display = collapsedIcon.style.display === "none" ? "block" : "none";
            expandedIcon.style.display = expandedIcon.style.display === "none" ? "block" : "none";
        }
    });
  }

  colapsedCustomSceltaStato() {
    // Seleziona l'elemento con classe 'container-filtro-ricerca-title'
    var titleElement = document.querySelector("#titleScelteStato");

    // Aggiungi un listener per il click
    titleElement?.addEventListener("click", function() {
        // Seleziona entrambi gli SVG
        var collapsedIcon = document.querySelector("#svg-collapsed-stato-collapsed") as HTMLElement;
        var expandedIcon = document.querySelector("#svg-collapsed-stato-expanded") as HTMLElement;

        // Verifica che gli elementi siano stati trovati
        if (collapsedIcon && expandedIcon) {
            // Alterna la visibilità degli SVG
            collapsedIcon.style.display = collapsedIcon.style.display === "none" ? "block" : "none";
            expandedIcon.style.display = expandedIcon.style.display === "none" ? "block" : "none";
        }
    });
  }

  
  colapsedCustomSceltaTipo() {
    // Seleziona l'elemento con classe 'container-filtro-ricerca-title'
    var titleElement = document.querySelector("#titleScelteTipo");

    // Aggiungi un listener per il click
    titleElement?.addEventListener("click", function() {
        // Seleziona entrambi gli SVG
        var collapsedIcon = document.querySelector("#svg-collapsed-stato-collapsed") as HTMLElement;
        var expandedIcon = document.querySelector("#svg-collapsed-scelta-expanded") as HTMLElement;

        // Verifica che gli elementi siano stati trovati
        if (collapsedIcon && expandedIcon) {
            // Alterna la visibilità degli SVG
            collapsedIcon.style.display = collapsedIcon.style.display === "none" ? "block" : "none";
            expandedIcon.style.display = expandedIcon.style.display === "none" ? "block" : "none";
        }
    });
  }

  addCheckboxEventListeners() {
    const checkboxes = document.querySelectorAll('.checkbox-filtri-ricerca') as NodeListOf<HTMLInputElement>;
    checkboxes.forEach(checkbox => {
      checkbox.addEventListener('change', () => this.categorySelection());
    });
  }

  categorySelection() {
    const selectedStati = Array.from(document.querySelectorAll('.checkbox-filtri-ricerca.stato:checked'))
      .map(checkbox => (checkbox as HTMLInputElement).value);
    
    const selectedCategorie = Array.from(document.querySelectorAll('.checkbox-filtri-ricerca.categoria:checked'))
      .map(checkbox => (checkbox as HTMLInputElement).value);

    console.log("Selected stati:", selectedStati);
    console.log("Selected categorie:", selectedCategorie);
    this.loadTable(selectedStati, selectedCategorie);
  }

  loadTable(selectedStati: string[], selectedCategorie: string[]) {
    let params = new HttpParams();

    if (selectedStati.length > 0) {
      selectedStati.forEach(stato => {
        params = params.append('idStatoRichiesta', stato);
      });
    } else {
      params = params.set('idStatoRichiesta', this.idStatoRichiesta || '');
    }

    if (selectedCategorie.length > 0) {
      selectedCategorie.forEach(categoria => {
        params = params.append('idTipoRichiesta', categoria);
      });
    } else {
      params = params.set('idTipoRichiesta', this.idTipoRichiesta || '');
    }

    console.log(params.toString()); // Per debug

    this.serviceME.getAllUtentiRichiesta('richiesta/getAllRichiestaPaged', params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
          this.totalPage = Math.ceil(res.totalResult / 10);
          this.totalResult = res.totalResult;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error.error);
        },
        complete: () => {}
      });
  }


  routerDomandaIscrizione(idRichiesta: number) {
    const params = new HttpParams()
    .set('idSocieta', idRichiesta)

    this.serviceME
      .getSocietaById('societa/getSocietaById', params)
      .subscribe((res: any) => {
        this.sharedService.onNameSocietaHeader(res.ragioneSociale);
        this.router.navigate(['/organismiDiMediazione'], { queryParams: {idRichiesta: idRichiesta, vistaMenu: "domanda_di_iscrizione"} });
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceME
      .getAllFormatoriPerAlbo('richiesta/getAllRichiestaPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index; 
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceME
    .getAllFormatoriPerAlbo('richiesta/getAllRichiestaPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceME
      .getAllFormatoriPerAlbo('richiesta/getAllRichiestaPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY') 
  }

  tipoRichiestaDescrizione(idTipoRichiesta: number) {
    if(idTipoRichiesta === 1) {
      return "Organismo di Mediazione";
    }
    else if(idTipoRichiesta === 2) {
      return "Ente di Formazione";
    }
    else if(idTipoRichiesta === 3) {
      return "Organismo ADR";
    }
    else {
      return "";
    }
  }

  emissionePdg(idRichiesta: number) {
    const params = new HttpParams()
      .set('idRichiesta', idRichiesta);

    this.serviceME
    .getAllFormatoriPerAlbo('emissionePdgOdm/accessEmettiPdg', params)
    .subscribe((res: any) => {
      this.accediPdg = res;

      if(this.accediPdg === true)
        this.router.navigate(['/emissionePdg'], { queryParams: {idRichiesta: idRichiesta} });
      else {
        this.sharedService.onMessage('error', 'Non è possibile procedere avendo moduli non validati')
      }
    });


  }

  modificaIntegr(idRichiesta: number) {
    const params = new HttpParams()
    .set('idRichiesta', idRichiesta)


        this.router.navigate(['/richiestaIntegrazione'], { queryParams: {idRichiesta: idRichiesta } });
     
  }


}

