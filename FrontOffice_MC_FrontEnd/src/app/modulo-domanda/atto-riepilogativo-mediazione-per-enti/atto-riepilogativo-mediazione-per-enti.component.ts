import { HttpParams } from "@angular/common/http";
import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { ExtraInfoComponent } from "src/app/modals/extra-info/extra-info.component";
import { SaveSedeComponent } from "src/app/modals/save-sede/save-sede.component";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-atto-riepilogativo-mediazione-per-enti",
  templateUrl: "./atto-riepilogativo-mediazione-per-enti.component.html",
  styleUrls: ["./atto-riepilogativo-mediazione-per-enti.component.css"],
})
export class AttoRiepilogativoMediazionePerEntiComponent implements OnInit {
  // PARAMETRI PER TUTTE LE SEZIONI
  idRichiesta: number = 0;
  isModifica = false;
  isConvalidato: boolean = false;
  // SEZIONE 1
  // Dati Richiesta
  soggRichiedente: number = 0;
  dataAttoCosti: string = "";
  dataStatutoVig: string = "";
  codFiscSocieta: string = "";
  piva: string = "";
  idNaturaSoc: number = 0;
  autonomo: boolean = true;
  @Output() autonomoCheck = new EventEmitter<boolean>();
  oggSociale: string = "";
  istitutoEntePub: boolean = false;
  denominaOdmPub: string = "";
  idNaturaGiu: number = 0;
  // Dati di riferimento
  societa: {
    idNaturaSoc: number;
    descrizioneBreve: string;
    descrizione: string;
  }[] = [];
  comuniSP: any = [];
  comuneSceltoSP: string = "";
  showListComuniSP: boolean = false;
  selectSoggRichiedenti: any = [];
  natureGiuridiche: any = [];
  // Per controlli
  codiceFiscaleValid: boolean = false;

  // SEZIONE 2
  @ViewChild(SaveSedeComponent) saveSedeComponent!: SaveSedeComponent;
  idTitolo: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  codiceFiscaleSS: string = "";
  cittadinanza: string = "";
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
  indirizzoPec: string = "";
  indirizzoEmail: string = "";
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
  domicilio: boolean = false;

  saveLock = true;
  saveLockAlert = false;
  // SEZIONE 3
  // Tabella Sedi legali
  searchTextTableSO: string = "";
  indexPageSO: number = 1;
  tableResultSO = new Array();
  totalPageSO = 0;
  totalResultSO = 0;
  // SEZIONE 4
  numeroComponentiOA: number = 0;
  numeroComponentiCS: number = 0;
  durataCarica: string = "";
  modCostOrg: number = 0;
  dataCostituzione: string = "";
  numPersonaleAdetto: number = 0;
  fontiFinanziamento: string = "";
  durataOrganismo: string = "";
  modalitaGestioneContabile: string = "";
  numMediatori: number = 0;
  numMediatoriInter: number = 0;
  numMediatoriCons: number = 0;
  respOrganismo: string = "";
  denRagSoc: string = "";

  listModCostOrg: any = null;

  // MODALE INFORMAZIONI
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.openSezione(1);

