import { Component, Input, OnInit } from '@angular/core';
import { MediazioneService } from 'src/app/mediazione.service';
import { environment } from 'src/environments/environment';

import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
import { AccessibilitaService } from 'src/accessibilita.service';
import { HttpParams } from '@angular/common/http';
import { SharedService } from 'src/app/shared.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-domanda-iscrizione',
  templateUrl: './domanda-iscrizione.component.html',
  styleUrls: ['./domanda-iscrizione.component.css']
})
export class DomandaIscrizioneComponent implements OnInit {
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  // Dati domanda iscrizione
  nome: string = '';
  cognome: string = '';
  ragioneSociale: string = '';
  denominazioneOdm: string = '';

  pdfDataUrl: SafeResourceUrl | null = null;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router,
    private accessibilita: AccessibilitaService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];

      const paramsRequest = new HttpParams()
        .set('idRichiesta', this.idRichiesta);

      this.serviceME
        .getRichiestaDomIscr('richiesta/getRichiestaDomIscr', paramsRequest)
        .subscribe({
          next: (res: any) => {
            this.nome = res.nome;
            this.cognome = res.cognome;
            this.ragioneSociale = this.accessibilita.getCookieValue("societaSelezionata");
            this.denominazioneOdm = res.denominazioneOdm;

            this.moduloIsConvalidato();
          },
          error: (error: any) => { }
        });
    });

  }

  activeModifica() {
    this.isModifica = true;
  }

  anteprimaPdf() {
    this.serviceME.getFileDomandaIscrizione('pdf/getFileModuloDomanda', this.idRichiesta)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, '_blank');
        },
        error: (error: any) => { }
      })
  }


  // Per formattare byte in file pdf
  convertiStringaBlobAFile(dati: string): File {
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], { type: 'application/pdf' });

    return new File([blobFile], 'file');
  }

  moduloIsConvalidato() {
    const params = new HttpParams()
      .set('idModulo', 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getModuloIsConvalidato('status/getModuloIsConvalidato', params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
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
      .validazioneDomandaIscrizione('status/validaDomandaDiIscrizione', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
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
      .annullaDomandaIscrizione('status/annullaDomandaDiIscrizione', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage('success', "l'annullamento è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }


}
