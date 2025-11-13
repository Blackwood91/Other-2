import { Component, HostBinding, Input, OnInit, SimpleChanges, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, FormsModule } from '@angular/forms';
import { GiustiziaService } from '../giustizia.service';
import { SharedService } from '../shared.service';
import { Validators } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-inserimento-mediatore',
  templateUrl: './inserimento-mediatore.component.html',
  styleUrls: ['./inserimento-mediatore.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class InserimentoMediatoreComponent implements OnInit {
  ente: any;
  form: FormGroup;
  allEnti = new Array();
  messageFromServer: string = ''; 
  searchText: string = '';
  nomeFile : string = '';

  isBottoneInserisciAbilitato: boolean = true;

  selectedFilePDF: any;
  existCodFiscale: boolean = false;

  @HostBinding('data-bs-dismiss')
  dismiss: string = '';
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  isMaggiorenne = (control: AbstractControl): { [key: string]: boolean } | null => {
    const dataNascita = new Date(control.value);
    const oggi = new Date();
    const eta = oggi.getFullYear() - dataNascita.getFullYear();

    // Verifica se l'utente è maggiorenne (età >= 18)
    if (eta < 18) {
      return { minorenni: true };
    }

    return null;
  };


  selectField: FormControl = new FormControl();

  //attivo: boolean = false;

  constructor(public fb: FormBuilder, private serviceGR: GiustiziaService, private sharedService: SharedService, private router: Router, private _location: Location) {

    this.form = fb.group({
      idMediatore: Number,//ID_MEDIATORE
      nomeMediatore:  ["", [Validators.required]],
      cognomeMediatore:  ["", [Validators.required]],
      codiceFiscale: ["", [Validators.required, Validators.maxLength(16)]], 
      indirizzoPec: ["", [Validators.required, Validators.maxLength(200)]],
      luogoDiNascita: ["", [Validators.required, Validators.maxLength(100)]],
      dataDiNascita: ["", [Validators.required, this.isMaggiorenne]],
      indirizzo: ["", [Validators.required]], //indirizzo 1 quella che arriva cosi dal tab agg, sara quella corretta
      numeroCivico: ["", [Validators.required]], //numero_civico quella che arriva cosi dal tab agg, sara quella corretta
      cittaDiResidenza: ["", [Validators.required]], //citta_residenza quella che arriva cosi dal tab agg, sara quella corretta
      provinciaDiResidenza: ["", [Validators.required]], //provicina_residenza quella che arriva cosi dal tab agg, sara quella corretta
      cap: ["", [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(5),
        Validators.pattern(/^[0-9]{5}$/) // Accetta solo 5 cifre
      ]], 
      isFormatore: 0,
      numeroIscrizioneElenco: Number,
      requisitiIscrizioneElenco: ["", [Validators.required]],
      dataIscrizioneElenco: ["", [Validators.required]],
      stato: "Iscritto",
      nomeEnteAttestato: "",
      enteAttestato:"",
      enteAttestatoId: Number, 
      tipologiaEnte: "",
      tipologiaEnteFormatore: Number,
      isConvenzionato: Number,
      provvedimento: [null],
      statoIscrizioneId: Number,
      dataStato: "",
      dataFine:"",
      tipologia: 1,
      motivazione: "",
      idStatoMediatore: 1,
      dataProvvedimento: ["", [Validators.required]],
    })

    
    const codiceFiscaleControl = this.form.get('codiceFiscale');

    if (codiceFiscaleControl) { 
      codiceFiscaleControl.setValidators([
        Validators.required,
        (control) => {
          const codiceFiscale = control.value;
          if (!codiceFiscale) return null;
    
          // Converte il codice fiscale in maiuscolo
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

  ngOnInit(): void { }

  // Metodo per cambiare la qualifica del formatore
  cambioQualificaFormatore(event: any) {
    this.form.patchValue({isFormatore: parseInt(event.target.value)});    
    if(parseInt(event.target.value) === 1) {
      this.form.patchValue({tipologiaEnteFormatore: ""}); 
      this.form.get('tipologiaEnteFormatore')?.setValidators([Validators.required]);
    }
    else {
      this.form.patchValue({tipologiaEnteFormatore: ""}); 
      this.form.get('tipologiaEnteFormatore')?.clearValidators()
      this.form.get('tipologiaEnteFormatore')?.updateValueAndValidity();
    }
  }

  // Metodo per cambiare lo stato
  cambioStato(event: any) {
    this.form.patchValue({idStatoMediatore: parseInt(event.target.value)});    
    if(parseInt(event.target.value) !== 1 || parseInt(event.target.value) !== 3) {
      this.form.patchValue({dataFine: ""}); 
      this.form.get('dataFine')?.setValidators([Validators.required]);
    }
    else {
      this.form.patchValue({dataFine: ""}); 
      this.form.get('dataFine')?.clearValidators()
      this.form.get('dataFine')?.updateValueAndValidity();
    }
  }
//Metodo per dare il validator su dataStato
  setDataStato(event: any) {
    this.form.patchValue({dataStato: parseInt(event.target.value)});    
    if(parseInt(event.target.value) === 2 || parseInt(event.target.value) === 3 || parseInt(event.target.value) === 4) {
      this.form.patchValue({dataStato: ""}); 
      this.form.get('dataStato')?.setValidators([Validators.required]);
    }
    else {
      this.form.patchValue({dataStato: ""}); 
      this.form.get('dataStato')?.clearValidators()
      this.form.get('dataStato')?.updateValueAndValidity();
    }

  }

  // Metodo per cambiare il tipo dell'ente ed aprire la select di appartenenza
  cambioTipoEnte(event: any) {    
    this.form.patchValue({tipologiaEnteFormatore: parseInt(event.target.value)});  
    // Deve essere diverso da null o vuoto per poter richiamare il caricamento dell'ente
    if(event.target.value != "" && event.target.value != null) { 
      if(parseInt(event.target.value) === 2) {
        this.form.patchValue({nomeEnteAttestato: ""}); 
        this.form.get('nomeEnteAttestato')?.setValidators([Validators.required]);
        this.form.patchValue({isConvenzionato: ""}); 
        this.form.get('isConvenzionato')?.setValidators([Validators.required]);
      }
      else {
        this.form.patchValue({nomeEnteAttestato: ""}); 
        this.form.get('nomeEnteAttestato')?.clearValidators()
        this.form.get('nomeEnteAttestato')?.updateValueAndValidity();
        this.form.patchValue({isConvenzionato: ""}); 
        this.form.get('isConvenzionato')?.clearValidators();
        this.form.get('isConvenzionato')?.updateValueAndValidity();   
      }

      // Il tipologia ente e l'enteAttestatoId sarà necessario in entrambe le casistiche
      this.form.get('enteAttestatoId')?.setValidators([Validators.required]);
      this.form.get('tipologiaEnte')?.setValidators([Validators.required]);

      this.caricaEnte() 
    }
  }

  // Metodo per cambiare l'ente e i parametri inerenti
  cambioEnte(event: any) {
    this.form.patchValue({enteAttestatoId: parseInt(event.target.value)});    
    
    // Solo se non è non presente verrà cercato l'ente associato
    if(parseInt(event.target.value) !== 1) {
      this.ente = this.allEnti.find(oggetto => oggetto.enteAttestatoID === parseInt(event.target.value));    
      this.form.patchValue({nomeEnteAttestato: this.ente.nomeEnteAttestato});    
      this.form.patchValue({isConvenzionato: this.ente.isConvenzionato});    
      this.form.patchValue({tipologiaEnte: this.ente.tipologiaEnte});    
    }
  }

  caricaEnte() {
    this.serviceGR.getEnte("ente/getAllEntiFormatore", {enteFormatore : this.form.value.tipologiaEnteFormatore}).subscribe((res: any) => {
    this.allEnti = res.result;      
    // Centro per la giustizia riparativa, solo tipologia ente verrà valorizzato
    if(this.form.value.tipologiaEnteFormatore === 1) {
      this.form.patchValue({nomeEnteAttestato: null});
      this.form.patchValue({isConvenzionato: null});
      this.form.patchValue({tipologiaEnte: this.allEnti[0].tipologiaEnte});     
      //this.form.value.tipologiaEnte = this.allEnti[0].tipologiaEnte;       
    }
  })
}

  verificaCodiceFiscale(event: any) {    
    this.serviceGR.verificaCodiceFiscale("mediatori/verificaCodiceFiscale", { codiceFiscale: event.target.value })
    .subscribe((res: any) => {
      if (res.message === "true") {
        // Puoi gestire il messaggio qui, ad esempio, assegnandolo a una variabile per visualizzarlo nell'HTML
        this.existCodFiscale = true;
      }
      else {
        this.existCodFiscale = false;
      }
    })
  }


  // Apertura selezione file cartella
  openInputFile() {
    var fileInput = document.getElementById('inputFilePdf');
    fileInput!.click(); // Simula il click sull'input di tipo file
  }

  onFileSelectedPDF(event: any) {
    this.selectedFilePDF = event.target.files[0];
    this.nomeFile = this.selectedFilePDF.name;
  }

  sendMediatore() {
    const formData: FormData = new FormData();
    formData.append('mediatoreGiustiziaRiparativa', new Blob([JSON.stringify(this.form.value)], {type: 'application/json'} ));
    formData.append('file', this.selectedFilePDF, this.selectedFilePDF.name);
    // Il file è obbligatorio
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage('error', "Si prega di inserire il file pdf");
      return; 
    }
    // Il file è obbligatorio
    if(this.existCodFiscale) {
      this.sharedService.onMessage('error', "Si prega di cambiare il codice fiscale");
      return;
    }

    if (this.form.valid) {
      this.isBottoneInserisciAbilitato = false;
      this.serviceGR.insertMediatoriPost("mediatori/setMediatori", formData).subscribe({
        next: (res: any) => { 
          this.sharedService.onMessage('success', res.message);
          window.location.href = encodeURI('/mediatori-interno');
        },
        error: (error: any) => { 
          this.sharedService.onMessage('error', error);
          this.router.navigate(['/inserimentoMediatore']);
        }
      }); 
    }else {
      this.sharedService.onMessage('error', "Si prega di inserire tutti i campi obbligatori contrassegnati dall'asterisco");
    }
  }

  backClicked() {
    this._location.back();
  }
}
