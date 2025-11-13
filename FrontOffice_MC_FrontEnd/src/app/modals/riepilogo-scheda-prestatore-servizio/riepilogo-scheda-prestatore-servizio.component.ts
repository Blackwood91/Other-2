import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-riepilogo-scheda-prestatore-servizio",
  templateUrl: "./riepilogo-scheda-prestatore-servizio.component.html",
  styleUrls: ["./riepilogo-scheda-prestatore-servizio.component.css"],
})
export class RiepilogoSchedaPrestatoreServizioComponent implements OnInit {
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
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

  ngOnInit(): void {
    this.loadTitoliAnagrafiche();
  }

  activeModifica() {
    this.isModifica = true;
  }

  openModal(idRichiesta: number, idAnagrafica: number) {
    this.resetParameters();
    this.idRichiesta = idRichiesta;
    this.idAnagrafica = idAnagrafica;
    this.loadSchedaAnagrafica();

    const buttonActiveModal = document.getElementById(
      "activeModalSchedaRiepilogoPrestSer"
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

  openPdfFile() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getFileRappresentante("pdf/getFilePrestatore", params)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res[0].file)], {
            type: "application/pdf",
          });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, "_blank");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  // Per formattare byte in file pdf
  convertiStringaBlobAFile(dati: string): File {
    let byteCharacters = atob(dati);
    let byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    let byteArray = new Uint8Array(byteNumbers);
    let blobFile = new Blob([byteArray], { type: "application/pdf" });

    return new File([blobFile], "file");
  }

  closeModal() {
    const closeModal = document.getElementById("closeModal");
    closeModal!.click();
  }
}
