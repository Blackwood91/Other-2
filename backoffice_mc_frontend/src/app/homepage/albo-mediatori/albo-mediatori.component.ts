import { HttpParams } from '@angular/common/http';
import {Inject, Injectable, Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { environment } from 'src/environments/environment';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-albo-mediatori',
  templateUrl: './albo-mediatori.component.html',
  styleUrls: ['./albo-mediatori.component.css']
})
export class AlboMediatoriComponent implements OnInit {

  tableResult = new Array();

  idAnagrafica: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  comuneSceltoNascita: string = "";
  comuneSceltoResidenza: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  statoResidenza: string = "";
  codiceFiscale: string = "";
  isEsteroNascita: string = "false";
  isEsteroResidenza: string = "false";
  idTipoAnagrafica: number = 0;
  indirizzoEmail: string = "";
  medTelefono: string = "";
  medCellulare: string = "";
  medFax: string = "";

  idAlboMediatore = 0;

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';

  totalRows = new Array();

  page: String = 'odm';
  tipoPage: String = '';

  tipoRicerca: number = 0;
  tipoMed: number = 0;

  constructor(private serviceME: MediazioneService,
              @Inject(APP_ENVIRONMENT) private env:AppEnvironment ) { }

  ngOnInit(): void {
    this.onInitLoad();
    this.serviceME.getAllMediatoriPerAlbo('alboMediatori/getAllMediatori', {}).subscribe({
      next: (res: any) => {
        this.totalRows = res;
      }
    });
  }

  public onInitLoad() {
    // const params = new HttpParams()
    //   .set('searchText', this.searchTextTable)
    //   .set('indexPage', this.indexPage - 1);

    this.serviceME.getAllMediatoriPerAlbo('alboMediatori/getAllMediatoriPaged', {}).subscribe({
      next: (res: any) => {
        // this.tableResult = res;
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      }
    });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceME
      .getAllMediatoriPerAlbo('alboMediatori/getAllMediatoriPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceME
    .getAllMediatoriPerAlbo('alboMediatori/getAllMediatoriPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceME
      .getAllMediatoriPerAlbo('alboMediatori/getAllMediatoriPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('tipoRicerca', this.tipoRicerca)
      .set('tipoMed', this.tipoMed);

    this.serviceME.getAllMediatoriPerAlbo('alboMediatori/getAllMediatoriPaged', params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / 10);
      this.totalResult = res.totalResult;
    })

  }

  filterByMediatori(tipoMed: number) {
    this.searchTextTable = " ";
    return this.getMediatori().filter(i => i.idTipoAnagrafica == tipoMed);
  }

  categorySelection() {
    this.searchTextTable = "";
    this.tipoMed = 0;
    this.onInitLoad();
  }

  public getMediatori() {
    return this.tableResult.filter(i => i.idTipoAnagrafica == '4' || i.idTipoAnagrafica == '5' || i.idTipoAnagrafica == '6');
  }

  public getRows() {
    return this.totalRows.filter(i => i.idTipoAnagrafica == '4' || i.idTipoAnagrafica == '5' || i.idTipoAnagrafica == '6').length;
  }  
  
  public getTipoMediatore(id: number) {
    switch(id) {
      case 4:
        return "Mediatore generico";
      case 5:
        return "Mediatore esperto in materia internazionale"
      case 6:
        return "Mediatore esperto in materia di consumo"
      default:
        return "errore"
    }
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  selectSedi() {
    //this.tipoPage = 'sedi';
  }

  openModal(selectedId: number) {
    this.idAlboMediatore = selectedId;
    const buttonActiveModal = document.getElementById("activeModalMediatore");
    buttonActiveModal!.click();
  }

}
