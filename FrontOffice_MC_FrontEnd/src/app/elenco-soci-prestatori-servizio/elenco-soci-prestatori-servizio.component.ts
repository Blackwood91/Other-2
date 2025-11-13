import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import * as moment from "moment";
import { AccessibilitaService } from "src/accessibilita.service";
import { MediazioneService } from "../mediazione.service";
import { ExtraInfoComponent } from "../modals/extra-info/extra-info.component";
import { RiepilogoSchedaPrestatoreServizioComponent } from "../modals/riepilogo-scheda-prestatore-servizio/riepilogo-scheda-prestatore-servizio.component";
import { SchedaPrestatoreServizioComponent } from "../modals/scheda-prestatore-servizio/scheda-prestatore-servizio.component";
import { MessageComponent } from "../principal-components/message/message.component";
import { SharedService } from "../shared.service";

@Component({
  selector: "app-elenco-soci-prestatori-servizio",
  templateUrl: "./elenco-soci-prestatori-servizio.component.html",
  styleUrls: ["./elenco-soci-prestatori-servizio.component.css"],
})
export class ElencoSociPrestatoriServizioComponent implements OnInit {
  @Input()
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  @ViewChild(SchedaPrestatoreServizioComponent)
  schedaPrestatoreServizioComponent!: SchedaPrestatoreServizioComponent;
  @ViewChild(RiepilogoSchedaPrestatoreServizioComponent)
  riepilogoSchedaPrestatoreServizioComponent!: RiepilogoSchedaPrestatoreServizioComponent;
  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

  idComuneNascita: any;
  comuniNascita = new Array();
  fileDocumento: any = null;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private router: Router,
    private accessibilita: AccessibilitaService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.loadAnagrafiche();
    this.loadComuni();
    this.moduloIsConvalidato();
    this.loadTable();
  }

  activeModifica() {
    this.isModifica = true;
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  updateAnagrafica(idAnagrafica: number) {
    this.schedaPrestatoreServizioComponent.openModalUpdate(
      this.idRichiesta,
      idAnagrafica
    );
  }

  loadAnagrafiche() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
        },
      });
  }

  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe({
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
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaPrestatori(
        "anagrafica/getAllAnagraficaPrestatori",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  loadComuni() {
    this.serviceME.getAllComune("comune/getAllComune").subscribe({
      next: (res: any) => {
        this.comuniNascita = res;
      },
    });
  }

  filterComune(id: number) {
    let comune = this.comuniNascita.find((obj: { idCodComune: number }) => {
      return obj.idCodComune === id;
    });
    if (comune) return comune.nomeComune;
  }

  viewAnagrafica(idAnagrafica: number) {
    this.riepilogoSchedaPrestatoreServizioComponent.openModal(
      this.idRichiesta,
      idAnagrafica
    );
  }

  moduloIsConvalidato() {
    const params = new HttpParams()
      .set("idModulo", 35)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getModuloIsConvalidato("status/getModuloIsConvalidato", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  getModalChildren(event: any) {
    this.isModifica = false;
    this.sharedService.onUpdateMenu();
    this.loadAnagrafiche();
    this.moduloIsConvalidato();
  }

  deleteAnagrafica(idAnagrafica: number) {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", idAnagrafica);

    this.serviceME
      .deleteAnagraficaPrestatore(
        "anagrafica/deleteAnagraficaPrestatore",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.loadAnagrafiche();
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  anteprimaPdf() {
    this.serviceME
      .getFileDomandaIscrizione(
        "pdf/getAnteprimaFilePrestaSerOpe",
        this.idRichiesta
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
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], { type: "application/pdf" });

    return new File([blobFile], "file");
  }

  convalidazione() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneElencoPrestServizio(
        "status/convalidazioneElencoPrestServizio",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.moduloIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
          return;
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile proseguire con la convalidazione dell'elenco"
          );
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
          return;
        },
      });
  }

  openPdfFile(idAnagrafica: number) {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", idAnagrafica);

    this.serviceME
      .getFileRappresentante("pdf/getFilePrestatore", params)
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

  // getFileDocumento() {
  //   var file = new Blob([this.convertiStringaBlobAFile(this.fileDocumento)], { type: 'application/pdf' });
  //   var fileURL = URL.createObjectURL(file);
  //   window.open(fileURL, '_blank');
  // }
}
