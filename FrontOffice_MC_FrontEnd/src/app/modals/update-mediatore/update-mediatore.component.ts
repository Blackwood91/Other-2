import { HttpParams } from "@angular/common/http";
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-update-mediatore",
  templateUrl: "./update-mediatore.component.html",
  styleUrls: ["./update-mediatore.component.css"],
})
export class UpdateMediatoreComponent implements OnInit {
  titolo: string = "";
  idAnagrafica: number = 0;
  idRichiesta: number = 0;
  isModifica = true;
  isConvalidato: boolean = false;
  @Input()
  viewOnly: boolean = null as unknown as boolean;
  @Input()
  component: string = "";
  @Input()
  elenco: string = "";
  @Output()
  eventConfirmMessage: EventEmitter<any> = new EventEmitter();

  idTitolo: number = 0;
  idOrdiniCollegi: number = 0;
  sesso: string = "";
  cognome: string = "";
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
  poTipoRappOdm: string = "";
  medTitoloDiStudio: string = "";
  medUniversita: string = "";
  lingueStraniere: string = "";
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
  dataOrdColProf: string = "";

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
  validIndirizzoDomicilio: boolean = true;
  validStatoDomicilio: boolean = true; /**/
  validComuneDomicilioEstero: boolean = true; /**/
  validComuneSceltoDomicilio: boolean = true; /**/
  validNumeroDomicilio: boolean = true;
  validCapDomicilio: boolean = true;
  validDataOrdColProf: boolean = true;
  validIdOrdineCollegi: boolean = true;
  requisito: string = "";

