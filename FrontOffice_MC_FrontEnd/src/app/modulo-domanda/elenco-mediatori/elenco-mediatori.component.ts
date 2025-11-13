import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { ExtraInfoComponent } from "src/app/modals/extra-info/extra-info.component";
import { ModalMediatoriComponent } from "src/app/modals/modal-mediatori/modal-mediatori.component";
import { UpdateMediatoreComponent } from "src/app/modals/update-mediatore/update-mediatore.component";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-elenco-mediatori",
  templateUrl: "./elenco-mediatori.component.html",
  styleUrls: ["./elenco-mediatori.component.css"],
})
export class ElencoMediatoriComponent implements OnInit {
  @Input()
  idRichiesta = 0;
  @Input()
  component = "";
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  @ViewChild(ModalMediatoriComponent)
  modalMediatoriComponent!: ModalMediatoriComponent;
  @ViewChild(UpdateMediatoreComponent)
  updateMediatoreComponent!: UpdateMediatoreComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

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
  medTelefono: string = "";
  medCellulare: string = "";
  medFax: string = "";
  totalResTables = 0;
  indexPageMG: number = 1;
  tableResultMG = new Array();
  totalPageMG = 0;
  totalResultMG = 0;
  searchTextTableMG = "";
  indexPageII: number = 1;
  tableResultII = new Array();
  totalPageII = 0;
  totalResultII = 0;
  searchTextTableII = "";
  indexPageMC: number = 1;
  tableResultMC = new Array();
  totalPageMC = 0;
  totalResultMC = 0;
  searchTextTableMC = "";

  viewOnly: boolean = false;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute,
              private sharedService: SharedService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.loadTables();
      this.loadIsConvalidato();
    });
  }
