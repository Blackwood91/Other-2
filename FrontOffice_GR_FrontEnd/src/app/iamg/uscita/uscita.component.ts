import { Inject, Injectable, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { AuthenticationService } from 'src/app/authentication.service';
import { SharedService } from 'src/app/shared.service';
import { AccessibilitaService } from 'src/app/accessibilita.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-uscita',
  templateUrl: './uscita.component.html',
  styleUrls: ['./uscita.component.css']
})
export class UscitaComponent implements OnInit {

  constructor(private authentication: AuthenticationService, 
			  private accessibilita: AccessibilitaService, 
              private sharedService: SharedService,  
			  private router: Router,
			  @Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

  ROUTE_IANG = {
    pathIang: encodeURI(this.env.uriConfig.path_iang_logout + "?" + "post_logout_redirect_uri=" + this.env.uriConfig.logout_redirect_uri)
  };
 
  ngOnInit(): void {
    // Se rispetta la condizione vuol dire che l'accesso non è stato fatto
    if(this.accessibilita.existCookieUser() === false) {
      this.accessibilita.deleteCookie();
      this.router.navigate(['/mediatori']);
    }
    else {
      this.sharedService.onLoad();
      this.authentication.logout().subscribe({
        next: () => {
          this.accessibilita.deleteCookie();
          this.sharedService.offLoad();
          this.sharedService.onMessage('success', "Logout effettuato con successo");
          this.router.navigate(['/mediatori']);
        },
        error: (err) => {
          //Errore non previsto nella risposta del logout
          this.accessibilita.deleteCookie();
          this.sharedService.offLoad();
          this.sharedService.onMessage('error', "Attenzione logout fallito");
          this.router.navigate(['/mediatori']);
        }
      });
    } 
  }
}