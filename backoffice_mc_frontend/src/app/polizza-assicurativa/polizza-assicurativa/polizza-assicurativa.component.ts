import { HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { SaveFileComponent } from 'src/app/modals/save-file/save-file.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-polizza-assicurativa',
  templateUrl: './polizza-assicurativa.component.html',
  styleUrls: ['./polizza-assicurativa.component.css']
})
export class PolizzaAssicurativaComponent implements OnInit {
  idRichiesta: number = 0;
  selectedFilePDF!: any;
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private sharedService: SharedService) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.loadTable();
      this.loadPolizzaIsConvalidato();

    })
  }

  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm('statoModuli/getAllPolizzaAssicurativa', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('statoModuli/getAllPolizzaAssicurativa', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('statoModuli/getAllPolizzaAssicurativa', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta('statoModuli/getAllPolizzaAssicurativa', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2)
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getAllSediOperativeByRichiesta('statoModuli/getAllPolizzaAssicurativa', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  loadPolizzaIsConvalidato() {
    const params = new HttpParams()
      .set('idModulo', 59)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm('status/getModuloIsConvalidato', params)
      .subscribe((res: any) => {
        this.isConvalidato = res.isConvalidato;
      });
  }


  openModalLoadFile(id: number /* parametro per capire da chi proviene la richiesta*/) {
    if (this.isModifica) {
      this.saveFile.openModal("Aggiornamento nuovo file", id);
    }
  }

  getFileModalChildren(params: any /* parametro per capire da chi proviene la richiesta*/) {
    // LA STRUTTURA DEI PERCORSI E DEL CARICAMENTO DEI FILE SARA CENTRALIZZATO DA UN UNICO METODO CHE SI OCCUPERA' DI CIO' 
    this.updateFileModulo(params.selectedFilePDF, params.id)
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
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

  send() {
    const fileAllegatoDto: {
      id: number, idRichiesta: number, file: any, nomeFile: string, idRiferimento: number
    } = {
      id: 0, idRichiesta: this.idRichiesta, file: null, nomeFile: this.selectedFilePDF.name, idRiferimento: 0
    }

    let formData: FormData = new FormData();
    formData.append('fileAllegatoDto', new Blob([JSON.stringify(fileAllegatoDto)], { type: 'application/json' }));
    formData.append('filePdf', this.selectedFilePDF);

    this.serviceME.saveFileAttoCostitutivoOdm('statoModuli/saveFilePolizzaAssicurativa', formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTable();
          this.loadPolizzaIsConvalidato();
          this.sharedService.onUpdateMenu();
          this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })

  }

  convalidazione() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validazionePolizzaAssicurativa('status/validazionePolizzaAssicurativa', params)
      .subscribe({
        next: (res: any) => {
          this.loadPolizzaIsConvalidato();
          this.sharedService.onUpdateMenu();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
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

    this.serviceME.saveFileAttoCostitutivoOdm('statoModuli/updateFilePolizza', formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTable();
          this.loadPolizzaIsConvalidato();
          this.sharedService.onUpdateMenu();
          this.sharedService.onMessage('success', "L'aggiornamento è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validazionePolizza('status/validazionePolizzaAssicurativa', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadTable();
          this.loadPolizzaIsConvalidato();
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
      .annullaPolizza('status/annullaPolizzaAssicurativa', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadTable();
          this.loadPolizzaIsConvalidato();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }


}
