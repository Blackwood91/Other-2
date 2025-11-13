import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Location } from '@angular/common';
import { GiustiziaService } from 'src/app/giustizia.service';
import { HttpParams } from '@angular/common/http';
import { AuthenticationService } from 'src/app/authentication.service';
import { Router } from '@angular/router';
import { SharedService } from 'src/app/shared.service';
import { AccessibilitaService } from 'src/app/accessibilita.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';


@Component({
  selector: 'app-accesso',
  templateUrl: './accesso.component.html',
  styleUrls: ['./accesso.component.css']
})

export class AccessoComponent implements OnInit {

  constructor(private location: Location, 
			        private authentication: AuthenticationService, 
			        private accessibilita: AccessibilitaService,
              private router: Router, 
			        private sharedService: SharedService,
			        @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

  ROUTE_IANG = {
    pathIang: encodeURI(this.env.uriConfig.path_iang + "?" + "prompt=login&" + "client_id=" + this.env.uriConfig.clientId +
    "&scope=" + this.env.uriConfig.scope + "&" + "redirect_uri=" + this.env.uriConfig.redirectUri + "&" +
    "response_mode=" + this.env.uriConfig.response_mode + "&" + "response_type=" + this.env.uriConfig.response_type + "&" +
    "code_challenge=" + this.env.uriConfig.code_challenge + "&" + "code_challenge_method=" + this.env.uriConfig.code_challenge_method)
  };

  ngOnInit(): void {
    // Controllo dell'accettazione dei cookie tecnici
    if (this.accessibilita.getCookieValue("cookieAccettati") != "true") {
      let buttonActiveModal = document.getElementById('cookie-container');
      buttonActiveModal?.classList.remove("cookie-hidden");
      this.sharedService.onMessage('attention', "Per poter accedere al portale di accesso bisogna accettare i cookie tecnici");
      this.router.navigate(['/mediatori']);
    }
    // Se rispetta la condizione vuol dire che un accesso è già stato fatto
    else if (this.accessibilita.existCookieUser()) {
      this.router.navigate(['/mediatori']);
    }
    else {
      // Attivazione caricamento totale
      this.sharedService.onLoad();
      // Divide valori nella path  
      const currentPath = window.location.hash.slice(1);
      const paramsURL = new URLSearchParams(currentPath);
      
      //Rientro in home in caso di richiesta alla url principale
      if (window.location.href.includes("/login") == false) {
        if (window.location.href.includes("#code") == false) {
          this.sharedService.offLoad();
          this.router.navigate(['/mediatori']);
          return;
        }
      }
      
      // Redirect page accesso IANG
      if (currentPath.length == 0) {
      //if (paramsURL.size == 0 || paramsURL.size == undefined) {
          // Trasferisce il csrf e parametri necessari per l'accesso al backend
          this.authentication.startSecurity().subscribe({
            next: (res: any) => {
              if(res.esito == "Successo"){ 
                window.location.href = this.ROUTE_IANG.pathIang;
                return;

              }
            },
            error: (error: any) => { this.sharedService.onMessage('error', "Si è verificato un errore nello richiesta dell'abilitazione per il pre accesso");}
        })
      }
      // Verifica della risposta della page IANG 
      else {
        const stateParam = paramsURL.get('state');
        const clientInfoParam = paramsURL.get('client_info');
        const code = paramsURL.get('code') + "-clientFE";

        // Passaggio codice al secondo controllo
        this.authentication.login(code!, "utenteIAMG")
          .subscribe({
            next: () => {
              // Richiesta dati utente loggato
              this.authentication.utenteLoggato()
                .subscribe({
                  next: (res: any) => {
                    // Passaggio dati utente per autorizzazioni visibilità component
                    this.accessibilita.updateParamCookie("nome", res.utente.nome);
                    this.accessibilita.updateParamCookie("cognome", res.utente.cognome);
                    this.accessibilita.updateParamCookie("ruolo", res.utente.ruolo);

                    // Disattivazione caricamento
                    this.sharedService.offLoad();
                    this.sharedService.onMessage('success', "Autenticazione avvenuta con successo");
                    this.router.navigate(['/mediatori-interno']);
                  },
                  error: (err) => {
                    // Errore nella richiesta dell'utente
                    // Fare logout...
                    this.sharedService.offLoad();
                    this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
                    this.router.navigate(['/mediatori']);
                  }
                });
            },
            error: (err) => {
              //Errore non previsto nella risposta dell'applicativo
              this.sharedService.offLoad();
              this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
              this.router.navigate(['/mediatori']);
            }
          });
      }
    }
  }
}
