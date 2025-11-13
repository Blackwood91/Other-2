import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-scheda-anagrafica-mediatore',
  templateUrl: './scheda-anagrafica-mediatore.component.html',
  styleUrls: ['./scheda-anagrafica-mediatore.component.css']
})
export class SchedaAnagraficaMediatoreComponent implements OnInit {
  @Input()
  component!: string;
  @Input()
  idRichiesta!: number;

  isModifica = false;
  idTitolo: number = 0;
  idOrdiniCollegi: number = 0;
  sesso: string = "";
  cognome:string = "";
  nome: string = "";
  dataNascita: string = "";
  poDataAssunzione: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  indirizzoResidenza: string = "";
  numeroCivicoResidenza: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  capResidenza: string = "";
  statoResidenza: string = "";
  codiceFiscaleSS: string = "";
  cittadinanza: string = "";  
  poTipoRappOdm:  string = "";
  medTitoloDiStudio: string = "";
  medUniversita: string = "";
  lingueStraniere: string = "";
  dataOrdColProf: string = "";
  idTipoAnagrafica: number = null as unknown as number;
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
  ordiniCollegi: any = [];
  piva: string = "";
  indirizzoDomicilio: string = "";
  numeroCivicoDomicilio: string = "";
  idComuneDomicilio: number = 0;
  capDomicilio: string = "";
  statoDomicilio: string = "";
  comuneDomicilioEstero: string = "";
  indirizzoPec: string = "";
  indirizzoEmail: string = "";
  medRappGiuridicoEconomico: string = "";
  medNumeroOrganismiDisp: number = null as unknown as number;
  domicilio: boolean = false;
  fileDocumento: any = null;
  existFileDocumento: boolean = false;
  //variabili per checks di validità
  validTitolo: boolean = true;
  validSesso: boolean = true;
  validCognome: boolean = true;
  validNome: boolean = true;
  validDataNascita: boolean = true;
  validComuneSceltoNascita: boolean = true;
  validStatoNascita: boolean = true;
  validComuneNascitaEstero: boolean = true;
  validComuneSceltoResidenza: boolean = true;
  validStatoResidenza: boolean = true;
  validComuneResidenzaEstero: boolean = true;
  validIndirizzoResidenza: boolean = true;
  validNumeroCivicoResidenza: boolean = true;
  validCapResidenza: boolean = true;
  validMedRappGiuridicoEconomico: boolean = true;
  validMedNumeroOrganismiDisp: boolean = true;
  validMedTitoloDiStudio: boolean = true;
  validMedUniversita: boolean = true;
  validCodiceFiscale: boolean = true;
  validCittadinanza: boolean = true;
  validRapporto: boolean = true;
  validDataAssunzione: boolean = true;
  validPiva: boolean = true;
  validIndirizzoPec: boolean = true;
  validIndirizzoEmail: boolean = true;
  validNumeroOrganismi:boolean = true;
  validIndirizzoDomicilio: boolean = true;
  validNumeroDomicilio: boolean = true;
  validCapDomicilio: boolean = true;
  validDataOrdColProf: boolean = true;
  requisito: string = 'triennale';

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) {
  }

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
  }

  decideTitolo() {
    switch(this.component) {
      case 'scheda-anagrafica-mediatore-generico-a':
        this.idTipoAnagrafica = 4;
        return 'SCHEDA DEL MEDIATORE GENERICO';
      case 'scheda-anagrafica-mediatore-generico-b':
        this.idTipoAnagrafica = 5;
        return 'SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA INTERNAZIONALE';
      case 'scheda-anagrafica-mediatore-generico-c':
        this.idTipoAnagrafica = 6;
        return 'SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA DEI RAPPORTI DI CONSUMO';
      default:
        return 'ERRORE'
    }
  }

  activeModifica() {
    this.isModifica = true;
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


  loadTitoliAnagrafiche() {
    this.serviceME.getAllTitoliAnagrafiche('titoloAnagrafiche/getAll')
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        }
      })
  }

  loadOrdiniCollegi() {
    this.serviceME.getAllOrdiniCollegi('ordiniCollegi/getAll')
      .subscribe({
        next: (res: any) => {
          this.ordiniCollegi = res;
        }
      })
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

  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  // loadExistFile() {
  //   const params = new HttpParams()
  //     .set('idRichiesta', this.idRichiesta)
  //     .set('idAnagrafica', this.idAnagrafica);

  //   this.serviceME
  //     .getFileRappresentante('pdf/getFileMediatore', params)
  //     .subscribe((res: any) => {
  //       // già esiste file documento
  //       if (res != null && res[0] != null) {
  //         this.existFileDocumento = true;
  //       }
  //       else {
  //         this.existFileDocumento = false;
  //       }
  //     });
  // }

  saveAnagraficaGen() {
    //CHECK...
    // ALERT RETURN
    //.....
    if(true) {
      let anagrafica: {
        idAnagrafica: Number, idTitoloAnagrafica: number, sesso: string, cognome: string, nome: string, dataNascita: string, medPiva: string,
        statoNascita: string, idComuneNascita: number, codiceFiscale: string, cittadinanza: string, comuneNascitaEstero: string,
        statoResidenza: string, idComuneResidenza: number, indirizzo: string, numeroCivico: string, cap: string, 
        comuneResidenzaEstero: string, indirizzoDomicilio: string, numeroCivicoDomicilio: string, idComuneDomicilio: number, 
        capDomicilio: string, statoDomicilio: string, comuneDomicilioEstero: string, indirizzoPec: string, indirizzoEmail: string,
        medRappGiuridicoEconomico: string, medNumeroOrganismiDisp: number, medTitoloDiStudio: string, idOrdiniCollegi: number,
        idTipoAnagrafica: number, lingueStraniere: string, medUniversita: string, idRichiesta: number, medDataOrdineCollegioProfess: string 
      } = {
        idAnagrafica: 0, idTitoloAnagrafica: this.idTitolo, sesso: this.sesso, cognome: this.cognome, medPiva: this.piva,
        nome: this.nome, dataNascita: this.dataNascita, statoNascita: this.statoNascita, idComuneNascita: this.idComuneNascita,
        codiceFiscale: this.codiceFiscaleSS, cittadinanza: this.cittadinanza, comuneNascitaEstero: this.comuneNascitaEstero,
        statoResidenza: this.statoResidenza, idComuneResidenza: this.idComuneResidenza, indirizzo: this.indirizzoResidenza, 
        numeroCivico: this.numeroCivicoResidenza, cap: this.capResidenza, comuneResidenzaEstero: this.comuneResidenzaEstero,
        indirizzoDomicilio: this.indirizzoDomicilio, numeroCivicoDomicilio: this.numeroCivicoDomicilio, 
        idComuneDomicilio: this.idComuneDomicilio, capDomicilio: this.capDomicilio, statoDomicilio: this.statoDomicilio, 
        comuneDomicilioEstero: this.comuneDomicilioEstero, indirizzoPec: this.indirizzoPec, indirizzoEmail: this.indirizzoEmail, 
        medRappGiuridicoEconomico: this.medRappGiuridicoEconomico, medNumeroOrganismiDisp: this.medNumeroOrganismiDisp,
        medTitoloDiStudio: this.medTitoloDiStudio, idOrdiniCollegi: this.idOrdiniCollegi, idTipoAnagrafica: this.idTipoAnagrafica,
        lingueStraniere: this.lingueStraniere, medUniversita: this.medUniversita, idRichiesta: this.idRichiesta, medDataOrdineCollegioProfess: this.dataOrdColProf
      }

      // Inserire i controlli

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

      let formData: FormData = new FormData();
      formData.append('anagraficaDto', new Blob([JSON.stringify(anagrafica)], { type: 'application/json' }));

      if (this.fileDocumento == null) {
        this.sharedService.onMessage('error', "Per poter proseguire è obbligatorio allegare il file del documento d'identità");
        return;
      }

      formData.append('fileDocumento', this.fileDocumento!, "documento");

      this.serviceME.saveAnagrafica('anagrafica/saveAnagraficaMedGen', formData)
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
    }

  }

  testDoc() {
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

  checkDataOrdColProf() {
    if(this.dataOrdColProf != undefined && this.dataOrdColProf != null && this.dataOrdColProf != '') {
      this.validDataOrdColProf = true;
      return true;
    }
    else {
      this.validDataOrdColProf = false;
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

  checkStatoResidenza() {
    if(this.statoResidenza.length > 0) {
      this.validStatoResidenza = true;
      return true;
    }
    else {
      this.validStatoResidenza = false;
      return false;
    }
  }

  checkComuneResidenzaEstero() {
    if(this.comuneResidenzaEstero.length > 0) {
      this.validComuneResidenzaEstero = true;
      return true;
    }
    else {
      this.validComuneResidenzaEstero = false;
      return false;
    }
  }

  checkComuneSceltoResidenza() {
    if(this.comuneSceltoResidenza != undefined && this.comuneSceltoResidenza != null && this.comuneSceltoResidenza != '') {
      this.validComuneSceltoResidenza = true;
      return true;
    }
    else {
      this.validComuneSceltoResidenza = false;
      return false;
    }
  }

  checkIndirizzoResidenza() {
    if(this.indirizzoResidenza.length > 0) {
      this.validIndirizzoResidenza = true;
      return true;
    }
    else {
      this.validIndirizzoResidenza = false;
      return false;
    }
  }

  checkNumeroCivicoResidenza() {
    if(this.numeroCivicoResidenza.length > 0) {
      this.validNumeroCivicoResidenza = true;
      return true;
    }
    else {
      this.validNumeroCivicoResidenza = false;
      return false;
    }
  }

  checkCapResidenza() {
    if(this.capResidenza.length == 5) {
      this.validCapResidenza = true;
      return true;
    }
    else {
      this.validCapResidenza = false;
      return false;
    }
  }

  checkCapDomicilio() {
    if(this.capResidenza.length == 5) {
      this.validCapDomicilio = true;
      return true;
    }
    else {
      this.validCapDomicilio = false;
      return false;
    }
  }

  checkPiva() {
    if(this.piva.length > 0) {
      this.validPiva = true;
      return true;
    }
    else {
      this.validPiva = false;
      return false;
    }
  }

  checkIndirizzoEmail() {
    if(this.indirizzoEmail.length > 0) {
      this.validIndirizzoEmail = true;
      return true;
    }   
    else {
      this.validIndirizzoEmail = false;
      return false;
    }
  }

  checkIndirizzoPec() {
    if(this.indirizzoPec.length > 0) {
      this.validIndirizzoPec = true;
      return true;
    }   
    else {
      this.validIndirizzoPec = false;
      return false;
    }
  }

  checkValidMedRappGiuridicoEconomico() {
    if(this.medRappGiuridicoEconomico.length > 0) {
      this.validMedRappGiuridicoEconomico = true;
      return true;
    }   
    else {
      this.validMedRappGiuridicoEconomico = false;
      return false;
    }
  }

  checkValidMedNumeroOrganismiDisp() {
    // if(this.medNumeroOrganismiDisp != 0 && this.medNumeroOrganismiDisp != null) {
    //   this.validMedNumeroOrganismiDisp = true;
    //   return true;
    // }
    // else {
    //   this.validMedNumeroOrganismiDisp = false;
    //   return false;
    // }

    if(this.medNumeroOrganismiDisp != null && 
      this.medNumeroOrganismiDisp > 0 && this.medNumeroOrganismiDisp <= 5) {
      this.validNumeroOrganismi = true;
      return true;
    }   
    else {
      this.validNumeroOrganismi = false;
      return false;
    }
  }

  checkValidMedTitoloDiStudio() {
    if(this.medTitoloDiStudio.length > 0) {
      this.validMedTitoloDiStudio = true;
      return true;
    }   
    else {
      this.validMedTitoloDiStudio = false;
      return false;
    }
  }

  checkNumeroOrganismi() {
    if(this.medNumeroOrganismiDisp > 0 && this.medNumeroOrganismiDisp <= 5) {
      this.validNumeroOrganismi = true;
      return true;
    }   
    else {
      this.validNumeroOrganismi = false;
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

  finalCheck() {
    let esito = true

    if(this.checkTitolo() === false)
      esito = false;
    if(this.checkSesso() === false)
      esito = false;
    if(this.checkCognome() === false)
      esito = false;
    if(this.checkNome() === false)
      esito = false;
    if(this.checkDataNascita() === false)
      esito = false;
    if(this.checkCodiceFiscale() === false)
      esito = false;
    if(this.checkCittadinanza() === false)
      esito = false;
    if(this.checkPiva() === false)
      esito = false;

    switch(this.isEsteroNascita) {
      case 'false':
        if(this.checkComuneSceltoNascita() === false)
         esito = false;
        break;
      case 'true':
        if(this.checkStatoNascita() === false)
          esito = false;
        if(this.checkComuneNascitaEstero() === false)
          esito = false;
        break;        
    }

    switch(this.isEsteroResidenza) {
      case 'false':
        if(this.checkComuneSceltoResidenza() === false)
         esito = false;
        break;
      case 'true':
        if(this.checkStatoResidenza() === false)
          esito = false;
        if(this.checkComuneResidenzaEstero() === false)
          esito = false;
        break;        
    }

    if(this.checkCapResidenza() === false)
      esito = false;
    if(this.checkNumeroCivicoResidenza() === false)
      esito = false;
    if(this.checkIndirizzoResidenza() === false)
      esito = false;
    if(this.checkIndirizzoPec() === false)
      esito = false;
    if(this.checkIndirizzoEmail() === false)
      esito = false;
    if(this.checkValidMedNumeroOrganismiDisp() === false)
      esito = false;
    if(this.checkValidMedRappGiuridicoEconomico() === false)
      esito = false;
    if(this.checkValidMedNumeroOrganismiDisp() === false)
      esito = false;
    if(this.checkValidMedRappGiuridicoEconomico() === false)
      esito = false;
    if(this.checkDomicilio() === false)
      esito = false;
        
    return esito;
    
  }

}
