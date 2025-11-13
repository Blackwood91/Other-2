import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { SaveFileComponent } from 'src/app/modals/save-file/save-file.component';
import { UpdateCertificazioneComponent } from 'src/app/modals/update-certificazione/update-certificazione.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-caricamento-file-appendici',
  templateUrl: './caricamento-file-appendici.component.html',
  styleUrls: ['./caricamento-file-appendici.component.css']
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
  searchTextTable: string = '';
  isModifica: boolean = false;
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  @ViewChild(UpdateCertificazioneComponent) updateCertificazione!: UpdateCertificazioneComponent;
  idModulo: number = 0;
  stato: string = "";
  lavorazione: string = "";
  // da mettere, proveniente dal mediatore della dropdown
  idAnagrafica: number = 0;
  codiceFiscale: string = '';
  mediatori = new Array();
  showTable: boolean = false;
  // campi certificazione
  isModificaCert: boolean = false;
  enteCertificatore: string = '';
  dataCertificazione: string = '';
  validEnteCertificazione: boolean = false;
  validDataCertificazione: boolean = false;
  anagraficaSelezionata: string = "";
  showElencoSelectMediatori: boolean = false;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private sharedService: SharedService) { }

  checkEnteCertificazione() {
    if(this.enteCertificatore.length > 0) {
      this.validEnteCertificazione = true;
      return true;
    }   
    else {
      this.validEnteCertificazione = false;
      return false;
    }   
  }

  checkDataCertificazione() {
    if(this.dataCertificazione != undefined && this.dataCertificazione != null && this.dataCertificazione != '') {
      this.validDataCertificazione = true;
      return true;
    }
    else {
      this.validDataCertificazione = false;
      return false;
    }
  }

  ngOnInit(): void {
    this.disabledSelectElencoCustom();
    this.loadSelectMediatori();

    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      //this.idAnagrafica = params['idAnagrafica'];

    if (this.component === "disponibilita-a") {
      this.idModulo = 40;
      if(this.idAnagrafica != 0 && !!this.idAnagrafica)      
      return;
    }
    else if (this.component === "disponibilita-b") {
      this.idModulo = 45;
      return;
    }
    else if (this.component === "disponibilita-c") {
      this.idModulo = 54;
      return;
    }
    else if (this.component === "formazione-iniziale-a") {
      this.idModulo = 41;
      return;
    }
    else if (this.component === "formazione-iniziale-b") {
      this.idModulo = 46;
      return;
    }
    else if (this.component === "formazione-iniziale-c") {
      this.idModulo = 55;
      return;
    }
    else if (this.component === "formazione-specifica-b") {
      this.idModulo = 77;
      return;
    }
    else if (this.component === "formazione-specifica-c") {
      this.idModulo = 80;
      return;
    }
    else if (this.component === "certificazione-lingue-b") {
      this.idModulo = 50;
      return;
    }
    else if (this.component === "certificazione-lingue-c") {
      this.idModulo = 82;
      return;
    }
    else if (this.component === "ulteriori-requisiti-a") {
      this.idModulo = 75;
      return;
    }
    else if (this.component === "ulteriori-requisiti-b") {
      this.idModulo = 78;
      return;
    }
    else if (this.component === "ulteriori-requisiti-c") {
      this.idModulo = 81;
      return;
    }
    else return;
    })

  }

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
    this.loadRappresentante();
  }

  loadRappresentante() {
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


  loadDisponibilitaA() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllAttoCostOdm('statoModuli/getAllDisponibilitaA', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadDisponibilitaB() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllStatutoOrg('statoModuli/getAllDisponibilitaB', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadDisponibilitaC() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura('statoModuli/getAllDisponibilitaC', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeA() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico('statoModuli/getAllFormazioneInizialeA', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeB() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllBilancio('statoModuli/getAllFormazioneInizialeB', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneInizialeC() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllAttoCostOdm('statoModuli/getAllFormazioneInizialeC', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneSpecificaB() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllStatutoOrg('statoModuli/getAllFormazioneSpecificaB', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadFormazioneSpecificaC() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura('statoModuli/getAllFormazioneSpecificaC', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadCertificazioneB() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico('statoModuli/getAllCertificazioneB', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadCertificazioneC() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllBilancio('statoModuli/getAllCertificazioneC', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiA() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllRegProcedura('statoModuli/getAllUlterioriRequisitiA', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiB() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllCodiceEtico('statoModuli/getAllUlterioriRequisitiB', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadUlterioriRequisitiC() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .getAllBilancio('statoModuli/getAllUlterioriRequisitiC', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  openModalLoadFile(dataCertificazione: string, enteCertificatore: string, idCertificazioneLingua: number, idStatoModuloFiglio: number) {
    if (this.isModifica && (this.component != 'certificazione-lingue-b' && this.component != 'certificazione-lingue-c')) {
      this.saveFile.openModal("Aggiornamento nuovo file", idStatoModuloFiglio);
    }
    else
      this.updateCertificazione.openModal("Aggiornamento certificazione", idStatoModuloFiglio, this.idRichiesta, idCertificazioneLingua, dataCertificazione, enteCertificatore);
  }

  getFileModalChildren(params: any /* parametro per capire da chi proviene la richiesta*/) {
    // LA STRUTTURA DEI PERCORSI E DEL CARICAMENTO DEI FILE SARA CENTRALIZZATO DA UN UNICO METODO CHE SI OCCUPERA' DI CIO' 
    this.updateFileModulo(params.selectedFilePDF, params.id)
  }

  getFileModalChildrenUpdate(params: any) {
    this.loadRappresentante();
  }


  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
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
    let blobFile = new Blob([byteArray], { type: 'application/pdf' });
    return new File([blobFile], 'file');
  }

  // OPENING FILE
  openPdfFile(id: number) {
    this.serviceME.getFileAttoCostOdm('pdf/getFileModulo', id)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, '_blank');
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  // FUNZIONI CENTRALIZZATE PER TUTTI I MODULI
  updateFileModulo(selectedFilePDF: any, id: number) {
    const fileAllegatoDto: {
      id: number, idRichiesta: number, file: any, nomeFile: string, idRiferimento: number
    } = {
      id: id, idRichiesta: this.idRichiesta, file: null, nomeFile: selectedFilePDF.name, idRiferimento: 0
    }

    let formData: FormData = new FormData();
    formData.append('fileAllegatoDto', new Blob([JSON.stringify(fileAllegatoDto)], { type: 'application/json' }));
    formData.append('filePdf', selectedFilePDF);

    this.serviceME.saveFileAttoCostitutivoOdm('statoModuli/updateFileModulo', formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onMessage('success', "L'aggiornamento del file è avvenuto con successo!");

          this.loadRappresentante();

        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  /************************************************************************************************************************************/
  /***********************************************FINIRE DI CORREGGERE NOMI************************************************************/
  /************************************************************************************************************************************/



  public titoloPagina() {
    if (this.component === "disponibilita-a")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE"
    else if (this.component === "disponibilita-b")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE"
    else if (this.component === "disponibilita-c")
      return "DICHIARAZIONE DI DISPONIBILITÀ DEL MEDIATORE"
    else if (this.component === "formazione-iniziale-a")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI"
    else if (this.component === "formazione-iniziale-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI"
    else if (this.component === "formazione-iniziale-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI"
    else if (this.component === "formazione-specifica-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI (Sezione B del Registro)"
    else if (this.component === "formazione-specifica-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SULLA FORMAZIONE INIZIALE DEI MEDIATORI (Sezione C del Registro)"
    else if (this.component === "certificazione-lingue-b")
      return "CERTIFICAZIONE CONOSCENZE LINGUE ESTERE (Sezione B del Registro)"
    else if (this.component === "certificazione-lingue-c")
      return "CERTIFICAZIONE CONOSCENZE LINGUE ESTERE (Sezione C del Registro)"
    else if (this.component === "ulteriori-requisiti-a")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI"
    else if (this.component === "ulteriori-requisiti-b")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI"
    else if (this.component === "ulteriori-requisiti-c")
      return "DICHIARAZIONE SOSTITUTIVA DI CERTIFICAZIONE SUGLI ULTERIORI REQUISITI DEI MEDIATORI"
    else 
      return 'errore'
  }

  activeModifica() {
    this.isModifica = true;
  }

  activeModificaCert() {
    this.isModificaCert = true;
  }

  validazione() {
    if(this.component === "disponibilita-a") {
      this.validazioneDisponibilitaA();
    }
    else if(this.component === "disponibilita-b") {
      this.validazioneDisponibilitaB();
    }
    else if(this.component === "disponibilita-c") {
      this.validazioneDisponibilitaC();
    }
    else if(this.component === "formazione-iniziale-a") {
     this.validazioneFormazioneInizialeA();
    }
    else if(this.component === "formazione-iniziale-b") {
      this.validazioneFormazioneInizialeB();
    }
    else if(this.component === "formazione-iniziale-c") {
      this.validazioneFormazioneInizialeC();
    }
    else if(this.component === "formazione-specifica-b") {
      this.validazioneFormazioneSpecificaB();
    }
    else if(this.component === "formazione-specifica-c") {
      this.validazioneFormazioneSpecificaC();
    }
    else if (this.component === "ulteriori-requisiti-a") {
      this.validazioneUlteReqMediatoriA();
    }
    else if (this.component === "ulteriori-requisiti-b") {
      this.validazioneUlteReqMediatoriB();
    }
    else if (this.component === "ulteriori-requisiti-c") {
      this.validazioneUlteReqMediatoriC();
    }
    else if (this.component === "certificazione-lingue-b") {
      this.validazioneCertificazioneB();
    }
    else if (this.component === "certificazione-lingue-c") {
      this.validazioneCertificazioneC();
    }

  }

  annullazione() {
    if(this.component === "disponibilita-a") {
      this.annullazioneDisponibilitaA();
    }
    else if(this.component === "disponibilita-b") {
      this.annullazioneDisponibilitaB();
    }
    else if(this.component === "disponibilita-c") {
      this.annullazioneDisponibilitaC();
    }
    else if(this.component === "formazione-iniziale-a") {
      this.annullazioneFormazioneInizialeA();
    }
    else if(this.component === "formazione-iniziale-b") {
      this.annullazioneFormazioneInizialeB();
    }
    else if(this.component === "formazione-iniziale-c") {
      this.annullazioneFormazioneInizialeC();
    }
    else if(this.component === "formazione-specifica-b") {
      this.annullazioneFormazioneSpecificaB();
    }
    else if(this.component === "formazione-specifica-c") {
      this.annullazioneFormazioneSpecificaC();
    }
    else if (this.component === "ulteriori-requisiti-a") {
      this.annullazioneUlteReqMediatoriA();
    }
    else if (this.component === "ulteriori-requisiti-b") {
      this.annullazioneUlteReqMediatoriB();
    }
    else if (this.component === "ulteriori-requisiti-c") {
      this.annullazioneUlteReqMediatoriC();
    }
    else if (this.component === "certificazione-lingue-b") {
      this.annullaCertificazioneB(); 
     }
     else if (this.component === "certificazione-lingue-c") {
      this.annullaCertificazioneC();
     }

  }

  validazioneDisponibilitaA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaDisponibilitaA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaA();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneDisponibilitaB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaDisponibilitaB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaB();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneDisponibilitaC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaDisponibilitaC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaC();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneFormazioneInizialeA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaFormazioneInizialeA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeA();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneFormazioneInizialeB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaFormazioneInizialeB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeB();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneFormazioneInizialeC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneDisponibilitaA('status/validaFormazioneInizialeC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeC();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneFormazioneSpecificaB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validaFormazioneSpecificaB('status/validaFormazioneSpecificaB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneSpecificaB();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneFormazioneSpecificaC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validaFormazioneSpecificaC('status/validaFormazioneSpecificaC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneSpecificaC();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneUlteReqMediatoriA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneUlterioriRequisitiA('status/validaUlterioriRequisitiA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiA();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneUlteReqMediatoriB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneUlterioriRequisitiB('status/validaUlterioriRequisitiB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiB();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneUlteReqMediatoriC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneUlterioriRequisitiC('status/validaUlterioriRequisitiC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiC();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        } 
      });
  }

  validazioneCertificazioneB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneCertificazioneB('status/validaCertificazioneB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadCertificazioneB();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  validazioneCertificazioneC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .validazioneCertificazioneC('status/validaCertificazioneC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadCertificazioneC();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneUlteReqMediatoriA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneUlteReqMediatoriA('status/annullaUlterioriRequisitiA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiA();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneUlteReqMediatoriB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneUlteReqMediatoriB('status/annullaUlterioriRequisitiB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiB();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneUlteReqMediatoriC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneUlteReqMediatoriC('status/annullaUlterioriRequisitiC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadUlterioriRequisitiC();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneDisponibilitaA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaDisponibilitaA('status/annullaDisponibilitaA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaA();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneDisponibilitaB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaDisponibilitaB('status/annullaDisponibilitaB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaB();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneDisponibilitaC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaDisponibilitaC('status/annullaDisponibilitaC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadDisponibilitaC();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneFormazioneInizialeA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneFormazioneInizialeA('status/annullaFormazioneInizialeA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeA();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneFormazioneInizialeB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneFormazioneInizialeB('status/annullaFormazioneInizialeB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeB();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneFormazioneInizialeC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullazioneFormazioneInizialeC('status/annullaFormazioneInizialeC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneInizialeC();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneFormazioneSpecificaB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaFormazioneSpecificaB('status/annullaFormazioneSpecificaB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneSpecificaB();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazioneFormazioneSpecificaC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaFormazioneSpecificaC('status/annullaFormazioneSpecificaC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadFormazioneSpecificaC();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullaCertificazioneB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaCertificaMediatoriB('status/annullaCertificazioneB', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadCertificazioneB();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullaCertificazioneC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idModulo', this.idModulo)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME
      .annullaCertificaMediatoriC('status/annullaCertificazioneC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadSelectMediatori();
          this.loadCertificazioneC();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

}
