import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AccessibilitaService } from 'src/accessibilita.service';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-home-page-mediazione',
  templateUrl: './home-page-mediazione.component.html',
  styleUrls: ['./home-page-mediazione.component.css']
})
export class HomePageMediazioneComponent implements OnInit {

  constructor(private router: Router, private accessibilita: AccessibilitaService, private sharedService: SharedService, private serviceME: MediazioneService) { }

  ngOnInit(): void {
    //this.sharedService.onNotification("prova messaggio");
        // TEMPORANEO DA CANCELLARE
      /*   this.serviceME.getAllTipoRichiedente('user/inizializzaDatiTabTemporanea') 
         .subscribe((res: any) => {
           //this.loadTable();
         });*/
  }

}
