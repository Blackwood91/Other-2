import { Inject, Injectable, Component, HostBinding, Output, Input, OnInit, SimpleChanges, EventEmitter } from '@angular/core';
import { AppComponent } from '../app.component';
import { GiustiziaService } from '../giustizia.service';
import { HttpParams } from '@angular/common/http';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { SharedService } from '../shared.service';
import { AccessibilitaService } from '../accessibilita.service';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';


@Component({
  selector: 'app-mediatori-interno',
  templateUrl: './mediatori-interno.component.html',
  styleUrls: ['./mediatori-interno.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class MediatoriInternoComponent implements OnInit {
  ruoloUser: string = "";
  form: FormGroup;
  //Var Tabella
  searchTextTable: string = "";
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  colonna: string = "";
  mediatore!: object;

  constructor(public fb: FormBuilder, 
              private appComponent: AppComponent, 
              private accessibilita: AccessibilitaService,
              private router: Router, 
              private serviceGR: GiustiziaService, 
              private sharedService: SharedService,
              @Inject(APP_ENVIRONMENT) private env:AppEnvironment) {

    this.form = fb.group({
      nomeMediatore: "",
      cognomeMediatore: "",
      codiceFiscale: "",
      isFormatore: "",
    })

  }

  ngOnInit() {
    // Controllo dell'accettazione dei cookie tecnici
    if(this.accessibilita.getCookieValue("cookieAccettati") != "true") {
      let buttonActiveModal = document.getElementById('cookie-container');
      buttonActiveModal?.classList.remove("cookie-hidden");
      this.sharedService.onMessage('attention', "Per poter accedere a questa pagina bisogna accettare i cookie tecnici");
      this.router.navigate(['/mediatori']);
    }
    
    this.ruoloUser = this.accessibilita.getCookieValue("ruolo");
    this.caricaTabella();
  }

  caricaTabella() {
	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", {}).subscribe((res: any) => {
	      this.tableResult = res.result;
	      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
	      this.totalResult = res.totalResult;
	    })
	  }

	  //FUNZIONI TABELLA
	  attivaRicerca() {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', this.indexPage - 1);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	      this.totalPage = Math.ceil(res.totalResult / this.env.rowsTable);
	      this.totalResult = res.totalResult;
	    })
	  }

	  cambiaPagina(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index - 1);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index;
	  }

	  nextPage(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index + 1;
	  }

	  previousPage(index: number) {
	    const params = new HttpParams()
	      .set('searchText', this.searchTextTable)
	      .set('indexPage', index - 2);

	    this.serviceGR.getElencoPubblico("mediatori-frontoffice/getElencoPubblico", params).subscribe((res: any) => {
	      this.tableResult = res.result;
	    })

	    this.indexPage = index - 1;
	  }

  checkColonnaSvg(colonna: String) {
    let svgColonna: string = "";

    if (colonna == this.colonna) {

      if (colonna.includes('+') == false && colonna.includes('-') == false) {
        svgColonna = `\\assets\\svg\\colonna.svg`;
      }
      else if (colonna.includes('+')) {
        svgColonna = "\\assets\\svg\\colonna_desc.svg";
      }
      else if (colonna.includes('-')) {
        svgColonna = "\\assets\\svg\\colonna_asc.svg";
      }
    }
    else {
      svgColonna = "\\assets\\svg\\colonna.svg";
    }
    return svgColonna;
  }

  checkColonna(colonna: any) {
    if (this.colonna == colonna) {
      colonna = colonna + '+';
      this.colonna = colonna;
    }
    else if (this.colonna == (colonna + '+')) {
      colonna = colonna + '-';
      this.colonna = colonna;

    }
    else if (this.colonna == (colonna + '-')) {
      colonna = colonna;
      this.colonna = colonna;

    }
    return colonna;
  }

  openModal(idMediatore: number) {
    const params = new HttpParams()
    .set('idMediatore', idMediatore)

    this.serviceGR.getExtraInfoMediatore("mediatori-frontoffice/getExtraInfoMediatore", params).subscribe((res: any) => {
      this.mediatore = res;
      const buttonActiveModal = document.getElementById("activeModalMediatore");
      // Esegui il click sul bottone nascosto "activeModalInsertElezione"
      buttonActiveModal != null ? buttonActiveModal.click() : "";
    })
  }

  onMediatore() {
    this.caricaTabella()
  }
}
