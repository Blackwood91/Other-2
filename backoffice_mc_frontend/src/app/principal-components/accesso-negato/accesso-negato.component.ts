import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-accesso-negato',
  templateUrl: './accesso-negato.component.html',
  styleUrls: ['./accesso-negato.component.css', '../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css']
})
export class AccessoNegatoComponent implements OnInit {
  // Il processe della valorizzazione in questo punto viene fatto solo in caso di variabile costante
  motivazione: string;
  mostraFooter: boolean = false;


  // Il costruttore vine eseguito prima che il dom e il componente con tutti i suoi elementi venga creato 
  constructor() {
    this.motivazione = "per la seguente motivazione...  elit ullamcorper dignissim cras. Dictum sit amet justo donec enim diam vulputate ut. Eu nisl nunc mi ipsum faucibus."
   }

  // Il ngOnInit vine eseguito subito dopo la creazione del  dom e del component  
  ngOnInit(): void {
    this.scrollToInfoMessage();
  }


  // Funzioni per lo scroll della pagina  
  scrollToInfoMessage(){
    let hero = document.getElementById('infoMessage')!;
    hero!.scrollIntoView({behavior: 'smooth'});

  }

  


}
