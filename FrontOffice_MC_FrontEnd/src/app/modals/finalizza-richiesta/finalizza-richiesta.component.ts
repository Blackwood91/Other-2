import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-finalizza-richiesta",
  templateUrl: "./finalizza-richiesta.component.html",
  styleUrls: ["./finalizza-richiesta.component.css"],
})
export class FinalizzaRichiestaComponent implements OnInit {
  idRichiesta: number = 0;
  fileDocumento: any = null;
  @Output() eventFinalizza: EventEmitter<any> = new EventEmitter();

  constructor(
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {}

  openModal(idRichiesta: number) {
    this.idRichiesta = idRichiesta;

    const buttonActiveModal = document.getElementById(
      "activeModalFinalizzaRichiesta"
    );
    buttonActiveModal!.click();
  }

  downloadFile() {
    this.serviceME
      .getFileDomandaIscrizione("finalizza/downloadRichiestaODM", this.idRichiesta)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], {
            type: "application/pdf",
          });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, "_blank");
        },
        error: (error: any) => {},
      });
  }

  // Per formattare byte in file pdf
  convertiStringaBlobAFile(dati: string): File {
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], { type: "application/pdf" });

    return new File([blobFile], "file");
  }

  onFileDocumento(event: any) {
    this.fileDocumento = event.target.files[0];
  }

  closeModal() {
    const closeModal = document.getElementById("closeModalFinalizza");
    closeModal!.click();
    this.eventFinalizza.emit("OPERIAZIONE_ESEGUITA");
  }

  inviaRichiesta() {
    // CONTROLLI PARAMETRI INSERITI
    if (this.fileDocumento === null) {
      this.sharedService.onMessage("error", "Per proseguire è necessario inserire il documento");
    }

    let formData: FormData = new FormData();
    formData.append("idRichiesta", new Blob([JSON.stringify(this.idRichiesta)], { type: "application/json" }));
    formData.append("filePdf", this.fileDocumento);

    this.serviceME
      .inviaRichiestaODM("finalizza/inviaRichiestaODM", formData)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage("success", "L'invio è avvenuto con successo!");
          this.closeModal();
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
          this.closeModal();
        },
      });
  }
}
