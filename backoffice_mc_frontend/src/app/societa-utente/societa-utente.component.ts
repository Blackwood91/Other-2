import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AppComponent } from '../app.component';
import { MediazioneService } from '../mediazione.service';
import { HttpParams } from '@angular/common/http';
import { MessageComponent } from '../principal-components/message/message.component';
import { SharedService } from '../shared.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AggiornamentoSocietaComponent } from '../modals/aggiornamento-societa/aggiornamento-societa.component';
@Component({
  selector: 'app-societa-utente',
  templateUrl: './societa-utente.component.html',
  styleUrls: [
    '../../style/bootstrap-italia/css/bootstrap-italia.min.css',
    '../../style/bootstrap-italia/assets/docs.min.css',
    './societa-utente.component.css',
  ],
})
export class SocietaUtenteComponent implements OnInit {
  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(AggiornamentoSocietaComponent) aggiornamentoSocietaComponent!: AggiornamentoSocietaComponent;

  //Var Per Passaggio dati component
  singolaSocieta = {
    id: 0,
    ragioneSociale: '',
    partitaIva: '',
    statoRichiestaOdm: '',
    statoRichiestaEf: '',
    codiceFiscaleSocieta: ''
  };
  // Richiesta singola
  richiesta = {
    idRichiesta: 0,
    idTipoRichiedente: 0,
    dataIscrAlbo: '',
    dataRichiesta: ''
  };
  //si valorizza dopo, alla selezione della società
  registroMediazione = {};
  registroEnte = {};
  //Tipologie richiedente
  tipologieROdm: any;
  tipologieREf: any;
  //Tipologia selezionata
  tipologiaRichiestaOdm: any = 49;
  tipologiaRichiestaEf: number = 0;
  selectId4Odm = false;

  //Var Tabella
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  constructor(
    private fb: FormBuilder,
    private appComponent: AppComponent,
    private serviceME: MediazioneService,
    private sharedService: SharedService,
  ) { }

  ngOnInit() {
      this.loadTable();
  }



  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1);

    this.serviceME
      .getAllSocietaUtente('societa/getAll', params)
      .subscribe({
        next: (res: any) => {
          this.tableResult = res.result;
          this.totalPage = Math.ceil(res.totalResult / 10);
          this.totalResult = res.totalResult;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error.error)

        }
      });


  }

  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1);

    this.serviceME
      .getAllSocietaUtente('societa/getAll', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1);

    this.serviceME
      .getAllSocietaUtente('societa/getAll', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index);

    this.serviceME
      .getAllSocietaUtente('societa/getAll', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2);

    this.serviceME
      .getAllSocietaUtente('societa/getAll', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }
  //FINE FUNZIONI TABELLA

  //FUNZIONI MODAL
  addSocieta() {
    var buttonActiveModal = document.getElementById('activeModalUpdateSocieta');
    // Esegui il click sul bottone nascosto "activeModalUpdateSocieta"
    this.singolaSocieta = {
      id: '' as unknown as number,
      ragioneSociale: '',
      partitaIva: '',
      statoRichiestaOdm: '',
      statoRichiestaEf: '',
      codiceFiscaleSocieta: ''
    };
    buttonActiveModal!.click();
  }

  //FINE FUNZIONI MODAL

  routerDomandaIscrizione(idRichiesta: number) {
    window.location.href = "/organismiDiMediazione?vistaMenu=domanda_di_iscrizione&idRichiesta=" + idRichiesta;
  }

  openModalSaveSocieta(idSocieta: number, ragioneSociale: string, partitaIva: string, codiceFiscaleSocieta: string) {
    this.aggiornamentoSocietaComponent.openModal(idSocieta, ragioneSociale, partitaIva, codiceFiscaleSocieta);
  }

  getModalSaveSocieta(event: any /* parametro per capire da chi proviene la richiesta*/) {
    this.loadTable();
  }

  getDescTipoRic(idTipoRichiedente: number) {
    if (idTipoRichiedente == 49) {
      return "Privato";
    }
    else if (idTipoRichiedente == 51) {
      return "Pubblico - Diverso da CCIAA e Ordine Professionale";
    }
    else if (idTipoRichiedente == 52) {
      return "Pubblico - CCIAA e Ordine Avvocati";
    }
    else if (idTipoRichiedente == 53) {
      return "Pubblico - Ordini Professionali Non Avvocati";
    }
    else {
      return "";
    }

  }

  //FINE---------------------------------------------

  //Serve per poter ricevere funzioni dichiarata dentro al COMPONENT figlio
  onConfirmedRequestDelete(event: string) {
  }

  openPaneRichieste(societa: any) {
    this.singolaSocieta = societa;
    // Cambiera il nome della società nell'header
    this.sharedService.onNameSocietaHeader(this.singolaSocieta.ragioneSociale);

    // La richiesta delle tipologie verra fatta solo se lo non è in Compilazione
    if (this.singolaSocieta.statoRichiestaOdm === '') {
      this.serviceME.getAllTipoRichiedente('api/tipoRichiedente/getAll')
        .subscribe((res: any) => {
          this.tipologieROdm = res;
        });
    }

    if (this.singolaSocieta.statoRichiestaEf === '') {
      this.serviceME.getAllTipoRichiedente('api/tipoRichiedente/getAllEf')
        .subscribe((res: any) => {
          this.tipologieREf = res;
        });
    }

    // In caso di compilazione o altri tipi di stato che non siano vuoti
    if (this.singolaSocieta.statoRichiestaOdm !== '') {
      const params = new HttpParams()
        .set('idRichiesta', this.singolaSocieta.id);

      this.serviceME.getRichiestaForSocieta('richiesta/getRichiestaForSocieta', params)
        .subscribe((res: any) => {
          this.richiesta = res;
        });
    }

    var buttonActivePanelRichieste = document.getElementById(
      'btnOpenRichiestePanel'
    );

    var panelRichieste = document.getElementById('sub-tables');
    panelRichieste!.classList.remove('show');
    buttonActivePanelRichieste!.click();

    setTimeout(() => {
      panelRichieste!.scrollIntoView({
        behavior: 'smooth',
      });
    }, 200);
  }

  changeTipologiaRichiestaOdm(event: any) {
    this.selectId4Odm = event.target.value == 3 ? true : false;
  }

  invioRichiestaODM() {
    let richiestaODM = {
      idSocieta: this.singolaSocieta.id,
      idTipoRichiedente: this.tipologiaRichiestaOdm,
      idTipoRichiesta: 1,
    };


    this.serviceME.insertRichiestaODM('societa/richiestaIscrizioneODM', richiestaODM).subscribe({
      next: (res: any) => {
        this.loadTable();

        // ASSEGNAZIONE NUOVI VALORI CON LA RICHIESTA CREATA
        const params = new HttpParams()
          .set('idRichiesta', this.singolaSocieta.id);
        this.serviceME.getRichiestaForSocieta('richiesta/getRichiestaForSocieta', params)
          .subscribe((res: any) => {
            this.richiesta = res;
            this.singolaSocieta = res;
          });

        this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!");
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "L'inserimento della richiesta non è andato a buon fine");
      }
    })
  }

  invioRichiestaADM() {
    // ...in attesa di dettagli 
  }

  invioRichiestaEF() {
    let richiesta = {
      idSocieta: this.singolaSocieta.id,
      idTipoRichiedente: this.tipologiaRichiestaOdm,
      idTipoRichiesta: 1,
    };

    this.serviceME.insertRichiestaEF('societa/richiestaIscrizioneEF', richiesta).subscribe({
      next: (res: any) => {

      },
      error: (error: any) => {

      }
    })
  }



}
