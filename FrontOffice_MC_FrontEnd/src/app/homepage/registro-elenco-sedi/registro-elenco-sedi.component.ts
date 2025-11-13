import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, SimpleChanges } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";

@Component({
  selector: "app-registro-elenco-sedi",
  templateUrl: "./registro-elenco-sedi.component.html",
  styleUrls: ["./registro-elenco-sedi.component.css"],
})
export class RegistroElencoSediComponent implements OnInit {
  @Input()
  page: String = "";
  @Input()
  numReg: number = 0;
  @Input()
  numIscrAlbo: number = 0;

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";

  tableResult = new Array();

  totalRows = new Array();

  tipoRicerca: number = 0;

  constructor(private serviceME: MediazioneService) {}

  ngOnInit(): void {
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes["numReg"]) {
      this.loadSediEf();
    }
    if (changes["numIscrAlbo"]) {
      this.loadSediOdm();
    }
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  decideTitle() {
    if (this.page == "odm")
      return "Dettaglio sedi dell'organismo di mediazione";
    else if (this.page == "form")
      return "Dettaglio sedi dell'Ente di Formazione";
    else return "error";
  }

  decideLoading() {
    if (this.page == "odm") this.loadSediOdm();
    else if (this.page == "form") this.loadSediEf();
  }

  public loadSediOdm() {
    const params = new HttpParams().set("numIscrAlbo", this.numIscrAlbo);

    this.serviceME.getAllOdmPerAlbo("alboOdmSedi/getAllOdmSediPaged", params).subscribe({
      next: (res: any) => {
        // this.tableResult = res;
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      },
    });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set("indexPage", index - 1);

    this.serviceME
      .getAllOdmPerAlbo("alboOdmSedi/getAllOdmSediPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index);

    this.serviceME
      .getAllOdmPerAlbo("alboOdmSedi/getAllOdmSediPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index - 2);

    this.serviceME
      .getAllOdmPerAlbo("alboOdmSedi/getAllOdmSediPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }



  public loadSediEf() {
    const params = new HttpParams().set("numReg", this.numReg);

    this.serviceME
      .getAllEfSediPerAlboByNumReg("alboEfSedi/getEfSediByNumReg", params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res;
        },
      });
  }
}
