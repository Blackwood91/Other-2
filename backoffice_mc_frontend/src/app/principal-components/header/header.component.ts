import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { MediazioneService } from 'src/app/mediazione.service';
import { NavigationEnd, Router } from '@angular/router';
import { AccessibilitaService } from 'src/accessibilita.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  selectedSocietaId!: number;
  selectedNomeSocieta: string = "";

  isLogin: boolean = true;
  ruolo: string = "";
  nome: string = "";
  cognome: string = "";
  actualPath: string = "";
  msalInstance: any = null;

  constructor(private router: Router, private accessibilita: AccessibilitaService, private sharedService: SharedService,
    private mediazioneService: MediazioneService, @Inject(APP_ENVIRONMENT) private env: AppEnvironment) {
  }

  ngOnInit(): void {
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        // Senza cookie tecnici accettati non si visualizza il link a mediatori interno 
        //    if(this.accessibilita.getCookieValue("cookieAccettati") === "true") {
        // Gestione della visibilita per il ruolo dell'utente
        this.ruolo = this.accessibilita.getCookieValue("ruolo");
        this.nome = this.accessibilita.getCookieValue("nome");
        this.cognome = this.accessibilita.getCookieValue("cognome");
        this.selectedNomeSocieta = this.accessibilita.getCookieValue("societaSelezionata");
        // Quando ritorna false vuol dire che l'utente è estitente
        if (this.accessibilita.existCookieUser()) {
          this.isLogin = false;
        }
        else {
          this.isLogin = true;
        }
        //     }
        /*      else {
                this.ruolo = "";
                this.nome = "";
                this.cognome = "";
              }
        */
        // Indica la url della in cui l'utente si trova
        let arraySplit = this.router.url.split("/");
        this.actualPath = arraySplit[arraySplit.length - 1];
      }
    });
  }


  logoutIamg() {
    // logut per la disconessione corretta dall'account azure utilizzato per l'accesso 
    // logut in caso di adn
    if (this.accessibilita.getCookieValue("isAdn") == "false") {
      window.location.href = encodeURI("https://auth03coll.giustizia.it/b2cmingiustiziaspidcoll.onmicrosoft.com/b2c_1a_signin_aad_spid/oauth2/v2.0/logout?clientId=" + this.env.uriConfig.clientId + "&post_logout_redirect_uri=" + this.env.uriConfig.logout_redirect_uri);
    }
    else {
      this.sharedService.onLoad();

      const larghezzaFinestra = 600; // Larghezza desiderata della finestra
      const altezzaFinestra = 400; // Altezza desiderata della finestra

      // Calcola le coordinate x e y per posizionare la finestra al centro dello schermo
      const x = (window.innerWidth - larghezzaFinestra) / 2 + window.screenX;
      const y = (window.innerHeight - altezzaFinestra) / 2 + window.screenY;

      // Apri la finestra popup posizionandola al centro dello schermo
      const finestraDisconessione = window.open(
        'https://login.microsoftonline.com/common/oauth2/v2.0/logout?clientId=' + this.env.uriConfig.clientId,
        'DisconessioneAccountAzure',
        `width=${larghezzaFinestra},height=${altezzaFinestra},left=${x},top=${y}`
      );

      const intervallo = setInterval(() => {
        if (finestraDisconessione!.closed) {
          clearInterval(intervallo);
          window.location.href = encodeURI("https://auth03coll.giustizia.it/b2cmingiustiziaspidcoll.onmicrosoft.com/b2c_1a_signin_aad_spid/oauth2/v2.0/logout?clientId=" + this.env.uriConfig.clientId + "&post_logout_redirect_uri=" + this.env.uriConfig.logout_redirect_uri);
        }
      }, 1000); // Intervallo per evitare di attivazione chiusura non voluta
    }
  }




  onChangeNomeSocieta(nomeSocieta: string) {
    // Quando ritorna false vuol dire che l'utente è estitente, verra aggiornato il valore nel cookie con la nuova societa selezionata dall'utente
    if (this.accessibilita.existCookieUser()) {
      this.accessibilita.updateParamCookie("societaSelezionata", nomeSocieta)
      this.selectedNomeSocieta = nomeSocieta;
    }
    else {
      this.selectedNomeSocieta = "";
    }
  }

}
