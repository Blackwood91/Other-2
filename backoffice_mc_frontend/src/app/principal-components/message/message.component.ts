import { Component, EventEmitter, OnInit } from '@angular/core';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  notification: string = "";
  typeMessage: string = "";
  descriptionMessage: string = "";

  constructor() { }

  ngOnInit(): void {}

  onMessage(type: string, description: string){ //success / error / attention
    this.typeMessage = type;
    this.descriptionMessage = description;
    this.notification = "#notication-" + type;

    var buttonActiveModal = document.getElementById('showMessage');
    //Bisogna aspettare tot prima che possa vedere i parametri modificati
    setTimeout(() => {
      buttonActiveModal!.click();
      //Per chiudere il messaggio dopo l'apertura
      setTimeout(() => {
        buttonActiveModal!.click();
      }, 8800);
    }, 400);

 
    
  }

}
