import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirm-message',
  templateUrl: './confirm-message.component.html',
  styleUrls: ['./confirm-message.component.css', '../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css']
})
export class ConfirmMessageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void { }

  //Serve per poter inviare il parametro al component padre
	@Output() sendData = new EventEmitter<string>()	
  //Serve per poter prendere il parametro dichiarato dentro al component padre
	@Input() message : any;   
  @Input() id : any;  
	
	btnConfirmedRequest() {
		this.sendData.emit();
	}
}
