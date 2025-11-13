import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { SaveSedeComponent } from 'src/app/modals/save-sede/save-sede.component';
import { SharedService } from 'src/app/shared.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-atto-riepilogativo-mediazione-per-enti',
  templateUrl: './atto-riepilogativo-mediazione-per-enti.component.html',
  styleUrls: ['./atto-riepilogativo-mediazione-per-enti.component.css']
})
export class AttoRiepilogativoMediazionePerEntiComponent implements OnInit {
  // PARAMETRI PER TUTTE LE SEZIONI
  idRichiesta: number = 0;
  isModifica = false;
  isConvalidato: boolean = false;
  areConvalidato: boolean = false;
  // SEZIONE 1
  // Dati Richiesta
  soggRichiedente: number = 0;
  dataAttoCosti: string = "";
  dataStatutoVig: string = "";
  codFiscSocieta: string = "";
  piva: string = "";
  idNaturaSoc: number = 0;
  autonomo: boolean = false;
  @Output() autonomoCheck = new EventEmitter<boolean>()                        
  oggSociale: string = "";
  istitutoEntePub: boolean = false;
  denominaOdmPub: string = "";
  idNaturaGiu: number = 0;
  // Dati di riferimento
  societa: { idNaturaSoc: number, descrizioneBreve: string, descrizione: string }[] = [];
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
  searchTextTableSO: string = '';
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
  durataOK: boolean = true;
  numMediatoriOK: boolean = true;

  listModCostOrg: any = null;

  // MODALE INFORMAZIONI
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.openSezione(1);

