import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notification-message',
  templateUrl: './notification-message.component.html',
  styleUrls: ['./notification-message.component.css']
})
export class NotificationMessageComponent implements OnInit {
  descriptionMessage: string = "";

  constructor() { }

  ngOnInit(): void {

  }

  onNotification(descriptionMessage: string) {
    alert(5)
    this.descriptionMessage = descriptionMessage;

    var buttonActiveModal = document.getElementById('btnModalActiveNotification');
    buttonActiveModal!.click();
  }

}
