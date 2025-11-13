import { Component, OnInit, ViewChild } from '@angular/core';
import { MediazioneService } from '../mediazione.service';
import { SharedService } from '../shared.service';
import { ActivatedRoute } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { ConfirmMessageComponent } from '../principal-components/confirm-message/confirm-message.component';
import { ExtraInfoComponent } from '../modals/extra-info/extra-info.component';

@Component({
  selector: 'app-elenco-totale-utenti',
  templateUrl: './elenco-totale-utenti.component.html',
  styleUrls: [ './elenco-totale-utenti.component.css']
})
export class ElencoTotaleUtentiComponent implements OnInit {
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

  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1);

    this.serviceME
      .getAllUtenti('user/getAllUtenti', params)
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

    this.serviceME.getAllUtenti('user/getAllUtenti', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceME.getAllUtenti('user/getAllUtenti', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceME.getAllUtenti('user/getAllUtenti', params)
    .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  openModalConfirmMessage(id: number, typeConfirmMes: string) {
    this.typeConfirmMes = typeConfirmMes;
    let message;
    if(typeConfirmMes == "attiva")
      message = "Sei sicuro di voler attivare questo account?";
    else
      message = "Sei sicuro di voler disattivare questo account?";

    this.confirmMessageComponent.openModal(id, message);
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      if(this.typeConfirmMes == "attiva")
        this.attivaUtente(event.idRitorno);
      else 
        this.disattivaUtente(event.idRitorno);
    }
  }

  attivaUtente(idUtente: number) {
    const params = new HttpParams()
    .set('idUtente', idUtente);

    this.serviceME.abilitaUtente('user/abilitaUtente', params).subscribe({
      next: (res:any) => {
        this.loadTable();
        this.sharedService.onMessage('success', "L'utente è stato abilitato con successo");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "Si è verificato un errore non previsto");
      }
    })
  }

  disattivaUtente(idUtente: number) {
    const params = new HttpParams()
    .set('idUtente', idUtente);

    this.serviceME.disabilitaUtente('user/disabilitaUtente', params).subscribe({
      next: (res: any) => {
        this.loadTable();
        this.sharedService.onMessage('success', "L'utente è stato disattivato con successo");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "Si è verificato un errore non previsto");
      }
    })
  }

}
