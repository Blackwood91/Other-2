import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MediazioneService } from 'src/app/mediazione.service';
import * as moment from 'moment';
import { SharedService } from 'src/app/shared.service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-dichiarazione-polizza-assicurativa',
  templateUrl: './dichiarazione-polizza-assicurativa.component.html',
  styleUrls: ['./dichiarazione-polizza-assicurativa.component.css']
})
export class DichiarazionePolizzaAssicurativaComponent implements OnInit {
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  compagniaAssicuratrice: string = "";
  massimaleAssicurato: string = "";
  dataStipulaPoliz: string = "";
  scadenzaPoliza: string = "";
  checkPrVisioneRinP: boolean = false;
  checkValidDate = true;
  checkValidMassimale = true;
  checkValidCompagniaAssicuratrice = true;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.serviceME.getPolizzaAssicurativaDOMODMP('richiesta/getPolizzaAssicurativa', this.idRichiesta)
        .subscribe({
          next: (res: any) => {
            if (res === null) {
              // Se non esiste ancora una polizza verrà messa di default la data attuale piu un anno
              // Data attuale
              this.dataStipulaPoliz = (moment(new Date().toISOString())).format('YYYY-MM-DD');
              // Data attuale più 365 giorni
              const dataFutura = new Date();
              dataFutura.setDate(dataFutura.getDate() + 365);
              this.scadenzaPoliza = (moment(dataFutura.toISOString())).format('YYYY-MM-DD');
            }
            else {
              this.compagniaAssicuratrice = res.compagniaAss;              
              this.massimaleAssicurato = this.formatNumberDecimalString(res.massimaleAssic.toString());
              this.dataStipulaPoliz = (moment(res.dataStipulaPoliz)).format('YYYY-MM-DD');
              this.scadenzaPoliza = (moment(res.scadenzaPoliza)).format('YYYY-MM-DD');
                
              // SERVE PER LA PRESA VISIONE DELLA NORMATIVA, SE GIA TORNERANNO DEI CAMPI SALVATI DA DB,
              // VERRA INTERPRETATO COME SE GIA' E' STATA FATTA LA PRESA VISIONE
              this.checkPrVisioneRinP = true;
            }

            this.loadIsConvalidato();
          },
          error: (res: any) => {

          }
        })


    }); 
  }

  loadIsConvalidato() { 
    const params = new HttpParams()
      .set('idModulo', 58)
      .set('idRichiesta', this.idRichiesta)

      this.serviceME.getStatusPolizza('status/getModuloIsConvalidato', params)
        .subscribe({
          next: (res: any) => {
            this.isConvalidato = res.isConvalidato;
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
  }

  checkCompagniaAssicurativa() {
    if (this.compagniaAssicuratrice.length > 0) {
      this.checkValidCompagniaAssicuratrice = true;
      return true;
    }
    else {
      this.checkValidCompagniaAssicuratrice = false;
      return false;
    }
  }

  checkDate() {
    // Converte le stringhe in oggetti Date
    const dataDa = new Date(this.dataStipulaPoliz);
    const dateA = new Date(this.scadenzaPoliza);

    // Converti le date in millisecondi e calcola la differenza
    const differenzaInMillisecondi = dataDa.getTime() - dateA.getTime();

    // Converti la differenza in giorni e restituisci il valore assoluto
    const differenzaGiorni = (Math.abs(differenzaInMillisecondi / (1000 * 60 * 60 * 24)));

    const giorniAnnoDatafine = 365 //this.isAnnoBisestile(dateA.getFullYear())
    if (differenzaGiorni >= giorniAnnoDatafine) {
      this.checkValidDate = true;
    }
    else {
      this.checkValidDate = false;
    }
  }

  /* isAnnoBisestile(anno: number): number {
     if ((anno % 4 === 0 && anno % 100 !== 0) || anno % 400 === 0) {
       return 366; // L'anno è bisestile
     } else {
       return 365; // L'anno non è bisestile
     }
   }*/

  checkMassimaleAss() {
    const cifra = this.massimaleAssicurato.replace(/\./g, "").replace(/,/g, ".");
    let cifraFormat = parseFloat(cifra);
    // IL MASSIMALE DOVRA' ESSERE DI ALMENO UN 1.000.000,00
    if (cifraFormat >= 1000000) {
      this.checkValidMassimale = true;
    }
    else {
      this.checkValidMassimale = false;
    }
  }

  // FUNZIONE PER LEGGERE VALORE PRECEDENTEMENTE SALVATO 
  formatNumberDecimalString(massimaleAssicurato: string): string {
    return massimaleAssicurato.replace(/\./g, ",");
  }

  formatNumberDecimal(): number {
    const cifra = this.massimaleAssicurato.replace(/\./g, "").replace(/,/g, ".");
    return parseFloat(cifra);
  }

  activeModifica() {
    this.isModifica = true;
  }

  openPdfFile() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME.getAnteprimaFilePolizzaAss('pdf/getAnteprimaFilePolizzaAss', params)
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

  sendDichiarazionePolizzaAssicurativa() {
    if (this.dataStipulaPoliz != "" && this.scadenzaPoliza != "" && this.massimaleAssicurato != "" && this.compagniaAssicuratrice.length > 0) {
      if (this.checkValidDate === false || this.checkValidMassimale === false) {
        this.sharedService.onMessage('error', "Inserire tutti i campi in modo valido per proseguire");
        return;
      }
    }
    else {
      this.sharedService.onMessage('error', "Inserire tutti i campi in modo valido per proseguire");
      return;
    }

    const polizzaAssicurativaDto: {
      idRichiesta: number, compagniaAssicuratrice: string, massimaleAssicurato: number, dataStipulaPoliz: string,
      scadenzaPoliza: string
    } = {
      idRichiesta: this.idRichiesta, compagniaAssicuratrice: this.compagniaAssicuratrice, massimaleAssicurato: this.formatNumberDecimal(),
      dataStipulaPoliz: this.dataStipulaPoliz, scadenzaPoliza: this.scadenzaPoliza
    }

    this.serviceME.savePolizzaAssicurativa('richiesta/saveDichiarazionePolizzaAss', polizzaAssicurativaDto)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadIsConvalidato();
          this.sharedService.onUpdateMenu();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
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
      .validazioneDichiarazionePolizzaAss('status/validazioneDichiarazionePolizzaAss', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
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
      .annullaElencoRapLegAndRespOrg('status/annullaDichiarazionePolizzaAss', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

}
