import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from '../shared.service';
import { MediazioneService } from '../mediazione.service';
import { ConfirmMessageComponent } from '../principal-components/confirm-message/confirm-message.component';
import { ExtraInfoComponent } from '../modals/extra-info/extra-info.component';
import { HttpParams } from '@angular/common/http';
import * as moment from 'moment';

@Component({
  selector: 'app-richieste-registrazione-utente',
  templateUrl: './richieste-registrazione-utente.component.html',
  styleUrls: ['./richieste-registrazione-utente.component.css']
})
export class RichiesteRegistrazioneUtenteComponent implements OnInit {
  idRichiesta: number = 0;
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  isConvalidato: boolean = false;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  idAlbo: number = 0;
  idUtente: number = 0;
  idRuolo: number = 0;
  dataRichiesta: string = "";
  ragioneSociale: string = "";
  partitaIva: string = "";
  idStatoRichiesta: number = 0;
  idTipoRichiesta: number = 0;

  isModifica: boolean = false;
  typeConfirmMes: string = "";

  constructor(private serviceME: MediazioneService, private sharedService: SharedService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadTable();
  }

  formatDateComplete(date: string) {
    return (moment(date)).format('DD-MM-YYYY HH:mm')
  }

  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1);

    this.serviceME
      .getAllRegistrazioniUtente('user/getAllRegistrazioniUtente', params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
          this.totalPage = Math.ceil(res.totalResult / 10);
          this.totalResult = res.totalResult;
        }
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceME.getAllRegistrazioniUtente('user/getAllRegistrazioniUtente', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceME.getAllRegistrazioniUtente('user/getAllRegistrazioniUtente', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceME.getAllRegistrazioniUtente('user/getAllRegistrazioniUtente', params)
    .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  openModalConfirmMessage(id: number, typeConfirmMes: string) {
    this.typeConfirmMes = typeConfirmMes;
    let message;
    if(typeConfirmMes == "approva")
      message = "Sei sicuro di voler approvare questo account?";
    else
      message = "Sei sicuro di voler rifiutare questo account?";

    this.confirmMessageComponent.openModal(id, message);
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      if(this.typeConfirmMes == "approva")
        this.approvaRichiesta(event.idRitorno);
      else 
        this.rifiutaRichiesta(event.idRitorno);
    }
  }

  approvaRichiesta(idUtente: number) {
    const params = new HttpParams()
    .set('idUtente', idUtente);

    this.serviceME.approvaUtente('user/approvaUtente', params).subscribe({
      next: (res: any) => {
        this.loadTable();
        this.sharedService.onMessage('success', "L'utente è stato approvato con successo");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "Si è verificato un errore non previsto");
      }
    })
  }

  rifiutaRichiesta(idUtente: number) {
    const params = new HttpParams()
    .set('idUtente', idUtente);

    this.serviceME.rifutaUtente('user/rifutaUtente', params).subscribe({
      next: (res: any) => {
        this.loadTable();
        this.sharedService.onMessage('success', "L'utente è stato rifiutato con successo");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "Si è verificato un errore non previsto");
      }
    })
  }

}
