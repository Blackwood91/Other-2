import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';

@Component({
  selector: 'app-registro-elenco-sedi',
  templateUrl: './registro-elenco-sedi.component.html',
  styleUrls: ['./registro-elenco-sedi.component.css']
})
export class RegistroElencoSediComponent implements OnInit {
  
  @Input()
  page: String = '';
  @Input()
  numReg: number = 0;
  @Input()
  rom: number = 0;

  tableResult = new Array();

  totalRows = new Array();

  tipoRicerca: number = 0;

  constructor(private serviceME: MediazioneService) { }

  ngOnInit(): void {
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['numReg']) {
      this.loadSediEf();
    }
    if(changes['rom']) {
      this.loadSediOdm();
    }
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  decideTitle() {
    if(this.page == 'odm')
      return "Dettaglio sedi dell'organismo di mediazione";
    else
      if(this.page == 'form')
        return "Dettaglio sedi dell'Ente di Formazione";
      else
        return 'error'
  }

  decideLoading() {
    if(this.page == 'odm')
      this.loadSediOdm();
    else
      if(this.page == 'form')
        this.loadSediEf();
  }


  public loadSediOdm() {
    const params = new HttpParams()
    .set('rom', this.rom)

    this.serviceME.getAllOdmSediPerAlboByRom('alboOdmSedi/getOdmSediByRom', params).subscribe({
      next: (res: any) => {
        this.tableResult = res;
      }
    });
  }

  public loadSediEf() {
    const params = new HttpParams()
      .set('numReg', this.numReg)

    this.serviceME.getAllEfSediPerAlboByNumReg('alboEfSedi/getEfSediByNumReg', params).subscribe({
      next: (res: any) => {
        this.tableResult = res;
      }
    });
  }

}
