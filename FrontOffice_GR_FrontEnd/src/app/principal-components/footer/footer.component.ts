import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AccessibilitaService } from 'src/app/accessibilita.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css', '../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css' ]
})
export class FooterComponent implements OnInit {

  isHomePage: boolean = true;

  sceltaCT: boolean = false;

  constructor(private router: Router, private accessibilita: AccessibilitaService) { }

  ngOnInit(): void {

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Se il cookie è stato già accettato non verrà visualizzato
        if(this.accessibilita.getCookieValue("cookieAccettati") === "true") {
          let buttonActiveModal = document.getElementById('cookie-container');
          buttonActiveModal?.classList.add("cookie-hidden");
        }
        
        if (this.router.url.includes('mediatoriInterno')) {
          this.isHomePage = false;
        } else {
          this.isHomePage = true;
        }
      }
    });
  }

  acceptCookie() {
    // Crea il cookie senza valori per l'accesso
    this.accessibilita.setCookie("", "", "");
    this.accessibilita.updateParamCookie("cookieAccettati", this.sceltaCT.toString());
    let buttonActiveModal = document.getElementById('cookie-container');
    buttonActiveModal?.classList.add("cookie-hidden");
  }

}
