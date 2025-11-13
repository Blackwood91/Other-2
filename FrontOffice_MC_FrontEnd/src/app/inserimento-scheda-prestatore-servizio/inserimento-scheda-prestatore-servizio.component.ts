import { Component, Input, OnInit, ViewChild, ElementRef } from "@angular/core";
import { MediazioneService } from "../mediazione.service";
import { SharedService } from "../shared.service";

@Component({
  selector: "app-inserimento-scheda-prestatore-servizio",
  templateUrl: "./inserimento-scheda-prestatore-servizio.component.html",
  styleUrls: ["./inserimento-scheda-prestatore-servizio.component.css"],
})
export class InserimentoSchedaPrestatoreServizioComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;


  @Input()
  idRichiesta: number = 0;
  isModifica = false;

  idTitolo: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  poDataAssunzione: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  codiceFiscaleSS: string = "";
  cittadinanza: string = "";
  poTipoRappOdm: string = "";
  // Dati di riferimento
  isEsteroNascita: string = "false";
  comuneSceltoNascita: string = "";
  showListComuneNascita: boolean = false;
  comuniNascita: any = [];
  titoliAnagrafiche: any = [];
  fileDocumento: any = null;
  //variabili per checks di validità
  validTitolo: boolean = true;
  validSesso: boolean = true;
  validCognome: boolean = true;
  validNome: boolean = true;
  validDataNascita: boolean = true;
  validComuneSceltoNascita: boolean = true;
  validStatoNascita: boolean = true;
  validComuneNascitaEstero: boolean = true;
  validCodiceFiscale: boolean = true;
  validCittadinanza: boolean = true;
  validRapporto: boolean = true;
  validDataAssunzione: boolean = true;

  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  resetForm() {
    this.idTitolo = 0;
    this.sesso ="";
    this.cognome = '';
    this.nome = '';
    this.dataNascita = '';
    this.statoNascita = '';
    this.comuneNascitaEstero = 'false';
    this.comuneSceltoNascita = '';
    this.codiceFiscaleSS = '';
    this.cittadinanza = '';
    this.poTipoRappOdm = '';
    this.poDataAssunzione = '';
    this.fileDocumento = null;
    this.resetFileInput(); // Call the reset file input method
  }
  
  resetFileInput() {
    this.fileInput.nativeElement.value = "";
  }

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
  }

  activeModifica() {
    this.isModifica = true;
  }

  loadComuneNascita(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniNascita = res;
            this.showListComuneNascita = true;
          },
        });
    } else {
      this.showListComuneNascita = false;
    }
  }

  loadTitoliAnagrafiche() {
    this.serviceME
      .getAllTitoliAnagrafiche("titoloAnagrafiche/getAll")
      .subscribe({
        next: (res: any) => {
          this.titoliAnagrafiche = res;
        },
      });
  }

  selectComuneNascita(idComune: string, comune: string) {
    this.idComuneNascita = parseInt(idComune);
    this.comuneSceltoNascita = comune;
    this.showListComuneNascita = false;
  }

  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  saveAnagrafica() {
    if (this.finalCheck() === true) {
      let datiAnagrafica: {
        idAnagrafica: number;
        idTitoloAnagrafica: number;
        sesso: string;
        cognome: string;
        nome: string;
        dataNascita: string;
        poDataAssunzione: string;
        statoNascita: string;
        idComuneNascita: number;
        codiceFiscale: string;
        cittadinanza: string;
        poTipoRappOdm: string;
        comuneNascitaEstero: string;
        idRichiesta: number;
      } = {
        idAnagrafica: 0,
        idTitoloAnagrafica: this.idTitolo,
        sesso: this.sesso,
        cognome: this.cognome,
        nome: this.nome,
        dataNascita: this.dataNascita,
        poDataAssunzione: this.poDataAssunzione,
        statoNascita: this.statoNascita,
        idComuneNascita: this.idComuneNascita,
        codiceFiscale: this.codiceFiscaleSS,
        cittadinanza: this.cittadinanza,
        poTipoRappOdm: this.poTipoRappOdm,
        comuneNascitaEstero: this.comuneNascitaEstero,
        idRichiesta: this.idRichiesta,
      };

      // Inserire i controlli
      let formData: FormData = new FormData();
      formData.append(
        "anagraficaDto",
        new Blob([JSON.stringify(datiAnagrafica)], { type: "application/json" })
      );
      // Solo se ineriti verrà creato il form apposito, omettendoli si stanno passando come null al server
      if (this.fileDocumento != null) {
        formData.append("fileDocumento", this.fileDocumento!, "documento");
      } else if (
        this.fileDocumento == null /* && this.existFileDocumento != true*/
      ) {
        this.sharedService.onMessage(
          "error",
          "Per poter proseguire è obbligatorio allegare il file del documento d'identità"
        );
        return;
      }

      // Inserire i controlli

      // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
      if (this.isEsteroNascita == "false") {
        datiAnagrafica.comuneNascitaEstero = "";
      } else {
        datiAnagrafica.idComuneNascita = 0;
      }

      this.serviceME
        .saveAnagraficaPrestatore(
          "anagrafica/saveAnagraficaPrestatore",
          formData
        )
        .subscribe({
          next: (res: any) => {
            this.sharedService.onUpdateMenu();
            this.sharedService.onMessage(
              "success",
              "L'inserimento è avvenuto con successo!"
            );
            return;
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error);
            return;
          },
        });

        this.resetForm();

    } else {
      this.sharedService.onMessage(
        "error",
        "Inserire tutti i dati e file obbligatori per proseguire"
      );
    }

  }

  /* CHECK VALIDITA' */

  checkTitolo() {
    if (this.idTitolo != 0) {
      this.validTitolo = true;
      return true;
    } else {
      this.validTitolo = false;
      return false;
    }
  }

  checkSesso() {
    if (this.sesso == "M" || this.sesso == "F") {
      this.validSesso = true;
      return true;
    } else {
      this.validSesso = false;
      return false;
    }
  }

  checkCognome() {
    if (this.cognome.length > 0) {
      this.validCognome = true;
      return true;
    } else {
      this.validCognome = false;
      return false;
    }
  }

  checkNome() {
    if (this.nome.length > 0) {
      this.validNome = true;
      return true;
    } else {
      this.validNome = false;
      return false;
    }
  }

  checkDataNascita() {
    if (
      this.dataNascita != undefined &&
      this.dataNascita != null &&
      this.dataNascita != ""
    ) {
      this.validDataNascita = true;
      return true;
    } else {
      this.validDataNascita = false;
      return false;
    }
  }

  checkStatoNascita() {
    if (this.statoNascita.length > 0) {
      this.validStatoNascita = true;
      return true;
    } else {
      this.validStatoNascita = false;
      return false;
    }
  }

  checkComuneNascitaEstero() {
    if (this.comuneNascitaEstero.length > 0) {
      this.validComuneNascitaEstero = true;
      return true;
    } else {
      this.validComuneNascitaEstero = false;
      return false;
    }
  }

  checkComuneSceltoNascita() {
    if (
      this.comuneSceltoNascita != undefined &&
      this.comuneSceltoNascita != null &&
      this.comuneSceltoNascita != ""
    ) {
      this.validComuneSceltoNascita = true;
      return true;
    } else {
      this.validComuneSceltoNascita = false;
      return false;
    }
  }

  checkCodiceFiscale() {
    const codiceFiscaleUpperCase = this.codiceFiscaleSS.toUpperCase();
    const codiceFiscaleRegex = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;

    if (codiceFiscaleRegex.test(codiceFiscaleUpperCase)) {
      this.validCodiceFiscale = true;
      return true;
    } else {
      this.validCodiceFiscale = false;
      return false;
    }
  }

  checkCittadinanza() {
    if (this.cittadinanza.length > 0) {
      this.validCittadinanza = true;
      return true;
    } else {
      this.validCittadinanza = false;
      return false;
    }
  }

  checkRapporto() {
    if (
      /*this.poTipoRappOdm != undefined && this.poTipoRappOdm != null && */ this
        .poTipoRappOdm != ""
    ) {
      this.validRapporto = true;
      return true;
    } else {
      this.validRapporto = false;
      return false;
    }
  }

  checkDataAssunzione() {
    if (
      this.poDataAssunzione != undefined &&
      this.poDataAssunzione != null &&
      this.poDataAssunzione != ""
    ) {
      this.validDataAssunzione = true;
      return true;
    } else {
      this.validDataAssunzione = false;
      return false;
    }
  }

  finalCheck() {
    let esito = true;

    if (this.checkTitolo() === false) esito = false;
    if (this.checkSesso() === false) esito = false;
    if (this.checkCognome() === false) esito = false;
    if (this.checkNome() === false) esito = false;
    if (this.checkDataNascita() === false) esito = false;

    switch (this.isEsteroNascita) {
      case "false":
        if (this.checkComuneSceltoNascita() === false) esito = false;
        break;
      case "true":
        if (this.checkStatoNascita() === false) esito = false;
        if (this.checkComuneNascitaEstero() === false) esito = false;
        break;
    }

    if (this.checkCodiceFiscale() === false) esito = false;
    if (this.checkCittadinanza() === false) esito = false;
    if (this.checkRapporto() === false) esito = false;
    if (this.checkDataAssunzione() === false) esito = false;

    return esito;
  }
}
