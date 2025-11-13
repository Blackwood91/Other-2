import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';

@Component({
  selector: 'app-registro-elenco-pdg',
  templateUrl: './registro-elenco-pdg.component.html',
  styleUrls: ['./registro-elenco-pdg.component.css']
})
export class RegistroElencoPdgComponent implements OnInit {

  @Input()
  page: String = '';
  @Input()
  numReg: number = 0;
  @Input()
  rom: number = 0;

  tableResult = new Array();


  constructor(private serviceME: MediazioneService) { }

  ngOnInit(): void {    
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['numReg']) {
      this.loadPdgEf();
    }
    if(changes['rom']) {
      this.loadPdgOdm();
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
      return "Dettaglio PDG dell'organismo di mediazione";
    else
      if(this.page == 'form')
        return "Dettaglio PDG dell'Ente di Formazione";
      else
        return 'error'
  }

  decideLoading() {
    if(this.page == 'odm')
      this.loadPdgOdm();
    else
      if(this.page == 'form')
        this.loadPdgEf();
  }

  public loadPdgOdm() {
    const params = new HttpParams()
      .set('rom', this.rom)
    this.serviceME.getAllOdmPdgPerAlboByRom('alboOdmPdg/getOdmPdgByRom', params).subscribe({
      next: (res: any) => {
        this.tableResult = res;
      }
    });
  }

  public loadPdgEf() {
    const params = new HttpParams()
      .set('numReg', this.numReg)

    this.serviceME.getAllEfPdgPerAlboByNumReg('alboEfPdg/getEfPdgByNumReg', params).subscribe({
      next: (res: any) => {
        this.tableResult = res;
      }
    });
  }

}