  isRiepilogo: boolean = false;


  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
  }

  resetParameters() {
    this.idTitolo = 0;
    this.idOrdiniCollegi = 0;
    this.sesso = "";
    this.cognome = "";
    this.nome = "";
    this.dataNascita = "";
    this.poDataAssunzione = "";
    this.idComuneNascita = 0;
    this.comuneNascitaEstero = "";
    this.statoNascita = "";
    this.indirizzoResidenza = "";
    this.numeroCivicoResidenza = "";
    this.idComuneResidenza = 0;
    this.comuneResidenzaEstero = "";
    this.capResidenza = "";
    this.statoResidenza = "";
    this.codiceFiscaleSS = "";
    this.cittadinanza = "";
    this.poTipoRappOdm = "";
    this.medTitoloDiStudio = "";
    this.medUniversita = "";
    this.lingueStraniere = "";
    this.idTipoAnagrafica = null as unknown as number;
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
    this.ordiniCollegi = [];
    this.piva = "";
    this.indirizzoDomicilio = "";
    this.numeroCivicoDomicilio = "";
    this.idComuneDomicilio = 0;
    this.capDomicilio = "";
    this.statoDomicilio = "";
    this.comuneDomicilioEstero = "";
    this.indirizzoPec = "";
    this.indirizzoEmail = "";
    this.medRappGiuridicoEconomico = "";
    this.medNumeroOrganismiDisp = null as unknown as number;
    this.domicilio = false;
    this.fileDocumento = null;
    this.existFileDocumento = false;
    this.validTitolo = true;
    this.validSesso = true;
    this.validCognome = true;
    this.validNome = true;
    this.validDataNascita = true;
    this.validComuneSceltoNascita = true;
    this.validStatoNascita = true;
    this.validComuneNascitaEstero = true;
    this.validComuneSceltoResidenza = true;
    this.validStatoResidenza = true;
    this.validComuneResidenzaEstero = true;
    this.validIndirizzoResidenza = true;
    this.validNumeroCivicoResidenza = true;
    this.validCapResidenza = true;
    this.validMedRappGiuridicoEconomico = true;
    this.validMedNumeroOrganismiDisp = true;
    this.validMedTitoloDiStudio = true;
    this.validMedUniversita = true;
    this.validCodiceFiscale = true;
    this.validCittadinanza = true;
    this.validRapporto = true;
    this.validDataAssunzione = true;
    this.validPiva = true;
    this.validIndirizzoPec = true;
    this.validIndirizzoEmail = true;
    this.validStatoDomicilio = true;
    this.validComuneDomicilioEstero = true;
    this.validComuneSceltoDomicilio = true;
    this.validIndirizzoDomicilio = true;
    this.validNumeroDomicilio = true;
    this.validCapDomicilio = true;
    this.validDataOrdColProf = true;
  }

  ngOnChanges(change: SimpleChanges) {
    if (change["elenco"]) {
      this.elenco = change["elenco"].currentValue;
    }
  }

  // DA PASSARE IL COMPONENTE TITOLO, UTILIZZO COME ONINIT
  /*
  openModal(idAnagrafica: number, idRichiesta: number) {
    this.resetParameters();
    this.decideTitolo();
    this.idAnagrafica = idAnagrafica;
    this.idRichiesta = idRichiesta;
    this.isModifica = true;

    const buttonActiveModal = document.getElementById(
      "activeModalUpdateMediatore"
    );
    buttonActiveModal!.click();

    this.loadIsConvalidato();
    this.loadSchedaAnagrafica();
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
  }

  openModalElenco(
    idAnagrafica: number,
    idRichiesta: number,
    idTipoAnagrafica: number
  ) {
    this.resetParameters();
    this.idTipoAnagrafica = idTipoAnagrafica;
    this.idAnagrafica = idAnagrafica;
    this.idRichiesta = idRichiesta;
    this.isModifica = true;
    this.titolo = this.decideTitoloElencoMediatori();

    const buttonActiveModal = document.getElementById(
      "activeModalUpdateMediatore"
    );
    buttonActiveModal!.click();

    this.loadIsConvalidato();
    this.loadSchedaAnagrafica();
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
  }
    */

  openModal(idAnagrafica: number, idRichiesta: number, component: string) {
    this.resetParameters();
    this.isRiepilogo = false
    this.isModifica = true;
    this.component = component;
    this.idAnagrafica = idAnagrafica;
    this.idRichiesta = idRichiesta;
    this.titolo = this.decideTitolo();

    const buttonActiveModal = document.getElementById("activeModalUpdateMediatore");
    buttonActiveModal!.click();

    this.loadSchedaAnagrafica();
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
  }

  // DA PASSARE IL COMPONENTE TITOLO, UTILIZZO COME ONINIT
  openModalRiepilogo(idAnagrafica: number, idRichiesta: number, component: string) {
    this.resetParameters();
    this.isModifica = false //sus
    this.isRiepilogo = true;
    this.component = component;
    this.idAnagrafica = idAnagrafica;
    this.idRichiesta = idRichiesta;
    this.titolo = this.decideTitolo();

    const buttonActiveModal = document.getElementById("activeModalUpdateMediatore");
    buttonActiveModal!.click();

    this.loadSchedaAnagrafica();
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
    this.loadExistFile();
  }
