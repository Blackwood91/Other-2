import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "../../mediazione.service";
import { ExtraInfoComponent } from "../../modals/extra-info/extra-info.component";
import { SaveFileComponent } from "../../modals/save-file/save-file.component";
import { SharedService } from "../../shared.service";
import { ConfirmMessageComponent } from "src/app/principal-components/confirm-message/confirm-message.component";

@Component({
  selector: "app-caricamento-file-odm",
  templateUrl: "./caricamento-file-odm.component.html",
  styleUrls: ["./caricamento-file-odm.component.css"],
})
export class CaricamentoFileOdmComponent implements OnInit {
  @Input()
  component!: string;
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;

  selectedFilePDF!: any;
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";
  showFilePdf: boolean = false;
  validDocumentPdf: boolean = true;
  @ViewChild(SaveFileComponent) saveFile!: SaveFileComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  idModulo: number = 0;
  stato: string = "";
  lavorazione: string = "";

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];

      if (this.component === "atto_costitutivo_odm") {
        this.idModulo = 4;
        this.loadTableAttoCostOdm();
      } else if (this.component === "statuto_organismo") {
        this.idModulo = 5;
        this.loadTableStatutoOrg();
      } else if (this.component === "regolamento_di_procedura") {
        this.idModulo = 16;
        this.loadTableRegProcedura();
      } else if (this.component === "atto_costitutivo_odm_non_autonomo") {
        this.idModulo = 71;
        this.loadTableAttoCostOdmNA();
      } else if (this.component === "statuto_organismo_non_autonomo") {
        this.idModulo = 72;
        this.loadTableStatutoOrgNA();
      } else if (this.component === "codice_etico") {
        this.idModulo = 17;
        this.loadTableCodiceEtico();
      } else if (this.component === "bilancio_certificazione_bancaria") {
        this.idModulo = 23;
        this.loadTableBilancio();
      }

      this.loadIsConvalidato();
    });
  }

  loadTableAttoCostOdm() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm("statoModuli/getAllAttoCostitutivoOdm", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrg() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllStatutoOrg("statoModuli/getAllStatutoOrg", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableAttoCostOdmNA() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAttoCostOdm("statoModuli/getAllAttoCostitutivoOdmNA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrgNA() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllStatutoOrg("statoModuli/getAllStatutoOrgNA", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableRegProcedura() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllRegProcedura("statoModuli/getAllRegProcedura", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableAttoCostNonAutonomo() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllAttoCostNonAutonomo(
        "statoModuli/getAllAttoCostNonAutonomo",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableStatutoOrgNonAutonomo() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllStatutoOrgNonAutonomo(
        "statoModuli/getAllStatutoOrgNonAutonomo",
        params
      )
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableCodiceEtico() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllCodiceEtico("statoModuli/getAllCodiceEtico", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  loadTableBilancio() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllBilancio("statoModuli/getAllBilancio", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  // FINE FUNZIONI BILANCIO ANNO PRECEDENTE

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
    this.updateFileOdm(params.selectedFilePDF, params.id);
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  public titoloPagina() {
    if (this.component === "atto_costitutivo_odm")
      return "ATTO COSTITUTIVO DELL'ORGANISMO DI MEDIAZIONE(CC)";
    else if (this.component === "statuto_organismo")
      return "STATUTO ORGANISMO (CC)";
    else if (this.component === "atto_costitutivo_odm_non_autonomo")
      return "ATTO COSTITUTIVO SOCIETA' (CC)";
    else if (this.component === "statuto_organismo_non_autonomo")
      return "STATUTO ORGANISMO SOCIETA'";
    else if (this.component === "regolamento_di_procedura")
      return "REGOLAMENTO DI PROCEDURA";
    else if (this.component === "spese_di_mediazione")
      return "SPESE DI MEDIAZIONE";
    else if (this.component === "codice_etico") return "CODICE ETICO";
    else if (this.component === "bilancio_certificazione_bancaria")
      return "BILANCIO ANNO PRECEDENTE/CERTIFICAZIONE BANCARIA";
    else if (this.component === "atto_costitutivo_odm_non_autonomo")
      return "ATTO COSTITUTIVO SOCIETA'";
    else if (this.component === "statuto_organismo_non_autonomo")
      return "STATUTO SOCIETA'";
    else return "errore";
  }

  activeModifica() {
    this.isModifica = true;
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
    if (this.idModulo === 4) {
      this.saveFileAttoCostOdm();
      return;
    } else if (this.idModulo === 5) {
      this.saveFileStatutoOrg();
      return;
    }
    if (this.idModulo === 71) {
      this.saveFileAttoCostOdmNA();
      return;
    } else if (this.idModulo === 72) {
      this.saveFileStatutoOrgNA();
      return;
    } else if (this.idModulo === 16) {
      this.saveFileRegProcedura();
      return;
    } else if (this.idModulo === 17) {
      this.saveFileCodiceEtico();
      return;
    } else if (this.idModulo === 23) {
      this.saveFileBilancio();
      return;
    } else if (this.idModulo === 71) {
      this.saveFileAttoCostNonAutonomo();
      return;
    } else if (this.idModulo === 72) {
      this.saveFileStatutoOrgNonAutonomo();
      return;
    }
  }

  convalidazione() {
    if (this.idModulo === 4) {
      this.convalidazioneAttoCostODM();
      return;
    } else if (this.idModulo === 5) {
      this.convalidazioneStatutoOrganismo();
      return;
    }
    if (this.idModulo === 71) {
      this.convalidazioneAttoCostODMNA();
      return;
    } else if (this.idModulo === 72) {
      this.convalidazioneStatutoOrganismoNA();
      return;
    } else if (this.idModulo === 16) {
      this.convalidazioneRegolamentoProcedura();
      return;
    } else if (this.idModulo === 17) {
      this.convalidazioneCodiceEtico();
      return;
    } else if (this.idModulo === 23) {
      this.convalidazioneBilancio();
      return;
    } else if (this.idModulo === 71) {
      this.convalidazioneAttoCostNonAutonomo();
      this.loadTableAttoCostOdmNA();
      return;
    } else if (this.idModulo === 72) {
      this.convalidazioneStatutoOrgNonAutonomo();
      this.loadTableStatutoOrgNonAutonomo();
      return;
      
    }
  }

  // OPENING FILE
  openPdfFile(id: number) {
    const params = new HttpParams().set("id", id).set("idRichiesta", this.idRichiesta);

    this.serviceME.getFileAttoCostOdm("pdf/getFileModulo", params).subscribe({
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

  // INSERIMENTI FILE
  // FUNZIONI ATTO COSTITUTIVO ODM

  saveFileAttoCostOdm() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileAttoCostitutivoOdm(
        "statoModuli/saveFileAttoCostitutivoOdm",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableAttoCostOdm();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileStatutoOrg() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileStatutoOrg("statoModuli/saveFileStatutoOrg", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableStatutoOrg();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileAttoCostOdmNA() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileAttoCostitutivoOdmNA(
        "statoModuli/saveFileAttoCostitutivoOdmNA",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableAttoCostOdmNA();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileStatutoOrgNA() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileStatutoOrgNA("statoModuli/saveFileStatutoOrgNA", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableStatutoOrgNA();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileRegProcedura() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileRegProcedura("statoModuli/saveFileRegProcedura", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableRegProcedura();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileCodiceEtico() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileCodiceEtico("statoModuli/saveFileCodiceEtico", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableCodiceEtico();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileAttoCostNonAutonomo() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileAttoCostNonAutonomo(
        "statoModuli/saveFileAttoCostNonAutonomo",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableAttoCostNonAutonomo();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileStatutoOrgNonAutonomo() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileStatutoOrgNonAutonomo(
        "statoModuli/saveFileStatutoOrgNonAutonomo",
        formData
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableStatutoOrgNonAutonomo();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  saveFileBilancio() {
    if(this.selectedFilePDF == null) {
      this.sharedService.onMessage(
        "error",
        "Per proseguire è necessario inserire un file"
      );
      return;
    }

    const fileAllegatoDto: {
      id: number;
      idRichiesta: number;
      file: any;
      nomeFile: string;
      idRiferimento: number;
    } = {
      id: 0,
      idRichiesta: this.idRichiesta,
      file: null,
      nomeFile: this.selectedFilePDF.name,
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", this.selectedFilePDF);

    this.serviceME
      .saveFileBilancio("statoModuli/saveFileBilancio", formData)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.loadTableBilancio();
          this.sharedService.onMessage(
            "success",
            "L'inserimento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  convalidazioneAttoCostODM() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/attoCostitutivoODM", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableAttoCostOdm();
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

  convalidazioneStatutoOrganismo() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/statutoOrganismo", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableStatutoOrg();
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

  convalidazioneAttoCostODMNA() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/attoCostitutivoODMNA", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableAttoCostOdm();
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

  convalidazioneStatutoOrganismoNA() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/statutoOrganismoNA", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableStatutoOrgNA();
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

  convalidazioneRegolamentoProcedura() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneSedi("status/regolamentoProcedura", params)
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableRegProcedura();
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

  convalidazioneCodiceEtico() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME.convalidazioneSedi("status/codiceEtico", params).subscribe({
      next: (res: any) => {
        this.isModifica = false;
        this.sharedService.onUpdateMenu();
        this.loadIsConvalidato();
        this.loadTableCodiceEtico();
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

  convalidazioneAttoCostNonAutonomo() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneAttoCostNonAutonomo(
        "status/convalidazioneAttoCostNonAutonomo",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableAttoCostNonAutonomo();
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

  convalidazioneStatutoOrgNonAutonomo() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .convalidazioneStatutoOrgNonAutonomo(
        "status/convalidazioneStatutoOrgNonAutonomo",
        params
      )
      .subscribe({
        next: (res: any) => {
          this.isModifica = false;
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableStatutoOrgNonAutonomo();
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

  convalidazioneBilancio() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME.convalidazioneSedi("status/bilancio", params).subscribe({
      next: (res: any) => {
        this.isModifica = false;
        this.sharedService.onUpdateMenu();
        this.loadIsConvalidato();
        this.loadTableBilancio();
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
      .set("idModulo", this.idModulo)
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

  // FUNZIONI CENTRALIZZATE PER TUTTI I MODULI
  updateFileOdm(selectedFilePDF: any, id: number) {
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
      idRiferimento: 0,
    };

    let formData: FormData = new FormData();
    formData.append(
      "fileAllegatoDto",
      new Blob([JSON.stringify(fileAllegatoDto)], { type: "application/json" })
    );
    formData.append("filePdf", selectedFilePDF);

    this.serviceME
      .saveFileAttoCostitutivoOdm("statoModuli/updateFileModulo", formData)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.isModifica = false;

          if (this.idModulo == 4) {
            this.loadTableAttoCostOdm();
          } else if (this.idModulo == 5) {
            this.loadTableStatutoOrg();
          } else if (this.idModulo == 9) {
            this.loadTableRegProcedura();
          } else if (this.idModulo == 71) {
            this.loadTableAttoCostOdmNA();
          } else if (this.idModulo == 72) {
            this.loadTableStatutoOrgNA();
          } else if (this.idModulo == 10) {
            this.loadTableCodiceEtico();
          } else if (this.idModulo == 23) {
            this.loadTableBilancio();
          }

          this.loadIsConvalidato();
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
}