      this.moduloIsConvalidato();
    });
  }

  anteprimaFileAttoRiepOdm() {
    this.serviceME.getAnteprimaFileAttoRiepOdm('pdf/getAnteprimaFileAttoRiepOdm', this.idRichiesta)
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

  activeModifica() {
    this.isModifica = true;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  moduloIsConvalidato() {
    const params = new HttpParams()
      .set('idModulo', 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusAttoRODMI('status/getStatusAttoRODM', params)
      .subscribe({
        next: (res: any) => {
          if(res.status === "c") {
            this.isConvalidato = true;
          }
          else {
            this.isConvalidato = false;
          }
        }
      })
  }

  // FUNZIONI SEZIONE 1 ---------------------------------------------------------------------------------

  loadParamsSezione1(): void {
    this.serviceME.getSezionePrimaDOMODMP('richiesta/getSezionePrimaDOMODMP', this.idRichiesta)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.soggRichiedente = res.idSoggRichiedente;
            this.autonomo = res.autonomo === 1 ? true : false;
            this.oggSociale = res.oggettoSociale;
            this.dataAttoCosti = (moment(res.dataAttoCosti)).format('YYYY-MM-DD');
            this.dataStatutoVig = (moment(res.dataStatutoVig)).format('YYYY-MM-DD');
            this.codFiscSocieta = res.codFiscSocieta;
            this.piva = res.piva;
            this.idNaturaSoc = res.idNaturaSoc;
            this.istitutoEntePub = res.istitutoEntePub == 1 ? true : false;
            this.denominaOdmPub = res.denominaOdmPub;
            this.idNaturaGiu = res.idNaturaGiu;
          }
        }
      })
  }

  loadSoggRichiedenti(): void {
    this.serviceME.getAllSoggettoRichiedente('soggetto-richiedente/getAllSoggettoRichiedente')
      .subscribe({
        next: (res: any) => {
          this.selectSoggRichiedenti = res;
        }
      })
  }

  loadNatureSocieta() {
    this.serviceME.getAllNaturaSocietaria('naturaSocietaria/getAllNaturaSocietaria')
      .subscribe({
        next: (res: any) => {
          this.societa = res;
        }
      })
  }

  loadComuneSP(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME.getAllComuni('comune/getAllComuneByNome', nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniSP = res;
            this.showListComuniSP = true;
          }
        })
    }
    else {
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
    if(this.soggRichiedente != 0) {
      this.validSoggRichiedente = true;
      return true;
    }
    else {
      this.validSoggRichiedente = false;
      return false;
    }
  }

  checkDataAttoCosti() {
    if(this.dataAttoCosti != undefined && this.dataAttoCosti != null && this.dataAttoCosti != '') {
      this.validDataAttoCosti = true;
      return true;
    }
    else {
      this.validDataAttoCosti = false;
      return false;
    }
  }

  checkDataStatutoVig() {
    if(this.dataStatutoVig != undefined && this.dataStatutoVig != null && this.dataStatutoVig != '') {
      this.validDataStatutoVig = true;
      return true;
    }
    else {
      this.validDataStatutoVig = false;
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

  checkIdNaturaSoc() {
    if(this.idNaturaSoc != 0) {
      this.validIdNaturaSoc = true;
      return true;
    }
    else {
      this.validIdNaturaSoc = false;
      return false;
    }
  }

  checkCodiceFiscaleUno() {
    const codiceFiscaleUpperCase = this.codFiscSocieta.toUpperCase();
    const codiceFiscaleRegex = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
    const partitaIvaRegex = /^[0-9]{11}$/;


    if(codiceFiscaleRegex.test(codiceFiscaleUpperCase) || partitaIvaRegex.test(this.codFiscSocieta)) {
      this.validCodiceFiscaleUno = true;
      return true;
    }
    else {
      this.validCodiceFiscaleUno = false;
      return false;
    }
  }


  finalCheckPrimaSez() {
    let esito = true

    if(this.checkSoggRichiedente() === false)
      esito = false;
    if(this.checkDataAttoCosti() === false)
      esito = false;
    if(this.checkDataStatutoVig() === false)
      esito = false;
    if(this.checkCodiceFiscaleUno() === false)
      esito = false;
    if(this.checkPiva() === false)
      esito = false;
    if(this.checkIdNaturaSoc() === false)
      esito = false;
        
    return esito;
    
  }

  // FINE FUNZIONI SEZIONE 1 ---------------------------------------------------------------------------------


  // FUNZIONI SEZIONE 2 ---------------------------------------------------------------------------------
  loadParamsSezione2(): void {
    this.serviceME.getSezioneSecondaDOMODMP('richiesta/getSezioneSecondaDOMODMP', this.idRichiesta)
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
            // VERIFICA PER AUTOSELEZIONARE O NO IL DOMICILIO
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

  loadTitoliAnagrafiche() {
    this.serviceME.getAllTitoliAnagrafiche('titoloAnagrafiche/getAll')
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        }
      })
  }

  loadAllNaturaGiuridica() {
    this.serviceME.getAllNaturaGiuridica('naturaGiuridica/getAllNaturaGiuridica')
      .subscribe({
        next: (res: any) => {
          this.natureGiuridiche = res;
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

  // necessaryStatoDomicilio: boolean = true;
  // necessaryComuneDomicilio: boolean = true;

  // FUNZIONE SEZIONE 3 -------------------------------------------------------------------------
  //FUNZIONI TABELLA
  loadTableSO() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTableSO)
      .set('indexPage', this.indexPageSO - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('sede/getAllSediOperativeByIdRichiesta', params)
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  attivaRicercaSO() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTableSO)
      .set('indexPage', this.indexPageSO - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('sede/getAllSediOperativeByIdRichiesta', params)
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  cambiaPaginaSO(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTableSO)
      .set('indexPage', index - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('sede/getAllSediOperativeByIdRichiesta', params)
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index;
  }

  nextPageSO(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTableSO)
      .set('indexPage', index)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('sede/getAllSediOperativeByIdRichiesta', params)
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index + 1;
  }

  previousPageSO(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTableSO)
      .set('indexPage', index - 2)
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getAllSediOperativeByRichiesta('sede/getAllSediOperativeByIdRichiesta', params)
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index - 1;
  }
  //FINE FUNZIONI TABELLA

  getFilePlanimetria(idSede: number) {
    this.serviceME.getFilePlanimetria('pdf/getFileSedePlanimetria', this.idRichiesta, idSede)
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

  getFileCopiaContratto(idSede: number) {
    this.serviceME.getFileCopiaContratto('pdf/getFileSedeCopiaContratto', this.idRichiesta, idSede)
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

  routerGestioneSedi() {
    this.router.navigate(['/organismiDiMediazione'], { queryParams: { idRichiesta: this.idRichiesta, vistaMenu: "sedi" } });
  }

  // FINE FUNZIONE SEZIONE 3 -------------------------------------------------------------------------
  
  // FUNZIONE SEZIONE 4 -------------------------------------------------------------------------
  loadParamsSezione4(): void {
    this.serviceME.getSezioneQuartaDOMODMP('richiesta/getSezioneQuartaDOMODMP', this.idRichiesta)
      .subscribe({
        next: (res: any) => {
          this.numeroComponentiOA = res.numCompoOrgAmm;
          this.numeroComponentiCS = res.numCompoCompSoc;
          this.durataCarica = res.durataCarica;
          this.modCostOrg = res.idModalitaCostOrgani;
          this.dataCostituzione =  (moment(res.dataCostituOrg).format('YYYY-MM-DD'));
          this.autonomo = res.autonomo == 1 ? true : false;
          this.numPersonaleAdetto = res.numPersonaleAdetto;
          this.fontiFinanziamento = res.fontiDiFinanziamento;
          this.durataOrganismo = res.durataCostituzioneOrganismo;
          this.modalitaGestioneContabile = res.modalitaGestioneContabile;
          this.numMediatori = res.numMediatori; 
          this.numMediatoriInter = res.numMediatoriInter;
          this.numMediatoriCons = res.numMediatoriCons;
          this.respOrganismo = res.respOrganismo;

          this.getlistModalitaCostituzioneOrganismo()

        }
      })
  }

  getlistModalitaCostituzioneOrganismo() {
    this.serviceME
      .getAllModalitaCostituzioneOrganismo('modalitaCostituzioneOrganismo/getAll')
      .subscribe((res: any) => {
        this.listModCostOrg = res;
      });
  }

  // FINE FUNZIONE SEZIONE 4 -------------------------------------------------------------------------

  openSezione(numeroSezione: number) {
    // Ottiene tutti gli elementi con la classe specifica
    let elements = document.querySelectorAll('.collapse-sezioni');

    // Itera su ogni elemento e verifica la proprietà aria-expanded per capire da quale
    // sezione viene fatta la richiesta per il load dei dati
    for (let index = 0; index < elements.length; index++) {
      let element = elements[index];
      let ariaExpanded = element.getAttribute('aria-expanded');

      // VERIFICHE CHE SI BASANO SULLE APERTURA O CHIUSURA  DELLE CLASSI HTML
      if (index === 0 && ariaExpanded === 'true') {
        // SEZIONE 1
        if (numeroSezione !== 1) {
          // QUALUNQUE CLASS E CONTAINER CHE NON SIA QUELLO APERTO PER L'APERTURA
          // Imposta aria-expanded su false per l'elemento corrente
          element.setAttribute('aria-expanded', 'false');
          let divCollapse = document.getElementById('collapse1c');
          element.classList.add('collapsed');
          // Chiude il div di riferimento della sezione
          divCollapse!.classList.remove('show');
        }
        else {
          // ISTRUZIONI CHE VERRANNO ESEGUITE UNA VOLTA APERTA LA SEGUENTE SEZIONE
          this.loadParamsSezione1();
          this.loadNatureSocieta();
          this.loadSoggRichiedenti();
          this.loadAllNaturaGiuridica();
        }
      }
      else if (index === 1 && ariaExpanded === 'true') {
        // SEZIONE 2
        if (numeroSezione !== 2) {
          element.setAttribute('aria-expanded', 'false');
          let divCollapse = document.getElementById('collapse2c');
          element.classList.add('collapsed');
          divCollapse!.classList.remove('show');
        }
        else {
          this.loadParamsSezione2();
          this.loadTitoliAnagrafiche();
        }
      }
      else if (index === 2 && ariaExpanded === 'true') {
        // SEZIONE 3
        if (numeroSezione !== 3) {
          element.setAttribute('aria-expanded', 'false');
          let divCollapse = document.getElementById('collapse3c');
          element.classList.add('collapsed');
          divCollapse!.classList.remove('show');
        }
        else {
          // ISTRUZIONI CHE VERANNO ESEGUITE UNA VOLTA APERTA LA SEGUENTE SEZIONE
          this.loadTableSO();
        }
      }
      else if (index === 3 && ariaExpanded === 'true') {
        // SEZIONE 4
        if (numeroSezione !== 4) {
          element.setAttribute('aria-expanded', 'false');
          let divCollapse = document.getElementById('collapse4c');
          element.classList.add('collapsed');
          divCollapse!.classList.remove('show');
        }
        else {
          this.loadParamsSezione4();
          this.getlistModalitaCostituzioneOrganismo();
        }
      }
    }
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validazioneAttoRiepilogativoODM('status/validaAttoRiepilogativoODM', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .annullaAttoRiepilogativoODM('status/annullaAttoRiepilogativoODM', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage('success', "l'annullamento è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }


  checkSezioneOpen(): string {
    // Ottiene tutti gli elementi con la classe specifica
    const elements = document.querySelectorAll('.collapse-sezioni');

    // Itera su ogni elemento e verifica la proprietà aria-expanded per capire da quale
    // sezione viene fatta la richiesta per il save dei dati
    for (let index = 0; index < elements.length; index++) {
      const element = elements[index];
      const ariaExpanded = element.getAttribute('aria-expanded');

      // Verifica se la proprietà aria-expanded è impostata su true
      if (index === 0 && ariaExpanded === 'true') {
        return "sezionePrima";
      }
      else if (index === 1 && ariaExpanded === 'true') {
        return "sezioneSeconda";
      }
      else if (index === 2 && ariaExpanded === 'true') {
        return "sezioneTerza";
      }
      else if (index === 3 && ariaExpanded === 'true') {
        return "sezioneQuarta";
      }

    }

    // Restituisci un valore di default o gestisci l'errore
    return "";  // Puoi anche restituire un valore diverso a seconda del caso
  }

  // loadSediAreConvalidato() {
  //   const params = new HttpParams()
  //     .set('idRichiesta', this.idRichiesta);

  //   this.serviceME.getStatusSedi('status/getStatusSedi', params)
  //     .subscribe({
  //       next: (res: any) => {
  //           if(res.status === "c") {
  //             this.areConvalidato = true;
  //           }
  //           else {
  //             this.areConvalidato = false;
  //           }
  //       },
  //       error: (error: any) => {
  //         this.areConvalidato = false;
  //       }
  //     })
  // }

}
