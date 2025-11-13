import { HttpParams } from "@angular/common/http";
import { Component, Inject, OnInit } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { APP_ENVIRONMENT, AppEnvironment } from "src/main";

@Component({
  selector: "app-modal-mediatori",
  templateUrl: "./modal-mediatori.component.html",
  styleUrls: ["./modal-mediatori.component.css"],
})
export class ModalMediatoriComponent implements OnInit {
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  comuneSceltoNascita: string = "";
  comuneSceltoResidenza: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  statoResidenza: string = "";
  codiceFiscale: string = "";
  isEsteroNascita: string = "false";
  isEsteroResidenza: string = "false";
  idTipoAnagrafica: number = 0;
  indirizzoEmail: string = "";
  indirizzoPec: string = "";
  medTelefono: string = "";
  medCellulare: string = "";
  medFax: string = "";

  fileDocumento: any = null;

  showListComuneNascita: boolean = false;
  showListComuneResidenza: boolean = false;
  comuniNascita: any = [];
  comuniResidenza: any = [];
  titoliAnagrafiche: any = [];
  qualifiche: any = [];

  constructor(
    private serviceME: MediazioneService,
    @Inject(APP_ENVIRONMENT) private env: AppEnvironment
  ) {}

  ngOnInit(): void {}

  openModal(/*idRichiesta: number, */ idAnagrafica: number) {
    this.idAnagrafica = idAnagrafica;
    //this.idRichiesta = idRichiesta;

    const params = new HttpParams().set("idAnagrafica", idAnagrafica).set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAnagraficaById("anagrafica/getAnagraficaById", params)
      .subscribe((res: any) => {
        this.idAnagrafica = res.idAnagrafica;
        this.sesso = res.sesso;
        this.cognome = res.cognome;
        this.nome = res.nome;
        this.dataNascita = moment(res.dataNascita).format("YYYY-MM-DD");
        this.idComuneNascita = res.idComuneNascita;
        this.comuneNascitaEstero = res.comuneNascitaEstero;
        this.statoNascita = res.statoNascita;
        this.idComuneResidenza = res.idComuneResidenza;
        this.comuneResidenzaEstero = res.comuneResidenzaEstero;
        this.statoResidenza = res.statoResidenza;
        this.codiceFiscale = res.codiceFiscale;
        this.indirizzoEmail = res.indirizzoEmail;
        this.medTelefono = res.medTelefono;
        this.medCellulare = res.medCellulare;
        this.medFax = res.medFax;
        this.indirizzoPec = res.indirizzoPec;

        this.isEsteroNascita = res.isEsteroNascita;
        this.isEsteroResidenza = res.isEsteroResidenza;
        this.comuneSceltoNascita = res.comuneSceltoNascita;
        this.comuneSceltoResidenza = res.comuneSceltoResidenza;

        this.loadComuneNascitaUpdate(res.idComuneNascita);
        this.loadComuneResidenzaUpdate(res.idComuneResidenza);
      });

    const buttonActiveModal = document.getElementById("activeModalMediatore");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal!.click();
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

  loadComuneResidenzaUpdate(idComune: number) {
    if (idComune != 0 && idComune != undefined) {
      this.serviceME.getComune("comune/getComune", idComune).subscribe({
        next: (res: any) => {
          this.idComuneResidenza = res.idCodComune;
          this.comuneSceltoResidenza =
            res.nomeComune + " (" + res.regioneProvince.siglaProvincia + ")";
          //this.showListComuneResidenza = false;
        },
      });
    } else {
      this.isEsteroResidenza = "true";
    }
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

  loadComuneResidenza(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME
        .getAllComuni("comune/getAllComuneByNome", nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuniResidenza = res;
            this.showListComuneResidenza = true;
          },
        });
    } else {
      this.showListComuneResidenza = false;
    }
  }

  getFileDocumento() {
    var file = new Blob([this.convertiStringaBlobAFile(this.fileDocumento)], {
      type: "application/pdf",
    });
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
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

  loadExistFile() {
    const params = new HttpParams()
      /*.set('idRichiesta', this.idRichiesta)*/
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getFileRappresentante("pdf/getFileMediatore", params)
      .subscribe((res: any) => {
        if (res != null && res[0] != null) {
          this.fileDocumento = res[0].file;
        }
      });
  }
}
