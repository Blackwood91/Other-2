import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { ExtraInfoComponent } from "src/app/modals/extra-info/extra-info.component";
import { ModalMediatoriComponent } from "src/app/modals/modal-mediatori/modal-mediatori.component";
import { UpdateMediatoreComponent } from "src/app/modals/update-mediatore/update-mediatore.component";
import { ConfirmMessageComponent } from "src/app/principal-components/confirm-message/confirm-message.component";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-gestione-mediatori-a",
  templateUrl: "./gestione-mediatori-a.component.html",
  styleUrls: ["./gestione-mediatori-a.component.css"],
})
export class GestioneMediatoriAComponent implements OnInit {
  @Input()
  idRichiesta = 0;
  @Input()
  component = "";
  viewOnly: boolean = false;
  @ViewChild(ModalMediatoriComponent)
  modalMediatoriComponent!: ModalMediatoriComponent;
  @ViewChild(ConfirmMessageComponent)
  confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(UpdateMediatoreComponent)
  updateMediatoreComponent!: UpdateMediatoreComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

  isModifica: boolean = false;
  idAnagrafica: number = 0;
  isConvalidato: boolean = false;
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
  medTelefono: string = "";
  medCellulare: string = "";
  medFax: string = "";
  comuniNascita = new Array();
  // DATI TABELLA
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  fileDocumento: any = null;
  searchTextTable: string = "";

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.getAllMediatori();
    this.loadIsConvalidato();
    this.loadTable();
  }

  getAllMediatori() {
    // TO DO PAGABLE
    const params = new HttpParams()
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
        },
      });
  }

  loadIsConvalidato() {
    const params = new HttpParams()
      .set("idModulo", 38)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getModuloIsConvalidato("status/isConvalidatoAllModuli", params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
      });
  }

  anteprimaPdf() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAnteprimaFileAppeA("pdf/getAnteprimaFileAppeA", params)
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

  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
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
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
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
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
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
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
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
      .getAllAnagrafica("anagrafica/getAllAnagraficaMediatoriA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
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

  public getRowsGen() {
    return this.tableResult.filter((i) => i.idTipoAnagrafica == "4").length;
  }

  viewAnagrafica(idAnagrafica: number) {
    this.modalMediatoriComponent.openModal(idAnagrafica);
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }
/*
  riepilogoAnagrafica(idAnagrafica: number) {
    this.updateMediatoreComponent.openModalRiepilogo(
      idAnagrafica,
      this.idRichiesta
    );
  }
*/

riepilogoAnagrafica(idAnagrafica: number, componente: string) {
  this.updateMediatoreComponent.openModalRiepilogo(idAnagrafica, this.idRichiesta, componente);
}

updateAnagrafica(idAnagrafica: number, componente: string) {
  this.updateMediatoreComponent.openModal(idAnagrafica, this.idRichiesta, componente);
}

  // updateAnagrafica(idAnagrafica: number) {
  //   this.updateMediatoreComponent.openModal(idAnagrafica, this.idRichiesta);
  // }

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

  openModalConfirmMessage(
    idAnagrafica: number,
    tipoRap: string,
    nomeRap: string
  ) {
    let message = "Si vuole confermare la cancellazione del " + tipoRap + " " + nomeRap + "?";
    this.confirmMessageComponent.openModal(idAnagrafica, message);
  }

  activeModifica() {
    this.isModifica = true;
  }

  activeViewOnly() {
    this.viewOnly = true;
  }

  inactiveViewOnly() {
    this.viewOnly = false;
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams()
        .set("idRichiesta", this.idRichiesta)
        .set("idAnagrafica", event.idRitorno);

      this.serviceME
        .deleteCompOrgAm("anagrafica/deleteMediatore", params)
        .subscribe({
          next: (res: any) => {
            this.sharedService.onUpdateMenu();
            this.getAllMediatori();
            this.loadIsConvalidato();

            this.isModifica = false;
            this.sharedService.onMessage(
              "success",
              "La cancellazione è avvenuta con successo!"
            );
          },
          error: (error: any) => {
            this.sharedService.onMessage("error", error);
          },
        });
    }

    this.isModifica = false;
  }

  getModalChildrenUpdate(event: any) {
    this.sharedService.onUpdateMenu();
    this.getAllMediatori();
    this.loadIsConvalidato();

    this.isModifica = false;
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
          this.loadIsConvalidato();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
          return;
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile proseguire con la convalidazione"
          );
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
          return;
        },
      });
  }

  getModalChildren(event: any) {
    this.isModifica = false;
    this.sharedService.onUpdateMenu();
    this.getAllMediatori();
    this.loadIsConvalidato();
  }
}
