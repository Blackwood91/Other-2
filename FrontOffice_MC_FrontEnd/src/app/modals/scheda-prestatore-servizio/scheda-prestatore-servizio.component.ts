import { HttpParams } from "@angular/common/http";
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-scheda-prestatore-servizio",
  templateUrl: "./scheda-prestatore-servizio.component.html",
  styleUrls: ["./scheda-prestatore-servizio.component.css"],
})
export class SchedaPrestatoreServizioComponent implements OnInit {
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
  isModifica = false;
  isConvalidato = false;
  @Output() eventRappresentante: EventEmitter<any> = new EventEmitter();

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

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
  }

  activeModifica() {
    this.isModifica = true;
  }

  openModalUpdate(idRichiesta: number, idAnagrafica: number) {
    this.resetParameters();
    this.idRichiesta = idRichiesta;
    this.idAnagrafica = idAnagrafica;
    this.loadSchedaAnagrafica();
    this.loadIsConvalidato();

    const buttonActiveModal = document.getElementById(
      "activeModalSchedaPrestatoreServizio"
    );
    // Esegui il click sul bottone nascosto
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  resetParameters() {
    this.idTitolo = 0;
    this.sesso = "";
    this.cognome = "";
    this.nome = "";
    this.dataNascita = "";
    this.poDataAssunzione = "";
    this.idComuneNascita = 0;
    this.comuneNascitaEstero = "";
    this.statoNascita = "";
    this.codiceFiscaleSS = "";
    this.cittadinanza = "";
    this.poTipoRappOdm = "";
    // Dati di riferimento
    this.isEsteroNascita = "false";
    this.comuneSceltoNascita = "";
    this.showListComuneNascita = false;
    this.fileDocumento = null;
    //variabili per checks di validità
    this.validTitolo = true;
    this.validSesso = true;
    this.validCognome = true;
    this.validNome = true;
    this.validDataNascita = true;
    this.validComuneSceltoNascita = true;
    this.validStatoNascita = true;
    this.validComuneNascitaEstero = true;
    this.validCodiceFiscale = true;
    this.validCittadinanza = true;
    this.validRapporto = true;
    this.validDataAssunzione = true;
  }

  loadSchedaAnagrafica(): void {
    const params = new HttpParams().set("idAnagrafica", this.idAnagrafica).set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAnagraficaById("anagrafica/getAnagraficaById", params)
      .subscribe({
        next: (res: any) => {
          if (res != null && res != undefined) {
            this.idTitolo = res.idTitoloAnagrafica;
            this.sesso = res.sesso;
            this.cognome = res.cognome;
            this.nome = res.nome;
            this.dataNascita = moment(res.dataNascita).format("YYYY-MM-DD");
            this.statoNascita = res.statoNascita;
            this.idComuneNascita = res.idComuneNascita;
            this.comuneNascitaEstero = res.comuneNascitaEstero;
            this.codiceFiscaleSS = res.codiceFiscale;
            this.cittadinanza = res.cittadinanza;
            this.poTipoRappOdm = res.poTipoRappOdm;
            this.poDataAssunzione = moment(res.poDataAssunzione).format(
              "YYYY-MM-DD"
            );

            this.loadComuneNascitaUpdate(res.idComuneNascita);
          }
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

  loadComuneNascitaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
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

  loadIsConvalidato() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .isConvalidato("status/getStatusPrestatoreServizio", params)
      .subscribe({
        next: (res: any) => {
          if (res.status === "c") this.isConvalidato = true;
          else this.isConvalidato = false;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  closeModal() {
    const closeModal = document.getElementById("closeModal");
    closeModal!.click();
    this.eventRappresentante.emit("Aggiornamento effettuato");
  }

  updateComponentMother() {
    this.eventRappresentante.emit("Aggiornamento effettuato");
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
        idAnagrafica: this.idAnagrafica,
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
      }

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
            this.updateComponentMother();
            this.loadIsConvalidato();
            this.sharedService.onMessage(
              "success",
              "L'inserimento è avvenuto con successo!"
            );
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error);
          },
        });
    } else {
      this.sharedService.onMessage(
        "error",
        "Completare tutti i campi con dati validi per proseguire con l'inserimento"
      );
    }
  }

  convalidazione() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazionePrestatoreServizio(
        "status/convalidazionePrestatoreServizio",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
          this.closeModal();
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile procedere con la convalidazione"
          );
          this.closeModal();
        },
      });
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
