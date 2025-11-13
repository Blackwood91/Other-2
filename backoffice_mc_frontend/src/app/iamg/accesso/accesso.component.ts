import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccessibilitaService } from 'src/accessibilita.service';
import { AuthenticationService } from 'src/app/authentication.service';
import { SharedService } from 'src/app/shared.service';
import { environment } from 'src/environments/environment';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-accesso',
  templateUrl: './accesso.component.html',
  styleUrls: ['./accesso.component.css']
})
export class AccessoComponent implements OnInit {
  constructor(private authentication: AuthenticationService, private accessibilita: AccessibilitaService,
    private router: Router, private sharedService: SharedService, @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

  ROUTE_IANG = {
    pathIang: encodeURI(this.env.uriConfig.path_iang + "?" + "client_id=" + this.env.uriConfig.clientId +
      "&scope=" + this.env.uriConfig.scope + "&" + "redirect_uri=" + this.env.uriConfig.redirectUri + "&" +
      "response_mode=" + this.env.uriConfig.response_mode + "&" + "response_type=" + this.env.uriConfig.response_type + "&" +
      "code_challenge=" + this.env.uriConfig.code_challenge + "&" + "code_challenge_method=" + this.env.uriConfig.code_challenge_method +
      "&spidACS=23")
  };

  ngOnInit(): void {
    // Se rispetta la condizione vuol dire che un accesso è già stato fatto
    //if (this.accessibilita.existCookieUser()) {
    //  this.router.navigate(['/homePage']);
    // }
    // else {
    // Attivazione caricamento totale
    this.sharedService.onLoad();
    // Divide valori nella path  
    const currentPath = window.location.hash.slice(1);
    const paramsURL = new URLSearchParams(currentPath);

    // Redirect page accesso IANG
    if (paramsURL.size == 0) {
      // Trasferisce il csrf e parametri necessari per l'accesso al backend
      this.authentication.startSecurity().subscribe({

      });
      
      window.location.href = this.ROUTE_IANG.pathIang;
    }
    // Verifica della risposta della page IANG 
    else {
      const code = paramsURL.get('code');

      // Passaggio codice al secondo controllo
      this.authentication.login(code!, "utenteIAMG")
        .subscribe({
          next: () => {
            // Richiesta dati utente loggato
            this.authentication.utenteLoggato()
              .subscribe({
                next: (res: any) => {
                  // Passaggio dati utente per autorizzazioni visibilità component
                  this.accessibilita.setCookie(res.utente.isAdn, res.utente.ruolo, res.utente.nome, res.utente.cognome);
                  // Disattivazione caricamento
                  this.sharedService.offLoad();
                  this.sharedService.onMessage('success', "L'autenticazione è avvenuta con successo");
                  this.router.navigate(['/homePage']);
                },
                error: (err: any) => {
                  // Errore nella richiesta dell'utente
                  this.sharedService.offLoad();
                  this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
                  this.router.navigate(['/accessoNegato']);
                }
              });
          },
          error: (error: any) => {
              this.sharedService.offLoad();
              this.sharedService.onMessage('error', error);
              this.router.navigate(['/accessoNegato']);
          }
        });

      //}

    }

  }

}
