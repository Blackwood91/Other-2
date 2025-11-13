import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-scheda-anagrafica-comp-org-am',
  templateUrl: './scheda-anagrafica-comp-org-am.component.html',
  styleUrls: ['./scheda-anagrafica-comp-org-am.component.css']
})
export class SchedaAnagraficaCompOrgAmComponent implements OnInit {
  idRichiesta: number = 0;
  isConvalidato: boolean = false;
  @Output() eventRappresentante: EventEmitter<any> = new EventEmitter();

  codiceFiscaleSS: string = "";
  idAnagrafica: number = 0;
  idTitolo: number = 0;
  cognome: string = "";
  nome: string = "";
  sesso: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  cittadinanza: string = "";
  indirizzoPec: string = "";
  indirizzoEmail: string = "";
  indirizzoResidenza: string = "";
  numeroCivicoResidenza: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  capResidenza: string = "";
  statoResidenza: string = "";
  indirizzoDomicilio: string = "";
  numeroCivicoDomicilio: string = "";
  idComuneDomicilio: number = 0;
  capDomicilio: string = "";
  statoDomicilio: string = "";
  comuneDomicilioEstero: string = "";
  idQualifica: string = "";
  descQualifica: string = "";
  fileDocumento: any = null;
  // Dati di riferimento
  isEsteroNascita: string = "false";
  isEsteroResidenza: string = "false";
  isEsteroDomicilio: string = "false";
  comuneSceltoNascita: string = "";
  comuneSceltoResidenza: string = "";
  comuneSceltoDomicilio: string = "";
  showListComuneNascita: boolean = false;
  showListComuneResidenza: boolean = false;
  showListComuneDomicilio: boolean = false;
  comuniNascita: any = [];
  comuniResidenza: any = [];
  comuniDomicilio: any = [];
  titoliAnagrafiche: any = [];
  qualifiche: any = [];
  existFileDocumento: boolean = false;
  domicilio: boolean = false;
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
  validComuneSceltoResidenza: boolean = true;
  validStatoResidenza: boolean = true;
  validComuneResidenzaEstero: boolean = true;
  validIndirizzoResidenza: boolean = true;
  validIdQualifica: boolean = true;
  validNumeroCivicoResidenza: boolean = true;
  validCapResidenza: boolean = true;
  validPec: boolean = true;
  validMail: boolean = true;
  validDescQualifica: boolean = true;
  validIndirizzoDomicilio: boolean = true;
  validNumeroDomicilio: boolean = true;
  validCapDomicilio: boolean = true;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {

  }

