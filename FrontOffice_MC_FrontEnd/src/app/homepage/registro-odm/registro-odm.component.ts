import { HttpParams } from "@angular/common/http";
import { Component, OnInit, ViewChild } from "@angular/core";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";
import { RegistroElencoPdgComponent } from "../registro-elenco-pdg/registro-elenco-pdg.component";
import { RegistroElencoSediComponent } from "../registro-elenco-sedi/registro-elenco-sedi.component";

@Component({
  selector: "app-registro-odm",
  templateUrl: "./registro-odm.component.html",
  styleUrls: ["./registro-odm.component.css"],
})
export class RegistroOdmComponent implements OnInit {
  constructor(private serviceME: MediazioneService) {}
  @ViewChild(RegistroElencoPdgComponent)
  registroElencoPdgComponent!: RegistroElencoPdgComponent;
  @ViewChild(RegistroElencoSediComponent)
  registroElencoSediComponent!: RegistroElencoSediComponent;

  idRichiesta: number = 0;
  numIscrAlbo: number = 0;
  denomimanzione: string = "";
  sitoWeb: string = "";
  email: string = "";
  naturaOrganismo: string = "";
  codiceFiscale: number = 0;
  piva: string = "";
  cancSospeso: string = "";
  dataCancellazione: string = "";
  tableResult = new Array();

  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = "";

  totalRows = new Array();

  tipoRicerca: number = 0;

  page: String = "odm";
  tipoPage: String = "";

  ngOnInit(): void {
    this.onInitLoad();
  }

  public onInitLoad() {
    this.serviceME.getAllOdmPerAlbo("alboOdm/getAllOdmPaged", {}).subscribe({
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
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 1);

    this.serviceME
      .getAllOdmPerAlbo("alboOdm/getAllOdmPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index);

    this.serviceME
      .getAllOdmPerAlbo("alboOdm/getAllOdmPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", index - 2);

    this.serviceME
      .getAllOdmPerAlbo("alboOdm/getAllOdmPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set("searchText", this.searchTextTable)
      .set("indexPage", this.indexPage - 1)
      .set("tipoRicerca", this.tipoRicerca);

    this.serviceME
      .getAllOdmPerAlbo("alboOdm/getAllOdmPaged", params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  categorySelection(tipoRicerca: number) {
    this.tipoRicerca = tipoRicerca;
    this.searchTextTable = "";
    //  this.tipoMed = 0;
    this.onInitLoad();
  }

  public getRows() {
    return this
      .tableResult /*.filter(i => i.idTipoAnagrafica == '4' || i.idTipoAnagrafica == '5' || i.idTipoAnagrafica == '6')*/
      .length;
  }

  public getTipoMediatore(id: number) {
    switch (id) {
      case 4:
        return "Mediatore generico";
      case 5:
        return "Mediatore esperto in materia internazionale";
      case 6:
        return "Mediatore esperto in materia di consumo";
      default:
        return "errore";
    }
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

  selectSedi() {
    this.tipoPage = "sedi";
  }

  selectPdg() {
    this.tipoPage = "pdg";
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  openModalSedi(selectedRom: number) {
    this.numIscrAlbo = selectedRom;
    const buttonActiveModal = document.getElementById("activeModalSaveFile");
    buttonActiveModal!.click();
  }

  openModalPdg(selectedRom: number) {
    this.idRichiesta = selectedRom;
    const buttonActiveModal = document.getElementById("activeModalSaveFile");
    buttonActiveModal!.click();
  }
}
