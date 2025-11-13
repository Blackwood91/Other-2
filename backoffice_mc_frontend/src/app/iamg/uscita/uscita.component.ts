import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccessibilitaService } from 'src/accessibilita.service';
import { AuthenticationService } from 'src/app/authentication.service';
import { SharedService } from 'src/app/shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-uscita',
  templateUrl: './uscita.component.html',
  styleUrls: ['./uscita.component.css']
})
export class UscitaComponent implements OnInit {

  constructor(private authentication: AuthenticationService, private accessibilita: AccessibilitaService,
    private sharedService: SharedService, private router: Router, @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }
    mostraFooter: boolean = false;

  ngOnInit(): void {
    
    // Se rispetta la condizione vuol dire che l'accesso non è stato fatto
    if (this.accessibilita.existCookieUser() == false) {
      this.accessibilita.deleteCookie();
      window.location.href = "/esitoLogout?esito=successo";
    }
    else {
      //this.sharedService.onLoad();
      this.authentication.logout().subscribe({
        next: () => { 
          this.accessibilita.deleteCookie();
          this.sharedService.offLoad();
          this.sharedService.onMessage('success', "Logout effettuato con successo");
          window.location.href = "/logout";
        },
        error: (err: any) => {
          //Errore non previsto nella risposta del logout
          this.accessibilita.deleteCookie();
          this.sharedService.offLoad();
          window.location.href = "/esitoLogout?esito=errore";
        }
      });
    }

  }


}
