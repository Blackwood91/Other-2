import { HttpParams } from "@angular/common/http";
import { Component, Input, OnInit, SimpleChanges } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";

@Component({
  selector: "app-dettaglio-formatore-mediatore",
  templateUrl: "./dettaglio-formatore-mediatore.component.html",
  styleUrls: ["./dettaglio-formatore-mediatore.component.css"],
})
export class DettaglioFormatoreMediatoreComponent implements OnInit {
  @Input()
  page: String = "";
  @Input()
  idAlboFormatori: number = 0;
  @Input()
  idAnagrafica: number = 0;

  tableResult = new Array();
  totalRows = new Array();

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";

  tipoRicerca: number = 0;

  constructor(private serviceME: MediazioneService) {}

  ngOnInit(): void {
    this.decideTitle();
    this.decideLoading();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes["idAlboFormatori"]) {
      this.loadEf();
    }
    if (changes["idAnagrafica"]) {
      this.loadOdm();
    }
  }

  public getRows() {
    return this.tableResult.length;
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  public getNatura(id: number) {
    switch (id) {
      case 0:
        return "Ente Non Autonomo";
      case 1:
        return "Ente Autonomo";
      default:
        return "errore";
    }
  }

  decideTitle() {
    if (this.page == "odm")
      return "Dettagli Organismi di Mediazione per il mediatore selezionato";
    else if (this.page == "form")
      return "Dettaglio Enti di Formazione per il formatore selezionato";
    else return "error";
  }

  decideLoading() {
    if (this.page == "odm") this.loadOdm();
    else if (this.page == "form") this.loadEf();
  }

  public loadOdm() {
    const params = new HttpParams().set(
      "idAnagrafica",
      this.idAnagrafica
    );

    this.serviceME
      .getMediatorePerAlboById("alboMediatori/getAllOdmByIdAnagraficaPaged", params)
      .subscribe({
        next: (res: any) => {
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
      .getAllMediatoriPerAlbo("alboMediatori/getAllOdmByIdAnagraficaPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index);

    this.serviceME
      .getAllMediatoriPerAlbo("alboMediatori/getAllOdmByIdAnagraficaPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("indexPage", index - 2);

    this.serviceME
      .getAllMediatoriPerAlbo("alboMediatori/getAllOdmByIdAnagraficaPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }



  public loadEf() {
    const params = new HttpParams().set(
      "idAlboFormatori",
      this.idAlboFormatori
    );

    this.serviceME
      .getFormatorePerAlboById("alboFormatori/getFormatoriByIdAlbo", params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
        },
      });
  }
}
