import { HttpParams } from '@angular/common/http';
import { Inject, Injectable, Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { environment } from 'src/environments/environment';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-albo-formatori',
  templateUrl: './albo-formatori.component.html',
  styleUrls: ['./albo-formatori.component.css']
})
export class AlboFormatoriComponent implements OnInit {

  tableResult = new Array();

  idAlboFormatori: number = 0;
  cognome: string = "";
  nome: string = "";
  codiceFiscale: string = "";
  dataNascita: string = "";
  numeroEnti: string = "";
  idTipoFormatore: number = 0;

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';

  totalRows = new Array();

  page: String = 'form';
  tipoPage: String = '';

  tipoRicerca = 0;

  constructor(private serviceME: MediazioneService,
              @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

  ngOnInit(): void {
    this.onInitLoad();
    // this.serviceME.getAllFormatoriPerAlbo(this.env.pathApi + 'api/richiesta/getAllAnagrafica', {}).subscribe({
    //   next: (res: any) => {
    //     this.totalRows = res;
    //   }
    // });
  }

  public onInitLoad() {
    // const params = new HttpParams()
    //   .set('searchText', this.searchTextTable)
    //   .set('indexPage', this.indexPage - 1);

    this.serviceME.getAllFormatoriPerAlbo('alboFormatori/getAllFormatoriPaged', {}).subscribe({
      next: (res: any) => {;
        
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
      .getAllFormatoriPerAlbo('alboFormatori/getAllFormatoriPaged', params)
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
    .getAllFormatoriPerAlbo('alboFormatori/getAllFormatoriPaged', params)
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
      .getAllFormatoriPerAlbo('alboFormatori/getAllFormatoriPaged', params)
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
      .set('tipoForm', this.idTipoFormatore);;

    this.serviceME.getAllFormatoriPerAlbo("alboFormatori/getAllFormatoriPaged", params).subscribe((res: any) => {
      this.tableResult = res.result;
      this.totalPage = Math.ceil(res.totalResult / 10);
      this.totalResult = res.totalResult;
    })
  }

  filterByFormatori(tipoMed: number) {
    this.searchTextTable = " ";
    return this.getMediatori().filter(i => i.idTipoDocente == tipoMed);
  }

  categorySelection() {
    this.searchTextTable = "";
    this.idTipoFormatore = 0;
    this.onInitLoad();
  }


  public getMediatori() {
    return this.tableResult.filter(i => i.idTipoAnagrafica == '4' || i.idTipoAnagrafica == '5' || i.idTipoAnagrafica == '6');
  }

  public getRows() {
    return this.tableResult.length;
  }  

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  public getTipoFormatore(id: number) {
    switch(id) {
      case 1:
        return "Teorico";
      case 2:
        return "Pratico"
      case 3:
        return "Teorico e Pratico"
      default:
        return "errore"
    }
  }

  selectSedi() {
    //this.tipoPage = 'sedi';
  }

  openModal(selectedId: number) {
    this.idAlboFormatori = selectedId;
    const buttonActiveModal = document.getElementById("activeModalFormatore");
    buttonActiveModal!.click();
  }


}