/*
  // DA PASSARE IL COMPONENTE TITOLO, UTILIZZO COME ONINIT
  openModalRiepilogo(idAnagrafica: number, idRichiesta: number) {
    this.resetParameters();
    this.decideTitolo();
    this.idAnagrafica = idAnagrafica;
    this.idRichiesta = idRichiesta;
    this.isModifica = false;

    const buttonActiveModal = document.getElementById(
      "activeModalUpdateMediatore"
    );
    buttonActiveModal!.click();

    this.loadSchedaAnagrafica();
    this.loadTitoliAnagrafiche();
    this.loadOrdiniCollegi();
    this.loadExistFile();
  }
*/
  decideTitolo() {
    switch (this.component) {
      case "gestione-mediatori-a":
        this.idTipoAnagrafica = 4;
        return (this.titolo = "SCHEDA DEL MEDIATORE GENERICO");
      case "gestione-mediatori-b":
        this.idTipoAnagrafica = 5;
        return (this.titolo =
          "SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA INTERNAZIONALE");
      case "gestione-mediatori-c":
        this.idTipoAnagrafica = 6;
        return (this.titolo =
          "SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA DEI RAPPORTI DI CONSUMO");
      default:
        return "errore";
    }
  }

  decideTitoloElencoMediatori() {
    switch (this.idTipoAnagrafica) {
      case 4:
        return "SCHEDA DEL MEDIATORE GENERICO";
      case 5:
        return "SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA INTERNAZIONALE";
      case 6:
        return "SCHEDA DEL MEDIATORE ESPERTO NELLA MATERIA DEI RAPPORTI DI CONSUMO";
      default:
        return "errore";
    }
  }

  loadComuneNascita(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniNascita = res;
            this.showListComuneNascita = true;
          },
        });
    } else {
      this.showListComuneNascita = false;
    }
  }

  loadComuneResidenza(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniResidenza = res;
            this.showListComuneResidenza = true;
          },
        });
    } else {
      this.showListComuneResidenza = false;
    }
  }

  loadComuneDomicilio(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniDomicilio = res;
            this.showListComuneDomicilio = true;
          },
        });
    } else {
      this.showListComuneDomicilio = false;
    }
  }

  loadTitoliAnagrafiche() {
    this.serviceME
      .getAllTitoliAnagrafiche("titoloAnagrafiche/getAll")
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        },
      });
  }

  loadOrdiniCollegi() {
    this.serviceME.getAllOrdiniCollegi("ordiniCollegi/getAll").subscribe({
      next: (res: any) => {
        this.ordiniCollegi = res;
      },
    });
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

  /* CHECK VALIDITA' */
  checkTitolo() {
    if (this.idTitolo != 0) {
      this.validTitolo = true;
      return true;
    } else {
      this.validTitolo = false;
      return false;
    }
  }

  checkSesso() {
    if (this.sesso == "M" || this.sesso == "F") {
      this.validSesso = true;
      return true;
    } else {
      this.validSesso = false;
      return false;
    }
  }

  checkCognome() {
    if (this.cognome.length > 0) {
      this.validCognome = true;
      return true;
    } else {
      this.validCognome = false;
      return false;
    }
  }

  checkNome() {
    if (this.nome.length > 0) {
      this.validNome = true;
      return true;
    } else {
      this.validNome = false;
      return false;
    }
  }

  checkDataNascita() {
    if (
      this.dataNascita != undefined &&
      this.dataNascita != null &&
      this.dataNascita != ""
    ) {
      this.validDataNascita = true;
      return true;
    } else {
      this.validDataNascita = false;
      return false;
    }
  }

  checkDataOrdColProf() {
    if (this.dataOrdColProf != undefined && this.dataOrdColProf != null &&
        this.dataOrdColProf != "" && this.dataOrdColProf != "Invalid date") {
      this.validDataOrdColProf = true;
      return true;
    } else {
      this.validDataOrdColProf = false;
      return false;
    }
  }

  checkStatoNascita() {
    if (this.statoNascita.length > 0) {
      this.validStatoNascita = true;
      return true;
    } else {
      this.validStatoNascita = false;
      return false;
    }
  }

  checkComuneNascitaEstero() {
    if (this.comuneNascitaEstero.length > 0) {
      this.validComuneNascitaEstero = true;
      return true;
    } else {
      this.validComuneNascitaEstero = false;
      return false;
    }
  }

  checkComuneSceltoNascita() {
    if (
      this.comuneSceltoNascita != undefined &&
      this.comuneSceltoNascita != null &&
      this.comuneSceltoNascita != ""
    ) {
      this.validComuneSceltoNascita = true;
      return true;
    } else {
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
    } else {
      this.validCodiceFiscale = false;
      return false;
    }
  }

  checkCittadinanza() {
    if (this.cittadinanza.length > 0) {
      this.validCittadinanza = true;
      return true;
    } else {
      this.validCittadinanza = false;
      return false;
    }
  }

  checkRapporto() {
    if (
      /*this.poTipoRappOdm != undefined && this.poTipoRappOdm != null && */ this
        .poTipoRappOdm != ""
    ) {
      this.validRapporto = true;
      return true;
    } else {
      this.validRapporto = false;
      return false;
    }
  }

  checkDataAssunzione() {
    if (
      this.poDataAssunzione != undefined &&
      this.poDataAssunzione != null &&
      this.poDataAssunzione != ""
    ) {
      this.validDataAssunzione = true;
      return true;
    } else {
      this.validDataAssunzione = false;
      return false;
    }
  }

  checkStatoResidenza() {
    if (this.statoResidenza.length > 0) {
      this.validStatoResidenza = true;
      return true;
    } else {
      this.validStatoResidenza = false;
      return false;
    }
  }

  checkComuneResidenzaEstero() {
    if (this.comuneResidenzaEstero.length > 0) {
      this.validComuneResidenzaEstero = true;
      return true;
    } else {
      this.validComuneResidenzaEstero = false;
      return false;
    }
  }

  checkComuneSceltoResidenza() {
    if (
      this.comuneSceltoResidenza != undefined &&
      this.comuneSceltoResidenza != null &&
      this.comuneSceltoResidenza != ""
    ) {
      this.validComuneSceltoResidenza = true;
      return true;
    } else {
      this.validComuneSceltoResidenza = false;
      return false;
    }
  }

  checkIndirizzoResidenza() {
    if (this.indirizzoResidenza.length > 0) {
      this.validIndirizzoResidenza = true;
      return true;
    } else {
      this.validIndirizzoResidenza = false;
      return false;
    }
  }

  checkNumeroCivicoResidenza() {
    if (this.numeroCivicoResidenza.length > 0) {
      this.validNumeroCivicoResidenza = true;
      return true;
    } else {
      this.validNumeroCivicoResidenza = false;
      return false;
    }
  }

  checkCapResidenza() {
    if (this.capResidenza.length == 5) {
      this.validCapResidenza = true;
      return true;
    } else {
      this.validCapResidenza = false;
      return false;
    }
  }

  checkPiva() {
    if (this.piva.length > 0) {
      this.validPiva = true;
      return true;
    } else {
      this.validPiva = false;
      return false;
    }
  }

  checkIndirizzoEmail() {
    if (this.indirizzoEmail.length > 0) {
      this.validIndirizzoEmail = true;
      return true;
    } else {
      this.validIndirizzoEmail = false;
      return false;
    }
  }

  checkIndirizzoPec() {
    if (this.indirizzoPec.length > 0) {
      this.validIndirizzoPec = true;
      return true;
    } else {
      this.validIndirizzoPec = false;
      return false;
    }
  }

  checkValidMedRappGiuridicoEconomico() {
    if (this.medRappGiuridicoEconomico.length > 0) {
      this.validMedRappGiuridicoEconomico = true;
      return true;
    } else {
      this.validMedRappGiuridicoEconomico = false;
      return false;
    }
  }

  checkValidMedNumeroOrganismiDisp() {
    if (
      this.medNumeroOrganismiDisp != null &&
      this.medNumeroOrganismiDisp >= 0 &&
      this.medNumeroOrganismiDisp <= 5
    ) {
      this.validMedNumeroOrganismiDisp = true;
      return true;
    } else {
      this.validMedNumeroOrganismiDisp = false;
      return false;
    }
  }

  checkValidMedTitoloDiStudio() {
    if (this.medTitoloDiStudio.length > 0) {
      this.validMedTitoloDiStudio = true;
      return true;
    } else {
      this.validMedTitoloDiStudio = false;
      return false;
    }
  }

  checkDomicilio() {
    let esito = true;
    if (this.domicilio) {
      if (
        this.indirizzoDomicilio == null ||
        this.indirizzoDomicilio.length == 0
      ) {
        this.validIndirizzoDomicilio = false;
        esito = false;
      }

      if (
        this.numeroCivicoDomicilio == null ||
        this.numeroCivicoDomicilio.length == 0
      ) {
        this.validNumeroDomicilio = false;
        esito = false;
      }

      if(this.isEsteroDomicilio == 'true') {
        if(this.statoDomicilio == null || this.statoDomicilio.length == 0) {
          this.validStatoDomicilio = false;
          esito = false;
        }
        if(this.comuneDomicilioEstero == null || this.comuneDomicilioEstero.length == 0) {
          this.validComuneDomicilioEstero = false;
          esito = false;
        }
      }
      else {
        if(this.comuneSceltoDomicilio != undefined &&
          this.comuneSceltoDomicilio != null &&
          this.comuneSceltoDomicilio != "") {
            this.validComuneSceltoDomicilio = false;
            esito = false;
          }
      }

      if (this.capDomicilio == null || this.capDomicilio.length == 0) {
        this.validCapDomicilio = false;
        esito = false;
      }

      if (esito === false) {
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  checkValidMedUniversita() {
    if (this.medUniversita.length > 0) {
      this.validMedUniversita = true;
      return true;
    } else {
      this.validMedUniversita = false;
      return false;
    }
  }

  
  checkValidIdOrdineCollegi() {
    if (this.idOrdiniCollegi != 0 &&
      this.idOrdiniCollegi != null &&
      this.idOrdiniCollegi != undefined) {
      this.validIdOrdineCollegi = true;
      return true;
    } else {
      this.validIdOrdineCollegi = false;
      return false;
    }
  }

  finalCheck() {
    let esito = true;

    if (this.checkTitolo() === false) esito = false;
    if (this.checkSesso() === false) esito = false;
    if (this.checkCognome() === false) esito = false;
    if (this.checkNome() === false) esito = false;
    if (this.checkDataNascita() === false) esito = false;
    if (this.checkCodiceFiscale() === false) esito = false;
    if (this.checkCittadinanza() === false) esito = false;
    if (this.checkPiva() === false) esito = false;

    switch (this.isEsteroNascita) {
      case "false":
        if (this.checkComuneSceltoNascita() === false) esito = false;
        break;
      case "true":
        if (this.checkStatoNascita() === false) esito = false;
        if (this.checkComuneNascitaEstero() === false) esito = false;
        break;
    }

    switch (this.isEsteroResidenza) {
      case "false":
        if (this.checkComuneSceltoResidenza() === false) esito = false;
        break;
      case "true":
        if (this.checkStatoResidenza() === false) esito = false;
        if (this.checkComuneResidenzaEstero() === false) esito = false;
        break;
    }

    if (this.checkValidMedTitoloDiStudio() === false) esito = false; 
    if (this.checkValidMedUniversita() === false) esito = false;

    if(this.requisito == "triennale") {
      if (this.checkValidIdOrdineCollegi() === false) 
        esito = false;
      if (this.checkDataOrdColProf() === false) 
        esito = false;        
    }
    else {
      this.idOrdiniCollegi = 0;
      this.dataOrdColProf = "";
    }

    if (this.checkCapResidenza() === false) esito = false;
    if (this.checkNumeroCivicoResidenza() === false) esito = false;
    if (this.checkIndirizzoResidenza() === false) esito = false;
    if (this.checkIndirizzoPec() === false) esito = false;
    if (this.checkIndirizzoEmail() === false) esito = false;
    if (this.checkValidMedNumeroOrganismiDisp() === false) esito = false;
    if (this.checkValidMedRappGiuridicoEconomico() === false) esito = false;
    if (this.checkDomicilio() === false) esito = false;

    return esito;
  }

  loadSchedaAnagrafica(): void {
    const params = new HttpParams().set("idAnagrafica", this.idAnagrafica).set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAnagraficaById("anagrafica/getAnagraficaByIdDto", params)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.idAnagrafica = res.idAnagrafica;
            this.idTitolo = res.idTitoloAnagrafica;
            this.sesso = res.sesso;
            this.cognome = res.cognome;
            this.nome = res.nome;
            this.dataNascita = moment(res.dataNascita).format("YYYY-MM-DD");
            this.statoNascita = res.statoNascita;
            this.idComuneNascita = res.idComuneNascita;
            this.comuneNascitaEstero = res.comuneNascitaEstero;
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
            this.piva = res.medPiva;
            this.medTitoloDiStudio = res.medTitoloDiStudio;
            this.medUniversita = res.medUniversita;
            this.idOrdiniCollegi = res.idOrdiniCollegi;
            this.medRappGiuridicoEconomico = res.medRappGiuridicoEconomico;
            this.medNumeroOrganismiDisp = res.medNumeroOrganismiDisp;
            this.dataOrdColProf = moment(res.medDataOrdineCollegioProfess).format("YYYY-MM-DD");
            this.lingueStraniere = res.lingueStraniere;
            // VERIFICA PER AUTOSELEZIONARE O NO IL DOMICILIO
            this.domicilio = this.indirizzoDomicilio != "" && this.indirizzoDomicilio != null ? true : false;
            // VERIFICA PER AUTOSELEZIONE DEL REQUISITO
            this.requisito = this.idOrdiniCollegi != null && this.idOrdiniCollegi != 0 ? "triennale" : "magistrale";

            this.loadComuneNascitaUpdate(res.idComuneNascita);
            this.loadComuneResidenzaUpdate(res.idComuneResidenza);
            this.loadComuneDomicilioUpdate(res.idComuneDomicilio);
          }
        },
      });
  }

  loadComuneNascitaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune("comune/getComune", idComune).subscribe({
        next: (res: any) => {
          this.idComuneNascita = res.idCodComune;
          this.comuneSceltoNascita =
            res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
          this.showListComuneNascita = false;
        },
      });
    } else {
      this.isEsteroNascita = "true";
    }
  }

  loadComuneResidenzaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune("comune/getComune", idComune).subscribe({
        next: (res: any) => {
          this.idComuneResidenza = res.idCodComune;
          this.comuneSceltoResidenza =
            res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
          this.showListComuneResidenza = false;
        },
      });
    } else {
      this.isEsteroResidenza = "true";
    }
  }

  loadComuneDomicilioUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune("comune/getComune", idComune).subscribe({
        next: (res: any) => {
          this.idComuneDomicilio = res.idCodComune;
          this.comuneSceltoDomicilio =
            res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
          this.showListComuneDomicilio = false;
        },
      });
    } else {
      this.isEsteroDomicilio = "true";
    }
  }

  getFileDocumento() {
    var file = new Blob([this.convertiStringaBlobAFile(this.fileDocumento)], {
      type: "application/pdf",
    });
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
  }

  convertiStringaBlobAFile(dati: string): File {
    let byteCharacters = atob(dati);
    let byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    let byteArray = new Uint8Array(byteNumbers);
    let blobFile = new Blob([byteArray], { type: "application/pdf" });

    return new File([blobFile], "file");
  }

  loadExistFile() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getFileRappresentante("pdf/getFileMediatore", params)
      .subscribe((res: any) => {
        if (res != null && res[0] != null) {
          this.fileDocumento = res[0].file;
        }
      });
  }

  loadIsConvalidato() {
    let idModulo;
    switch (this.component) {
      case "gestione-mediatori-a":
        idModulo = 38;
        break;
      case "gestione-mediatori-b":
        idModulo = 43;
        break;
      case "gestione-mediatori-c":
        idModulo = 52;
        break;
      default:
        return;
    }

    const params = new HttpParams()
      .set("idModulo", idModulo)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getModuloIsConvalidatoAdPersonam("status/getModuloIsConvalidatoAdPersonam", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
      });
  }

  closeModal() {
    let closeModal = document.getElementById("activeModalUpdateMediatore");
    closeModal!.click();

    const params: any = { message: "confermato" };
    this.eventConfirmMessage.emit(params);
  }

  updateComponentMother() {
    this.eventConfirmMessage.emit("Aggiornamento effettuato");
  }

  convalidaMediatore() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneRquisitiOnorabilita(
        "status/convalidazioneMediatore",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.loadIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
          this.closeModal();
        },
        error: (res: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile procedere con la convalidazione"
          );
        },
      });
  }

  saveAnagraficaGen() {
    if (this.finalCheck() === true) {
      let anagrafica: {
        idAnagrafica: Number;
        idTitoloAnagrafica: number;
        sesso: string;
        cognome: string;
        nome: string;
        dataNascita: string;
        medPiva: string;
        statoNascita: string;
        idComuneNascita: number;
        codiceFiscale: string;
        cittadinanza: string;
        comuneNascitaEstero: string;
        statoResidenza: string;
        idComuneResidenza: number;
        indirizzo: string;
        numeroCivico: string;
        cap: string;
        comuneResidenzaEstero: string;
        indirizzoDomicilio: string;
        numeroCivicoDomicilio: string;
        idComuneDomicilio: number;
        capDomicilio: string;
        statoDomicilio: string;
        comuneDomicilioEstero: string;
        indirizzoPec: string;
        indirizzoEmail: string;
        medRappGiuridicoEconomico: string;
        medNumeroOrganismiDisp: number;
        medTitoloDiStudio: string;
        idOrdiniCollegi: number;
        idTipoAnagrafica: number;
        lingueStraniere: string;
        medUniversita: string;
        idRichiesta: number;
        medDataOrdineCollegioProfess: any;
      } = {
        idAnagrafica: this.idAnagrafica,
        idTitoloAnagrafica: this.idTitolo,
        sesso: this.sesso,
        cognome: this.cognome,
        medPiva: this.piva,
        nome: this.nome,
        dataNascita: this.dataNascita,
        statoNascita: this.statoNascita,
        idComuneNascita: this.idComuneNascita,
        codiceFiscale: this.codiceFiscaleSS,
        cittadinanza: this.cittadinanza,
        comuneNascitaEstero: this.comuneNascitaEstero,
        statoResidenza: this.statoResidenza,
        idComuneResidenza: this.idComuneResidenza,
        indirizzo: this.indirizzoResidenza,
        numeroCivico: this.numeroCivicoResidenza,
        cap: this.capResidenza,
        comuneResidenzaEstero: this.comuneResidenzaEstero,
        indirizzoDomicilio: this.indirizzoDomicilio,
        numeroCivicoDomicilio: this.numeroCivicoDomicilio,
        idComuneDomicilio: this.idComuneDomicilio,
        capDomicilio: this.capDomicilio,
        statoDomicilio: this.statoDomicilio,
        comuneDomicilioEstero: this.comuneDomicilioEstero,
        indirizzoPec: this.indirizzoPec,
        indirizzoEmail: this.indirizzoEmail,
        medRappGiuridicoEconomico: this.medRappGiuridicoEconomico,
        medNumeroOrganismiDisp: this.medNumeroOrganismiDisp,
        medTitoloDiStudio: this.medTitoloDiStudio,
        idOrdiniCollegi: this.idOrdiniCollegi,
        idTipoAnagrafica: this.idTipoAnagrafica,
        lingueStraniere: this.lingueStraniere,
        medUniversita: this.medUniversita,
        idRichiesta: this.idRichiesta,
        medDataOrdineCollegioProfess:
          this.dataOrdColProf == null || this.dataOrdColProf == "Invalid date"
            ? null
            : this.dataOrdColProf,
      };

      // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
      if (this.isEsteroNascita == "false") {
        anagrafica.comuneNascitaEstero = "";
      } else {
        anagrafica.idComuneNascita = 0;
      }

      if (this.isEsteroResidenza == "false") {
        anagrafica.comuneResidenzaEstero = "";
      } else {
        anagrafica.idComuneResidenza = 0;
      }

      if (this.domicilio) {
        if (this.isEsteroDomicilio == "false") {
          anagrafica.comuneDomicilioEstero = "";
          anagrafica.statoDomicilio = "";
        } else {
          anagrafica.idComuneDomicilio = 0;
        }
      } else {
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
      formData.append(
        "anagraficaDto",
        new Blob([JSON.stringify(anagrafica)], { type: "application/json" })
      );

      if (this.fileDocumento != null) {
        formData.append("fileDocumento", this.fileDocumento!, "documento");
      }

      this.serviceME
        .saveAnagrafica("anagrafica/saveAnagraficaMedGen", formData)
        .subscribe({
          next: (res: any) => {
            this.updateComponentMother();
            this.loadIsConvalidato();
            this.sharedService.onMessage(
              "success",
              "L'inserimento è avvenuto con successo!"
            );
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error);
          },
        });
    } else {
      this.sharedService.onMessage(
        "error",
        "Inserire tutti i dati e i file inerenti per proseguire"
      );
      return;
    }
  }
}
