import { Component, OnInit } from "@angular/core";
import * as moment from "moment";

@Component({
  selector: "app-documento-identita",
  templateUrl: "./documento-identita.component.html",
  styleUrls: ["./documento-identita.component.css"],
})
export class DocumentoIdentitaComponent implements OnInit {
  isModifica: boolean = false;
  inviato: boolean = false;

  selectedFilePDF: any;
  doc: any;

  constructor() {
    this.doc = { nome: "Caio Tizio", dataInserimento: "2001-09-11" };
  }

  ngOnInit(): void {}

  public formatDate(date: Date) {
    return moment(date).format("DD-MM-YYYY");
  }

  activeModifica() {
    this.isModifica = true;
  }

  openPdfFile() {
    var file = new Blob([this.selectedFilePDF], { type: "application/pdf" });
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, "_blank");
  }

  salva() {
    this.isModifica = false;
    this.inviato = true;
  }
}
