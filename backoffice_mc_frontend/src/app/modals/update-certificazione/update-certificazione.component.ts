import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-update-certificazione',
  templateUrl: './update-certificazione.component.html',
  styleUrls: ['./update-certificazione.component.css']
})
export class UpdateCertificazioneComponent implements OnInit {

//idStatoModRicFigl

  @Input()
  component!: string;
  @Output() sendFileComponent = new EventEmitter<any>();
  idRiferimento: number = 0;
  idCertificazioneLingua: number = 0;
  idStatoModuloFiglio: number = 0;
  idRichiesta: number = 0;
  selectedFilePDF: any = null;
  titolo: string = "";

  //campi certificazione
  isModificaCert: boolean = false;
  enteCertificatore: string = '';
  dataCertificazione: string = '';
  validEnteCertificazione: boolean = false;
  validDataCertificazione: boolean = false;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private sharedService: SharedService) { }

  ngOnInit(): void {    
  }

  openModal(titolo: string, idStatoModuloFiglio: number, idRichiesta: number, idCertificazioneLingua: number, dataCertificazione: string, enteCertificatore: string) {
      this.titolo = titolo;
      this.idCertificazioneLingua = idCertificazioneLingua;
      this.idStatoModuloFiglio = idStatoModuloFiglio;
      this.idRichiesta = idRichiesta;
      this.dataCertificazione = !!dataCertificazione ? (moment(dataCertificazione)).format('YYYY-MM-DD') : "";
      this.enteCertificatore = enteCertificatore;
      const buttonActiveModal = document.getElementById("activeModalUpdateCert");
      buttonActiveModal!.click();
      console.log(this.enteCertificatore + this.dataCertificazione + ' 3');
  }

  onFilePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
  }

  closeModal() {
    this.sendFileComponent.emit('OPERAZIONE_EFFETTUATA');
    const buttonActiveModal = document.getElementById("closeModal");
    buttonActiveModal!.click();
  }

  public formatDate(date: string) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  sendCertificazione() {
    const fileAllegatoAppendiciDto: {
      id: number, idCertificazioneLingua: number, idRichiesta: number, dataCertificazione: string, enteCertificatore: string, 
      file: any/*, idRiferimento: number*/, nomeFile: string
    } = {
      id: this.idStatoModuloFiglio , idCertificazioneLingua: this.idCertificazioneLingua, idRichiesta: this.idRichiesta, dataCertificazione: this.dataCertificazione,
      enteCertificatore: this.enteCertificatore, file: null/*, idRiferimento: 0*/, nomeFile: this.selectedFilePDF.name
    }

    let formData: FormData = new FormData();
    formData.append('fileAllegatoAppendiciDto', new Blob([JSON.stringify(fileAllegatoAppendiciDto)], { type: 'application/json' }));

    if(this.selectedFilePDF != null)
      formData.append('filePdf', this.selectedFilePDF);

    this.serviceME.saveFileAttoCostitutivoOdm('statoModuli/updateFileAppendici', formData)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', "L'aggiornamento è avvenuto con successo!");

          this.closeModal();

        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }
}

  // checkEnteCertificazione() {
  //   if(this.enteCertificatore.length > 0) {
  //     this.validEnteCertificazione = true;
  //     return true;
  //   }   
  //   else {
  //     this.validEnteCertificazione = false;
  //     return false;
  //   }   
  // }

  // checkDataCertificazione() {
  //   if(this.dataCertificazione != undefined && this.dataCertificazione != null && this.dataCertificazione != '') {
  //     this.validDataCertificazione = true;
  //     return true;
  //   }
  //   else {
  //     this.validDataCertificazione = false;
  //     return false;
  //   }
  // }


