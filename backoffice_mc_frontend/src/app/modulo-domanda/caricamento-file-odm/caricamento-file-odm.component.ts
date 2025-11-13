import { Component, Input, OnInit, ViewChild } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from '../../mediazione.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from '../../shared.service';
import { environment } from 'src/environments/environment';
import { HttpParams } from '@angular/common/http';
import { SaveFileComponent } from '../../modals/save-file/save-file.component';
import { ExtraInfoComponent } from '../../modals/extra-info/extra-info.component';


@Component({
  selector: 'app-caricamento-file-odm',
  templateUrl: './caricamento-file-odm.component.html',
  styleUrls: ['./caricamento-file-odm.component.css']
})
export class CaricamentoFileOdmComponent implements OnInit {
  @Input()
  component!: string;
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;

  selectedFilePDF!: any;
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  idModulo: number = 0;
  stato: string = "";
  lavorazione: string = "";

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private sharedService: SharedService) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.loadTable();
    })

  }

  loadTable() {
    if (this.component === "atto_costitutivo_odm") {
      this.idModulo = 4;
      this.loadTableAttoCostOdm();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "statuto_organismo") {
      this.idModulo = 5;
      this.loadTableStatutoOrg();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "regolamento_di_procedura") {
      this.idModulo = 9;
      this.loadTableRegProcedura();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "atto_costitutivo_odm_non_autonomo") {
      this.idModulo = 71;
      this.loadTableAttoCostOdmNA();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "statuto_organismo_non_autonomo") {
      this.idModulo = 72;
      this.loadTableStatutoOrgNA();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "codice_etico") {
      this.idModulo = 17;
      this.loadTableCodiceEtico();
      this.loadIsConvalidato();
      return;
    }
    else if (this.component === "bilancio_certificazione_bancaria") {
      this.idModulo = 23;
      this.loadTableBilancio();
      this.loadIsConvalidato();
      return;
    }

    else return 'errore'
  }

  loadTableAttoCostOdm() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm('statoModuli/getAllAttoCostitutivoOdm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrg() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllStatutoOrg('statoModuli/getAllStatutoOrg', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableAttoCostOdmNA() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm('statoModuli/getAllAttoCostitutivoOdmNA', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrgNA() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllStatutoOrg('statoModuli/getAllStatutoOrgNA', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableRegProcedura() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllRegProcedura('statoModuli/getAllRegProcedura', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableAttoCostNonAutonomo() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAttoCostNonAutonomo('statoModuli/getAllAttoCostNonAutonomo', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrgNonAutonomo() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllStatutoOrgNonAutonomo('statoModuli/getAllStatutoOrgNonAutonomo', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableCodiceEtico() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllCodiceEtico('statoModuli/getAllCodiceEtico', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableBilancio() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllBilancio('statoModuli/getAllBilancio', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  // FINE FUNZIONI BILANCIO ANNO PRECEDENTE
  openModalLoadFile(id: number /* parametro per capire da chi proviene la richiesta*/) {
    if (this.isModifica) {
      this.saveFile.openModal("Aggiornamento nuovo file", id);
    }
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  public titoloPagina() {
    if (this.component === "atto_costitutivo_odm")
      return "ATTO COSTITUTIVO DELL'ORGANISMO DI MEDIAZIONE(CC)"
    else if (this.component === "statuto_organismo")
      return "STATUTO ORGANISMO (CC)"
    else if (this.component === "atto_costitutivo_odm_non_autonomo")
      return "ATTO COSTITUTIVO SOCIETA' (CC)"
    else if (this.component === "statuto_organismo_non_autonomo")
      return "STATUTO ORGANISMO SOCIETA'";
    else if (this.component === "regolamento_di_procedura")
      return "REGOLAMENTO DI PROCEDURA"
    else if (this.component === "spese_di_mediazione")
      return "SPESE DI MEDIAZIONE"
    else if (this.component === "codice_etico")
      return "CODICE ETICO"
    else if (this.component === "bilancio_certificazione_bancaria")
      return "BILANCIO ANNO PRECEDENTE/CERTIFICAZIONE BANCARIA"
    else if (this.component === "atto_costitutivo_odm_non_autonomo")
      return "ATTO COSTITUTIVO SOCIETA'"
    else if (this.component === "statuto_organismo_non_autonomo")
      return "STATUTO SOCIETA'"

    else return 'errore'
  }

  activeModifica() {
    this.isModifica = true;
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

  loadIsConvalidato() {
    const params = new HttpParams()
    .set('idModulo', this.idModulo) 
    .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getModuloIsConvalidato('status/getModuloIsConvalidato', params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

      if (this.idModulo === 4) {
        this.validazioneAttoCostODM();
        return;
      }
      else if (this.idModulo === 5) {
        this.validazioneStatutoOrganismo();
        return;
      }
      if (this.idModulo === 71) {
        this.validazioneAttoCostODMNA();
        return;
      }
      else if (this.idModulo === 72) {
        this.validazioneStatutoOrganismoNA();
        return;
      }
      else if (this.idModulo === 9) {
        this.validazioneRegolamentoProcedura();
        return;
      }
      else if (this.idModulo === 17) {
        this.validazioneCodiceEtico();
        return;
      }
      else if (this.idModulo === 23) {
        this.validazioneBilancio();
        return;
      }
      else if (this.idModulo === 71) {
        this.validazioneAttoCostNonAutonomo();
        return;
      }
      else if (this.idModulo === 72) {
        this.validazioneStatutoOrgNonAutonomo();
        return;
      }

  }

  annullazione() {

    if (this.idModulo === 4) {
      this.annullaAttoCostODM();
      return;
    }
    else if (this.idModulo === 5) {
      this.annullaStatutoOrganismo();
      return;
    }
    if (this.idModulo === 71) {
      this.annullaAttoCostODMNA();
      return;
    }
    else if (this.idModulo === 72) {
      this.annullaStatutoOrganismoNA();
      return;
    }
    else if (this.idModulo === 9) {
      this.annullaRegolamentoProcedura();
      return;
    }
    else if (this.idModulo === 17) {
      this.annullaCodiceEtico();
      return;
    }
    else if (this.idModulo === 23) {
      this.annullaBilancio();
      return;
    }
    else if (this.idModulo === 71) {
      this.annullaAttoCostNonAutonomo();
      return;
    }
    else if (this.idModulo === 72) {
      this.annullaStatutoOrgNonAutonomo();
      return;
    }

  }

  validazioneAttoCostODM() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneAttoCostODM('status/validaAttoCostitutivoODM', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneStatutoOrganismo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneStatutoOrganismo('status/validaStatutoOrganismo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneAttoCostODMNA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneAttoCostODMNA('status/validaAttoCostitutivoODMNA', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneStatutoOrganismoNA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneStatutoOrganismoNA('status/validaStatutoOrganismoNA', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneRegolamentoProcedura() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneRegolamentoProcedura('status/validaRegolamentoProcedura', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneCodiceEtico() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneCodiceEtico('status/validaCodiceEtico', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneBilancio() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneBilancio('status/validaBilancio', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneAttoCostNonAutonomo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneAttoCostNonAutonomo('status/validazioneAttoCostNonAutonomo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  validazioneStatutoOrgNonAutonomo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .validazioneStatutoOrgNonAutonomo('status/validazioneStatutoOrgNonAutonomo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaAttoCostODM() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaAttoCostODM('status/annullaAttoCostitutivoODM', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L' annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaStatutoOrganismo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaStatutoOrganismo('status/annullaStatutoOrganismo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaAttoCostODMNA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaAttoCostODMNA('status/annullaAttoCostitutivoODMNA', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaStatutoOrganismoNA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaStatutoOrganismoNA('status/annullaStatutoOrganismoNA', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L' annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaRegolamentoProcedura() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaRegolamentoProcedura('status/annullaRegolamentoProcedura', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaCodiceEtico() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaCodiceEtico('status/annullaCodiceEtico', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaBilancio() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaBilancio('status/annullaBilancio', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaAttoCostNonAutonomo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaAttoCostNonAutonomo('status/annullaAttoCostNonAutonomo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', " è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

  annullaStatutoOrgNonAutonomo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .annullaStatutoOrgNonAutonomo('status/annullaStatutoOrgNonAutonomo', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadTable();
        this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', error);
      }
    });
  }

}
