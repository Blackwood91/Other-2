import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { MediazioneService } from '../../mediazione.service';
import { MessageComponent } from '../../principal-components/message/message.component';
import { SharedService } from '../../shared.service';

@Component({
  selector: 'app-aggiornamento-societa',
  templateUrl: './aggiornamento-societa.component.html',
  styleUrls: ['./aggiornamento-societa.component.css', '../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css',
  ],
})
export class AggiornamentoSocietaComponent implements OnInit {
  @Output() eventSocieta: EventEmitter<any> = new EventEmitter();
  idSocieta: number = 0; 
  ragioneSociale: string = ""; 
  partitaIva: string = "";
  codiceFiscaleSocieta: string = "";
  titolo: string = 'Nuova Società';
  selectField: FormControl = new FormControl();
  attivo: boolean = false;

  constructor(public fb: FormBuilder, private serviceME: MediazioneService, private sharedService: SharedService) {

  }

  ngOnInit(): void {
  }

  openModal(idSocieta: number, ragioneSociale: string, partitaIva: string, codiceFiscaleSocieta: string) {
        this.idSocieta = idSocieta; 
        this.ragioneSociale = ragioneSociale; 
        this.partitaIva = partitaIva;
        this.codiceFiscaleSocieta = codiceFiscaleSocieta;

        if(idSocieta != 0 && idSocieta != null) {
          this.titolo = "Modifica Società";
        }
        else {
          this.titolo = "Inserimento Società";
        }

        var buttonActiveModal = document.getElementById('activeModalUpdateSocieta');
        // Esegui il click sul bottone nascosto "activeModalUpdateSocieta"
        buttonActiveModal!.click();
  }

  closeModal() {
    const closeModal = document.getElementById('closeModal');
    closeModal!.click(); 
    this.eventSocieta.emit("OPERIAZIONE_ESEGUITA");
  }

  sendSocieta() {
    let form: {
      id: number, ragioneSociale: string, partitaIva: string, codiceFiscaleSocieta: string
    } = {
      id: this.idSocieta, ragioneSociale: this.ragioneSociale, partitaIva: this.partitaIva,
      codiceFiscaleSocieta: this.codiceFiscaleSocieta
    }

    if((this.ragioneSociale != null && this.ragioneSociale != "") === false) {
        this.sharedService.onMessage('error', "Il campo ragione sociale è obbligatorio per il salvataggio"); 
        return;
    } else if (!this.partitaIva && !this.codiceFiscaleSocieta){
      this.sharedService.onMessage('error', "Inserire partita Iva o Codice Fiscale della Società per proseguire con" + 
      "il salvataggio");
      return;
    }

    if(this.idSocieta != 0) {
      this.serviceME.updateSocieta('societa/update', form)
      .subscribe({
        next: (res: any) => { 
          this.sharedService.onMessage('success', "La modifica è avvenuta con successo!"); 
          this.closeModal()
        },
        error: (error: any) => { 
          this.sharedService.onMessage('error', error); 
        }
      });
    }
    else {
      this.serviceME.insertSocieta('societa/insert', form)
      .subscribe({
        next: (res: any) => { 
          this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!"); 
          this.closeModal()
        },
        error: (error: any) => { 
          this.sharedService.onMessage('error', error); 
        }
      });
    }

  }

}
