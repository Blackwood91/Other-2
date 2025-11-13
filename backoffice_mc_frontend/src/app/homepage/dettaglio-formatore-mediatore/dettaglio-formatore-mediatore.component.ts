import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';

@Component({
  selector: 'app-dettaglio-formatore-mediatore',
  templateUrl: './dettaglio-formatore-mediatore.component.html',
  styleUrls: ['./dettaglio-formatore-mediatore.component.css']
})
export class DettaglioFormatoreMediatoreComponent implements OnInit {

  @Input()
  page: String = '';
  @Input()
  idAlboFormatori: number = 0;
  @Input()
  idAlboMediatore: number = 0;

  tableResult = new Array();
  totalRows = new Array();

  tipoRicerca: number = 0;

  constructor(private serviceME: MediazioneService) { }

  ngOnInit(): void {
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['idAlboFormatori']) {
      this.loadEf();
    }
    if(changes['idAlboMediatore']) {
      this.loadOdm();
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
      return "Dettagli Organismi di Mediazione per il mediatore selezionato";
    else
      if(this.page == 'form')
        return "Dettaglio Enti di Formazione per il formatore selezionato";
      else
        return 'error'
  }

  decideLoading() {
    if(this.page == 'odm')
      this.loadOdm();
    else
      if(this.page == 'form')
        this.loadEf();
  }


  public loadOdm() {
    const params = new HttpParams()
    .set('idAlboMediatori', this.idAlboMediatore)

    this.serviceME.getMediatorePerAlboById('alboMediatori/getMediatoriByIdAlbo', params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result; 
      }
    });
  }
  

  public loadEf() {
    const params = new HttpParams()
      .set('idAlboFormatori', this.idAlboFormatori)

    this.serviceME.getFormatorePerAlboById('alboFormatori/getFormatoriByIdAlbo', params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result; 
      }
    });
  }

}
