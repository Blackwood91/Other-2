import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-autocertificazione-onorabilita-comp-org-am",
  templateUrl: "./autocertificazione-onorabilita-comp-org-am.component.html",
  styleUrls: ["./autocertificazione-onorabilita-comp-org-am.component.css"],
})
export class AutocertificazioneOnorabilitaCompOrgAmComponent implements OnInit {
  @Input()
  idRichiesta: number = 0;
  idAnagrafica: number = 0;
  isConvalidato: boolean = false;
  // DATI TABELLA
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;

  searchTextTable: string = "";
  oggSociale: string = "";
  codiceFiscale: string = "";
  idTitolo: number = 0;
  cognome: string = "";
  nome: string = "";
  sesso: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  statoResidenza: string = "";
  idQualifica: number = 0;

  isEsteroNascita: string = "false";
  isEsteroResidenza: string = "false";
  comuneSceltoNascita: string = "";
  comuneSceltoResidenza: string = "";
  showListComuneNascita: boolean = false;
  showListComuneResidenza: boolean = false;
  comuniNascita: any = [];
  comuniResidenza: any = [];
  titoliAnagrafiche: any = [];
  qualifiche: any = [];
  rappresentanti: any = [];
  //ESISTERA SOLO SE ESISTE IL CLONE DI RAP.LEGALE
  anagraficaRapLegClone: any = [];
  anagraficaSelezionata: string = "";
  showElencoSelectAnagrafiche: boolean = false;

  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.disabledSelectElencoCustom();
    this.loadTitoliAnagrafiche();
    this.loadQualifiche();
    this.loadSelectCompOrgAm();
  }

  onShowElencoSelectAnagrafiche() {
    this.showElencoSelectAnagrafiche = this.showElencoSelectAnagrafiche ? false : true;
  }

  selectAnagrafica(idAnagrafica: number, anagraficaSelezionata: string) {
    this.showElencoSelectAnagrafiche = false;
    this.anagraficaSelezionata = anagraficaSelezionata;
    this.idAnagrafica = idAnagrafica;
    this.loadAnagrafica();
  }

  disabledSelectElencoCustom() {
    // DISABILITAZIONE MENU IN DISCE PER ELENCO PERSONALIZZATO CON SVG
    document.getElementById('selectAutocertificazioneAnagrafiche')!.addEventListener('mousedown', function(event) {
      event.preventDefault();
    });
  }

  loadAnagrafica() {
    this.resetParametrs();

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
            this.dataNascita = moment(res.dataNascita).format("DD-MM-YYYY");
            this.statoNascita = res.statoNascita;
            this.idComuneNascita = res.idComuneNascita;
            this.comuneNascitaEstero = res.comuneNascitaEstero;
            this.statoResidenza = res.statoResidenza;
            this.idComuneResidenza = res.idComuneResidenza;
            this.comuneResidenzaEstero = res.comuneResidenzaEstero;
            this.idQualifica = res.idQualifica.toString();
            this.codiceFiscale = res.codiceFiscale;

            this.loadComuneNascitaUpdate(res.idComuneNascita);
            this.loadComuneResidenzaUpdate(res.idComuneResidenza);
            this.loadOnorabilitaIsConvalidato();
            this.loadAutocertificazione(this.idAnagrafica);
          }
        },
      });
  }

  loadSelectCompOrgAm() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSelectAutocertReqOnoForCompOrgAm(
        "anagrafica/getAllSelectAutocertReqOnoForCompOrgAm",
        params
      )
      .subscribe((res: any) => {
        this.rappresentanti = res;
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
          this.showListComuneResidenza = false;
        },
      });
    } else {
      this.isEsteroResidenza = "true";
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

  loadQualifiche() {
    this.serviceME.getAllQualifiche("qualifica/getAll").subscribe({
      next: (res: any) => {
        this.qualifiche = res;
      },
    });
  }

  loadRichiesta() {
    this.serviceME.getAllQualifiche("qualifica/getAll").subscribe({
      next: (res: any) => {
        this.qualifiche = res;
      },
    });
  }

  getTitolo(idTitolo: number) {
    let titolo = this.titoliAnagrafiche.find(
      (obj: { idTitoloAnagrafiche: number }) => {
        return obj.idTitoloAnagrafiche === idTitolo;
      }
    );
    if (titolo) return titolo.descrizione;
  }

  getQualifica(idQualifica: number) {
    if (idQualifica == 1) {
      return "Rappresentante Legale";
    } else if (idQualifica == 2) {
      return "Responsabile dell'Organismo";
    } else if (idQualifica == 3) {
      return "Componente organo Amministrazione";
    } else if (idQualifica == 4) {
      return "Socio";
    } else if (idQualifica == 5) {
      return "Associato";
    } else if (idQualifica == 6) {
      return "Altro";
    }
    return "";
  }

  resetParametrs() {
    this.oggSociale = "";
    this.codiceFiscale = "";
    this.idTitolo = 0;
    this.cognome = "";
    this.nome = "";
    this.sesso = "";
    this.dataNascita = "";
    this.idComuneNascita = 0;
    this.comuneNascitaEstero = "";
    this.statoNascita = "";
    this.idComuneResidenza = 0;
    this.comuneResidenzaEstero = "";
    this.statoResidenza = "";
    this.idQualifica = 0;

    this.isEsteroNascita = "false";
    this.isEsteroResidenza = "false";
    this.comuneSceltoNascita = "";
    this.comuneSceltoResidenza = "";
    this.showListComuneNascita = false;
    this.showListComuneResidenza = false;
    this.comuniNascita = [];
    this.comuniResidenza = [];
    this.titoliAnagrafiche = [];
    this.qualifiche = [];
  }

  public getRows() {
    return this.tableResult.length;
  }

  openPdfFile() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(
        "pdf/getAnteprimaFileAutocertificazioneReqOnoCompOrgAm",
        params
      )
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], {
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

  loadOnorabilitaIsConvalidato() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getStatusRquisitiOnorabilita("status/getStatusReqOnoCompOrgAm", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  convalidazione() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .convalidazioneRequisitiOnorabilitaCompOrgAm(
        "status/convalidazioneRequisitiOnorabilitaCompOrgAm",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadOnorabilitaIsConvalidato();
          this.loadSelectCompOrgAm();
          this.loadAnagrafica();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile procedere con la convalidazione."
          );
        },
      });
  }

  loadAutocertificazione(idAnagrafica: any) {
    const idModulo = 32;
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", idAnagrafica)
      .set("idModulo", idModulo)
      .set("indexPage", this.indexPage - 1)
      .set("searchText", this.searchTextTable);

    this.serviceME
      .getElencoAutocerficatiRequisitiOnorabilitApp(
        "statoModuli/getElencoAutocerficatiRequisitiOnorabilitApp",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
          // this.totalPage = Math.ceil(res.length / 10);
          // this.totalResult = res.totalResult;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error.error);
        },
        complete: () => {},
      });
  }
}
