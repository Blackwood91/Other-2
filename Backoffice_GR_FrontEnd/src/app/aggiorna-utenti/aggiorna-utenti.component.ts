import { Component, HostBinding, OnInit, Input, Output, SimpleChanges, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { GiustiziaService } from '../giustizia.service';
import { Router } from "@angular/router";
import * as moment from 'moment'; //SERVE PER FORMATTARE LA DATA
import { SharedService } from '../shared.service';


@Component({
  selector: 'app-aggiorna-utenti',
  templateUrl: './aggiorna-utenti.component.html',
  styleUrls: ['./aggiorna-utenti.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})

export class AggiornaUtentiComponent implements OnInit {

  /*handleCheckboxChange(event: any) {
    // Se la casella di controllo è selezionata, imposta il valore su 1
    // Se è deselezionata, imposta il valore su 0
    this.form.value.isAbilitato.setValue(event.target.checked ? 1 : 0);
  } */

  message = "Si prega di inserire tutti i campi obbligatori contrassegnati dall'asterisco";
  @Input()
  utenti!: object;
  
  //Serve per poter inviare il parametro al component padre
  @Output() onUtenti = new EventEmitter<string>()

  @HostBinding('data-bs-dismiss')
  dismiss: string = '';
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  selectField: FormControl = new FormControl();
  form: FormGroup;

  constructor(public fb: FormBuilder, private serviceGR: GiustiziaService, private sharedService: SharedService) {
    
    this.form = fb.group({
      utenteId: "",
      nomeUtente: "",
      cognomeUtente: "",
      codiceFiscaleUtente: "",
      isAbilitato: "",
      enteAppartenenza: "",
      dataLogin: "", //utentiStructure.dataLogin
      dataLogout: "", //utentiStructure.dataLogout,
      idRuoloUtente: "",
    })

  }
   
  ngOnInit(): void { }

  handleCheckboxChange(event: any) {
    const isAbilitatoControl = this.form.get('isAbilitato');
    
    if (isAbilitatoControl) {
      // Imposta il valore come intero (0 o 1)
      isAbilitatoControl.setValue(event.target.checked ? 1 : 0);
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    let utentiStructure: any = this.utenti;

    this.form.patchValue({
      utenteId: utentiStructure.utenteId,
      nomeUtente: utentiStructure.nomeUtente,
      cognomeUtente: utentiStructure.cognomeUtente,
      codiceFiscaleUtente: utentiStructure.codiceFiscaleUtente,
      isAbilitato: utentiStructure.isAbilitato,
      enteAppartenenza: utentiStructure.enteAppartenenza,
      dataLogin: utentiStructure.dataLogin, //utentiStructure.dataLogin
      dataLogout: utentiStructure.dataLogout, //utentiStructure.dataLogout,
      idRuoloUtente: utentiStructure.idRuoloUtente,
    });

  }
  
  sendUtenza() {
    let utentiStructure: any = this.utenti;
    //Impostare successivamente casi di errore con messaggio
    this.dismiss = 'modal';
    var closeModal = document.getElementById('closeModal');

    if (this.form.valid) {
      closeModal!.click();
    } else {
      alert(this.message);
    }
	// this.sendpk.emit(this.form.value.pkElezione);

    this.serviceGR.UpdateUtenti("utente-abilitato/UpdateUtenti", this.form.value).subscribe({
      next: (res: any) => { 

        this.sharedService.onMessage('success', res.message);
        this.utenzaAggiornata();
      },
      error: (error: any) => { 
        this.sharedService.onMessage('error', error);
      },
      complete: () => { }
    }); 
  }

  // Metodo per emettere evento
  utenzaAggiornata() {
    this.onUtenti.emit();
  }
}
