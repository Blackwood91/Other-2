import { Component, HostBinding, OnInit, Input, Output, SimpleChanges, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { GiustiziaService } from '../giustizia.service';
import * as moment from 'moment'; //SERVE PER FORMATTARE LA DATA
import { SharedService } from '../shared.service';
import {Validators} from '@angular/forms';

@Component({
  selector: 'app-aggiornamento-mediatore',
  templateUrl: './aggiornamento-mediatore.component.html',
  styleUrls: ['./aggiornamento-mediatore.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})

export class AggiornamentoMediatoreComponent implements OnInit {
  @Input()
  mediatore!: object;
  ente: any;
  form: FormGroup;
  allEnti = new Array();
  messageFromServer: string = ''; 
  searchText: string = '';
  nomeFile : string = '';

  //Serve per poter inviare il parametro al component padre
  @Output() onMediatore = new EventEmitter<string>()

  selectedFilePDF: any;

  existCodFiscale: boolean = false;

  @HostBinding('data-bs-dismiss')
  dismiss: string = '';
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  selectField: FormControl = new FormControl();

  constructor(public fb: FormBuilder, private serviceGR: GiustiziaService, private sharedService: SharedService) {

    this.form = fb.group({
      idMediatore: Number,//ID_MEDIATORE
      nomeMediatore:  ["", [Validators.required]],
      cognomeMediatore:  ["", [Validators.required]],
      codiceFiscale: ["", [Validators.required, Validators.maxLength(16)]], 
      indirizzoPec: ["", [Validators.required, Validators.maxLength(200)]],
      luogoDiNascita: ["", [Validators.required, Validators.maxLength(100)]],
      dataDiNascita: ["", [Validators.required]],
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

  }

  ngOnInit(): void { }

  ngOnChanges(changes: SimpleChanges) {
    let mediatoriStructure: any = this.mediatore;
    if(mediatoriStructure != null || mediatoriStructure != undefined) {
      this.form.patchValue({
        idMediatore: mediatoriStructure.idMediatore,//ID_MEDIATORE
        nomeMediatore: mediatoriStructure.nomeMediatore,  //NOME_MEDIATORE
        cognomeMediatore: mediatoriStructure.cognomeMediatore, //COGNOME_MEDIATORE
        codiceFiscale: mediatoriStructure.codiceFiscale, //CODICE_FISCALE
        indirizzoPec: mediatoriStructure.indirizzoPec, //INDIRIZZO PEC
        luogoDiNascita: mediatoriStructure.luogoDiNascita, //LUOGO_NASCITA
        dataDiNascita: (moment(mediatoriStructure.dataDiNascita)).format('YYYY-MM-DD'),  //DATA_NASCITA
        indirizzo: mediatoriStructure.indirizzo1, //INDIRIZZO
        numeroCivico: mediatoriStructure.numero_civico, //NUMERO_CIVICO
        cittaDiResidenza: mediatoriStructure.citta_residenza, //CITTA_RESIDENZA
        provinciaDiResidenza: mediatoriStructure.provicina_residenza, //PROVICINA_RESIDENZA
        cap: mediatoriStructure.cap, //CAP    
        isFormatore: mediatoriStructure.isFormatore, //QUALIFICA_FORMATORE
        numeroIscrizioneElenco: mediatoriStructure.numeroIscrizioneElenco, //NUMERO_ISCRIZIONE
        requisitiIscrizioneElenco: mediatoriStructure.requisitiIscrizioneElenco, //REQUISITI_ISCRIZIONE
        dataIscrizioneElenco: (moment(mediatoriStructure.dataIscrizioneElenco)).format('YYYY-MM-DD'),  //DATA_ISCRIZIONE
        stato: mediatoriStructure.stato, //STATO_ISCRIZIONE_ID
        tipologiaEnteFormatore: mediatoriStructure.idTipologiaFormatore, //ID_TIPOLOGIA_ENTE_FORMATORE
        enteAttestato: mediatoriStructure.enteAttestato, //ENTE_ATTESTATO
        tipologiaEnte: mediatoriStructure.tipologiaEnte, 
        isConvenzionato: mediatoriStructure.isConvenzionato,
        provvedimento: mediatoriStructure.provvedimento,
        statoIscrizioneId: mediatoriStructure.stato_iscrizione_id, 
        dataStato: this.convertiStringInDate(mediatoriStructure.data_stato),
        dataFine: this.convertiStringInDate(mediatoriStructure.data_fine),
        tipologia: mediatoriStructure.tipologia,
        motivazione: mediatoriStructure.motivazione,
        idStatoMediatore: mediatoriStructure.id_stato_mediatore,
        dataProvvedimento: (moment(mediatoriStructure.data_provvedimento)).format('YYYY-MM-DD'),
        nomeEnteAttestato: mediatoriStructure.nomeEnteAttestato,
      });

      // Serve per riferimento al posto del nome del file che ad oggi non è presente in banca dati 
      // Ottenere le componenti della data
      const data = new Date(mediatoriStructure.data_provvedimento);

      const anno = data.getFullYear();
      const mese = data.getMonth() + 1; // Gennaio è 0, quindi aggiungiamo 1
      const giorno = data.getDate();
      // Formattare la data in data semplice
      this.nomeFile = `${anno}-${mese < 10 ? '0' : ''}${mese}-${giorno < 10 ? '0' : ''}${giorno}`;
      // Caricare campo input file con il blob string
      this.selectedFilePDF = mediatoriStructure.provvedimento == null ? null : this.convertiStringaBlobAFile(mediatoriStructure.provvedimento);

      const codiceFiscaleControl = this.form.get('codiceFiscale');
      if (codiceFiscaleControl) { // Controlla se il controllo esiste
        codiceFiscaleControl.setValidators([
          Validators.required,
          (control) => {
            const codiceFiscale = control.value;
            if (!codiceFiscale) return null;
  
            const codiceFiscaleRegex = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
            if (!codiceFiscaleRegex.test(codiceFiscale)) {
              return { codiceFiscaleInvalido: true };
            }
  
            return null;
          }
        ]);
      }
      
      // Gestione ente formatore e dei campi collegati
      if(mediatoriStructure.idTipologiaFormatore != null && mediatoriStructure.idTipologiaFormatore != "") {
        this.serviceGR.getEnte("ente/getAllEntiFormatore", {enteFormatore : this.form.value.tipologiaEnteFormatore}).subscribe((res: any) => {
          this.allEnti = res.result;      
          if(mediatoriStructure.enteAttestatoId !== 1) {
            this.ente = this.allEnti.find(oggetto => oggetto.enteAttestatoID === mediatoriStructure.enteAttestatoId);   
            this.form.patchValue({enteAttestatoId: mediatoriStructure.enteAttestatoId});         
            this.form.patchValue({nomeEnteAttestato: this.ente.nomeEnteAttestato});    
            this.form.patchValue({isConvenzionato: this.ente.isConvenzionato});    
            this.form.patchValue({tipologiaEnte: this.ente.tipologiaEnte});
          }
        })

      }
    }
  }

  // Per formattare date che arrivano in formato string
  convertiStringInDate(data: string): any {
    if(data === undefined || data === null || data === "") {
      return "";
    }

    const parts = data.split('/');
    
    if (parts.length === 3) {
      const year = parseInt(parts[2], 10) + 2000;
      const month = parseInt(parts[1], 10) - 1; // Mese è 0-based in JavaScript (0 = Gennaio)
      const day = parseInt(parts[0], 10);

      // Costruisci una stringa nel formato "YYYY-MM-DD" richiesto per il tipo 'date'
      data = `${year}-${this.paddZero(month + 1)}-${this.paddZero(day)}`;
      return data;
    }
  }

  paddZero(value: number): string {
    return value < 10 ? `0${value}` : `${value}`;
  }

  // Per formattare stringa in file pdf
  convertiStringaBlobAFile(dati: string): File {
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);
  
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
  
    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], {type: 'application/pdf'});
  
    return new File([blobFile], 'file');
  }



  openPdfFile() {    
    var file = new Blob([this.selectedFilePDF], {type: 'application/pdf'});
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, '_blank')
  }

  private arrayBufferToBase64(buffer: ArrayBuffer): string {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;

    for (let i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }

    return window.btoa(binary);
  }

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

  //metodo per validare data da 
  cambioDataDa(event: any) {
    this.form.patchValue({idStatoMediatore: parseInt(event.target.value)});       
    if(parseInt(event.target.value) !== 1 ) {
      this.form.patchValue({dataStato: ""}); 
      this.form.get('dataStato')?.clearValidators()
      this.form.get('dataStato')?.updateValueAndValidity();
    }
    else {
      this.form.patchValue({dataStato: ""}); 
      this.form.get('dataStato')?.setValidators([Validators.required]);
    }

  }
  
  // Metodo per cambiare data a 
  cambioStato(event: any) {
    this.form.patchValue({idStatoMediatore: parseInt(event.target.value)});      
    if(parseInt(event.target.value) == 1 || parseInt(event.target.value) == 3) {
      this.form.patchValue({dataFine: ""}); 
      this.form.get('dataFine')?.clearValidators()
      this.form.get('dataFine')?.updateValueAndValidity();
    }
    else {
      this.form.patchValue({dataFine: ""}); 
      this.form.get('dataFine')?.setValidators([Validators.required]);
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

  // Metodo per emettere evento
  mediatoreAggiornato() {
    this.onMediatore.emit();
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
    //Impostare successivamente casi di errore con messaggio
    this.dismiss = 'modal';
    var closeModal = document.getElementById('closeModal');

    const formData: FormData = new FormData();
    // Altri controlli oltre quelli del form
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage('error', "Si prega di inserire il file pdf");
      return;
    }
    // Il file è obbligatorio
    if(this.existCodFiscale) {
      this.sharedService.onMessage('error', "Si prega di cambiare il codice fiscale");
      return;
    }
    // Fine altri controlli

    formData.append('mediatoreGiustiziaRiparativa', new Blob([JSON.stringify(this.form.value)], {type: 'application/json'} ));
    formData.append('file', this.selectedFilePDF, this.selectedFilePDF.name);

    if (this.form.valid) {
      this.serviceGR.UpdateMediatoriPost("mediatori/UpdateMediatori", formData).subscribe({
        next: (res: any) => { 
          this.sharedService.onMessage('success', res.message);
          this.mediatoreAggiornato();
          closeModal!.click();
        },
        error: (error: any) => { 
          this.sharedService.onMessage('error', error);
        }
      }); 
    }else {
      this.sharedService.onMessage('error', "Si prega di inserire tutti i campi obbligatori contrassegnati dall'asterisco");
    }
	// this.sendpk.emit(this.form.value.pkElezione);
  }
}
