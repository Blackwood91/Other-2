import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-onorabilita-appendici",
  templateUrl: "./onorabilita-appendici.component.html",
  styleUrls: ["./onorabilita-appendici.component.css"],
})
export class OnorabilitaAppendiciComponent implements OnInit {
  @Input()
  idRichiesta: number = 0;
  @Input()
  component!: string;
  idAnagrafica: number = 0;
  // DATI TABELLA
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  idModulo: number = 0;

  searchTextTable: string = "";
  denominazioneOdm: string = "";
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
  idTipoAnagrafica: number = 0;
  rapprResp: string = "";

  isConvalidato = false;
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
  mediatori = new Array();
  anagraficaSelezionata: string = "";
  showElencoSelectMediatori: boolean = false;

  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.disabledSelectElencoCustom();
    this.loadTitoliAnagrafiche();
    this.loadQualifiche();
    this.loadSelectMediatori();
    
  }

  disabledSelectElencoCustom() {
    // DISABILITAZIONE MENU IN DISCE PER ELENCO PERSONALIZZATO CON SVG
    document
      .getElementById("selectAutocertificazioneAnagrafiche")!
      .addEventListener("mousedown", function (event) {
        event.preventDefault();
      });
  }

  onShowElencoSelectMediatori() {
    this.showElencoSelectMediatori = this.showElencoSelectMediatori
      ? false
      : true;
  }

  selectAnagrafica(idAnagrafica: number, anagraficaSelezionata: string) {
    this.showElencoSelectMediatori = false;
    this.anagraficaSelezionata = anagraficaSelezionata;
    this.idAnagrafica = idAnagrafica;
    this.loadRappresentante();
  }

  loadRappresentante() {
    const params = new HttpParams().set("idAnagrafica", this.idAnagrafica).set("idRichiesta", this.idRichiesta);

    this.resetParametrs();  //  temporaneamente commentato: lava via idAnagrafica prima dell'uso
    //  e quindi ogni metodo correlato non funge

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
            this.idTipoAnagrafica = res.idTipoAnagrafica;
            this.codiceFiscale = res.codiceFiscale;

            this.loadComuneNascitaUpdate(res.idComuneNascita);
            this.loadComuneResidenzaUpdate(res.idComuneResidenza);
            this.loadOnorabilitaIsConvalidato();
            this.loadDenominazione();
            this.loadAutocertificazione(this.idAnagrafica);
          }
        },
      });
  }

  loadSelectMediatori() {
    // this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatori'/*, params*/)
    //   .subscribe((res: any) => {
    //     if (this.component == "onorabilita-a")
    //       this.mediatori = res.result.filter((i: { idTipoAnagrafica: number; }) => i.idTipoAnagrafica === 4)
    //     else if (this.component == "onorabilita-b")
    //       this.mediatori = res.result.filter((i: { idTipoAnagrafica: number; }) => i.idTipoAnagrafica === 5)
    //     else if (this.component == "onorabilita-c")
    //       this.mediatori = res.result.filter((i: { idTipoAnagrafica: number; }) => i.idTipoAnagrafica === 6)
    //   });

    // TO DO PAGABLE
    // TEMPORANEO DA FIXARE PER OGNI MEDIATORE
    const params = new HttpParams()
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    switch (this.component) {
      case "onorabilita-a":
        this.serviceME
          .getAllAnagrafica("anagrafica/getAllAutocertificazioneMediatoriA", params)
          .subscribe({
            next: (res: any) => {
              this.mediatori = res.result;
              this.idModulo = 39;
            },
            error: (error: any) => {},
          });
        break;
      case "onorabilita-b":
        this.serviceME
          .getAllAnagrafica("anagrafica/getAllAutocertificazioneMediatoriB", params)
          .subscribe({
            next: (res: any) => {
              this.mediatori = res.result;
              this.idModulo = 44;
            },
            error: (error: any) => {},
          });
        break;
      case "onorabilita-c":
        this.serviceME
          .getAllAnagrafica("anagrafica/getAllAutocertificazioneMediatoriC", params)
          .subscribe({
            next: (res: any) => {
              this.mediatori = res.result;
              this.idModulo = 53;
            },
            error: (error: any) => {},
          });
        break;
    }
  }

  loadDenominazione() {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];

      const paramsRequest = new HttpParams().set(
        "idRichiesta",
        this.idRichiesta
      );

      this.serviceME
        .getRichiestaDomIscr("richiesta/getRichiestaDomIscr", paramsRequest)
        .subscribe({
          next: (res: any) => {
            //this.ragioneSociale = this.accessibilita.getCookieValue("societaSelezionata");
            this.denominazioneOdm = res.denominazioneOdm;
          },
          error: (error: any) => {},
        });
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

  // loadRichiesta() {
  //   this.serviceME.getAllQualifiche('qualifiche/getAll')
  //     .subscribe({
  //       next: (res: any) => {
  //         this.qualifiche = res;
  //       }
  //     })
  // }

  getTitolo(idTitolo: number) {
    let titolo = this.titoliAnagrafiche.find(
      (obj: { idTitoloAnagrafiche: number }) => {
        return obj.idTitoloAnagrafiche === idTitolo;
      }
    );
    if (titolo) return titolo.descrizione;
  }

  getTipoMediatore() {
    switch (this.component) {
      case "onorabilita-a":
        return "Mediatore Generico";
      case "onorabilita-b":
        return "Mediatore esperto in materia Internazionale";
      case "onorabilita-c":
        return "Mediatore esperto in materia di Consumo";
      default:
        return "errore";
    }
  }

  resetParametrs() {
    this.denominazioneOdm = "";
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
    //this.idAnagrafica = 0;
    this.rapprResp = "";

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

    let apiPath = "";
    switch (this.component) {
      case "onorabilita-a":
        apiPath = "getAnteprimaFileAutocertificazioneReqOnoAppA";
        break;
      case "onorabilita-b":
        apiPath = "getAnteprimaFileAutocertificazioneReqOnoAppB";
        break;
      case "onorabilita-c":
        apiPath = "getAnteprimaFileAutocertificazioneReqOnoAppC";
        break;
      default:
        return;
    }

    this.serviceME
      .getAnteprimaFileAutocertificazioneReqOno("pdf/" + apiPath, params)
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
      .set("idModulo", this.idModulo)
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);
    // .set('convalidaAnagraficaRapLegale', (this.anagraficaRapLegClone != null &&
    //                                       this.anagraficaRapLegClone.codiceFiscale == this.codiceFiscale) ? true : false);

    this.serviceME
      .getStatusRquisitiOnorabilita("status/getModuloIsConvalidatoAdPersonam", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato; //exist;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  convalidazione() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idModulo", this.idModulo)
      .set("idAnagrafica", this.idAnagrafica);
    // .set('convalidaAnagraficaRapLegale', (this.anagraficaRapLegClone != null &&
    //                                       this.anagraficaRapLegClone.codiceFiscale == this.codiceFiscale) ? true : false);

    this.serviceME
      .convalidazioneRquisitiOnorabilita(
        "status/convalidazioneRequisitiOnorabilitaAppendici",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadOnorabilitaIsConvalidato();
          this.loadTitoliAnagrafiche();
          this.loadSelectMediatori();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibilie procedere con la convalidazione"
          );
        },
      });
  }

  loadAutocertificazione(idAnagrafica: any) {
    switch (this.component) {
      case "onorabilita-a":
        this.idModulo = 39;
        break;
      case "onorabilita-b":
        this.idModulo = 44;
        break;
      case "onorabilita-c":
        this.idModulo = 53;
        break;
    }

    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", idAnagrafica)
      .set("idModulo", this.idModulo)
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
