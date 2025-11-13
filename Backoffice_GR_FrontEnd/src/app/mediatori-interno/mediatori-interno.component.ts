import { Inject, Injectable, Component, HostBinding, Output, Input, OnInit, SimpleChanges, EventEmitter } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AppComponent } from '../app.component';
import { GiustiziaService } from '../giustizia.service';
import { HttpParams } from '@angular/common/http';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import * as moment from 'moment'; //SERVE PER FORMATTARE LA DATA
import { SharedService } from '../shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-mediatori-interno',
  templateUrl: './mediatori-interno.component.html',
  styleUrls: ['./mediatori-interno.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class MediatoriInternoComponent implements OnInit {

  constructor(public fb: FormBuilder, 
			  private appComponent: AppComponent, 
			  private serviceGR: GiustiziaService, 
			  private sharedService: SharedService,
			  @Inject(APP_ENVIRONMENT) private env:AppEnvironment) {

    this.form = fb.group({
      idMediatore: "",//ID_MEDIATORE
      nomeMediatore: "",
      cognomeMediatore: "",
      codiceFiscale: "",
      indirizzoPec: "",
      luogoDiNascita: "",
      dataDiNascita: "",
      indirizzo: "",
      civico: "",
      cittaDiResidenza: "",
      provinciaDiResidenza: "",
      cap: "",
      isFormatore: "",
      numeroIscrizioneElenco: "",
      requisitiIscrizioneElenco: "",
      dataIscrizioneElenco: "",
      stato: "",
      enteAttestato: "",
      enteAttestatoID: "",
      tipologiaEnte: "",
      isConvenzionato: "",
      provvedimento: "",
      indirizzo1: "",
       numero_civico: "",
       citta_residenza: "",
       provicina_residenza: "",
       stato_iscrizione_id: "",
       data_stato: "",
       data_fine: "",
       tipologia: "",
       motivazione:"",
       id_stato_mediatore:"",
       data_provvedimento:"",
    })

  }
  //Var Tabella
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  colonna: string = "";
  selectedFileCsv: any;

  form: FormGroup;
  mediatore!: object;

  dateResult: any;

  ngOnInit() { 
    this.caricaTabella();
    this.caricaData();
  }

  onAggiornamentoMediatore() {
    this.caricaTabella()
  }
  
  caricaData() {
    this.serviceGR.getUltimaModificaMediatore("mediatori/getUltimaModificaMediatore", {}).subscribe((res: any) => {
      this.dateResult = res.dataUltimaModifica;
    })
  }

  caricaTabella() {
    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", {}).subscribe((res: any) => {
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

    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
      this.totalResult = res.totalResult;
    })
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })
    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })
    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })
    this.indexPage = index - 1;
  }

  //FINE FUNZIONI TABELLA

  //FUNZIONI MODAL

  //INIZIO FUNZIONI MODAL

  //FUNZIONI COMPONENTI FIGLIO

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

  sortTable(colonna: any) {
    colonna = this.checkColonna(colonna);

    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('colonna', colonna);

    this.serviceGR.getElencoPubblico("mediatori/getElencoPubblico", params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
      this.totalResult = res.totalResult;
    })
    //FINE FUNZIONI MODAL
  }

  onFileSelectedCsv(event: any) {
    this.selectedFileCsv = event.target.files[0];
  }

  inserimentoMediatoriCsv() {
    // Chiusura moodal
    var buttonActiveModal = document.getElementById("closeLoadCsv");

    // se file vuoto...
    let formData = new FormData();
    formData.append('file', new Blob([this.selectedFileCsv], { type: 'multipart/form-data;boundary=--my-boundary--' }));
    this.selectedFileCsv = null;

    this.serviceGR.saveMediatoriFileCsv("mediatori/saveMediatoriFileCSV", formData).subscribe({
      next: (res: any) => { 
          // new impl
          // solo se qualche mediatore non è stato valorizzato tornerà questo campo dalla risposta
          if(res.paramsNotValid) {
            this.sharedService.onMessage('success-attention', "L'inserimento dei mediatori tramite csv è avvenuto parzialmente per le seguenti motivazioni:" +  res.paramsNotValid);
          }
          else {
            this.sharedService.onMessage('success', "L'inserimento dei mediatori tramite csv è avvenuto con successo");
          } 
      },
      error: (error: any) => { this.sharedService.onMessage('error', error); },
      complete: () => {
        // Chiusura modal csv
        buttonActiveModal != null ? buttonActiveModal.click() : "";
        // Ricaricamento della tabella
        this.attivaRicerca();
      }
    })
  }

  //FUNZIONI MODAL
  openUpdateMediatore(indexmediatore: number) {
    this.mediatore = this.tableResult.find((object) => object.idMediatore === indexmediatore);
    var buttonActiveModal = document.getElementById("activeModalInsertMediatore");
    // Esegui il click sul bottone nascosto "activeModalInsertElezione"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }
}