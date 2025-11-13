import { HttpParams } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "../mediazione.service";
import { SharedService } from "../shared.service";

@Component({
  selector: "app-richieste-inviate",
  templateUrl: "./richieste-inviate.component.html",
  styleUrls: ["./richieste-inviate.component.css"],
})
export class RichiesteInviateComponent implements OnInit {
  idRichiesta: number = 0;
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
      this.loadTable();
    });
  }

  formatDateComplete(date: string) {
    return moment(date).format("DD-MM-YYYY HH:mm");
  }

  loadTable() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllRichiestaInviata("statoModuli/getAllRichiestaInviata", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllRichiestaInviata("status/getAllRichiestaInviata", params)
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
      .getAllRichiestaInviata("statoModuli/getAllRichiestaInviata", params)
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
      .getAllRichiestaInviata("statoModuli/getAllRichiestaInviata", params)
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
      .getAllRichiestaInviata("statoModuli/getAllRichiestaInviata", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
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
}
