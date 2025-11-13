import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { ExtraInfoComponent } from "src/app/modals/extra-info/extra-info.component";
import { SaveFileComponent } from "src/app/modals/save-file/save-file.component";
import { UpdateCertificazioneComponent } from "src/app/modals/update-certificazione/update-certificazione.component";
import { ConfirmMessageComponent } from "src/app/principal-components/confirm-message/confirm-message.component";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-caricamento-file-appendici",
  templateUrl: "./caricamento-file-appendici.component.html",
  styleUrls: ["./caricamento-file-appendici.component.css"],
})
export class CaricamentoFileAppendiciComponent implements OnInit {
  @Input()
  component!: string;
  @Input()
  idRichiesta: number = 0;
  isConvalidato = false;
  selectedFilePDF!: any;
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";
  isModifica: boolean = false;
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  @ViewChild(UpdateCertificazioneComponent)
  updateCertificazione!: UpdateCertificazioneComponent;
  @ViewChild(ConfirmMessageComponent)
  confirmMessageComponent!: ConfirmMessageComponent;
  idModulo: number = 0;
  stato: string = "";
  lavorazione: string = "";

  //da mettere, proveniente dal mediatore della dropdown
  idAnagrafica: number = 0;
  codiceFiscale: string = "";
  mediatori = new Array();
  showTable: boolean = false;
  //campi certificazione
  isModificaCert: boolean = false;
  enteCertificatore: string = "";
  dataCertificazione: string = "";
  validEnteCertificazione: boolean = false;
  validDataCertificazione: boolean = false;
  anagraficaSelezionata: string = "";
  showElencoSelectMediatori: boolean = false;
  payloadCert: {dataCertificazione: string, enteCertificatore: string, idCertificazioneLingua: number, idStatoModuloFiglio: number}[] = []; 

  isInserito: boolean = false;
  
  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  disabledSelectElencoCustom() {
    // DISABILITAZIONE MENU IN DISCE PER ELENCO PERSONALIZZATO CON SVG
    document.getElementById('selectAutocertificazioneAnagrafiche')!.addEventListener('mousedown', function(event) {
      event.preventDefault();
    });
  }

  onShowElencoSelectMediatori () {
    this.showElencoSelectMediatori = this.showElencoSelectMediatori ? false : true;
  }

  selectAnagrafica(idAnagrafica: number, anagraficaSelezionata: string) {
    this.showElencoSelectMediatori = false;
    this.anagraficaSelezionata = anagraficaSelezionata;
    this.idAnagrafica = idAnagrafica;
    this.loadAnagrafica();
  }

