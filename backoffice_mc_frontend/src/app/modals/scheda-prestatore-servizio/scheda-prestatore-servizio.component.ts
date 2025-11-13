import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-scheda-prestatore-servizio',
  templateUrl: './scheda-prestatore-servizio.component.html',
  styleUrls: ['./scheda-prestatore-servizio.component.css']
})
export class SchedaPrestatoreServizioComponent implements OnInit {
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
  isModifica = false;
  isConvalidato = false;
  @Output() eventRappresentante: EventEmitter<any> = new EventEmitter();
  
  idTitolo: number = 0;
  sesso: string = "";
  cognome:string = "";
  nome: string = ""; 
  dataNascita: string = "";
  poDataAssunzione: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  codiceFiscaleSS: string = "";
  cittadinanza: string = "";  
  poTipoRappOdm:  string = "";
  // Dati di riferimento
  isEsteroNascita: string = "false";
  comuneSceltoNascita: string = "";  
  showListComuneNascita: boolean = false; 
  comuniNascita: any = [];
  titoliAnagrafiche: any = [];
  fileDocumento: any = null;
  //variabili per checks di validità
  validTitolo: boolean = true;
  validSesso: boolean = true;
  validCognome: boolean = true;
  validNome: boolean = true;
  validDataNascita: boolean = true;
  validComuneSceltoNascita: boolean = true;
  validStatoNascita: boolean = true;
  validComuneNascitaEstero: boolean = true;
  validCodiceFiscale: boolean = true;
  validCittadinanza: boolean = true;
  validRapporto: boolean = true;
  validDataAssunzione: boolean = true;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
  }

  activeModifica() {
    this.isModifica = true;
  }

  openModalUpdate(idRichiesta: number, idAnagrafica: number) {
    this.resetParameters();
    this.idRichiesta = idRichiesta;
    this.idAnagrafica = idAnagrafica;
    this.loadSchedaAnagrafica();
    this.moduloIsConvalidato();

    const buttonActiveModal = document.getElementById("activeModalSchedaPrestatoreServizio");
    // Esegui il click sul bottone nascosto
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  resetParameters() {
    this.idTitolo = 0;
    this.sesso = "";
    this.cognome = "";
    this.nome = ""; 
    this.dataNascita = "";
    this.poDataAssunzione = "";
    this.idComuneNascita = 0;
    this.comuneNascitaEstero = "";
    this.statoNascita = "";
    this.codiceFiscaleSS = "";
    this.cittadinanza = "";  
    this.poTipoRappOdm = "";
    // Dati di riferimento
    this.isEsteroNascita = "false";
    this.comuneSceltoNascita = "";  
    this.showListComuneNascita = false; 
    this.fileDocumento = null;
    //variabili per checks di validità
    this.validTitolo = true;
    this.validSesso = true;
    this.validCognome = true;
    this.validNome = true;
    this.validDataNascita = true;
    this.validComuneSceltoNascita = true;
    this.validStatoNascita = true;
    this.validComuneNascitaEstero = true;
    this.validCodiceFiscale = true;
    this.validCittadinanza = true;
    this.validRapporto = true;
    this.validDataAssunzione = true;
  }

  loadSchedaAnagrafica(): void {
    const params = new HttpParams()
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME.getAnagraficaById('anagrafica/getAnagraficaById', params)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.idTitolo = res.idTitoloAnagrafica;
            this.sesso = res.sesso;
            this.cognome = res.cognome;
            this.nome = res.nome;
            this.dataNascita = (moment(res.dataNascita)).format('YYYY-MM-DD');
            this.statoNascita = res.statoNascita;
            this.idComuneNascita = res.idComuneNascita;
            this.comuneNascitaEstero = res.comuneNascitaEstero
            this.codiceFiscaleSS = res.codiceFiscale;
            this.cittadinanza = res.cittadinanza;
            this.poTipoRappOdm = res.poTipoRappOdm;
            this.poDataAssunzione = (moment(res.poDataAssunzione)).format('YYYY-MM-DD');

            this.loadComuneNascitaUpdate(res.idComuneNascita);
          }
        }
      })
  }

  loadComuneNascita(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME.getAllComuni('comune/getAllComuneByNome', nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniNascita = res;
            this.showListComuneNascita = true;
          }
        })
    }
    else {
      this.showListComuneNascita = false;
    }
  }

  loadComuneNascitaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune('comune/getComune', idComune)
        .subscribe({
          next: (res: any) => {
            this.idComuneNascita = res.idCodComune;
            this.comuneSceltoNascita = res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
            this.showListComuneNascita = false;
          }
        })
    }
    else {
      this.isEsteroNascita = "true";
    }
  }

  loadTitoliAnagrafiche() {
    this.serviceME.getAllTitoliAnagrafiche('titoloAnagrafiche/getAll')
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        }
      })
  }

  selectComuneNascita(idComune: string, comune: string) {
    this.idComuneNascita = parseInt(idComune);
    this.comuneSceltoNascita = comune;
    this.showListComuneNascita = false;
  }

  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  moduloIsConvalidato() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME.getModuloIsConvalidato('status/getStatusPrestatoreServizio', params)
      .subscribe({
        next: (res: any) => {
          if(res.status === "c")
            this.isConvalidato = true;
          else 
            this.isConvalidato = false;

        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  closeModal() {
    const closeModal = document.getElementById('closeModal');
    closeModal!.click(); 
    this.eventRappresentante.emit("Aggiornamento effettuato");
  }

  convalidazione() {
    const params = new HttpParams()
                   .set('idRichiesta', this.idRichiesta)
                   .set('idAnagrafica', this.idAnagrafica);

    this.serviceME.validazionePrestatoreServizio('status/validazionePrestatoreServizio', params)
    .subscribe({
      next: (res: any) => {
        this.closeModal();
        this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!");
        return;
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
        return;
      }
    })
  }

  /* CHECK VALIDITA' */
  checkTitolo() {
    if(this.idTitolo != 0) {
      this.validTitolo = true;
      return true;
    }
    else {
      this.validTitolo = false;
      return false;
    }
  }

  checkSesso() {
    if(this.sesso == 'M' || this.sesso == 'F') {
      this.validSesso = true;
      return true;
    }
    else {
      this.validSesso = false;
      return false; 
    }
  }

  checkCognome() {
    if(this.cognome.length > 0) {
      this.validCognome = true;
      return true;
    }   
    else {
      this.validCognome = false;
      return false;
    }
  }

  checkNome() {
    if(this.nome.length > 0) {
      this.validNome = true;
      return true;
    }   
    else {
      this.validNome = false;
      return false;
    }   
  }

  checkDataNascita() {
    if(this.dataNascita != undefined && this.dataNascita != null && this.dataNascita != '') {
      this.validDataNascita = true;
      return true;
    }
    else {
      this.validDataNascita = false;
      return false;
    }
  }

  checkStatoNascita() {
    if(this.statoNascita.length > 0) {
      this.validStatoNascita = true;
      return true;
    }
    else {
      this.validStatoNascita = false;
      return false;
    }
  }

  checkComuneNascitaEstero() {
    if(this.comuneNascitaEstero.length > 0) {
      this.validComuneNascitaEstero = true;
      return true;
    }
    else {
      this.validComuneNascitaEstero = false;
      return false;
    }
  }

  checkComuneSceltoNascita() {
    if(this.comuneSceltoNascita != undefined && this.comuneSceltoNascita != null && this.comuneSceltoNascita != '') {
      this.validComuneSceltoNascita = true;
      return true;
    }
    else {
      this.validComuneSceltoNascita = false;
      return false;
    }
  }

  checkCodiceFiscale() {
    const codiceFiscaleUpperCase = this.codiceFiscaleSS.toUpperCase();
    const codiceFiscaleRegex = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;

    if(codiceFiscaleRegex.test(codiceFiscaleUpperCase)) {
      this.validCodiceFiscale = true;
      return true;
    }
    else {
      this.validCodiceFiscale = false;
      return false;
    }
  }

  checkCittadinanza() {
    if(this.cittadinanza.length > 0) {
      this.validCittadinanza = true;
      return true;
    }
    else {
      this.validCittadinanza = false;
      return false;
    }
  }

  checkRapporto() {    
    if(/*this.poTipoRappOdm != undefined && this.poTipoRappOdm != null && */this.poTipoRappOdm != '') {
      this.validRapporto = true;
      return true;
    }
    else {
      this.validRapporto = false;
      return false;
    }
  }

  checkDataAssunzione() {
    if(this.poDataAssunzione != undefined && this.poDataAssunzione != null && this.poDataAssunzione != '') {
      this.validDataAssunzione = true;
      return true;
    }
    else {
      this.validDataAssunzione = false;
      return false;
    }
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazionePrestatoreServizio('status/validazionePrestatoreServizio', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
          this.closeModal();
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  annullazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaPrestatoreServizio('status/annullaPrestatoreServizio', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', " è avvenuto con successo!");
          this.closeModal();
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

}
