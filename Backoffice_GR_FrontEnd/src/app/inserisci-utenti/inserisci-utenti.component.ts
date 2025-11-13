import { Component, HostBinding, Input, OnInit, SimpleChanges, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { delay } from 'rxjs';
import { Location } from '@angular/common';
import { GiustiziaService } from '../giustizia.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router'
import { SharedService } from '../shared.service';
import * as moment from 'moment'; //SERVE PER FORMATTARE LA DATA
import {Validators} from '@angular/forms';
import {ReactiveFormsModule} from "@angular/forms"; 
import { AbstractControl } from '@angular/forms';
import { HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-inserisci-utenti',
  templateUrl: './inserisci-utenti.component.html',
  styleUrls: ['./inserisci-utenti.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class InserisciUtentiComponent implements OnInit {
  message = "Si prega di inserire tutti i campi obbligatori!";

  @Input()
  utenti!: object;
  //Serve per poter inviare il parametro al component padre
  @Output() onElezione = new EventEmitter<string>()

  @HostBinding('data-bs-dismiss')
  dismiss: string = '';

  selectField: FormControl = new FormControl();
  form: FormGroup;
  dataAttuale = new Date();
  messageFromServer: string = ''; 
  searchText: string = '';

  constructor(public fb: FormBuilder, private serviceGR: GiustiziaService, private route: ActivatedRoute, private _location: Location, private router: Router, private sharedService: SharedService) {

    this.form = fb.group({
      utenteId: "",
      nomeUtente: ['', Validators.required],//["", [Validators.required, Validators.maxLength(30)]],
      cognomeUtente: ['', Validators.required],
      codiceFiscaleUtente: ['', Validators.required],
      enteAppartenenza:['', Validators.required],
      isAbilitato: ['', Validators.required],
      dataLogin: (moment(this.dataAttuale)).format('YYYY-MM-DD'),
      dataLogout: (moment(this.dataAttuale)).format('YYYY-MM-DD'),
      idRuoloUtente: ['', Validators.required],
    })

    //per validazione codice fiscale
    const codiceFiscaleControl = this.form.get('codiceFiscaleUtente');
    if (codiceFiscaleControl) { // Controlla se il controllo esiste
      codiceFiscaleControl.setValidators([
        Validators.required,
        (control) => {
          const codiceFiscale = control.value;
          if (!codiceFiscale) return null;

          const codiceFiscaleUpperCase = codiceFiscale.toUpperCase();
          const codiceFiscaleRegex = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
          if (!codiceFiscaleRegex.test(codiceFiscaleUpperCase)) {
            return { codiceFiscaleInvalido: true };
          }
    
          return null;
        }
      ]);
    }
    

  }

  

  // serve per mandare is abilitato in int all'api invece che boolean 
  handleCheckboxChange(event: any) {
    const isAbilitatoControl = this.form.get('isAbilitato');
    
    if (isAbilitatoControl) {
      // Imposta il valore come intero (0 o 1)
      isAbilitatoControl.setValue(event.target.checked ? 1 : 0);
    }
  }

  hasError(controlName: string, errorName: string): boolean {
    const control: AbstractControl | null = this.form.get(controlName);
    return control ? control.hasError(errorName) : false;
  }


  backClicked() {
    this._location.back();
  }

  ngOnInit(): void {  }

  ngOnChanges(changes: SimpleChanges) {
    let utentiStructure: any = this.utenti;
    this.form.patchValue({
      utenteId: utentiStructure.utenteId,
      nomeUtente: utentiStructure.nomeUtente,
      cognomeUtente: utentiStructure.cognomeUtente,
      codiceFiscaleUtente: utentiStructure.codiceFiscaleUtente,
      isAbilitato: utentiStructure.isAbilitato ,
      enteAppartenenza: utentiStructure.enteAppartenenza,
      dataLogin: (moment(utentiStructure.dataLogin)).format('YYYY-MM-DD'), //utentiStructure.dataLogin
      dataLogout: (moment(utentiStructure.dataLogout)).format('YYYY-MM-DD'), //utentiStructure.dataLogout,
      idRuoloUtente: utentiStructure.idRuoloUtente,
    });


  }

  salvaUtente() {
    let utentiStructure: any = this.utenti;
    this.dismiss = 'modal';
    var closeModal = document.getElementById('closeModal');
    
    if (this.form.valid) {
      this.serviceGR.getAllutentiPost("utente-abilitato/getAllutentiPost", this.form.value).subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', res.message);
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        },
        complete: () => { }
      });
      this.router.navigate(['/utenti']);

    }else{
      alert(this.message);
    }
  }
  elezioneInserita() {
    this.onElezione.emit();
  }

  verificaCodiceFiscaleUtenti() {
    const params = new HttpParams();
    
    this.serviceGR.verificaCodiceFiscaleUtenti("utente-abilitato/verificaCodiceFiscaleUtenti", { codiceFiscale: this.searchText })
    .subscribe((res: any) => {
      if (res.message) {
        // Puoi gestire il messaggio qui, ad esempio, assegnandolo a una variabile per visualizzarlo nell'HTML
        this.messageFromServer = res.message;
      }
    })
  }
}