/*
  updateAnagrafica(idAnagrafica: number, idTipoAnagrafica: number) {
    this.updateMediatoreComponent.openModalElenco(
      idAnagrafica,
      this.idRichiesta,
      idTipoAnagrafica
    );
  }

  riepilogoAnagrafica(idAnagrafica: number) {
    this.updateMediatoreComponent.openModalRiepilogo(
      idAnagrafica,
      this.idRichiesta
    );
  }
*/

  updateAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModal(idAnagrafica, this.idRichiesta, componente);
  }

  riepilogoAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModalRiepilogo(idAnagrafica, this.idRichiesta, componente);
  }

  activeViewOnly() {
    this.viewOnly = true;
  }

  inactiveViewOnly() {
    this.viewOnly = false;
  }

  loadTables() {
    this.loadTableMG();
    this.loadTableII();
    this.loadTableMC();
  }

  getModalChildrenUpdate(event: any) {
    this.sharedService.onUpdateMenu();
    //this.getAllMediatori();
    this.loadTables()
    this.loadIsConvalidato();

    this.isModifica = false;
  }

  // FUNZIONI MEDIATORI GENERICI
  loadTableMG() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMG)
      .set("indexPage", this.indexPageMG - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedGen(
        "anagrafica/getAllAnagraficaMediatoriA",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.tableResultMG = res.result;
          this.totalPageMG = Math.ceil(res.totalResult / 10);
          this.totalResultMG = res.totalResult;
        },
      });
  }

  attivaRicercaMG() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMG)
      .set("indexPage", this.indexPageMG - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedGen(
        "anagrafica/getAllAnagraficaMediatoriA",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMG = res.result;
        this.totalPageMG = Math.ceil(res.totalResult / 10);
        this.totalResultMG = res.totalResult;
      });
  }

  cambiaPaginaMG(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMG)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedGen(
        "anagrafica/getAllAnagraficaMediatoriA",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMG = res.result;
      });

    this.indexPageMG = index;
  }

  nextPageMG(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMG)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedGen(
        "anagrafica/getAllAnagraficaMediatoriA",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMG = res.result;
      });

    this.indexPageMG = index + 1;
  }

  previousPageMG(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMG)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedGen(
        "anagrafica/getAllAnagraficaMediatoriA",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMG = res.result;
      });

    this.indexPageMG = index - 1;
  }

  // FUNZIONI MEDIATORI INTERNAZIONALI
  loadTableII() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", this.indexPageII - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedInter(
        "anagrafica/getAllAnagraficaMediatoriB",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.tableResultII = res.result;
          this.totalPageII = Math.ceil(res.totalResult / 10);
          this.totalResultII = res.totalResult;
        },
      });
  }

  attivaRicercaII() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", this.indexPageII - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedInter(
        "anagrafica/getAllAnagraficaMediatoriB",
        params
      )
      .subscribe((res: any) => {
        this.tableResultII = res.result;
        this.totalPageII = Math.ceil(res.totalResult / 10);
        this.totalResultII = res.totalResult;
      });
  }

  cambiaPaginaII(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedInter(
        "anagrafica/getAllAnagraficaMediatoriB",
        params
      )
      .subscribe((res: any) => {
        this.tableResultII = res.result;
      });

    this.indexPageII = index;
  }

  nextPageII(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedInter(
        "anagrafica/getAllAnagraficaMediatoriB",
        params
      )
      .subscribe((res: any) => {
        this.tableResultII = res.result;
      });

    this.indexPageII = index + 1;
  }

  previousPageII(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMedInter(
        "anagrafica/getAllAnagraficaMediatoriB",
        params
      )
      .subscribe((res: any) => {
        this.tableResultII = res.result;
      });

    this.indexPageII = index - 1;
  }

  // FUNZIONI MEDIATORI MATERIA DI CONSUMO
  loadTableMC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableII)
      .set("indexPage", this.indexPageMC - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaMediatoriMatCons(
        "anagrafica/getAllAnagraficaMediatoriC",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.tableResultMC = res.result;
          this.totalPageMC = Math.ceil(res.totalResult / 10);
          this.totalResultMC = res.totalResult;
        },
      });
  }

  attivaRicercaMC() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMC)
      .set("indexPage", this.indexPageMC - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm(
        "anagrafica/getAllAnagraficaMediatoriC",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMC = res.result;
        this.totalPageMC = Math.ceil(res.totalResult / 10);
        this.totalResultMC = res.totalResult;
      });
  }

  cambiaPaginaMC(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMC)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm(
        "anagrafica/getAllAnagraficaMediatoriC",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMC = res.result;
      });

    this.indexPageMC = index;
  }

  nextPageMC(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMC)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm(
        "anagrafica/getAllAnagraficaMediatoriC",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMC = res.result;
      });

    this.indexPageMC = index + 1;
  }

  previousPageMC(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableMC)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm(
        "anagrafica/getAllAnagraficaMediatoriC",
        params
      )
      .subscribe((res: any) => {
        this.tableResultMC = res.result;
      });

    this.indexPageMC = index - 1;
  }

  loadIsConvalidato() {
    const params = new HttpParams()
      .set("idModulo", 26)
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

  openPdfFile() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta)
      .set("idAnagrafica", this.idAnagrafica);

    this.serviceME
      .getAnteprimaFileElencoMediatori(
        "pdf/getAnteprimaFileElencoMediatori",
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

  activeModifica() {
    this.isModifica = true;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  dettaglioMediatore(idAnagrafica: number) {
    this.modalMediatoriComponent.openModal(idAnagrafica);
  }

  updateMediatore(idAnagrafica: number) {
    // this.modalUpdateMediatore.openModal("titolo", idAnagrafica, this.idRichiesta);
  }

  convalidazione() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneElencoMediatore(
        "status/convalidazioneElencoMediatori",
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
        },
        error: (error: any) => {
          this.sharedService.onMessage(
            "error",
            "Non è stato possibile proseguire con la convalidiazione"
          );
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }
  
  /*
  openModalConfirmMessage(idAnagrafica: number, nomeRap: string) {
    let message = "Si vuole confermare la cancellazione del mediatore: " + nomeRap + "?";
    this.confirmMessageComponent.openModal(idAnagrafica, message);
    
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams()
        .set('idRichiesta', this.idRichiesta)
        .set('idAnagrafica', event.idRitorno);

      this.serviceME
        .deleteRappresentante('anagrafica/deleteRappresentante', params)
        .subscribe({
          next: (res: any) => {
            //this.isModifica = false;
            this.loadTables();
            //this.loadIsConvalidato();
            this.sharedService.onMessage('success', "La cancellazione del rappresentante è avvenuto con successo");
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
    }
  } */
}
