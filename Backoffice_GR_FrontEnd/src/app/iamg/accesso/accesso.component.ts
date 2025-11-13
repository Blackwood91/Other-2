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
  private apiUrl = this.env.pathApi;

  constructor(private location: Location, private authentication: AuthenticationService, private accessibilita: AccessibilitaService,
              private router: Router, private sharedService: SharedService, @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { 
                
              }

  ROUTE_IANG = {
    pathIang: encodeURI(this.env.uriConfig.path_iang + "?" + "client_id=" + this.env.uriConfig.clientId +
    "&scope=" + this.env.uriConfig.scope + "&" + "redirect_uri=" + this.env.uriConfig.redirectUri + "&" +
    "response_mode=" + this.env.uriConfig.response_mode + "&" + "response_type=" + this.env.uriConfig.response_type + "&" +
    "code_challenge=" + this.env.uriConfig.code_challenge + "&" + "code_challenge_method=" + this.env.uriConfig.code_challenge_method)
  };

  ngOnInit(): void {
  
    // Se rispetta la condizione vuol dire che un accesso è già stato fatto
    if(this.accessibilita.existCookieUser()) {     
      this.router.navigate(['/mediatori-interno']);
    }
    else {
      // Attivazione caricamento totale
      this.sharedService.onLoad();
      // Divide valori nella path  
      const currentPath = window.location.hash.slice(1); 
      const paramsURL = new URLSearchParams(currentPath);
      
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
            error: (error: any) => { this.sharedService.onMessage('error', "Si è verificato un errore nello richiesta dell'abilitazione per il pre accesso"); }
          })
      }
      // Verifica della risposta della page IANG 
      else {
        const stateParam = paramsURL.get('state');
        const clientInfoParam = paramsURL.get('client_info');
        const code = paramsURL.get('code') + "-clientBE";
                  
        // Passaggio codice al secondo controllo
        this.authentication.login(code!, "utenteIAMG")
        .subscribe({
          next: () => {
            // Richiesta dati utente loggato
            this.authentication.utenteLoggato()
            .subscribe({
              next: (res: any) => {                
                // Passaggio dati utente per autorizzazioni visibilità component
                this.accessibilita.setCookie(res.utente.ruolo, res.utente.nome, res.utente.cognome);
                // Disattivazione caricamento
                this.sharedService.offLoad();
                this.sharedService.onMessage('success', "Autenticazione avvenuta con successo");
                this.router.navigate(['/mediatori-interno']);
              },
              error: (err) => {
                // Errore nella richiesta dell'utente
                this.sharedService.offLoad();
                this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
                this.router.navigate(['/home']);
              }
            });
          },
          error: (err) => {
            //Errore non previsto nella risposta dell'applicativo
            this.sharedService.offLoad();
            this.sharedService.onMessage('error', "Attenzione autenticazione fallita");
            this.router.navigate(['/home']);
          }
        });
      }
    }
  }
}
