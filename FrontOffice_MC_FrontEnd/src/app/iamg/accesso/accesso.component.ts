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

    if (window.location.href.includes("/login") == false) {
      if (window.location.href.includes("#code") == false) {
        this.sharedService.offLoad();
        this.router.navigate(['/homePage']);
        return;
      }
    }

    // Redirect page accesso IANG
    if (paramsURL.size == 0) {
      this.authentication.startSecurity().subscribe({
        next: (res: any) => {
          if (res.esito == "Successo") {
            window.location.href = this.ROUTE_IANG.pathIang;
            return;

          }
        },
        error: (error: any) => { 
          this.sharedService.onMessage('error', "Si è verificato un errore nello richiesta dell'abilitazione per il pre accesso");
          return;
        }
      })
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
                  this.sharedService.onMessage('success', "Autenticazione avvenuta con successo");
                  this.router.navigate(['/societa']);
                },
                error: (error: any) => {
                  // Errore nella richiesta dell'utente
                  this.sharedService.offLoad();
                  this.sharedService.onMessage('error', error);
                  this.router.navigate(['/homePage']);
                }
              });
          },
          error: (error: any) => {
            // Richiesta dati utente anche se non loggato per la registrazione
            if(error === "L'account non è registrato") {
            this.authentication.utenteLoggato()
              .subscribe({
                next: (res: any) => {
                  // Risposta positiva se lo spid ha risposto in modo corretto
                  this.sharedService.offLoad();
                  this.router.navigate(['/registrazioneUtente'], {
                    queryParams: {
                      nome: res.utente.nome,
                      cognome: res.utente.cognome, codiceFiscale: res.utente.codiceFiscale
                    }
                  });
                },
                error: (err: any) => {
                  // Errore nella richiesta dell'utente
                  this.sharedService.offLoad();
                  this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
                  this.router.navigate(['/homePage']);
                }
              });
            }
            else {
              this.sharedService.offLoad();
              this.sharedService.onMessage('error', error);
              this.router.navigate(['/homePage']);
            }
          }
        });

      //}

    }

  }

}
