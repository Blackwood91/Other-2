import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MediazioneService } from '../mediazione.service';
import { SharedService } from '../shared.service';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';

@Component({
  selector: 'app-lettura-pdg',
  templateUrl: './lettura-pdg.component.html',
  styleUrls: ['../../style/bootstrap-italia/css/bootstrap-italia.min.css',
  '../../style/bootstrap-italia/assets/docs.min.css', './lettura-pdg.component.css']
})
export class LetturaPdgComponent implements OnInit {

  idRichiesta: number = 0;
  richiesta: any = new Array();
  motivazione: any = null;
  activeInviaRic = false;

  idTipologia: number = 0;
  tipologie: any = new Array();
  file: any = null; 
  //Var Tabella
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.loadTable();
      this.loadTipologiePdg();
    })
  }

  loadTipologiePdg() {
    this.serviceME
    .getAllTipologiaPdg('tipoPdg/getAll')
      .subscribe((res: any) => {
        this.tipologie = res;
      });
  }

  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllEmissionePdgOdmForTable('emissionePdgOdm/getAllEmissionePdgOdmForTable', params)
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
      .getAllEmissionePdgOdmForTable('emissionePdgOdm/getAllEmissionePdgOdmForTable', params)
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
      .getAllEmissionePdgOdmForTable('emissionePdgOdm/getAllEmissionePdgOdmForTable', params)
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
      .getAllEmissionePdgOdmForTable('emissionePdgOdm/getAllEmissionePdgOdmForTable', params)
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
      .getAllEmissionePdgOdmForTable('emissionePdgOdm/getAllEmissionePdgOdmForTable', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  formatDateComplete(date: string) {
    return (moment(date)).format('DD-MM-YYYY, HH:mm')
  }

  openPdfFile(idEmissionePdg: number) {
    const params = new HttpParams()
    .set('idEmissionePdg', idEmissionePdg);

    this.serviceME.getFilePdg('emissionePdgOdm/getFilePdg', params)
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

  onFilePdg(event: any) {
    this.file = event.target.files[0];
  }

}
