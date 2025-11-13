import { Inject, Component, HostBinding, Output, Input, OnInit, SimpleChanges, EventEmitter, Injectable } from '@angular/core';
import { AppComponent } from '../app.component';
import { GiustiziaService } from '../giustizia.service';
import { HttpParams, HttpClient, HttpEvent, HttpRequest, HttpHeaders  } from '@angular/common/http';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as moment from 'moment'; //SERVE PER FORMATTARE LA DATA
import { SharedService } from '../shared.service';
import { environment } from '../../environments/environment';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';


@Component({
  selector: 'app-mediatori',
  templateUrl: './mediatori.component.html',
  styleUrls: ['./mediatori.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})

export class MediatoriComponent implements OnInit {
	
  constructor(public fb: FormBuilder, 
			  private appComponent: AppComponent, 
		      private serviceGR: GiustiziaService, 
			  private sharedService: SharedService,
			  @Inject(APP_ENVIRONMENT) private env:AppEnvironment) {

    this.form = fb.group({
      nomeMediatore: "",
      cognomeMediatore: "",
      codiceFiscale: "",
      isFormatore: "",
      numeroIscrizioneElenco: "",
      dataIscrizioneElenco: "",
      dataDiNascita: "",
    })

  }
  form: FormGroup;

  
  //Var Per Passaggio dati component figli
  message = "";
  id = "";
  elezione = {};

  //Var Tabella
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  colonna: string = "";
  selectedFileCsv : any;


  ngOnInit() {
    this.caricaTabella();
  }

  caricaTabella() {
    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", {}).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
      this.totalResult = res.totalResult;
    })
  }


	  //FUNZIONI TABELLA
	  attivaRicerca() {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', this.indexPage - 1);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
	      this.totalResult = res.totalResult;
	    })
	  }

	  cambiaPagina(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index - 1);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index;
	  }

	  nextPage(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index + 1;
	  }

	  previousPage(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index - 2);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index - 1;
	  }

  checkColonnaSvg(colonna: String) {
    let svgColonna: string = "";

    if (colonna == this.colonna) {

      if (colonna.includes('+') == false && colonna.includes('-') == false) {
        svgColonna = `\\assets\\svg\\colonna.svg`;
      }
      else if (colonna.includes('+')) {
        svgColonna = "\\assets\\svg\\colonna_desc.svg";
      }
      else if (colonna.includes('-')) {
        svgColonna = "\\assets\\svg\\colonna_asc.svg";
      }
    }
    else {
      svgColonna = "\\assets\\svg\\colonna.svg";
    }
    return svgColonna;
  }

  checkColonna(colonna: any) {
    if (this.colonna == colonna) {
      colonna = colonna + '+';
      this.colonna = colonna;
    }
    else if (this.colonna == (colonna + '+')) {
      colonna = colonna + '-';
      this.colonna = colonna;

    }
    else if (this.colonna == (colonna + '-')) {
      colonna = colonna;
      this.colonna = colonna;

    }
    return colonna;
  }
  //FINE FUNZIONI TABELLA

  //FUNZIONI MODAL
  openUpdateElezione(indexElezione: number) {
    this.elezione = this.tableResult.find((object) => object.pkElezione === indexElezione);
    var buttonActiveModal = document.getElementById("activeModalInsertElezione");
    // Esegui il click sul bottone nascosto "activeModalInsertElezione"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  openModalDeleteElezione(pkElezione: number) {
    this.elezione = this.tableResult.find((object) => object.pkElezione === pkElezione);

    this.message = "prova mia";
    this.id = "22";

    var buttonActiveModal = document.getElementById("openModalConfirm");
    // Esegui il click sul bottone nascosto "activeModalInsertElezione"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }
  //INIZIO FUNZIONI MODAL


  //FUNZIONI COMPONENTI FIGLIO

  onAggiornamentoElezione() {
    this.caricaTabella()
  }

  //Serve per poter ricevere var dichiarata dentro alla classe figlio
  onConfirmedRequestDelete(event: any) {
    const params = new HttpParams()
    let elezioneStructure: any = this.elezione;

    this.form.patchValue({
      pkElezione: elezioneStructure.pkElezione,
      titolo: elezioneStructure.titolo,
      descrizione: elezioneStructure.descrizione,
      tipologiaElezione: elezioneStructure.tipologiaElezione,
      flagAttivo: 1,//elezioneStructure.flagAttivo == 1 ? true : false,
      numeroschede: elezioneStructure.numeroschede,
      dataElezioneDa: (moment(elezioneStructure.dataElezioneDa)).format('YYYY-MM-DD'), //elezioneStructure.dataElezioneDa
      dataElezioneA: (moment(elezioneStructure.dataElezioneA)).format('YYYY-MM-DD'), //elezioneStructure.dataElezioneA,
      pkvUtenteInserimento: elezioneStructure.pkvUtenteInserimento,
      pkvUtenteModifica: elezioneStructure.pkvUtenteModifica,
      dataInserimento: (moment(elezioneStructure.dataInserimento)).format('YYYY-MM-DD'), //elezioneStructure.dataInserimento,
      dataModifica: (moment(elezioneStructure.dataModifica)).format('YYYY-MM-DD'), //elezioneStructure.dataModifica,
      dataElezione: (moment(elezioneStructure.dataElezione)).format('YYYY-MM-DD'), //elezioneStructure.dataElezione,
      note: elezioneStructure.note,
      eliminato: 1,
    });

    this.serviceGR.cancellaElezione("cancellaElezione", this.form.value).subscribe({
      next: (res: any) => { this.sharedService.onMessage('success', res.message); this.onAggiornamentoElezione(); },
      error: (error: any) => { this.sharedService.onMessage('error', error.message); },
      complete: () => { }
    })

  }
  
  onFileSelectedCsv(event: any) {
	    this.selectedFileCsv = event.target.files[0];
	  }
}
