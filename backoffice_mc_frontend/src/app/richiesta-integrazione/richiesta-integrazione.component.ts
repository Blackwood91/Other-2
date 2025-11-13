import { Component, Input, OnInit } from '@angular/core';
import { MediazioneService } from '../mediazione.service';
import { SharedService } from '../shared.service';
import { ActivatedRoute } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import * as moment from 'moment';

@Component({
  selector: 'app-richiesta-integrazione',
  templateUrl: './richiesta-integrazione.component.html',
  styleUrls: [ './richiesta-integrazione.component.css']
})
export class RichiestaIntegrazioneComponent implements OnInit {
  idRichiesta: number = 0;
  richiesta: any = new Array();
  motivazione: any = null;
  fileDownload: any 
  activeInviaRic = false;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private route: ActivatedRoute) { }

  ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
       this.idRichiesta = params['idRichiesta'];
       this.getAllModuloAnnulato();
       this.loadRichiesta();
       this.loadActiveInviaRic();
     })
  }

  loadRichiesta() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .getRichiestaIntegrazione('richiestaInvio/getRichiestaIntegrazione', params)
      .subscribe((res: any) => {
        this.richiesta = res;
      });

  }

  loadActiveInviaRic() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta);

    this.serviceME.activeInviaRic('richiestaInvio/activeInviaRic', params)
      .subscribe({ 
        next: (res: any) => { 
          this.activeInviaRic = res.active
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  getAllModuloAnnulato() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta);

    this.serviceME
    .getAllModuloAnnulato('status/getAllModuloAnnulato', params)
      .subscribe((res: any) => {
        let motivaioneMessage = "I seguenti moduli sono stati annullati:";

        res.forEach(function(moduloAllInfo: any) {
          let extreInfo = "";
          if(moduloAllInfo.idModulo === 68) {
            extreInfo = " situata in " + moduloAllInfo.indirizzo + " " + moduloAllInfo.numeroCivico  
                        + " " + moduloAllInfo.nomeComune + " (" + moduloAllInfo.siglaProvincia + ")";
          }
          else if(moduloAllInfo.idModulo === 69) {
            extreInfo = " riguardante la sede situata in " + moduloAllInfo.indirizzo + " " + moduloAllInfo.numeroCivico  
                        + " " + moduloAllInfo.nomeComune + " (" + moduloAllInfo.siglaProvincia + ")";
          }
          else if(moduloAllInfo.idModulo === 70) {
            extreInfo = " riguardante la sede situata in " + moduloAllInfo.indirizzo + " " + moduloAllInfo.numeroCivico  
                        + " " + moduloAllInfo.nomeComune + " (" + moduloAllInfo.siglaProvincia + ")";
          }
          else if(moduloAllInfo.idModulo === 50) {
            extreInfo = " con il seguente nome del file: " + moduloAllInfo.nomeFile;
          }
          else if(moduloAllInfo.idModulo === 53 || moduloAllInfo.idModulo === 40 ||
                  moduloAllInfo.idModulo === 44 || moduloAllInfo.idModulo === 46 ||
                  moduloAllInfo.idModulo === 55
          ) {
            extreInfo = " del seguente mediatore: " + moduloAllInfo.cognome + " " + moduloAllInfo.nome;
          }
          else if(moduloAllInfo.idModulo === 29 || moduloAllInfo.idModulo === 32 || 
                  moduloAllInfo.idModulo === 36 || moduloAllInfo.idModulo === 37 ||
                  moduloAllInfo.idModulo === 39 ||
                  moduloAllInfo.idModulo === 53 || moduloAllInfo.idModulo === 30) {
            extreInfo = " della seguente anagrafica: " + moduloAllInfo.cognome + " " + moduloAllInfo.nome;
          }
          else if(moduloAllInfo.idModulo === 41 || moduloAllInfo.idModulo === 82) {
            extreInfo = " del seguente mediatore: " + moduloAllInfo.cognome + " " + moduloAllInfo.nome;
          }

          motivaioneMessage = motivaioneMessage + "\n" + moduloAllInfo.descrizioneModulo + extreInfo;
        });

        motivaioneMessage = motivaioneMessage + "\n" + "Per le seguenti motivazioni: \n"

        this.motivazione = motivaioneMessage;
      });

  }

  formatDateComplete(date: string) {
    return (moment(date)).format('DD-MM-YYYY HH:mm')
  }

  openPdfFile() {
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta);

    this.serviceME.downloadFileRI('richiestaInvio/downloadFileRI', params)
      .subscribe({ 
        next: (res: any) => { 
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, '_blank');
        },
        error: (error: any) => {
          if(error)
          this.sharedService.onMessage('error', "Per scaricare il file bisogna prima inviare la richiesta di integrazione");
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


  invia(){
    const params = new HttpParams()
    .set('idRichiesta', this.idRichiesta)
    .set('motivazione', this.motivazione);

    this.serviceME.invioRichiestaIntegrazione('richiestaInvio/invioRichiestaIntegrazione', params)
      .subscribe({ 
        next: (res: any) => { 
          this.getAllModuloAnnulato();
          this.loadRichiesta();
          this.loadActiveInviaRic();
          this.sharedService.onMessage('success', "La richiesta d'integrazione è stata inviata con successo");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

}