  openModal(idRichiesta: number, idAnagrafica: number) { 
    // PER NON AVERE PROBLEMI SE GIA' E' STATA APERTA UNA MODALE IN PRECEDENZA
    this.resetParameters();
    this.idRichiesta = idRichiesta;
    this.idAnagrafica = idAnagrafica;

    this.loadTitoliAnagrafiche();
    this.loadSchedaAnagrafica();
    this.loadExistFile();
    this.loadIsConvalidato();
    this.loadQualifiche();

    const buttonActiveModal = document.getElementById("activeModalSchedaAnagrafica");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  //uso questo metodo di atto-riepilogativo perchè 
  //sono i medesimi dati ad essere richiamati
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
            this.statoResidenza = res.statoResidenza;
            this.idComuneResidenza = res.idComuneResidenza;
            this.comuneResidenzaEstero = res.comuneResidenzaEstero;
            this.indirizzoResidenza = res.indirizzo;
            this.numeroCivicoResidenza = res.numeroCivico;
            this.capResidenza = res.cap;
            this.indirizzoDomicilio = res.indirizzoDomicilio;
            this.numeroCivicoDomicilio = res.numeroCivicoDomicilio;
            this.statoDomicilio = res.statoDomicilio;
            this.idComuneDomicilio = res.idComuneDomicilio;
            this.comuneDomicilioEstero = res.comuneDomicilioEstero;
            this.capDomicilio = res.capDomicilio;
            this.indirizzoPec = res.indirizzoPec;
            this.indirizzoEmail = res.indirizzoEmail;
            this.idQualifica = res.idQualifica.toString();
            this.descQualifica = res.descQualifica;
            // SE VALORIZZATO L'INDIRIZZO DOMICILIO VERRA CONSIDERATO SELEZIONATO IL DOMICILIO
            this.domicilio = (this.indirizzoDomicilio != "" && this.indirizzoDomicilio != null) ? true : false;

            this.loadComuneNascitaUpdate(res.idComuneNascita);
            this.loadComuneResidenzaUpdate(res.idComuneResidenza);
            this.loadComuneDomicilioUpdate(res.idComuneDomicilio);
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

  loadComuneResidenza(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME.getAllComuni('comune/getAllComuneByNome', nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniResidenza = res;
            this.showListComuneResidenza = true;
          }
        })
    }
    else {
      this.showListComuneResidenza = false;
    }
  }

  loadComuneDomicilio(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME.getAllComuni('comune/getAllComuneByNome', nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniDomicilio = res;
            this.showListComuneDomicilio = true;
          }
        })
    }
    else {
      this.showListComuneDomicilio = false;
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

  loadComuneResidenzaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune('comune/getComune', idComune)
        .subscribe({
          next: (res: any) => {
            this.idComuneResidenza = res.idCodComune;
            this.comuneSceltoResidenza = res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
            this.showListComuneResidenza = false;
          }
        })
    }
    else {
      this.isEsteroResidenza = "true";
    }
  }

  loadComuneDomicilioUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune('comune/getComune', idComune)
        .subscribe({
          next: (res: any) => {
            this.idComuneDomicilio = res.idCodComune;
            this.comuneSceltoDomicilio = res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
            this.showListComuneDomicilio = false;
          }
        })
    }
    else {
      this.isEsteroDomicilio = "true";
    }
  }

  selectComuneNascita(idComune: string, comune: string) {
    this.idComuneNascita = parseInt(idComune);
    this.comuneSceltoNascita = comune;
    this.showListComuneNascita = false;
  }

  selectComuneResidenza(idComune: string, comune: string) {
    this.idComuneResidenza = parseInt(idComune);
    this.comuneSceltoResidenza = comune;
    this.showListComuneResidenza = false;
  }

  selectComuneDomicilio(idComune: string, comune: string) {
    this.idComuneDomicilio = parseInt(idComune);
    this.comuneSceltoDomicilio = comune;
    this.showListComuneDomicilio = false;
  }

  loadTitoliAnagrafiche() {
    this.serviceME.getAllTitoliAnagrafiche('titoloAnagrafiche/getAll')
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        }
      })
  }

  loadExistFile() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta)
    .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getFileRappresentante('pdf/getFileRappresentante', params)
      .subscribe((res: any) => {
        // già esiste file documento
        if(res != null && res[0] != null) {
          this.existFileDocumento = true;
        }
        else {
          this.existFileDocumento = false;
        }
    });
  }

  resetParameters() {
    this.idRichiesta = 0;
    this.codiceFiscaleSS = "";
    this.idAnagrafica = 0;
    this.idTitolo = 0;
    this.cognome = "";
    this.nome = "";
    this.sesso = "";
    this.dataNascita = "";
    this.idComuneNascita = 0;
    this.comuneNascitaEstero = "";
    this.statoNascita = "";
    this.cittadinanza = "";
    this.indirizzoPec = "";
    this.indirizzoEmail = "";
    this.indirizzoResidenza = "";
    this.numeroCivicoResidenza = "";
    this.idComuneResidenza = 0;
    this.comuneResidenzaEstero = "";
    this.capResidenza = "";
    this.statoResidenza = "";
    this.indirizzoDomicilio = "";
    this.numeroCivicoDomicilio = "";
    this.idComuneDomicilio = 0;
    this.capDomicilio = "";
    this.statoDomicilio = "";
    this.comuneDomicilioEstero = "";
    this.idQualifica = "";
    this.descQualifica = "";
    this.fileDocumento = null;
    // Dati di riferimento
    this.isEsteroNascita = "false";
    this.isEsteroResidenza = "false";
    this.isEsteroDomicilio = "false";
    this.comuneSceltoNascita = "";
    this.comuneSceltoResidenza = "";
    this.comuneSceltoDomicilio = "";
    this.showListComuneNascita = false;
    this.showListComuneResidenza = false;
    this.showListComuneDomicilio = false;
    this.comuniNascita = [];
    this.comuniResidenza = [];
    this.comuniDomicilio = [];
    this.titoliAnagrafiche = [];
    this.existFileDocumento = false;
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
    this.validComuneSceltoResidenza = true;
    this.validStatoResidenza = true;
    this.validComuneResidenzaEstero = true;
    this.validIndirizzoResidenza = true;
    this.validNumeroCivicoResidenza = true;
    this.validIdQualifica = true;
    this.validCapResidenza = true;
    this.validPec = true;
    this.validMail = true;  
    this.validDescQualifica = true;  

  }

  sessoCheckboxChange(select: string) {
    this.sesso = select;
  }

  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  closeModal() {
    const closeModal = document.getElementById('closeModal');
    closeModal!.click(); 
    this.eventRappresentante.emit("Aggiornamento effettuato");
  }

  loadQualifiche() {
    this.serviceME.getAllQualificaCompOrgAmAndCompSoc('qualifica/getAllCompOrgAmAndCompSoc')
      .subscribe({
        next: (res: any) => {
          this.qualifiche = res;
        }
      })
  }

  loadIsConvalidato() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica)

    this.serviceME
      .getStatusCompOrgAm('status/getStatusCompOrgAm', params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  /* CHECK CONTROLLI' */
  checkTitolo() {
    if (this.idTitolo != 0) {
      this.validTitolo = true;
      return true;
    }
    else {
      this.validTitolo = false;
      return false;
    }
  }

  checkSesso() {
    if (this.sesso == 'M' || this.sesso == 'F') {
      this.validSesso = true;
      return true;
    }
    else {
      this.validSesso = false;
      return false;
    }
  }

  checkCognome() {
    if (this.cognome.length > 0) {
      this.validCognome = true;
      return true;
    }
    else {
      this.validCognome = false;
      return false;
    }
  }

  checkNome() {
    if (this.nome.length > 0) {
      this.validNome = true;
      return true;
    }
    else {
      this.validNome = false;
      return false;
    }
  }

  checkDataNascita() {
    if (this.dataNascita != undefined && this.dataNascita != null && this.dataNascita != '') {
      this.validDataNascita = true;
      return true;
    }
    else {
      this.validDataNascita = false;
      return false;
    }
  }

  checkStatoNascita() {
    if (this.statoNascita.length > 0) {
      this.validStatoNascita = true;
      return true;
    }
    else {
      this.validStatoNascita = false;
      return false;
    }
  }

  checkComuneNascitaEstero() {
    if (this.comuneNascitaEstero.length > 0) {
      this.validComuneNascitaEstero = true;
      return true;
    }
    else {
      this.validComuneNascitaEstero = false;
      return false;
    }
  }

  checkComuneSceltoNascita() {
    if (this.comuneSceltoNascita != undefined && this.comuneSceltoNascita != null && this.comuneSceltoNascita != '') {
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

    if (codiceFiscaleRegex.test(codiceFiscaleUpperCase)) {
      this.validCodiceFiscale = true;
      return true;
    }
    else {
      this.validCodiceFiscale = false;
      return false;
    }
  }

  checkCittadinanza() {
    if (this.cittadinanza.length > 0) {
      this.validCittadinanza = true;
      return true;
    }
    else {
      this.validCittadinanza = false;
      return false;
    }
  }

  checkStatoResidenza() {
    if (this.statoResidenza.length > 0) {
      this.validStatoResidenza = true;
      return true;
    }
    else {
      this.validStatoResidenza = false;
      return false;
    }
  }

  checkComuneResidenzaEstero() {
    if (this.comuneResidenzaEstero.length > 0) {
      this.validComuneResidenzaEstero = true;
      return true;
    }
    else {
      this.validComuneResidenzaEstero = false;
      return false;
    }
  }

  checkComuneSceltoResidenza() {
    if (this.comuneSceltoResidenza != undefined && this.comuneSceltoResidenza != null && this.comuneSceltoResidenza != '') {
      this.validComuneSceltoResidenza = true;
      return true;
    }
    else {
      this.validComuneSceltoResidenza = false;
      return false;
    }
  }

  checkIndirizzoResidenza() {
    if (this.indirizzoResidenza.length > 0) {
      this.validIndirizzoResidenza = true;
      return true;
    }
    else {
      this.validIndirizzoResidenza = false;
      return false;
    }
  }

  checkNumeroCivicoResidenza() {
    if (this.numeroCivicoResidenza.length > 0) {
      this.validNumeroCivicoResidenza = true;
      return true;
    }
    else {
      this.validNumeroCivicoResidenza = false;
      return false;
    }
  }

  checkIdQualifica() {
    if(this.idQualifica === "" || this.idQualifica === null){
      this.validIdQualifica = false;
      return false;
    }
    
    this.validIdQualifica = true;
    return true;
  }

  checkDescQualifica() {
    if(this.idQualifica === "6"){
      if (this.descQualifica.length > 0) {
        this.validDescQualifica = true;
        return true;
      }
      else {
        this.validDescQualifica = false;
        return false;
      }
    }
    else {
      this.validDescQualifica = true;
      return true;
    }
  }

  checkCapResidenza() {
    if (this.capResidenza.length == 5) {
      this.validCapResidenza = true;
      return true;
    }
    else {
      this.validCapResidenza = false;
      return false;
    }
  }

  checkPec() {
    const pecRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (pecRegex.test(this.indirizzoPec)) {
      this.validPec = true;
      return true;
    }
    else {
      this.validPec = false;
      return false;
    }
  }

  checkMail() {
    // if(this.indirizzoEmail.length > 0) {
    //   this.validMail = true;
    //   return true;
    // }   
    // else {
    //   this.validMail = false;
    //   return false;
    // }

    const mailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (mailRegex.test(this.indirizzoEmail)) {
      this.validMail = true;
      return true;
    }
    else {
      this.validMail = false;
      return false;
    }
  }
  
  checkDomicilio() {
    let esito = true;
    if (this.domicilio) {
      if (this.indirizzoDomicilio == null || this.indirizzoDomicilio.length == 0) {
        this.validIndirizzoDomicilio = false
        esito = false;
      }

      if (this.numeroCivicoDomicilio == null || this.numeroCivicoDomicilio.length == 0) {
        this.validNumeroDomicilio = false
        esito = false;
      }

      if (this.capDomicilio == null || this.capDomicilio.length == 0) {
        this.validCapDomicilio = false
        esito = false;
      }

      if (esito === false) {
        return false;
      }
      else {
        return true;
      }

    }
    else {
      return true;
    }
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneCompOrgAmAndCompSoc('status/validazioneCompOrgAmAndCompSoc', params)
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
      .annullaCompOrgAmAndCompSoc('status/annullaCompOrgAmAndCompSoc', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', "l'annullamento è avvenuto con successo!");
          this.closeModal();
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

}
