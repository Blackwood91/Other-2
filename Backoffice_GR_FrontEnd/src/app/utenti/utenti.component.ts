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
  selector: 'app-utenti',
  templateUrl: './utenti.component.html',
  styleUrls: ['./utenti.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class UtentiComponent implements OnInit {

  constructor(public fb: FormBuilder, 
			  private appComponent: AppComponent, 
			  private serviceGR: GiustiziaService, 
			  private sharedService: SharedService,
			  @Inject(APP_ENVIRONMENT) private env:AppEnvironment) {

    this.form = fb.group({
      utenteId: "",
      nomeUtente: "",
      cognomeUtente: "",
      codiceFiscaleUtente: "",
      isAbilitato: "",
      enteAppartenenza: "",
      dataLogin: "", //utentiStructure.dataLogin
      dataLogout: "", //utentiStructure.dataLogout,
      idRuoloUtente: "",
    })

  }
  form: FormGroup;
  utenti!: object;
  //Var Tabella
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  colonna: string = "";
  selectedFileCsv: any;

  dateResultUtente: any;
  dataResultDisabilita: any;


  ngOnInit() {
    this.caricaTabella();
    this.caricaData();
    this.caricaUtenteDisabilitato();
  }

  caricaTabella() {
      this.serviceGR.getAllUtenti("utente-abilitato/getAllUtenti", {}).subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
        this.totalResult = res.totalResult;
      })
    
  }

  caricaData() {
    this.serviceGR.getUltimaModificaUtente("utente-abilitato/getUltimaModificaUtente", {}).subscribe((res: any) => {
      this.dateResultUtente = res.dataUltimaModifica;
    })
  }

  caricaUtenteDisabilitato(){
    this.serviceGR.getUltimoDisabilitaUtente("utente-abilitato/getUltimoDisabilitaUtente", {}).subscribe((res: any) => {
      this.dataResultDisabilita = res.dataUltimaModifica;
    })
  }


  //FUNZIONI TABELLA
  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1);

    this.serviceGR.getAllUtenti("utente-abilitato/getAllUtenti", params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
      this.totalResult = res.totalResult;
    })
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceGR.getAllUtenti("utente-abilitato/getAllUtenti", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceGR.getAllUtenti("utente-abilitato/getAllUtenti", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceGR.getAllUtenti("utente-abilitato/getAllUtenti", params).subscribe((res: any) => {
      this.tableResult = res.result;
    })

    this.indexPage = index - 1;
  }

  //FINE FUNZIONI TABELLA

  //FUNZIONI MODAL
  openUpdateUtenti(indexUtente: number) {
    this.utenti = this.tableResult.find((object) => object.utenteId === indexUtente);
    var buttonActiveModal = document.getElementById("activeModalInsertUtenti");
    // Esegui il click sul bottone nascosto "activeModalInsertElezione"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  openModalDeleteUtenti(utenteId: number) {
    this.utenti = this.tableResult.find((object) => object.utenteId === utenteId);
    var buttonActiveModal = document.getElementById("openModalConfirm");
    // Esegui il click sul bottone nascosto "activeModalInsertElezione"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }
  //INIZIO FUNZIONI MODAL


  //FUNZIONI COMPONENTI FIGLIO

  onAggiornamentoUtente() {
    this.caricaTabella()
  }

  //Serve per poter ricevere var dichiarata dentro alla classe figlio
  onConfirmedRequestDelete(event: any) {
    const params = new HttpParams()
    let utentiStructure: any = this.utenti;

    this.form.patchValue({
      utenteId: utentiStructure.utenteId,
      nomeUtente: utentiStructure.nomeUtente,
      cognomeUtente: utentiStructure.cognomeUtente,
      codiceFiscaleUtente: utentiStructure.codiceFiscaleUtente,
      isAbilitato: utentiStructure.isAbilitato,
      enteAppartenenza: utentiStructure.enteAppartenenza,
      dataLogin: (moment(utentiStructure.dataLogin)).format('YYYY-MM-DD'), //utentiStructure.dataLogin
      dataLogout: (moment(utentiStructure.dataLogout)).format('YYYY-MM-DD'), //utentiStructure.dataLogout,
      idRuoloUtente: utentiStructure.idRuoloUtente,

    });

    this.serviceGR.deleteUtente("utente-abilitato/deleteUtente", this.form.value).subscribe({
      next: (res: any) => { this.sharedService.onMessage('success', res.message); this.onAggiornamentoUtente(); },
      error: (error: any) => { this.sharedService.onMessage('error', error.message); },
      complete: () => { }
    })
  }


  onFileSelectedCsv(event: any) {
    this.selectedFileCsv = event.target.files[0];
  }

}