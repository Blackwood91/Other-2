import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-scheda-comp-org-am-and-comp-soc',
  templateUrl: './scheda-comp-org-am-and-comp-soc.component.html',
  styleUrls: ['./scheda-comp-org-am-and-comp-soc.component.css']
})
export class SchedaCompOrgAmAndCompSocComponent implements OnInit {
  idRichiesta: number = 0;
  isModifica = false;

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
  domicilio: boolean = false;
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
  //variabili per checks di validità
  validIndirizzoDomicilio: boolean = true;
  validNumeroDomicilio: boolean = true;
  validCapDomicilio: boolean = true;
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
  validNumeroCivicoResidenza: boolean = true;
  validCapResidenza: boolean = true;
  validPec: boolean = true;
  validMail: boolean = true;
  validIdQualifica: boolean = true;
  validDescQualifica: boolean = true;


  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];

      this.loadTitoliAnagrafiche();
      this.loadQualifiche();
    })
  }

  activeModifica() {
    this.isModifica = true;
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
            this.idAnagrafica = res.idAnagrafica;
            this.idTitolo = res.idTitoloAnagrafica;
            this.sesso = res.sesso;
            this.cognome = res.cognome;
            this.nome = res.nome;
            this.dataNascita = (moment(res.dataNascita)).format('DD-MM-YYYY');
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

  loadQualifiche() {
    this.serviceME.getAllQualificaCompOrgAmAndCompSoc('qualifica/getAllCompOrgAmAndCompSoc')
      .subscribe({
        next: (res: any) => {
          this.qualifiche = res;
        }
      })
  }
  
  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  insertScheda() {
    if (this.finalCheck() === true) {
      let anagrafica: {
        idAnagrafica: number,
        idRichiesta: number, idTitoloAnagrafica: number, sesso: string, cognome: string, nome: string, dataNascita: string, statoNascita: string,
        idComuneNascita: number, codiceFiscale: string, cittadinanza: string, comuneNascitaEstero: string, statoResidenza: string, idComuneResidenza: number,
        indirizzo: string, numeroCivico: string, cap: string, comuneResidenzaEstero: string, indirizzoDomicilio: string, numeroCivicoDomicilio: string,
        idComuneDomicilio: number, capDomicilio: string, statoDomicilio: string, comuneDomicilioEstero: string,
        indirizzoPec: string, indirizzoEmail: string, idQualifica: number, convalidazioeRapOrRespOrOrgAm: boolean, descQualifica: string
      } = {
        idAnagrafica: this.idAnagrafica, idRichiesta: this.idRichiesta, idTitoloAnagrafica: this.idTitolo, sesso: this.sesso, cognome: this.cognome,
        nome: this.nome, dataNascita: this.dataNascita, statoNascita: this.statoNascita, idComuneNascita: this.idComuneNascita,
        codiceFiscale: this.codiceFiscaleSS, cittadinanza: this.cittadinanza, comuneNascitaEstero: this.comuneNascitaEstero,
        statoResidenza: this.statoResidenza, idComuneResidenza: this.idComuneResidenza,
        indirizzo: this.indirizzoResidenza, numeroCivico: this.numeroCivicoResidenza, cap: this.capResidenza, comuneResidenzaEstero: this.comuneResidenzaEstero,
        indirizzoDomicilio: this.indirizzoDomicilio, numeroCivicoDomicilio: this.numeroCivicoDomicilio, idComuneDomicilio: this.idComuneDomicilio,
        capDomicilio: this.capDomicilio, statoDomicilio: this.statoDomicilio, comuneDomicilioEstero: this.comuneDomicilioEstero,
        indirizzoPec: this.indirizzoPec, indirizzoEmail: this.indirizzoEmail,
        idQualifica: this.idQualifica != "" ? parseInt(this.idQualifica) : 0, convalidazioeRapOrRespOrOrgAm: true, descQualifica: this.descQualifica
      }

      // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
      if (this.isEsteroNascita == "false") {
        anagrafica.comuneNascitaEstero = "";
        anagrafica.statoNascita = "";
      }
      else {
        anagrafica.idComuneNascita = 0;
      }

      if (this.isEsteroResidenza == "false") {
        anagrafica.comuneResidenzaEstero = "";
        anagrafica.statoResidenza = "";
      }
      else {
        anagrafica.idComuneResidenza = 0;
      }

      if(this.domicilio) {
        if (this.isEsteroDomicilio == "false") {
          anagrafica.comuneDomicilioEstero = "";
          anagrafica.statoDomicilio = "";
        }
        else {
          anagrafica.idComuneDomicilio = 0;
        }
      }
      else {       
          anagrafica.indirizzoDomicilio = "";
          anagrafica.numeroCivicoDomicilio = "";
          anagrafica.idComuneDomicilio = 0;
          anagrafica.capDomicilio = "";
          anagrafica.statoDomicilio = "";
          anagrafica.comuneDomicilioEstero = "";
          anagrafica.comuneDomicilioEstero = "";
          anagrafica.statoDomicilio = "";        
          anagrafica.idComuneDomicilio = 0;
      }

      // Inserire i controlli
      let formData: FormData = new FormData();
      formData.append('anagraficaDto', new Blob([JSON.stringify(anagrafica)], { type: 'application/json' }));

      if (this.fileDocumento == null) {
        this.sharedService.onMessage('error', "Per poter proseguire è obbligatorio allegare il file del documento d'identità");
        return;
      }

      formData.append('fileDocumento', this.fileDocumento!, "documento");
      // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
      if (this.isEsteroNascita == "false") {
        anagrafica.comuneNascitaEstero = "";
      }
      else {
        anagrafica.idComuneNascita = 0;
      }
      if (this.isEsteroResidenza == "false") {
        anagrafica.comuneResidenzaEstero = "";
      }
      else {
        anagrafica.idComuneResidenza = 0;
      }

      this.serviceME.saveCompOrgAmAndCompSoc('anagrafica/saveCompOrgAmAndCompSoc', formData)
        .subscribe({
          next: (res: any) => {
            this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!");
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }

        })
    }
    else {
      this.sharedService.onMessage('error', "Tutti i campi obbligatori devono essere inseriti per poter proseguire");
      return;
    }

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

  finalCheck() {
    let esito = true

    if (this.checkTitolo() === false)
      return false;
    if (this.checkSesso() === false)
      return false;
    if (this.checkCognome() === false)
      return false;
    if (this.checkNome() === false)
      return false;
    if (this.checkDataNascita() === false)
      return false;

    if (this.isEsteroNascita == 'false') {
      if (this.checkComuneSceltoNascita() === false)
        return false;
    }

    if (this.isEsteroNascita == 'true') {
      if (this.checkStatoNascita() === false)
        return false;
      if (this.checkComuneNascitaEstero() === false)
        return false;
    }

    if (this.checkCodiceFiscale() === false)
      return false;
    if (this.checkCittadinanza() === false)
      return false;

    if (this.isEsteroResidenza == 'false') {
      if (this.checkComuneSceltoResidenza() === false)
        return false;
    }
    if (this.isEsteroResidenza == 'true') {
      if (this.checkStatoResidenza() === false)
        return false;
      if (this.checkComuneResidenzaEstero() === false)
        return false;
    }

    if (this.checkCapResidenza() === false)
      return false;
    if (this.checkNumeroCivicoResidenza() === false)
      return false;
    if (this.checkIndirizzoResidenza() === false)
      return false;
    if (this.checkPec() === false)
      return false;
    if (this.checkMail() === false)
      return false;
    if (this.checkIdQualifica() === false)
      return false;
    if (this.checkDescQualifica() === false)
      return false;
    if (this.checkDomicilio() === false)
      return false;

    return esito;
  }

}
