import { Component, EventEmitter, OnInit, Output } from "@angular/core";

@Component({
  selector: "app-confirm-message",
  templateUrl: "./confirm-message.component.html",
  styleUrls: ["./confirm-message.component.css"],
})
export class ConfirmMessageComponent implements OnInit {
  //Serve per poter inviare il parametro al component padre
  @Output() eventConfirmMessage: EventEmitter<any> = new EventEmitter();
  idRitorno: number = 0;
  message: string = "";

  constructor() {}

  ngOnInit(): void {}

  openModal(idRitorno: number, message: string) {
    this.message = message;
    this.idRitorno = idRitorno;

    const buttonActiveModal = document.getElementById("activeModalConfirm");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  btnConfirmedRequest() {
    const closeModal = document.getElementById("closeModal");
    closeModal!.click();

    const params: any = { message: "confermato", idRitorno: this.idRitorno };
    this.eventConfirmMessage.emit(params);
  }
}
