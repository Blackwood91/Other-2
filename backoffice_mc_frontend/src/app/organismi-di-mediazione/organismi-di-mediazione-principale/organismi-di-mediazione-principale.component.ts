import { Location } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-organismi-di-mediazione-principale',
  templateUrl: './organismi-di-mediazione-principale.component.html',
  styleUrls: ['./organismi-di-mediazione-principale.component.css']
})
export class OrganismiDiMediazionePrincipaleComponent implements OnInit {

  //di default sarà visualizzato domanda di iscrizione
  // La vista di default se non verra passato nessun parametro, sara questa
  component = "domanda_di_iscrizione";
  idRichiesta: number = 0;
  
  autonomo: boolean = true;
  receiveAutonomo(autonomoVal: boolean) {
    this.autonomo = autonomoVal;
  }

  constructor(private route: ActivatedRoute, private location: Location, private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      let vista = params['vistaMenu'];
      this.idRichiesta = params['idRichiesta'];

      if(!this.idRichiesta) {
        this.router.navigate(['/paginaInfo'], {
          queryParams: {
            typeError: "request_not_valid"
          }
        })
        
        return;
      }

      if(vista) {
        this.component = vista;
      } 

    });
  }

  //FUNZIONI COMPONENT
  // Output Handler
  changeComponent(nomeComponent:string){
    this.location.go("/organismiDiMediazione?vistaMenu=" + nomeComponent + "&idRichiesta=" + this.idRichiesta);

    this.component = nomeComponent;
  }

  //FINE FUNZIONI COMPONENT------------------

}
