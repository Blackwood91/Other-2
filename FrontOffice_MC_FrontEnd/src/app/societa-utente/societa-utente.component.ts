import { HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { MediazioneService } from "../mediazione.service";
import { AggiornamentoSocietaComponent } from "../modals/aggiornamento-societa/aggiornamento-societa.component";
import { FinalizzaRichiestaComponent } from "../modals/finalizza-richiesta/finalizza-richiesta.component";
import { MessageComponent } from "../principal-components/message/message.component";
import { SharedService } from "../shared.service";
@Component({
  selector: "app-societa-utente",
  templateUrl: "./societa-utente.component.html",
  styleUrls: ["./societa-utente.component.css"],
})
export class SocietaUtenteComponent implements OnInit {
  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(AggiornamentoSocietaComponent)
  aggiornamentoSocietaComponent!: AggiornamentoSocietaComponent;
  @ViewChild(FinalizzaRichiestaComponent)
  finalizzaRichiestaComponent!: FinalizzaRichiestaComponent;

  // SOCIETA SELEZIONATA PER APERTURA DELLA MODALE
  singolaSocieta = {
    id: 0,
    ragioneSociale: "",
    partitaIva: "",
    statoRichiesta: "",
    statoRichiestaEf: "",
    codiceFiscaleSocieta: "",
  };
  // RAPPRESENTA LA RICHIESTA SINGOLA ASSOCIATA ALLA SOCIETA SELEZIONATA
  richiesta: any = {};
  // PER VERIFICA DELLA ESISTENZA O NO DI RICHIESTA JOB ASSOCIATO
  statusJobRichiesta: string = "";

  // PER VERIFICA DELLA ESISTENZA O NO DI DELLA RICHIESTA INVIATA
  //existRichiestaInviata: boolean = false;
  // VERRA' VALORIZZATO POST SELEZIONE DELLA SOCIETA'
  registroMediazione = {};
  registroEnte = {};
  // PER SELECT DELLE TIPOLOGIE
  tipologieROdm: any;
  tipologieREf: any;
  // TIPOLOGIA SELEZIONATA
  tipologiaRichiestaOdm: any = 49;
  tipologiaRichiestaEf: number = 0;
  selectId4Odm = false;

  isFinalizza: boolean = false;
  // VAR TABELLA
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  ngOnInit() {
    this.loadTable();
  }

  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1);

    this.serviceME.getAllSocietaUtente("societa/getAll", params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      },
      error: (error: any) => {
        this.sharedService.onMessage("error", error.error);
      },
    });
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1);

    this.serviceME
      .getAllSocietaUtente("societa/getAll", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 1);

    this.serviceME
      .getAllSocietaUtente("societa/getAll", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index);

    this.serviceME
      .getAllSocietaUtente("societa/getAll", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 2);

    this.serviceME
      .getAllSocietaUtente("societa/getAll", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }
  //FINE FUNZIONI TABELLA

  //FUNZIONI MODAL
  addSocieta() {
    var buttonActiveModal = document.getElementById("activeModalUpdateSocieta");
    // Esegui il click sul bottone nascosto "activeModalUpdateSocieta"
    this.singolaSocieta = {
      id: "" as unknown as number,
      ragioneSociale: "",
      partitaIva: "",
      statoRichiesta: "",
      statoRichiestaEf: "",
      codiceFiscaleSocieta: "",
    };
    buttonActiveModal!.click();
  }

  //FINE FUNZIONI MODAL

  routerDomandaIscrizione(idRichiesta: number) {
    window.location.href =
      "/organismiDiMediazione?vistaMenu=domanda_di_iscrizione&idRichiesta=" +
      idRichiesta;
  }

  routerRichiesteInviate(idRichiesta: number) {
    window.location.href = "/richiesteInviate?idRichiesta=" + idRichiesta;
  }

  openModalSaveSocieta(
    idSocieta: number,
    ragioneSociale: string,
    partitaIva: string,
    codiceFiscaleSocieta: string
  ) {
    this.aggiornamentoSocietaComponent.openModal(
      idSocieta,
      ragioneSociale,
      partitaIva,
      codiceFiscaleSocieta
    );
  }

  getModalSaveSocieta(event: any) {
    this.loadTable();
  }

  getModalFinalizza(event: any) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1);
    
    const params2 = new HttpParams().set("idRichiesta", this.singolaSocieta.id);

    this.serviceME.getAllSocietaUtente("societa/getAll", params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result;

        this.serviceME
          .getRichiestaForSocieta("richiesta/getRichiestaForSocieta", params2)
          .subscribe((res: any) => {
            if (res != null) {
              this.richiesta = res;
              this.singolaSocieta = {
                id: res.idRichiesta,
                ragioneSociale: res.ragioneSociale,
                partitaIva: res.partitaIva,
                statoRichiesta: res.statoRichiesta,
                statoRichiestaEf: res.statoRichiestaEf,
                codiceFiscaleSocieta: res.codiceFiscaleSocieta,
              };
              this.loadSocietaSelezionata();
            } else {
              this.richiesta = null;
            }
          });

      },
      error: (error: any) => {
        this.sharedService.onMessage("error", error.error);
      },
    });
  }

  getDescTipoRic(idTipoRichiedente: number) {
    if (idTipoRichiedente == 1) {
      return "Privato";
    } else if (idTipoRichiedente == 2) {
      return "Pubblico - Diverso da CCIAA e Ordine Professionale";
    } else if (idTipoRichiedente == 3) {
      return "Pubblico - CCIAA e Ordine Avvocati";
    } else if (idTipoRichiedente == 4) {
      return "Pubblico - Ordini Professionali Non Avvocati";
    } else {
      return "";
    }
  }

  //FINE---------------------------------------------

  //Serve per poter ricevere funzioni dichiarata dentro al COMPONENT figlio
  onConfirmedRequestDelete(event: string) {}

  openPanelRichieste(societa: any) {
    this.singolaSocieta = societa;
    // Cambiera il nome della società nell'header
    this.sharedService.onNameSocietaHeader(this.singolaSocieta.ragioneSociale);

    /*    if (this.singolaSocieta.statoRichiestaEf === '') {
      this.serviceME.getAllTipoRichiedente('api/tipoRichiedente/getAllEf')
        .subscribe((res: any) => {
          this.tipologieREf = res;
        });
    }*/

    // LOAD RICHIESTA ODM
    if (this.singolaSocieta.statoRichiesta !== "") {
      const params = new HttpParams().set(
        "idRichiesta",
        this.singolaSocieta.id
      );

      this.serviceME
        .getRichiestaForSocieta("richiesta/getRichiestaForSocieta", params)
        .subscribe((res: any) => {
          this.richiesta = res;

          // LOAD SELECT IN CASO RICHIESTA NON ESISTENTE
          if (this.richiesta === null) {
            this.serviceME
              .getAllTipoRichiedente("api/tipoRichiedente/getAll")
              .subscribe((res: any) => {
                this.tipologieROdm = res;
              });
          }
        });
    }

    // ESSENDO UTILIZZATA SOLO IN FASE DI APERTURA DI TUTTE LE RICHIESTE, IL PANNELLO DELL'ODM SARA QUELLO APERTO DI DEFAULT
    this.loadModRicAndFE("ODM");

    // VERIFICA SE LA RICHIESTA E' STATA INVIATA
    //this.loadExistRichistaInviata();

    // APERTURA E CHIUSURE PER LA PARTE RIGUARDANTE LA RICHIESTA
    var buttonActivePanelRichieste = document.getElementById(
      "btnOpenRichiestePanel"
    );

    var panelRichieste = document.getElementById("sub-tables");
    panelRichieste!.classList.remove("show");
    buttonActivePanelRichieste!.click();

    setTimeout(() => {
      panelRichieste!.scrollIntoView({
        behavior: "smooth",
      });
    }, 200);
  }

  changeTipologiaRichiestaOdm(event: any) {
    this.selectId4Odm = event.target.value == 3 ? true : false;
  }

  insertRichiestaODM() {
    let richiestaODM = {
      idSocieta: this.singolaSocieta.id,
      idTipoRichiedente: this.tipologiaRichiestaOdm,
      idTipoRichiesta: 1,
    };

    this.serviceME
      .insertRichiestaODM("societa/richiestaIscrizioneODM", richiestaODM)
      .subscribe({
        next: (res: any) => {
          this.loadTable();
          this.loadSocietaSelezionata();

          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "L'inserimento non è andato a buon fine"
          );
        },
      });
  }

  invioRichiestaADM() {
    // ...in attesa di dettagli
  }

  invioRichiestaEF() {
    let richiesta = {
      idSocieta: this.singolaSocieta.id,
      idTipoRichiedente: this.tipologiaRichiestaOdm,
      idTipoRichiesta: 1,
    };

    this.serviceME
      .insertRichiestaEF("societa/richiestaIscrizioneEF", richiesta)
      .subscribe({
        next: (res: any) => {},
        error: (error: any) => {},
      });
  }

  loadSocietaSelezionata() {
    // ASSEGNAZIONE NUOVI VALORI CON LA RICHIESTA CREATA
    const params = new HttpParams().set("idRichiesta", this.singolaSocieta.id);

    this.serviceME
      .getRichiestaForSocieta("richiesta/getRichiestaForSocieta", params)
      .subscribe((res: any) => {
        if (res != null) {
          this.richiesta = res;
          this.singolaSocieta = {
            id: res.idRichiesta,
            ragioneSociale: res.ragioneSociale,
            partitaIva: res.partitaIva,
            statoRichiesta: res.statoRichiesta,
            statoRichiestaEf: res.statoRichiestaEf,
            codiceFiscaleSocieta: res.codiceFiscaleSocieta,
          };
        } else {
          this.richiesta = null;
        }
      });
  }

  loadModRicAndFE(tipologiaRichiesta: string) {
    const params = new HttpParams()
      .set("idRichiesta", this.singolaSocieta.id)
      .set("tipoRichiesta", tipologiaRichiesta);

    this.serviceME
      .statusJobRichiesta("jobRichiesta/statusJobRichiesta", params)
      .subscribe({
        next: (res: any) => {
          // VERIFICHERA LO STATUS DELLA RICHIESTA JOB ASSOCIATA A QUEL IDRICHIESTA
          this.statusJobRichiesta = res.status;
        },
      });
  }

  finalizzaRichiestaODM() {
    if (this.statusJobRichiesta === "N" || this.statusJobRichiesta === "SN") {
      const paramsIR = new HttpParams().set(
        "idRichiesta",
        this.singolaSocieta.id
      );

      this.serviceME
        .insertRichiestaEF("finalizza/finalizzazioneRichiestaODM", paramsIR)
        .subscribe({
          next: (res: any) => {
            this.sharedService.onMessage(
              "success",
              "La finalizzazione è avvenuta con successo!"
            );
            this.loadTable();
            this.loadSocietaSelezionata();
            this.loadModRicAndFE("ODM");
            this.finalizzaRichiestaComponent.openModal(this.singolaSocieta.id);
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error);
          },
        });
    } else {
      // APERTURA DIRETTA DELLA MODALE DEL FINALIZZA
      this.finalizzaRichiestaComponent.openModal(this.singolaSocieta.id);
    }
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

  openModalRichiesteInviate() {}
}