      this.moduloIsConvalidato();
    });
  }

  getAnteprimaFileAttoRiepOdm() {
    this.serviceME
      .getAnteprimaFileAttoRiepOdm(
        "pdf/getAnteprimaFileAttoRiepOdm",
        this.idRichiesta
      )
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], {
            type: "application/pdf",
          });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, "_blank");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  activeModifica() {
    this.isModifica = true;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  moduloIsConvalidato() {
    const params = new HttpParams()
      .set("idModulo", 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusAttoRODMI("status/getStatusAttoRODM", params)
      .subscribe({
        next: (res: any) => {
          if (res.status === "c") {
            this.isConvalidato = true;
          } else {
            this.isConvalidato = false;
          }
        },
      });
  }

  // FUNZIONI SEZIONE 1 ---------------------------------------------------------------------------------

  loadParamsSezione1(): void {
    this.serviceME
      .getSezionePrimaDOMODMP(
        "richiesta/getSezionePrimaDOMODMP",
        this.idRichiesta
      )
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.soggRichiedente = res.idSoggRichiedente;
            this.autonomo = res.autonomo == 1 ? true : false;
            this.oggSociale = res.oggettoSociale;
            this.dataAttoCosti = moment(res.dataAttoCosti).format("YYYY-MM-DD");
            this.dataStatutoVig = moment(res.dataStatutoVig).format(
              "YYYY-MM-DD"
            );
            this.codFiscSocieta = res.codFiscSocieta;
            this.piva = res.piva;
            this.idNaturaSoc = res.idNaturaSoc;
            this.istitutoEntePub = res.istitutoEntePub == 1 ? true : false;
            this.denominaOdmPub = res.denominaOdmPub;
            this.idNaturaGiu = res.idNaturaGiu;
          }
        },
      });

    /*const params = new HttpParams() //A COSA SERVE??
      .set('idSocieta', this.idRichiesta);
      //.set('indexPage', 0);
      
      this.serviceME.getSocietaById('societa/getSocietaByIdRichiesta', params)
      .subscribe({
        next: (res: any) => {
          this.codFiscSocieta = res.codiceFiscaleSocieta;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error.error)

        }
      });*/
  }

  loadSoggRichiedenti(): void {
    this.serviceME
      .getAllSoggettoRichiedente(
        "soggetto-richiedente/getAllSoggettoRichiedente"
      )
      .subscribe({
        next: (res: any) => {
          this.selectSoggRichiedenti = res;
        },
      });
  }

  loadNatureSocieta() {
    this.serviceME
      .getAllNaturaSocietaria("naturaSocietaria/getAllNaturaSocietaria")
      .subscribe({
        next: (res: any) => {
          this.societa = res;
        },
      });
  }

  loadComuneSP(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniSP = res;
            this.showListComuniSP = true;
          },
        });
    } else {
      this.showListComuniSP = false;
    }
  }

  /*selectComuneSP(idComune: string, comune: string) {
    this.idComune = parseInt(idComune);
    this.comuneSceltoSP = comune;
    this.showListComuniSP = false;
  }*/

  // checkCodiceFiscaleValid(cFEvent: any) {
  //   let codiceFiscale = cFEvent.target.value
  //   // Verifica la lunghezza del codice fiscale italiano
  //   if (codiceFiscale.length !== 16) {
  //     this.codiceFiscaleValid = false;
  //   }
  //   // Verifica se il codice fiscale contiene solo caratteri alfanumerici
  //   if (!/^[A-Z0-9]+$/.test(codiceFiscale)) {
  //     this.codiceFiscaleValid = false;
  //   }
  //   // Se tutte le verifiche hanno successo, consideriamo il codice fiscale valido
  //   this.codiceFiscaleValid = true;
  // }

  autonomoCheckboxChange(select: boolean): any {
    this.autonomo = select;
  }

  emitAutonomo() {
    this.autonomoCheck.emit(this.autonomo);
  }

  /*onFileAllegatoCopContrat(event: any) {
    this.allegatoCopContratto = event.target.files[0];
  }

  onFileAllegatoPlanimetria(event: any) {
    this.allegatoPlanimetria = event.target.files[0];
  }*/

  /***************************************************************************************/
  /********************************CONTROLLI SEZIONE PRIMA********************************/
  /***************************************************************************************/

  //variabili per checks di validità
  validSoggRichiedente: boolean = true;
  validDataAttoCosti: boolean = true;
  validDataStatutoVig: boolean = true;
  validCodiceFiscaleUno: boolean = true;
  validPiva: boolean = true;
  validIdNaturaSoc: boolean = true;

  /* CHECK VALIDITA' */

  checkSoggRichiedente() {
    if (this.soggRichiedente != 0) {
      this.validSoggRichiedente = true;
      return true;
    } else {
      this.validSoggRichiedente = false;
      return false;
    }
  }

  checkDataAttoCosti() {
    if (
      this.dataAttoCosti != undefined &&
      this.dataAttoCosti != null &&
      this.dataAttoCosti != ""
    ) {
      this.validDataAttoCosti = true;
      return true;
    } else {
      this.validDataAttoCosti = false;
      return false;
    }
  }

  checkDataStatutoVig() {
    if (
      this.dataStatutoVig != undefined &&
      this.dataStatutoVig != null &&
      this.dataStatutoVig != ""
    ) {
      this.validDataStatutoVig = true;
      return true;
    } else {
      this.validDataStatutoVig = false;
      return false;
    }
  }

  checkPiva() {
    if (this.piva != null && this.piva.length > 0) {
      this.validPiva = true;
      return true;
    } else {
      this.validPiva = false;
      return false;
    }
  }

  checkIdNaturaSoc() {
    if (this.idNaturaSoc != 0 && this.idNaturaSoc != null) {
      this.validIdNaturaSoc = true;
      return true;
    } else {
      this.validIdNaturaSoc = false;
      return false;
    }
  }

  checkCodiceFiscaleUno() {
    if (this.codFiscSocieta != null && this.codFiscSocieta.length > 0 && !isNaN(Number(this.codFiscSocieta))) {
      this.validCodiceFiscaleUno = true;
      return true;
    } else {
      this.validCodiceFiscaleUno = false;
      return false;
    }
  }

  finalCheckPrimaSez() {
    let esito = true;

    if (this.checkSoggRichiedente() === false) esito = false;
    if (this.checkDataAttoCosti() === false) esito = false;
    if (this.checkDataStatutoVig() === false) esito = false;
    if (this.checkCodiceFiscaleUno() === false) esito = false;
    if (this.checkPiva() === false) esito = false;
    if (this.checkIdNaturaSoc() === false) esito = false;

    return esito;
  }

  // FINE FUNZIONI SEZIONE 1 ---------------------------------------------------------------------------------

  // FUNZIONI SEZIONE 2 ---------------------------------------------------------------------------------
  loadParamsSezione2(): void {
    this.serviceME
      .getSezioneSecondaDOMODMP(
        "richiesta/getSezioneSecondaDOMODMP",
        this.idRichiesta
      )
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
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
            // VERIFICA PER AUTOSELEZIONARE O NO IL DOMICILIO
            this.domicilio =
              this.indirizzoDomicilio != "" && this.indirizzoDomicilio != null
                ? true
                : false;

            this.loadComuneNascitaUpdate(res.idComuneNascita);
            this.loadComuneResidenzaUpdate(res.idComuneResidenza);
            this.loadComuneDomicilioUpdate(res.idComuneDomicilio);
          }
        },
      });
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

  loadAllNaturaGiuridica() {
    this.serviceME
      .getAllNaturaGiuridica("naturaGiuridica/getAllNaturaGiuridica")
      .subscribe({
        next: (res: any) => {
          this.natureGiuridiche = res;
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

  /*****************************************************************************************/
  /********************************CONTROLLI SEZIONE SECONDA********************************/
  /*****************************************************************************************/

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
  validComuneSceltoResidenza: boolean = true;
  validStatoResidenza: boolean = true;
  validComuneResidenzaEstero: boolean = true;
  validIndirizzoResidenza: boolean = true;
  validNumeroCivicoResidenza: boolean = true;
  validCapResidenza: boolean = true;
  validIndirizzoPec: boolean = true;
  validIndirizzoEmail: boolean = true;
  validStatoDomicilio: boolean = true;
  validComuneSceltoDomicilio: boolean = true;
  validComuneDomicilioEstero: boolean = true;

  // necessaryStatoDomicilio: boolean = true;
  // necessaryComuneDomicilio: boolean = true;

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

  checkIndirizzoPec() {
    if (this.indirizzoPec.length > 0) {
      this.validIndirizzoPec = true;
      return true;
    } else {
      this.validIndirizzoPec = false;
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

  checkDomicilio() {
    let esito = true;
    if (this.domicilio) {
      if (
        this.indirizzoDomicilio == null ||
        this.indirizzoDomicilio.length == 0
      ) {
        esito = false;
      }

      if (
        this.numeroCivicoDomicilio == null ||
        this.numeroCivicoDomicilio.length == 0
      ) {
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
        if(this.comuneSceltoDomicilio == undefined ||
          this.comuneSceltoDomicilio == null ||
          this.comuneSceltoDomicilio == "") {
            this.validComuneSceltoDomicilio = false;
            esito = false;
          }
      }

      if (this.capDomicilio == null || this.capDomicilio.length == 0) {
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

  finalCheckSecondaSez() {
    let esito = true;

    debugger

    if (this.checkTitolo() === false) esito = false;
    if (this.checkSesso() === false) esito = false;
    if (this.checkCognome() === false) esito = false;
    if (this.checkNome() === false) esito = false;
    if (this.checkDataNascita() === false) esito = false;
    if (this.checkCodiceFiscale() === false) esito = false;
    if (this.checkCittadinanza() === false) esito = false;

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

    if (this.checkCapResidenza() === false) esito = false;
    if (this.checkNumeroCivicoResidenza() === false) esito = false;
    if (this.checkIndirizzoResidenza() === false) esito = false;
    if (this.checkIndirizzoPec() === false) esito = false;
    if (this.checkIndirizzoEmail() === false) esito = false;
    if (this.checkDomicilio() === false) esito = false;

    return esito;
  }

  // FINE FUNZIONI SEZIONE 2 ---------------------------------------------------------------------------------

  // FUNZIONE SEZIONE 3 -------------------------------------------------------------------------
  //FUNZIONI TABELLA
  loadTableSO() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", this.indexPageSO - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  attivaRicercaSO() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", this.indexPageSO - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  cambiaPaginaSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index;
  }

  nextPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index + 1;
  }

  previousPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index - 1;
  }
  //FINE FUNZIONI TABELLA

  getFilePlanimetria(idSede: number) {
    this.serviceME
      .getFilePlanimetria(
        "pdf/getFileSedePlanimetria",
        this.idRichiesta,
        idSede
      )
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], {
            type: "application/pdf",
          });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, "_blank");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  getFileCopiaContratto(idSede: number) {
    this.serviceME
      .getFileCopiaContratto(
        "pdf/getFileSedeCopiaContratto",
        this.idRichiesta,
        idSede
      )
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], {
            type: "application/pdf",
          });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, "_blank");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  // Per formattare byte in file pdf
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

  routerGestioneSedi() {
    this.router.navigate(["/organismiDiMediazione"], {
      queryParams: { idRichiesta: this.idRichiesta, vistaMenu: "sedi" },
    });
  }

  // FINE FUNZIONE SEZIONE 3 -------------------------------------------------------------------------

  // FUNZIONE SEZIONE 4 -------------------------------------------------------------------------
  loadParamsSezione4(): void {
    this.serviceME
      .getSezioneQuartaDOMODMP(
        "richiesta/getSezioneQuartaDOMODMP",
        this.idRichiesta
      )
      .subscribe({
        next: (res: any) => {
          this.numeroComponentiOA = res.numCompoOrgAmm;
          this.numeroComponentiCS = res.numCompoCompSoc;
          this.durataCarica = res.durataCarica;
          this.modCostOrg = res.idModalitaCostOrgani;
          this.dataCostituzione = moment(res.dataCostituOrg).format(
            "YYYY-MM-DD"
          );
          // console.log(this.dataCostituzione +' '+ res.dataCostituOrg )
          this.autonomo = res.autonomo == 1 ? true : false;
          this.numPersonaleAdetto = res.numPersonaleAdetto;
          this.fontiFinanziamento = res.fontiDiFinanziamento;
          this.durataOrganismo = res.durataCostituzioneOrganismo;
          this.modalitaGestioneContabile = res.modalitaGestioneContabile;
          this.numMediatori = res.numMediatori;
          this.numMediatoriInter = res.numMediatoriInter;
          this.numMediatoriCons = res.numMediatoriCons;
          this.respOrganismo = res.respOrganismo;

          this.getlistModalitaCostituzioneOrganismo();
        },
      });
  }

  getlistModalitaCostituzioneOrganismo() {
    this.serviceME
      .getAllModalitaCostituzioneOrganismo(
        "modalitaCostituzioneOrganismo/getAll"
      )
      .subscribe((res: any) => {
        this.listModCostOrg = res;
      });
  }

  /*****************************************************************************************/
  /********************************CONTROLLI SEZIONE QUARTA*********************************/
  /*****************************************************************************************/

  validNumeroComponentiOA: boolean = true;
  validNumeroComponentiCS: boolean = true;
  validDurataCarica: boolean = true;
  durataOK: boolean = true;
  validModCostOrg: boolean = true;
  validDataCostituzione: boolean = true;
  validNumPersonaleAddetto: boolean = true;
  validFontiFinanziamento: boolean = true;
  validModalitaGestioneContabile: boolean = true;
  numMediatoriOK: boolean = true;

  checkNumeroComponentiOA() {
    if (
      this.numeroComponentiOA != null &&
      this.numeroComponentiOA != undefined &&
      this.numeroComponentiOA != 0
    ) {
      this.validNumeroComponentiOA = true;
      return true;
    } else {
      this.validNumeroComponentiOA = false;
      return false;
    }
  }

  checkNumeroComponentiCS() {
    if (
      this.numeroComponentiCS != null &&
      this.numeroComponentiCS != undefined &&
      this.numeroComponentiCS != 0
    ) {
      this.validNumeroComponentiCS = true;
      return true;
    } else {
      this.validNumeroComponentiCS = false;
      return false;
    }
  }

  checkDurataCarica() {
    if (
      this.durataCarica != null &&
      this.durataCarica != undefined &&
      this.durataCarica != ""
    ) {
      this.validDurataCarica = true;
      return true;
    } else {
      this.validDurataCarica = false;
      return false;
    }
  }

  checkModCostOrg() {
    console.log(this.modCostOrg);
    if (this.modCostOrg != null && this.modCostOrg != undefined) {
      this.validModCostOrg = true;
      return true;
    } else {
      this.validModCostOrg = false;
      return false;
    }
  }

  checkNumPersonaleAddetto() {
    if (
      this.numPersonaleAdetto != null &&
      this.numPersonaleAdetto != undefined
    ) {
      this.validNumPersonaleAddetto = true;
      return true;
    } else {
      this.validNumPersonaleAddetto = false;
      return false;
    }
  }

  checkFontiFinanziamento() {
    if (
      this.fontiFinanziamento != null &&
      this.fontiFinanziamento != undefined &&
      this.fontiFinanziamento != ""
    ) {
      this.validFontiFinanziamento = true;
      return true;
    } else {
      this.validFontiFinanziamento = false;
      return false;
    }
  }

  checkDataCostituzione() {
    if (
      this.dataCostituzione != null &&
      this.dataCostituzione != undefined &&
      this.dataCostituzione != ""
    ) {
      this.validDataCostituzione = true;
      return true;
    } else {
      this.validDataCostituzione = false;
      return false;
    }
  }

  checkDurata() {
    if (parseInt(this.durataOrganismo) >= 5) {
      this.durataOK = true;
      return true;
    } else {
      this.durataOK = false;
      return false;
    }
  }

  checkModalitaGestioneContabile() {
    if (
      this.modalitaGestioneContabile != null &&
      this.modalitaGestioneContabile
    ) {
      this.validModalitaGestioneContabile = true;
      return true;
    } else {
      this.validModalitaGestioneContabile = false;
      return false;
    }
  }

  checkNumMedGen() {
    if (this.numMediatori != undefined && this.numMediatori != null) {
      return true;
    } else {
      return false;
    }
  }

  checkNumMedInt() {
    if (this.numMediatoriInter != undefined && this.numMediatoriInter != null) {
      return true;
    } else {
      return false;
    }
  }

  checkNumMedCon() {
    if (this.numMediatoriCons != undefined && this.numMediatoriCons != null) {
      return true;
    } else {
      return false;
    }
  }

  checkNumOK() {
    if (
      this.checkNumMedGen() &&
      this.checkNumMedInt() &&
      this.checkNumMedCon() &&
      this.numMediatori + this.numMediatoriInter + this.numMediatoriCons >= 5
    ) {
      this.numMediatoriOK = true;
      return true;
    } else {
      this.numMediatoriOK = false;
      return false;
    }
  }

  finalCheckQuartaSez() {
    let esito = true;

    if (this.checkNumeroComponentiOA() === false) esito = false;
    if (this.checkNumeroComponentiCS() === false) esito = false;
    if (this.checkDurataCarica() === false) esito = false;
    if (this.checkModCostOrg() === false) esito = false;
    if (this.checkDataCostituzione() === false) esito = false;
    if (this.checkNumPersonaleAddetto() === false) esito = false;
    if (this.checkFontiFinanziamento() === false) esito = false;
    if (this.checkModalitaGestioneContabile() === false) esito = false;

    if (this.checkDurata() === false) esito = false;
    if (this.checkNumOK() === false) esito = false;

    return esito;
  }

  // FINE FUNZIONE SEZIONE 4 -------------------------------------------------------------------------

  openSezione(numeroSezione: number) {
    // Ottiene tutti gli elementi con la classe specifica
    let elements = document.querySelectorAll(".collapse-sezioni");

    // Itera su ogni elemento e verifica la proprietà aria-expanded per capire da quale
    // sezione viene fatta la richiesta per il load dei dati
    for (let index = 0; index < elements.length; index++) {
      let element = elements[index];
      let ariaExpanded = element.getAttribute("aria-expanded");

      // VERIFICHE CHE SI BASANO SULLE APERTURA O CHIUSURA  DELLE CLASSI HTML
      if (index === 0 && ariaExpanded === "true") {
        // SEZIONE 1
        if (numeroSezione !== 1) {
          // QUALUNQUE CLASS E CONTAINER CHE NON SIA QUELLO APERTO PER L'APERTURA
          // Imposta aria-expanded su false per l'elemento corrente
          element.setAttribute("aria-expanded", "false");
          let divCollapse = document.getElementById("collapse1c");
          element.classList.add("collapsed");
          // Chiude il div di riferimento della sezione
          divCollapse!.classList.remove("show");
        } else {
          // ISTRUZIONI CHE VERRANNO ESEGUITE UNA VOLTA APERTA LA SEGUENTE SEZIONE
          this.loadParamsSezione1();
          this.loadNatureSocieta();
          this.loadSoggRichiedenti();
          this.loadAllNaturaGiuridica();
        }
      } else if (index === 1 && ariaExpanded === "true") {
        // SEZIONE 2
        if (numeroSezione !== 2) {
          element.setAttribute("aria-expanded", "false");
          let divCollapse = document.getElementById("collapse2c");
          element.classList.add("collapsed");
          divCollapse!.classList.remove("show");
        } else {
          this.loadParamsSezione2();
          this.loadTitoliAnagrafiche();
        }
      } else if (index === 2 && ariaExpanded === "true") {
        // SEZIONE 3
        if (numeroSezione !== 3) {
          element.setAttribute("aria-expanded", "false");
          let divCollapse = document.getElementById("collapse3c");
          element.classList.add("collapsed");
          divCollapse!.classList.remove("show");
        } else {
          // ISTRUZIONI CHE VERANNO ESEGUITE UNA VOLTA APERTA LA SEGUENTE SEZIONE
          this.loadTableSO();
        }
      } else if (index === 3 && ariaExpanded === "true") {
        // SEZIONE 4
        if (numeroSezione !== 4) {
          element.setAttribute("aria-expanded", "false");
          let divCollapse = document.getElementById("collapse4c");
          element.classList.add("collapsed");
          divCollapse!.classList.remove("show");
        } else {
          this.loadParamsSezione4();
          this.getlistModalitaCostituzioneOrganismo();
        }
      }
    }
  }

  saveSezione() {
    let sezioneRichieta = this.checkSezioneOpen();

    if (sezioneRichieta === "sezionePrima") {
      if (this.finalCheckPrimaSez() === true) this.sendSezionePrima();
      else
        this.sharedService.onMessage(
          "error",
          "Per proseguire al salvataggio è necessario avere tutti i campi obbligatori completati e validi della sezione prima"
        );
      return;
    } else if (sezioneRichieta === "sezioneSeconda") {
      debugger;
      if (this.finalCheckSecondaSez() === true) this.sendSezioneSeconda();
      else
        this.sharedService.onMessage(
          "error",
          "Per proseguire al salvataggio è necessario avere tutti i campi obbligatori completati e validi della sezione seconda"
        );
      return;
    } else if (sezioneRichieta === "sezioneTerza") {
      this.sharedService.onMessage(
        "attention",
        "Per proseguire al salvataggio e gestione delle sedi è necessario completarne l'inserimento sull'apposita area del menù"
      );
    } else if (sezioneRichieta === "sezioneQuarta") {
      if (this.finalCheckQuartaSez() === true) this.sendSezioneQuarta();
      else
        this.sharedService.onMessage(
          "error",
          "Per proseguire al salvataggio è necessario avere tutti i campi obbligatori completati e validi della sezione quarta"
        );
      return;
    } else {
     // alert("nessuna sezione è aperta");
      this.sharedService.onMessage(
        "error",
        "Nessuna sezione è aperta"
      );
      return;
    }
  }

  convalidazione() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneAttoRiepilogativoODM("status/attoRiepilogativoODM", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Impossibile proseguire con la convalidazione"
          );
          // SE NELL'ERRORE DI RITORNO CONTERRA' IL <BR> VUOL DIRE CHE L'ERRORE E' GESTITO
          if (error.includes("<br>")) {
            this.extraInfoComponent.openModal(
              "Impossibile proseguire con la convalidazione:",
              error
            );
          }
        },
      });
  }

  checkSezioneOpen(): string {
    // Ottiene tutti gli elementi con la classe specifica
    const elements = document.querySelectorAll(".collapse-sezioni");

    // Itera su ogni elemento e verifica la proprietà aria-expanded per capire da quale
    // sezione viene fatta la richiesta per il save dei dati
    for (let index = 0; index < elements.length; index++) {
      const element = elements[index];
      const ariaExpanded = element.getAttribute("aria-expanded");

      // Verifica se la proprietà aria-expanded è impostata su true
      if (index === 0 && ariaExpanded === "true") {
        return "sezionePrima";
      } else if (index === 1 && ariaExpanded === "true") {
        return "sezioneSeconda";
      } else if (index === 2 && ariaExpanded === "true") {
        return "sezioneTerza";
      } else if (index === 3 && ariaExpanded === "true") {
        return "sezioneQuarta";
      }
    }

    // Restituisci un valore di default o gestisci l'errore
    return ""; // Puoi anche restituire un valore diverso a seconda del caso
  }

  sendSezionePrima() {
    let datiOrganismoDiMediazioneDto: {
      idRichiesta: number;
      dataAttoCosti: string;
      dataStatutoVig: string;
      codFiscSocieta: string;
      piva: string;
      idNaturaSoc: number;
      idSoggettoRichiedente: number;
      autonomo: number;
      oggettoSociale: string;
      istitutoEntePub: number;
      denominaOdmPub: string;
      idNaturaGiu: number;
    } = {
      idRichiesta: this.idRichiesta,
      dataAttoCosti: this.dataAttoCosti,
      dataStatutoVig: this.dataStatutoVig,
      codFiscSocieta: this.codFiscSocieta,
      piva: this.piva,
      idNaturaSoc: this.idNaturaSoc,
      idSoggettoRichiedente: this.soggRichiedente,
      autonomo: this.autonomo === true ? 1 : 0,
      oggettoSociale: this.oggSociale,
      istitutoEntePub: this.istitutoEntePub ? 1 : 0,
      denominaOdmPub: this.denominaOdmPub,
      idNaturaGiu: this.idNaturaGiu,
    };

    // Inserire i controlli

    this.serviceME
      .saveSezionePrima(
        "richiesta/saveSezionePrima",
        datiOrganismoDiMediazioneDto
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  sendSezioneSeconda() {
    let datiSezioneSecondaDto: {
      idRichiesta: number;
      idTitoloAnagrafica: number;
      sesso: string;
      cognome: string;
      nome: string;
      dataNascita: string;
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
    } = {
      idRichiesta: this.idRichiesta,
      idTitoloAnagrafica: this.idTitolo,
      sesso: this.sesso,
      cognome: this.cognome,
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
    };

    // Inserire i controlli

    // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
    if (this.isEsteroNascita == "false") {
      datiSezioneSecondaDto.comuneNascitaEstero = "";
    } else {
      datiSezioneSecondaDto.idComuneNascita = 0;
    }

    if (this.isEsteroResidenza == "false") {
      datiSezioneSecondaDto.comuneResidenzaEstero = "";
    } else {
      datiSezioneSecondaDto.idComuneResidenza = 0;
    }

    if (this.domicilio) {
      if (this.isEsteroDomicilio == "false") {
        datiSezioneSecondaDto.comuneDomicilioEstero = "";
        datiSezioneSecondaDto.statoDomicilio = "";
      } else {
        datiSezioneSecondaDto.idComuneDomicilio = 0;
      }
    } else {
      datiSezioneSecondaDto.indirizzoDomicilio = "";
      datiSezioneSecondaDto.numeroCivicoDomicilio = "";
      datiSezioneSecondaDto.idComuneDomicilio = 0;
      datiSezioneSecondaDto.capDomicilio = "";
      datiSezioneSecondaDto.statoDomicilio = "";
      datiSezioneSecondaDto.comuneDomicilioEstero = "";
      datiSezioneSecondaDto.comuneDomicilioEstero = "";
      datiSezioneSecondaDto.statoDomicilio = "";
      datiSezioneSecondaDto.idComuneDomicilio = 0;
    }

    this.serviceME
      .saveSezioneSeconda("richiesta/saveSezioneSeconda", datiSezioneSecondaDto)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  sendSezioneQuarta() {
    const datiSezioneQuartaDto: {
      idRichiesta: number;
      numCompoOrgAmm: number;
      numCompoCompSoc: number;
      durataCarica: string;
      idModalitaCostOrgani: number;
      dataCostituOrg: string;
      autonomo: number;
      numPersonaleAdetto: number;
      fontiDiFinanziamento: string;
      durataCostituzioneOrganismo: string;
      modalitaGestioneContabile: string;
      numMediatori: number;
      numMediatoriInter: number;
      numMediatoriCons: number;
    } = {
      idRichiesta: this.idRichiesta,
      numCompoOrgAmm: this.numeroComponentiOA,
      numCompoCompSoc: this.numeroComponentiCS,
      durataCarica: this.durataCarica,
      idModalitaCostOrgani: this.modCostOrg,
      dataCostituOrg: this.dataCostituzione,
      autonomo: this.autonomo == true ? 1 : 0,
      numPersonaleAdetto: this.numPersonaleAdetto,
      fontiDiFinanziamento: this.fontiFinanziamento,
      durataCostituzioneOrganismo: this.durataOrganismo,
      modalitaGestioneContabile: this.modalitaGestioneContabile,
      numMediatori: this.numMediatori,
      numMediatoriInter: this.numMediatoriInter,
      numMediatoriCons: this.numMediatoriCons,
    };

    // Inserire i controlli...

    this.serviceME
      .saveSezioneQuarta("richiesta/saveSezioneQuarta", datiSezioneQuartaDto)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }
}
