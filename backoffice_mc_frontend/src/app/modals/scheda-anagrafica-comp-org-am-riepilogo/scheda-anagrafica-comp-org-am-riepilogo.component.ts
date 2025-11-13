import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-scheda-anagrafica-comp-org-am-riepilogo',
  templateUrl: './scheda-anagrafica-comp-org-am-riepilogo.component.html',
  styleUrls: ['./scheda-anagrafica-comp-org-am-riepilogo.component.css']
})
export class SchedaAnagraficaCompOrgAmRiepilogoComponent implements OnInit {
  idRichiesta: number = 0;
  isModificaContatti = false;
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
  domicilio: boolean = false;


  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {

  }

  openModal(idRichiesta: number, idAnagrafica: number, visibleRadioIsEqualRapLeg: boolean) {
    // PER NON AVERE PROBLEMI SE GIA' E' STATA APERTA UNA MODALE IN PRECEDENZA
    this.resetParameters();
    this.idRichiesta = idRichiesta;
    this.idAnagrafica = idAnagrafica;

    this.loadTitoliAnagrafiche();
    this.loadSchedaAnagrafica();
    this.loadExistFile();
    this.loadQualifiche();

    const buttonActiveModal = document.getElementById("activeModalSchedaAnagraficaRiepilogo");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  activeModifica() {
    this.isModificaContatti = true;
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

  loadQualifiche() {
    this.serviceME.getAllQualificaCompOrgAmAndCompSoc('qualifica/getAllCompOrgAmAndCompSoc')
      .subscribe({
        next: (res: any) => {
          this.qualifiche = res;
        }
      })
  }

  loadExistFile() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getFileCompOrgAm('pdf/getFileCompOrgAm', params)
      .subscribe((res: any) => {

        if (res != null && res[0] != null) {
          this.fileDocumento = res[0].file;
        }

      });
  }

  getFileDocumento() {
    var file = new Blob([this.convertiStringaBlobAFile(this.fileDocumento)], { type: 'application/pdf' });
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, '_blank');
  }

  // Per formattare byte in file pdf
  convertiStringaBlobAFile(dati: string): File {
    let byteCharacters = atob(dati);
    let byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    let byteArray = new Uint8Array(byteNumbers);
    let blobFile = new Blob([byteArray], { type: 'application/pdf' });

    return new File([blobFile], 'file');
  }

  resetParameters() {
    this.idRichiesta = 0;
    this.isModificaContatti = false;
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
  }

}
