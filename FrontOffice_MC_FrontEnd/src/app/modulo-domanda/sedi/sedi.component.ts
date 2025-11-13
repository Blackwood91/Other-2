import { HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import * as moment from "moment";
import { DettaglioSedeComponent } from "src/app/modals/dettaglio-sede/dettaglio-sede.component";
import { ConfirmMessageComponent } from "src/app/principal-components/confirm-message/confirm-message.component";
import { MediazioneService } from "../../mediazione.service";
import { ExtraInfoComponent } from "../../modals/extra-info/extra-info.component";
import { SaveSedeComponent } from "../../modals/save-sede/save-sede.component";
import { SharedService } from "../../shared.service";

@Component({
  selector: "app-sedi",
  templateUrl: "./sedi.component.html",
  styleUrls: ["./sedi.component.css"],
})
export class SediComponent implements OnInit {
  @ViewChild(SaveSedeComponent) saveSedeComponent!: SaveSedeComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  @ViewChild(DettaglioSedeComponent)
  dettaglioSedeComponent!: DettaglioSedeComponent;
  @ViewChild(ConfirmMessageComponent)
  confirmMessageComponent!: ConfirmMessageComponent;
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  // DATI TABELLA
  searchTextTableSO: string = "";
  indexPageSO: number = 1;
  tableResultSO = new Array();
  totalPageSO = 0;
  totalResultSO = 0;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.loadTableSO();
      this.loadIsConvalidato();
    });
  }

  //FUNZIONI TABELLA
  loadTableSO() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", this.indexPageSO - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  attivaRicercaSO() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", this.indexPageSO - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
        this.totalPageSO = Math.ceil(res.totalResult / 10);
        this.totalResultSO = res.totalResult;
      });
  }

  cambiaPaginaSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index;
  }

  nextPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index + 1;
  }

  previousPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTableSO)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSediOperativeByRichiesta(
        "sede/getAllSediOperativeByIdRichiesta",
        params
      )
      .subscribe((res: any) => {
        this.tableResultSO = res.result;
      });

    this.indexPageSO = index - 1;
  }

  //FINE FUNZIONI TABELLA

  activeModifica() {
    this.isModifica = true;
  }

  loadIsConvalidato() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME.getStatusSedi("status/getStatusSedi", params).subscribe({
      next: (res: any) => {
        if (res.status === "c") {
          this.isConvalidato = true;
        } else {
          this.isConvalidato = false;
        }
      },
      error: (error: any) => {
        this.isConvalidato = false;
      },
    });
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  openModalSaveSedeUpdate(
    idSede: number,
    idComune: number,
    nomeComune: string,
    siglaProvincia: string
  ) {
    if (this.isModifica) {
      this.saveSedeComponent.openModalUpdate(
        this.idRichiesta,
        idSede,
        idComune,
        nomeComune,
        siglaProvincia
      );
    }
  }

  closeModalSaveSedeUpdate(event: any) {
    this.isModifica = false;
    this.loadTableSO();
    this.loadIsConvalidato();
    this.sharedService.onUpdateMenu();
  }

  getFilePlanimetria(idSede: number) {
    this.serviceME
      .getFilePlanimetria(
        "pdf/getFileSedePlanimetria",
        this.idRichiesta,
        idSede
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

  getFileCopiaContratto(idSede: number) {
    this.serviceME
      .getFileCopiaContratto(
        "pdf/getFileSedeCopiaContratto",
        this.idRichiesta,
        idSede
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

  openModalConfirmMessage(
    idSede: number,
    sedeLegale: string,
    infoSede: string
  ) {
    if (sedeLegale == "1") {
      this.sharedService.onMessage(
        "error",
        "Non si può proseguire con la cancellazione perchè la sede è legale"
      );
      return;
    }

    let message =
      "Si vuole confermare la cancellazione della sede sita in: " +
      infoSede +
      "?";
    this.confirmMessageComponent.openModal(idSede, message);
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams().set("idSede", event.idRitorno).set("idRichiesta", this.idRichiesta);      ;

      this.serviceME
        .deleteSedeOperativa("sede/deleteSedeOperativa", params)
        .subscribe({
          next: (res: any) => {
            this.isModifica = false;
            this.loadTableSO();
            this.loadIsConvalidato();
            this.sharedService.onUpdateMenu();
            this.sharedService.onMessage(
              "success",
              "La cancellazione è avvenuta con successo!"
            );
          },
          error: (error: any) => {
            this.sharedService.onMessage(
              "error",
              "La cancellazione non è andata a buon fine"
            );
          },
        });
    }
  }

  convalidazione() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/sediAttoRiepilogativo", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSO();
          this.sharedService.onMessage(
            "success",
            "La convalidazione è avvenuta con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
          this.extraInfoComponent.openModal(
            "Impossibile proseguire con la convalidazione:",
            error
          );
        },
      });
  }

  viewSede(idSede: number) {
    this.dettaglioSedeComponent.openModal(idSede);
  }
}
