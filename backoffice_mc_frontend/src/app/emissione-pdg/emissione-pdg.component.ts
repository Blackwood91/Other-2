import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MediazioneService } from '../mediazione.service';
import { SharedService } from '../shared.service';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';

@Component({
  selector: 'app-emissione-pdg',
  templateUrl: './emissione-pdg.component.html',
  styleUrls: ['../../style/bootstrap-italia/css/bootstrap-italia.min.css',
              '../../style/bootstrap-italia/assets/docs.min.css', './emissione-pdg.component.css']
})
export class EmissionePdgComponent implements OnInit {
  idRichiesta: number = 0;
  richiesta: any = new Array();
  motivazione: any = null;
  fileDownload: any = null;

  idTipologia: number = 0;
  tipologie: any = new Array();
  file: any = null; 

  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
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

  invia(){
    // CONTROLLI DATI INSERITI
    if(this.idTipologia === 0 || this.file === null) {
      this.sharedService.onMessage('error', "Per proseguire è necessario inserire il file e la tipologia del PDG");
      return;
    }

    let emissionePdg: {idRichiesta: number, idTipoPdg: number} = {
      idRichiesta: this.idRichiesta, idTipoPdg: this.idTipologia
    }

    let formData: FormData = new FormData();
    formData.append('emissionePdgOdmDto', new Blob([JSON.stringify(emissionePdg)], { type: 'application/json' }));
    formData.append('filePdf', this.file);

    this.serviceME.emissionePdgOdm('emissionePdgOdm/emissionePdg', formData)
      .subscribe({ 
        next: (res: any) => {
          this.sharedService.onMessage('success', "L'emissione del pdg è andato a buon fine");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
    
  }

}
 