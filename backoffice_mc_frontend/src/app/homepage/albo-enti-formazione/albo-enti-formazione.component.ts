import { HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { environment } from 'src/environments/environment';
import { RegistroElencoPdgComponent } from '../registro-elenco-pdg/registro-elenco-pdg.component';
import { RegistroElencoSediComponent } from '../registro-elenco-sedi/registro-elenco-sedi.component';

@Component({
  selector: 'app-albo-enti-formazione',
  templateUrl: './albo-enti-formazione.component.html',
  styleUrls: ['./albo-enti-formazione.component.css']
})
export class AlboEntiFormazioneComponent implements OnInit {

  @ViewChild(RegistroElencoPdgComponent) registroElencoPdgComponent!: RegistroElencoPdgComponent;
  @ViewChild(RegistroElencoSediComponent) registroElencoSediComponent!: RegistroElencoSediComponent;

  tableResult = new Array();

  numeroRegistro: number = 0;
  numReg: number = 0;
  denominazione: string = "";
  sitoWeb: string = "";
  email: string = "";
  natura: string = "";
  codiceFiscale: number = 0;
  partitaIva: string = "";
  cancellato: string = "";
  dataCancellazione: string = "";

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';

  page: String = 'form';
  tipoPage: String = '';

  totalRows = new Array();

  tipoRicerca: number = 0;

  constructor(private serviceME: MediazioneService) {
   }

  ngOnInit(): void {
    this.onInitLoad()
  }

  public onInitLoad() {

      this.serviceME.getAllEntiFormatoriPerAlbo('alboEntiFormatori/getAllEntiFormatoriPaged', {}).subscribe({
        next: (res: any) => {;      
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
      .getAllEntiFormatoriPerAlbo('alboEntiFormatori/getAllEntiFormatoriPaged', params)
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
    .getAllEntiFormatoriPerAlbo('alboEntiFormatori/getAllEntiFormatoriPaged', params)
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
      .getAllEntiFormatoriPerAlbo('alboEntiFormatori/getAllEntiFormatoriPaged', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('tipoRicerca', this.tipoRicerca);

    this.serviceME.getAllEntiFormatoriPerAlbo("alboEntiFormatori/getAllEntiFormatoriPaged", params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / 10);
      this.totalResult = res.totalResult;
    })
  }

  categorySelection() {
    this.searchTextTable = "";
    //this.tipoDocente = 0;
    this.onInitLoad();
  }

  public getRows() {
    return this.tableResult/*.filter(i => i.idTipoAnagrafica == '4' || i.idTipoAnagrafica == '5' || i.idTipoAnagrafica == '6')*/.length;
  }  

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY');
  }

  selectSedi() {
    this.tipoPage = 'sedi';
  }

  selectPdg() {
    this.tipoPage = 'pdg';
  }

  openModal(selectedNumReg: number) {
    this.numReg = selectedNumReg;
    const buttonActiveModal = document.getElementById("activeModalSaveFile");
    buttonActiveModal!.click();
  }

}
