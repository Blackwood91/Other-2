import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormControl } from "@angular/forms";
import { MediazioneService } from "../../mediazione.service";
import { SharedService } from "../../shared.service";

@Component({
  selector: "app-aggiornamento-societa",
  templateUrl: "./aggiornamento-societa.component.html",
  styleUrls: ["./aggiornamento-societa.component.css"],
})
export class AggiornamentoSocietaComponent implements OnInit {
  @Output() eventSocieta: EventEmitter<any> = new EventEmitter();
  idSocieta: number = 0;
  ragioneSociale: string = "";
  partitaIva: string = "";
  codiceFiscaleSocieta: string = "";
  titolo: string = "Nuova Società";
  selectField: FormControl = new FormControl();
  attivo: boolean = false;
  validCodiceFiscale: boolean = true;

  constructor(
    public fb: FormBuilder,
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {}

  openModal(
    idSocieta: number,
    ragioneSociale: string,
    partitaIva: string,
    codiceFiscaleSocieta: string
  ) {
    this.idSocieta = idSocieta;
    this.ragioneSociale = ragioneSociale;
    this.partitaIva = partitaIva;
    this.codiceFiscaleSocieta = codiceFiscaleSocieta;

    if (idSocieta != 0 && idSocieta != null) {
      this.titolo = "Modifica Società";
    } else {
      this.titolo = "Inserimento Società";
    }

    var buttonActiveModal = document.getElementById("activeModalUpdateSocieta");
    // Esegui il click sul bottone nascosto "activeModalUpdateSocieta"
    buttonActiveModal!.click();
  }

  closeModal() {
    const closeModal = document.getElementById("closeModal");
    closeModal!.click();
    this.eventSocieta.emit("OPERIAZIONE_ESEGUITA");
  }

  sendSocieta() {
    const codiceFiscaleUpperCase = this.codiceFiscaleSocieta ? this.codiceFiscaleSocieta.toUpperCase() : '';
    const partitaIvaRegex = /^[0-9]{11}$/;

    let form: {
      id: number;
      ragioneSociale: string;
      partitaIva: string;
      codiceFiscaleSocieta: string;
    } = {
      id: this.idSocieta,
      ragioneSociale: this.ragioneSociale,
      partitaIva: this.partitaIva,
      codiceFiscaleSocieta: this.codiceFiscaleSocieta,
    };

    if (!this.partitaIva || !this.codiceFiscaleSocieta || !this.ragioneSociale) {
      this.sharedService.onMessage(
        "error",
        "Inserire partita Iva , Codice Fiscale e regione sociale della Società per proseguire con" +
          " il salvataggio"
      );
      return;
    }

    if ((this.ragioneSociale != null && this.ragioneSociale != "") === false) {
      this.sharedService.onMessage(
        "error",
        "Il campo ragione sociale è obbligatorio per il salvataggio"
      );
      return;
    } else if (!this.partitaIva && !this.codiceFiscaleSocieta) {
      this.sharedService.onMessage(
        "error",
        "Inserire partita Iva o Codice Fiscale della Società per proseguire con" +
          " il salvataggio"
      );
      return;
    } else if (
      !partitaIvaRegex.test(this.partitaIva) &&
      this.partitaIva != null &&
      this.partitaIva != ""
    ) {
      this.sharedService.onMessage("error", "Inserire partita Iva valida");
      return;
    } else if (this.idSocieta != 0) {
      this.serviceME.updateSocieta("societa/update", form).subscribe({
        next: (res: any) => {
          this.closeModal();
          this.sharedService.onMessage(
            "success",
            "L'aggiornamento è avvenuto con successo!"
          );
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
    } else {
      this.serviceME.insertSocieta("societa/insert", form).subscribe({
        next: (res: any) => {
          this.closeModal();
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
  }
}