  loadAnagrafica() {
    const params = new HttpParams()
    .set('idModulo', this.idModulo)
    .set('idRichiesta', this.idRichiesta)
    .set('idAnagrafica', this.idAnagrafica);
    
    this.serviceME.getAnagraficaById('anagrafica/getAnagraficaById', params)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.codiceFiscale = res.codiceFiscale;            
          }
        }
      })
    
    this.serviceME.getStatusRquisitiOnorabilita('status/getModuloIsConvalidatoAdPersonam', params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato //exist;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })

    this.checkAlreadyInserito()

    switch(this.component) {
      case "disponibilita-a": this.loadDisponibilitaA(); 
        break;
      case "disponibilita-b": this.loadDisponibilitaB();
        break;
      case "disponibilita-c": this.loadDisponibilitaC();
        break;
      case "formazione-iniziale-a": this.loadFormazioneInizialeA();
        break;
      case "formazione-iniziale-b": this.loadFormazioneInizialeB();
        break;
      case "formazione-iniziale-c": this.loadFormazioneInizialeC();
        break;
      case "formazione-specifica-b": this.loadFormazioneSpecificaB(); 
        break;
      case "formazione-specifica-c": this.loadFormazioneSpecificaC();
        break;
      case "certificazione-lingue-b": this.loadCertificazioneB();
        break;
      case "certificazione-lingue-c": this.loadCertificazioneC(); 
        break;
      case "ulteriori-requisiti-a": this.loadUlterioriRequisitiA(); 
        break;
      case "ulteriori-requisiti-b": this.loadUlterioriRequisitiB(); 
        break;
      case "ulteriori-requisiti-c": this.loadUlterioriRequisitiC(); 
        break;
      default: null;
    }
    this.showTable = true;
  }


  loadSelectMediatori() {
    const params = new HttpParams()
    .set('indexPage', "MAX_RESULT")
    .set('idRichiesta', this.idRichiesta);    

    if(this.component === "disponibilita-a") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllDicDispMediatoriA', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "disponibilita-b") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllDicDispMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "disponibilita-c") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllDicDispMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "formazione-iniziale-a") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllFormazIniMediatoriA', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "formazione-iniziale-b") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllFormazIniMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "formazione-iniziale-c") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllFormazIniMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "formazione-specifica-b") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllFormazSpeciMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if(this.component === "formazione-specifica-c") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllFormazSpeciMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if (this.component === "ulteriori-requisiti-a") {
      this.idModulo = 75;
      this.serviceME.getAllAnagrafica('anagrafica/getAllUlteReqMediatoriA', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if (this.component === "ulteriori-requisiti-b") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllUlteReqMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if (this.component === "ulteriori-requisiti-c") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllUlteReqMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if (this.component === "certificazione-lingue-b") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllCertificaMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }
    else if (this.component === "certificazione-lingue-c") {
      this.serviceME.getAllAnagrafica('anagrafica/getAllCertificaMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }

    else if (this.component.endsWith('-a')) {
      this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatoriA', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;

      });
    }  
    else if (this.component.endsWith('-b')) {
      this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatoriB', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }   
    else if (this.component.endsWith('-c')) {
      this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatoriC', params)
      .subscribe((res: any) => {
        this.mediatori = res.result;
      });
    }     

  }

  checkEnteCertificazione() {
    if (this.enteCertificatore.length > 0) {
      this.validEnteCertificazione = true;
      return true;
    } else {
      this.validEnteCertificazione = false;
      return false;
    }
  }

  checkDataCertificazione() {
    if (
      this.dataCertificazione != undefined &&
      this.dataCertificazione != null &&
      this.dataCertificazione != ""
    ) {
      this.validDataCertificazione = true;
      return true;
    } else {
      this.validDataCertificazione = false;
      return false;
    }
  }

  finalCheck() {
    let esito = true;

    if (this.checkEnteCertificazione() === false) esito = false;
    if (this.checkDataCertificazione() === false) esito = false;

    return esito;
  }

  ngOnInit(): void {
    this.disabledSelectElencoCustom();
    this.loadSelectMediatori();

    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.idAnagrafica = params["idAnagrafica"];

      if (this.component === "disponibilita-a") {
        this.idModulo = 40;
      } else if (this.component === "disponibilita-b") {
        this.idModulo = 45;
      } else if (this.component === "disponibilita-c") {
        this.idModulo = 54;
      } else if (this.component === "formazione-iniziale-a") {
        this.idModulo = 41;
      } else if (this.component === "formazione-iniziale-b") {
        this.idModulo = 46;
      } else if (this.component === "formazione-iniziale-c") {
        this.idModulo = 55;
      } else if (this.component === "formazione-specifica-b") {
        this.idModulo = 77;
      } else if (this.component === "formazione-specifica-c") {
        this.idModulo = 80;
      } else if (this.component === "certificazione-lingue-b") {
        this.idModulo = 50;
      } else if (this.component === "certificazione-lingue-c") {
        this.idModulo = 82;
      } else if (this.component === "ulteriori-requisiti-a") {
        this.idModulo = 75;
      } else if (this.component === "ulteriori-requisiti-b") {
        this.idModulo = 78;
      } else if (this.component === "ulteriori-requisiti-c") {
        this.idModulo = 81;
      }
    });
  }

  isAllConvalidato() {
    const params = new HttpParams()
      .set("idModulo", this.idModulo)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getStatusRquisitiOnorabilita("status/getAllModuliAreConvalidatoAdPersonam", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato; //exist;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  loadModuli() {
    const params = new HttpParams()
      .set("idModulo", this.idModulo)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAnagraficaById("anagrafica/getAnagraficaById", params)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.codiceFiscale = res.codiceFiscale;
          }
        },
      });

    switch (this.component) {
      case "disponibilita-a":
        this.loadDisponibilitaA();
        break;
      case "disponibilita-b":
        this.loadDisponibilitaB();
        break;
      case "disponibilita-c":
        this.loadDisponibilitaC();
        break;
      case "formazione-iniziale-a":
        this.loadFormazioneInizialeA();
        break;
      case "formazione-iniziale-b":
        this.loadFormazioneInizialeB();
        break;
      case "formazione-iniziale-c":
        this.loadFormazioneInizialeC();
        break;
      case "formazione-specifica-b":
        this.loadFormazioneSpecificaB();
        break;
      case "formazione-specifica-c":
        this.loadFormazioneSpecificaC();
        break;
      case "certificazione-lingue-b":
        this.loadCertificazioneB();
        break;
      case "certificazione-lingue-c":
        this.loadCertificazioneC();
        break;
      case "ulteriori-requisiti-a":
        this.loadUlterioriRequisitiA();
        break;
      case "ulteriori-requisiti-b":
        this.loadUlterioriRequisitiB();
        break;
      case "ulteriori-requisiti-c":
        this.loadUlterioriRequisitiC();
        break;
      default:
        null;
    }

    this.isAllConvalidato();
    this.showTable = true;
  }

  loadDisponibilitaA() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllAttoCostOdm("statoModuli/getAllDisponibilitaA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadDisponibilitaB() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllStatutoOrg("statoModuli/getAllDisponibilitaB", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadDisponibilitaC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura("statoModuli/getAllDisponibilitaC", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeA() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico("statoModuli/getAllFormazioneInizialeA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeB() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllBilancio("statoModuli/getAllFormazioneInizialeB", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllAttoCostOdm("statoModuli/getAllFormazioneInizialeC", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneSpecificaB() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllStatutoOrg("statoModuli/getAllFormazioneSpecificaB", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneSpecificaC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura("statoModuli/getAllFormazioneSpecificaC", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadCertificazioneB() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico("statoModuli/getAllCertificazioneB", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadCertificazioneC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllBilancio("statoModuli/getAllCertificazioneC", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiA() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura("statoModuli/getAllUlterioriRequisitiA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiB() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico("statoModuli/getAllUlterioriRequisitiB", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAllBilancio("statoModuli/getAllUlterioriRequisitiC", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  openModalConfirmMessageCert(dataCertificazione: string, enteCertificatore: string, idCertificazioneLingua: number, idStatoModuloFiglio: number) {
    this.payloadCert.push({dataCertificazione: dataCertificazione, enteCertificatore: enteCertificatore,
                           idCertificazioneLingua: idCertificazioneLingua, idStatoModuloFiglio: idStatoModuloFiglio
    });
    
    let message = "Sei sicuro di voler sovrascrivere il file attuale con uno nuovo?";
    this.confirmMessageComponent.openModal(0, message);
  }

  openModalLoadFile(dataCertificazione: string, enteCertificatore: string, idCertificazioneLingua: number, idStatoModuloFiglio: number) {
    if (this.isModifica && this.component != "certificazione-lingue-b" && this.component != "certificazione-lingue-c") {
      this.saveFile.openModal("Aggiornamento nuovo file", idStatoModuloFiglio);
    } else
      this.updateCertificazione.openModal(
        "Aggiornamento certificazione",
        idStatoModuloFiglio,
        this.idRichiesta,
        idCertificazioneLingua,
        dataCertificazione,
        enteCertificatore
      );
  }

  getFileModalChildren(
    params: any /* parametro per capire da chi proviene la richiesta*/
  ) {
    // LA STRUTTURA DEI PERCORSI E DEL CARICAMENTO DEI FILE SARA CENTRALIZZATO DA UN UNICO METODO CHE SI OCCUPERA' DI CIO'
    this.updateFileModulo(params.selectedFilePDF, params.id);
  }

  getFileModalChildrenUpdate(params: any) {
    this.updateFileModulo(params.selectedFilePDF, params.id);
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  openLoadFile() {
    this.showFilePdf = true;
  }

  openUpdateFile() {
    this.showFilePdf = true;
  }

  onFilePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
  }

  onFileUpdatePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
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

  send() {
    if (this.idModulo === 40) {
      this.saveDisponibilitaA();
    } else if (this.idModulo === 45) {
      this.saveDisponibilitaB();
    } else if (this.idModulo === 54) {
      this.saveDisponibilitaC();
    } else if (this.idModulo === 41) {
      this.saveFormazioneInizialeA();
    } else if (this.idModulo === 46) {
      this.saveFormazioneInizialeB();
    }
    if (this.idModulo === 55) {
      this.saveFormazioneInizialeC();
    } else if (this.idModulo === 77) {
      this.saveFormazioneSpecificaB();
    } else if (this.idModulo === 80) {
      this.saveFormazioneSpecificaC();
    } else if (this.idModulo === 50) {
      this.saveCertificazioneB();
      this.dataCertificazione = "";
      this.enteCertificatore = "";
      this.selectedFilePDF = "";
    } else if (this.idModulo === 82) {
      this.saveCertificazioneC();
      this.dataCertificazione = "";
      this.enteCertificatore = "";
      this.selectedFilePDF = "";
    } else if (this.idModulo === 75) {
      this.saveUlterioriRequisitiA();
    } else if (this.idModulo === 78) {
      this.saveUlterioriRequisitiB();
    } else if (this.idModulo === 81) {
      this.saveUlterioriRequisitiC();
    }
  }

  convalidazione() {
    if (this.idModulo === 40) {
      this.convalidazioneDisponibilitaA();
    } else if (this.idModulo === 45) {
      this.convalidazioneDisponibilitaB();
    } else if (this.idModulo === 54) {
      this.convalidazioneDisponibilitaC();
    } else if (this.idModulo === 41) {
      this.convalidazioneFormazioneInizialeA();
    } else if (this.idModulo === 46) {
      this.convalidazioneFormazioneInizialeB();
    } else if (this.idModulo === 55) {
      this.convalidazioneFormazioneInizialeC();
    } else if (this.idModulo === 77) {
      this.convalidazioneFormazioneSpecificaB();
    } else if (this.idModulo === 80) {
      this.convalidazioneFormazioneSpecificaC();
    } else if (this.idModulo === 50) {
      this.convalidazioneCertificazioneB();
    } else if (this.idModulo === 82) {
      this.convalidazioneCertificazioneC();
    } else if (this.idModulo === 75) {
      this.convalidazioneUlterioriRequisitiA();
    } else if (this.idModulo === 78) {
      this.convalidazioneUlterioriRequisitiB();
    } else if (this.idModulo === 81) {
      this.convalidazioneUlterioriRequisitiC();
    }

    this.isModifica = false;
    this.loadModuli();
    this.isAllConvalidato();
  }

  // OPENING FILE
  openPdfFile(id: number) {
    const params = new HttpParams().set("id", id).set("idRichiesta", this.idRichiesta);

    this.serviceME.getFileAttoCostOdm("pdf/getFileModulo", params).subscribe({
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

  // INSERIMENTI FILE
  // FUNZIONI ATTO COSTITUTIVO ODM
  saveDisponibilitaA() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileDisponibilitaA("statoModuli/saveFileDisponibilitaA", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveDisponibilitaB() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileDisponibilitaB("statoModuli/saveFileDisponibilitaB", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveDisponibilitaC() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileDisponibilitaC("statoModuli/saveFileDisponibilitaC", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveFormazioneInizialeA() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileFormazioneInizialeA(
        "statoModuli/saveFileFormazioneInizialeA",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveFormazioneInizialeB() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileFormazioneInizialeB(
        "statoModuli/saveFileFormazioneInizialeB",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  // INSERIMENTI FILE
  // FUNZIONI ATTO COSTITUTIVO ODM

  saveFormazioneInizialeC() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileFormazioneInizialeC(
        "statoModuli/saveFileFormazioneInizialeC",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveFormazioneSpecificaB() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileFormazioneSpecificaB(
        "statoModuli/saveFileFormazioneSpecificaB",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveFormazioneSpecificaC() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileFormazioneSpecificaC(
        "statoModuli/saveFileFormazioneSpecificaC",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveCertificazioneB() {
    const fileAllegatoAppendiciDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      dataCertificazione: string;
      enteCertificatore: string;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      dataCertificazione: this.dataCertificazione,
      enteCertificatore: this.enteCertificatore,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoAppendiciDto",
      new Blob([JSON.stringify(fileAllegatoAppendiciDto)], {
        type: "application/json",
      })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileCertificazioneB("statoModuli/saveFileCertificazioneB", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
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

  saveCertificazioneC() {
    const fileAllegatoAppendiciDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      dataCertificazione: string;
      enteCertificatore: string;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      dataCertificazione: this.dataCertificazione,
      enteCertificatore: this.enteCertificatore,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoAppendiciDto",
      new Blob([JSON.stringify(fileAllegatoAppendiciDto)], {
        type: "application/json",
      })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileCertificazioneC("statoModuli/saveFileCertificazioneC", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
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

  saveUlterioriRequisitiA() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileUlterioriRequisitiA(
        "statoModuli/saveFileUlterioriRequisitiA",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveUlterioriRequisitiB() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileUlterioriRequisitiB(
        "statoModuli/saveFileUlterioriRequisitiB",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  saveUlterioriRequisitiC() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      idAnagrafica: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      idAnagrafica: this.idAnagrafica,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileUlterioriRequisitiC(
        "statoModuli/saveFileUlterioriRequisitiC",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.isInserito = true;
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

  /************************************************************************************************************************************/
  /***********************************************FINIRE DI CORREGGERE NOMI************************************************************/
  /************************************************************************************************************************************/

  convalidazioneDisponibilitaA() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneDisponibilitaA("status/disponibilitaA", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneDisponibilitaB() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/disponibilitaB", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneDisponibilitaC() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/disponibilitaC", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneFormazioneInizialeA() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/formazioneInizialeA", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneFormazioneInizialeB() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/formazioneInizialeB", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneFormazioneInizialeC() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/formazioneInizialeC", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneFormazioneSpecificaB() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/formazioneSpecificaB", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneFormazioneSpecificaC() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/formazioneSpecificaC", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneCertificazioneB() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/certificazioneB", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneCertificazioneC() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/certificazioneC", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneUlterioriRequisitiA() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/ulterioriRequisitiA", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneUlterioriRequisitiB() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/ulterioriRequisitiB", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
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
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  convalidazioneUlterioriRequisitiC() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneSedi("status/ulterioriRequisitiC", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.loadSelectMediatori();
          this.loadAnagrafica();
          this.sharedService.onMessage("success", "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", "Impossibile proseguire con la convalidazione"
          );
          this.extraInfoComponent.openModal("Impossibile proseguire con la convalidazione:", error);
        },
      });
  }

  // FUNZIONI CENTRALIZZATE PER TUTTI I MODULI
  updateFileModulo(selectedFilePDF: any, id: number) {
    const fileAllegatoDto: {id: number; idRichiesta: number; file: any; nomeFile: string; idRiferimento: number;} = {
      id: id,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", selectedFilePDF);

    this.serviceME
      .saveFileAttoCostitutivoOdm("statoModuli/updateFileModulo", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.isAllConvalidato();
          this.loadModuli();
          this.sharedService.onMessage(
            "success",
            "L'aggiornamento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      if(event.idRitorno != 0) {
        const params = new HttpParams()
          .set("idRichiesta", this.idRichiesta)
          .set("idCertificato", event.idRitorno);

        if(this.component == 'certificazione-lingue-b')
          this.serviceME
            .deleteCertificazioneB("statoModuli/deleteCertificazioneB", params)
            .subscribe({
              next: (res: any) => {
                this.sharedService.onUpdateMenu();
                this.loadCertificazioneB();
                //this.certificazione();

                this.isModifica = false;
                this.sharedService.onMessage("success", "La cancellazione è avvenuta con successo!");
              },
              error: (error: any) => {
                this.sharedService.onMessage("error", error);
              },
            });
        if(this.component == 'certificazione-lingue-c')
          this.serviceME
            .deleteCertificazioneC("statoModuli/deleteCertificazioneC", params)
            .subscribe({
              next: (res: any) => {
                this.sharedService.onUpdateMenu();
                this.loadCertificazioneC();
              // this.loadIsConvalidato();

                this.isModifica = false;
                this.sharedService.onMessage("success", "La cancellazione è avvenuta con successo!");
              },
              error: (error: any) => {
                this.sharedService.onMessage("error", error);
              },
            });
      }
      else {
        // ESTRAZIONE DEI VALORI DEL PAYLOAD
        const firstCert = this.payloadCert[0];
        const dataCertificazione = firstCert.dataCertificazione;
        const enteCertificatore = firstCert.enteCertificatore;
        const idCertificazioneLingua = firstCert.idCertificazioneLingua;
        const idStatoModuloFiglio = firstCert.idStatoModuloFiglio;
        this.openModalLoadFile(dataCertificazione, enteCertificatore, idCertificazioneLingua, idStatoModuloFiglio);
      }

    }

    this.isModifica = false;
  }

  openModalConfirmMessage(idCertificazione: number) {
    let message = "Si vuole confermare la cancellazione della certificazione?"
    this.confirmMessageComponent.openModal(idCertificazione, message);
  }

  /************************************************************************************************************************************/
  /***********************************************FINIRE DI CORREGGERE NOMI************************************************************/
  /************************************************************************************************************************************/
  public titoloPagina() {
    if (this.component === "disponibilita-a")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE";
    else if (this.component === "disponibilita-b")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE";
    else if (this.component === "disponibilita-c")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE";
    else if (this.component === "formazione-iniziale-a")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI";
    else if (this.component === "formazione-iniziale-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI";
    else if (this.component === "formazione-iniziale-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI";
    else if (this.component === "formazione-specifica-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI (Sezione B del Registro)";
    else if (this.component === "formazione-specifica-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI (Sezione C del Registro)";
    else if (this.component === "certificazione-lingue-b")
      return "CERTIFICAZIONE CONOSCENZE LINGUE ESTERE (Sezione B del Registro)";
    else if (this.component === "certificazione-lingue-c")
      return "CERTIFICAZIONE CONOSCENZE LINGUE ESTERE (Sezione C del Registro)";
    else if (this.component === "ulteriori-requisiti-a")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI";
    else if (this.component === "ulteriori-requisiti-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI";
    else if (this.component === "ulteriori-requisiti-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI";
    else return "errore";
  }

  activeModifica() {
    this.isModifica = true;
  }

  activeModificaCert() {
    this.isModificaCert = true;
  }

  checkAlreadyInserito() {
    const params = new HttpParams()
    .set("idModulo", this.idModulo)
    .set("idRichiesta", this.idRichiesta)
    .set("idAnagrafica", this.idAnagrafica);

    this.serviceME.isConvalidato("statoModuli/getModuloIsInserito", params)
    .subscribe({
      next: (res: any) => {
        this.isInserito = res.isInserito; //exist;
      },
      error: (error: any) => {
        this.sharedService.onMessage("error", error);
      },
    });
  }

  checkSize() {
    if(this.tableResult.length > 0) 
      this.isInserito = true;
  }

}
