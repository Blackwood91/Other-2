import { HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "../../mediazione.service";
import { ExtraInfoComponent } from "../../modals/extra-info/extra-info.component";
import { SaveFileComponent } from "../../modals/save-file/save-file.component";
import { SharedService } from "../../shared.service";
import { ConfirmMessageComponent } from "src/app/principal-components/confirm-message/confirm-message.component";

@Component({
  selector: "app-spese-mediazione",
  templateUrl: "./spese-mediazione.component.html",
  styleUrls: ["./spese-mediazione.component.css"],
})
export class SpeseMediazioneComponent implements OnInit {
  isModifica: boolean = false;
  idRichiesta: number = 0;
  selectedFilePDF: any;
  allegatoPdf: File | null = null;
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  isConvalidato: boolean = false;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  stato: string = "";
  tabella: number = 2;

  isInserito: boolean = false;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.loadTableSpeseMed();
      this.loadIsConvalidato();
      this.checkSpeseAlreadyInserito();
    });
  }

  onInitSpeseMed() {
    this.loadTableSpeseMed();
  }

  loadTableSpeseMed() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSpeseMed("statoModuli/getAllSpeseMed", params)
      .subscribe((res: any) => {
        // PER RIFERIMENTO DEL RADIO BUTTON PRECEDENTEMENTE SCELTO
        if (res.result.length == 0 || res.result[0].idRiferimento != 1) {
          // Solo è stato creato il modulo per la richiesta verra valorizzato il radio al caricamento della pagina
          if (res.result[0] != null && res.result[0].idRiferimento != 1) {
            this.tabella = 2;
          }
          this.indexPage = 1;
          this.tableResult = new Array();
          this.totalPage = 0;
          this.totalResult = 0;
        } else {
          this.tabella = 1;
          this.tableResult = res.result;
          this.totalPage = Math.ceil(res.totalResult / 10);
          this.totalResult = res.totalResult;
        }

        this.checkSpeseAlreadyInserito();
      });
  }

  openModalLoadFile(
    id: number /* parametro per capire da chi proviene la richiesta*/
  ) {
    if (this.isModifica) {
      this.saveFile.openModal("Aggiornamento nuovo file", id);
    }
  }

  getFileModalChildren(
    params: any /* parametro per capire da chi proviene la richiesta*/
  ) {
    // LA STRUTTURA DEI PERCORSI E DEL CARICAMENTO DEI FILE SARA CENTRALIZZATO DA UN UNICO METODO CHE SI OCCUPERA' DI CIO'
    this.updateFileSpeseMed(params.selectedFilePDF, params.id);
  }

  openLoadFile() {
    this.showFilePdf = true;
  }

  openUpdateFile() {
    this.showFilePdf = true;
  }

  onFilePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
  }

  onFileUpdatePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  activeModifica() {
    this.isModifica = true;
    this.allegatoPdf = null;
  }

  changeOptionTabella(tabella: number) {
    // Per non far rimanere le scelte selezionate entrambe impostare momentaneamente il nuovo valore
    this.tabella = tabella;

    if (tabella == 2 && this.tableResult.length > 0) {
      // Per non avere problemi con la selezione multipla per la troppa velocità nel cambiamento
      setTimeout(() => {
        const buttonActiveModal = document.getElementById("labRad1");
        buttonActiveModal!.click();
        this.tabella = 1;
        this.checkSpeseAlreadyInserito();
      }, 200);

      this.sharedService.onMessage(
        "error",
        "Per proseguire con il cambio di scelta, è necessario prima eliminare i file presenti nella tabella"
      );
      return;
    } else {
      this.tabella = tabella;
      this.checkSpeseAlreadyInserito();
    }
  }

  openPdfFile(id: number) {
    const params = new HttpParams().set("id", id).set("idRichiesta", this.idRichiesta);

    this.serviceME.getFileSpeseMed("pdf/getFileModulo", params).subscribe({
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

  openModalConfirmMessage(id: number) {
    let message = "Sei sicuro di voler sovrascrivere il file attuale con uno nuovo?";
    this.confirmMessageComponent.openModal(id, message);
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      this.openModalLoadFile(event.idRitorno);
    }
  }

  send() {
    if (this.tableResult.length > 0) {
      this.isModifica = false;
      return;
    }
    

    let fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF?.name,
      idRiferimento: this.tabella,
    };

    let formData: FormData = new FormData();
    if (this.tabella != 1 && this.tabella != 2) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire con il salvataggio è necessario prima fare una scelta per la tabella"
      );
      return;
    } else if (this.tabella == 2 && this.tableResult.length > 0) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire con la scelta di Dichiarazione di adozione, è necessario prima eliminare i file presenti"
      );
      return;
    } else if (this.tabella == 1 && this.tableResult.length > 0) {
      // Solo se la scelta è "Tabella delle spese di mediazione" verrà inserito anche il file nella richiesta
   //   formData.append("filePdf", this.selectedFilePDF);
   //   fileAllegatoDto.nomeFile = this.selectedFilePDF.name;
    }
    else if(this.tabella == 1 && this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);
    //fileAllegatoDto.nomeFile = this.selectedFilePDF.name;

    this.serviceME
      .saveFileSpeseMed("statoModuli/saveFileSpeseMed", formData)
      .subscribe({
        next: (res: any) => {
          this.isInserito = true;
          this.sharedService.onMessage("success", "L'inserimento è avvenuto con successo!");
          this.loadTableSpeseMed();
          this.isModifica = false;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  convalidazione() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/speseMediazione", params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSpeseMed();
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

  loadIsConvalidato() {
    const params = new HttpParams()
      .set("idModulo", 73)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getCheckConvalidato("status/getModuloIsConvalidato", params)
      .subscribe((res: any) => {
        this.isConvalidato = res.isConvalidato;
      });
  }

  updateFileSpeseMed(selectedFilePDF: any, id: number) {
    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: id,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: selectedFilePDF.name,
      idRiferimento: this.tabella,
    };

    let formData: FormData = new FormData();
    if (this.tabella != 1 && this.tabella != 2) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire con il salvataggio è necessario prima fare una scelta per la tabella"
      );
      return;
    } else if (this.tabella == 2 && this.tableResult.length > 0) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire con la scelta di Dichiarazione di adozione, è necessario prima eliminare i file presenti"
      );
      return;
    } else if (this.tabella == 1) {
      // Solo se la scelta è "Tabella delle spese di mediazione" verrà inserito anche il file nella richiesta
      formData.append("filePdf", selectedFilePDF);
    }

    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );

    this.serviceME
      .saveFileSpeseMed("statoModuli/updateFileSpeseMed", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSpeseMed();
          this.sharedService.onMessage(
            "success",
            "L'aggiornamento del file è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  deleteFileSpeseMed(id: number) {
    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: id,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: "",
      idRiferimento: this.tabella,
    };

    let formData: FormData = new FormData();
    formData.append("fileAllegatoDto", new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" }));

    this.serviceME
      .saveFileSpeseMed("statoModuli/deleteFileSpeseDiMediazione", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.allegatoPdf = null;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSpeseMed();
          this.sharedService.onMessage("success", "La cancellazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  openModal() {
    const buttonActiveModal = document.getElementById(
      "activeModalTabellaAllegatoA"
    );
    buttonActiveModal!.click();
  }

  cambiaPaginaSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSpeseMed("statoModuli/getAllSpeseMed", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSpeseMed("statoModuli/getAllSpeseMed", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPageSO(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSpeseMed("statoModuli/getAllSpeseMed", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllSpeseMed("statoModuli/getAllSpeseMed", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  checkSpeseAlreadyInserito() {
    const params = new HttpParams()
    .set("idModulo", 73)
    .set("idRichiesta", this.idRichiesta);

    if(this.tabella === 1) {
      if(this.totalResult === null || this.totalResult === 0)
        this.isInserito = false;
      else
        this.isInserito = true; 


      /*this.serviceME.isConvalidato("statoModuli/getSpeseIsInserito", params)
      .subscribe({
        next: (res: any) => {
          this.isInserito = res.isInserito; //exist;
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });*/
    }
    else {
      this.isInserito = false;
    }

  }

}
