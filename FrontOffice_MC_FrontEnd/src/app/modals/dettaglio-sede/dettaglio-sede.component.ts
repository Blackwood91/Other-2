import { HttpParams } from "@angular/common/http";
import { Component, Inject, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import * as moment from "moment";
import { MediazioneService } from "src/app/mediazione.service";

@Component({
  selector: "app-dettaglio-sede",
  templateUrl: "./dettaglio-sede.component.html",
  styleUrls: ["./dettaglio-sede.component.css"],
})
export class DettaglioSedeComponent implements OnInit {
  idRichiesta: number = 0;
  idTitoloDefinizione: number = 0;
  dataContratto: string = "";
  dataInserimentoSede: string = "";
  dataCancellazione: string = "";

  idSede: number = 0;

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];
    });
  }

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  openModal(idSede: number) {
    this.idSede = idSede;

    const params = new HttpParams().set("idSede", idSede).set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getSedeOperativa("sede/getSedeOperativa", params)
      .subscribe((res: any) => {
        this.idTitoloDefinizione = res.idTitoloDefinizione;
        this.dataContratto = moment(res.dataContratto).format("YYYY-MM-DD");
        this.dataInserimentoSede = moment(res.dataInserimentoSede).format("YYYY-MM-DD");
        this.dataCancellazione = moment(res.dataCancellazione).format("YYYY-MM-DD");
      });

    const buttonActiveModal = document.getElementById("activeModalSede");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal!.click();
  }

  titoloDetenzione(id: number) {
    switch (id) {
      case 1:
        return "Proprietà";
      case 2:
        return "Locazione";
      case 3:
        return "Comodato d'uso";
      default:
        return "errore";
    }
  }
}
