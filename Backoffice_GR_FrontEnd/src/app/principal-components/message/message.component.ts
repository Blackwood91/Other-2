import { Component, EventEmitter, OnInit } from '@angular/core';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css', './message.component.css']
})
export class MessageComponent implements OnInit {
  notification = "";
  typeMessage = "";
  descriptionMessage = "";
  timeOutMessage? : NodeJS.Timeout;

  constructor() { }

  ngOnInit(): void { }

  onMessage(type: string, description: string){ //success / error / attention
    this.typeMessage = type;
    this.descriptionMessage = description;
    this.notification = "#notication-" + type;

    var buttonActiveModal = document.getElementById('showMessage');
    //Bisogna aspettare tot prima che possa vedere i parametri modificati
    setTimeout(() => {
      buttonActiveModal!.click();
      //Per chiudere il messaggio dopo l'apertura
      this.timeOutMessage = setTimeout(() => {
        buttonActiveModal!.click();
      }, 12800);
    }, 400);
  }

  closeMessage(){
    clearTimeout(this.timeOutMessage);
  }
}