import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-aggiornamento-anagrafica",
  templateUrl: "./aggiornamento-anagrafica.component.html",
  styleUrls: ["./aggiornamento-anagrafica.component.css"],
})
export class AggiornamentoAnagraficaComponent implements OnInit {
  titolo: string = "Modifica anagrafica";
  selectField: FormControl = new FormControl();
  // form: FormGroup;
  attivo: boolean = false;
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
  idTitolo: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  poDataAssunzione: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  codiceFiscale: string = "";
  cittadinanza: string = "";
  poTipoRappOdm: string = "";
  // Dati di riferimento
  isEsteroNascita: string = "false";
  comuneSceltoNascita: string = "";
  showListComuneNascita: boolean = false;
  comuniNascita: any = [];
  titoliAnagrafiche: any = [];

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
    public fb: FormBuilder, private serviceME: MediazioneService, 
    private route: ActivatedRoute, private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
    });
  }

  openModal(idAnagrafica: number) {
    this.idAnagrafica = idAnagrafica;

    const params = new HttpParams().set("idAnagrafica", idAnagrafica).set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAnagraficaById("anagrafica/getAnagraficaById", params)
      .subscribe((res: any) => {
        this.idTitolo = res.idTitoloAnagrafica;
        this.sesso = res.sesso;
        this.cognome = res.cognome;
        this.nome = res.nome;
        this.dataNascita = moment(res.dataNascita).format("YYYY-MM-DD");
        this.poDataAssunzione = moment(res.poDataAssunzione).format(
          "YYYY-MM-DD"
        );
        this.idComuneNascita = res.idComuneNascita;
        this.comuneNascitaEstero = res.comuneNascitaEstero;
        this.statoNascita = res.statoNascita;
        this.codiceFiscale = res.codiceFiscale;
        this.cittadinanza = res.cittadinanza;
        this.poTipoRappOdm = res.poTipoRappOdm;

        this.isEsteroNascita = res.isEsteroNascita;
        this.comuneSceltoNascita = res.comuneSceltoNascita;
        this.showListComuneNascita = res.showListComuneNascita;
        this.comuniNascita = res.comuniNascita;
        this.titoliAnagrafiche = res.titoliAnagrafiche;

        this.loadComuneNascitaUpdate(res.idComuneNascita);
        this.loadTitoliAnagrafiche();
      });

    const buttonActiveModal = document.getElementById(
      "activeModalUpdateAnagrafica"
    );
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal!.click();
  }

  sendAnagrafica(idAnagrafica: number) {
    //Un controllo di form è valido se tutti i suoi controlli di sottoform e controlli di input hanno un valore valido.
    //Impostare successivamente casi di errore con messaggio
    //this.dismissEvent.emit('modal');
    this.idAnagrafica = idAnagrafica;

    if (this.finalCheck() === true) {
      let datiAnagrafica: {
        idAnagrafica: number;
        idTitoloAnagrafica: number;
        sesso: string;
        cognome: string;
        nome: string;
        dataNascita: string | null;
        poDataAssunzione: string | null;
        statoNascita: string;
        idComuneNascita: number;
        codiceFiscale: string;
        cittadinanza: string;
        poTipoRappOdm: string;
        comuneNascitaEstero: string;
      } = {
        idAnagrafica: this.idAnagrafica,
        idTitoloAnagrafica: this.idTitolo,
        sesso: this.sesso,
        cognome: this.cognome,
        nome: this.nome,
        dataNascita:
          this.dataNascita /*== undefined ? this.dataNascita : null*/,
        poDataAssunzione:
          this.poDataAssunzione /*== undefined ? this.poDataAssunzione : null*/,
        statoNascita: this.statoNascita,
        idComuneNascita: this.idComuneNascita,
        codiceFiscale: this.codiceFiscale,
        cittadinanza: this.cittadinanza,
        poTipoRappOdm: this.poTipoRappOdm,
        comuneNascitaEstero: this.comuneNascitaEstero,
      };

      // Inserire i controlli

      // PER VALORIZZARE E ELIMINARE VALORI A SECONDA DELLA SCELTA SE ESTERO
      if (this.isEsteroNascita == "false") {
        datiAnagrafica.comuneNascitaEstero = "";
      } else {
        datiAnagrafica.idComuneNascita = 0;
      }

      this.serviceME
        .updateAnagrafica("anagrafica/saveAnagrafica", datiAnagrafica)
        .subscribe({
          next: (res: any) => {
            this.sharedService.onMessage(
              "success",
              "L'aggiornamento è avvenuto con successo!"
            );
            var closeModal = document.getElementById("closeModal");
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error.message);
          },
          complete: () => {},
        });
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

  selectComuneNascita(idComune: string, comune: string) {
    this.idComuneNascita = parseInt(idComune);
    this.comuneSceltoNascita = comune;
    this.showListComuneNascita = false;
  }

  loadComuneNascitaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this, (this.isEsteroNascita = "false");
      this.serviceME.getComune("comune/getComune", idComune).subscribe({
        next: (res: any) => {
          this.idComuneNascita = res.idCodComune;
          this.comuneSceltoNascita =
            res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
          this.showListComuneNascita = false;
        },
      });
    } else {
      this.isEsteroNascita = "true";
    }
  }

  testDoc() {}

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
    const codiceFiscaleUpperCase = this.codiceFiscale.toUpperCase();
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
