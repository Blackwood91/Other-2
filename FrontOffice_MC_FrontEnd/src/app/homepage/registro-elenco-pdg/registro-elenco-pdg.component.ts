import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, SimpleChanges } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";

@Component({
  selector: "app-registro-elenco-pdg",
  templateUrl: "./registro-elenco-pdg.component.html",
  styleUrls: ["./registro-elenco-pdg.component.css"],
})
export class RegistroElencoPdgComponent implements OnInit {
  @Input()
  page: String = "";
  @Input()
  numReg: number = 0;
  @Input()
  idRichiesta: number = 0;

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";
  tableResult = new Array();

  constructor(private serviceME: MediazioneService) {}

  ngOnInit(): void {
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes["numReg"]) {
      this.loadPdgEf();
    }
    if (changes["idRichiesta"]) {
      this.loadPdgOdm();
    }
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY HH:mm");
  }

  decideTitle() {
    if (this.page == "odm") return "Dettaglio PDG dell'organismo di mediazione";
    else if (this.page == "form")
      return "Dettaglio PDG dell'Ente di Formazione";
    else return "error";
  }

  decideLoading() {
    if (this.page == "odm") this.loadPdgOdm();
    else if (this.page == "form") this.loadPdgEf();
  }

  public loadPdgOdm() {
    const params = new HttpParams()
      .set("idRichiesta", this.idRichiesta);

    this.serviceME.getAllOdmPerAlbo("emissionePdgOdm/getAllEmissionePdgOdmForTable", params).subscribe({
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
      .set("indexPage", index - 1)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllOdmPerAlbo("emissionePdgOdm/getAllEmissionePdgOdmForTable", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllOdmPerAlbo("emissionePdgOdm/getAllEmissionePdgOdmForTable", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index - 2)
      .set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getAllOdmPerAlbo("emissionePdgOdm/getAllEmissionePdgOdmForTable", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }



  public loadPdgEf() {
    const params = new HttpParams().set("numReg", this.numReg);

    this.serviceME
      .getAllEfPdgPerAlboByNumReg("alboEfPdg/getEfPdgByNumReg", params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res;
        },
      });
  }
}
