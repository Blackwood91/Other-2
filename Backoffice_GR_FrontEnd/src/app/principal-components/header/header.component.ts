import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { NavigationEnd, Router } from '@angular/router';
import { AccessibilitaService } from 'src/app/accessibilita.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', '../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css' ]
})

export class HeaderComponent implements OnInit {
  isLogin: boolean = true;
  ruolo: string = "";
  nome: string = "";
  cognome: string = "";
  actualPath: string = "";

  constructor(private router: Router, 
			  private accessibilita: AccessibilitaService,
			  @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

  ngOnInit(): void {

    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        // Gestione della visibilita per il ruolo dell'utente
        this.ruolo = this.accessibilita.getCookieValue("ruolo");
        this.nome = this.accessibilita.getCookieValue("nome");
        this.cognome = this.accessibilita.getCookieValue("cognome");

        // riferimento per il pannello di login o logout del header
        if(this.accessibilita.existCookieUser()) {
          this.isLogin = false;
        }
        else {
          this.isLogin = true;
        }
        
        
        // Indica la url della in cui l'utente si trova
        let arraySplit = this.router.url.split("/");    
        this.actualPath = arraySplit[arraySplit.length - 1];
      }
    });
  }

  logoutIamg() {
    window.location.href = encodeURI(this.env.uriLogout);
  }

}