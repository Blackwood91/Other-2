import { HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { SchedaAnagraficaCompOrgAmRiepilogoComponent } from 'src/app/modals/scheda-anagrafica-comp-org-am-riepilogo/scheda-anagrafica-comp-org-am-riepilogo.component';
import { SchedaAnagraficaCompOrgAmComponent } from 'src/app/modals/scheda-anagrafica-comp-org-am/scheda-anagrafica-comp-org-am.component';
import { ConfirmMessageComponent } from 'src/app/principal-components/confirm-message/confirm-message.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-elenco-componenti-organo-amm',
  templateUrl: './elenco-componenti-organo-amm.component.html',
  styleUrls: ['./elenco-componenti-organo-amm.component.css']
})
export class ElencoComponentiOrganoAmmComponent implements OnInit {
  idRichiesta: number = 0;
  isModifica: boolean = false;
  isConvalidato: boolean = false;
  @ViewChild(SchedaAnagraficaCompOrgAmComponent) schedaAnagraficaCompOrgAmComponent!: SchedaAnagraficaCompOrgAmComponent;
  @ViewChild(SchedaAnagraficaCompOrgAmRiepilogoComponent) schedaAnagraficaCompOrgAmRiepilogoComponent!: SchedaAnagraficaCompOrgAmRiepilogoComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  //Var Tabella
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;

  idAnagrafica: number = 0;
  sesso: string = "";
  cognome: string = "";
  nome: string = "";
  dataNascita: string = "";
  idComuneNascita: number = 0;
  comuneNascitaEstero: string = "";
  statoNascita: string = "";
  comuneSceltoNascita: string = "";
  comuneSceltoResidenza: string = "";
  idComuneResidenza: number = 0;
  comuneResidenzaEstero: string = "";
  statoResidenza: string = "";
  codiceFiscale: string = "";
  isEsteroNascita: string = "false";
  isEsteroResidenza: string = "false";
  idTipoAnagrafica: number = 0;
  indirizzoEmail: string = "";
  medTelefono: string = "";
  medCellulare: string = "";
  medFax: string = "";

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.loadTable();
      this.loadIsConvalidato()
    });
  }


  getQualifica(idQualifica: number) {
    if (idQualifica == 1) {
      return "Rappresentante Legale";
    }
    else if (idQualifica == 2) {
      return "Responsabile dell'Organismo";
    }
    else if (idQualifica == 3) {
      return "Componente organo Amministrazione";
    }
    else if (idQualifica == 4) {
      return "Socio";
    }
    else if (idQualifica == 5) {
      return "Associato";
    }
    else if (idQualifica == 6) {
      return "Altro";
    }
    return "";
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  activeModifica() {
    this.isModifica = true;
  }

  openModalUpdateScheda(idAnagrafica: number) {
    this.schedaAnagraficaCompOrgAmComponent.openModal(this.idRichiesta, idAnagrafica); 
  }

  openModalRiepilogoScheda(idAnagrafica: number) {
    this.schedaAnagraficaCompOrgAmRiepilogoComponent.openModal(this.idRichiesta, idAnagrafica, this.tableResult.length > 1 ? false : true);
  }

  openModalConfirmMessage(idAnagrafica: number, tipoRap: string, nomeRap: string) {
    if (tipoRap != "Rappresentante Legale") {
      let message = "Si vuole confermare la cancellazione del " + tipoRap + " " + nomeRap + "?";
      this.confirmMessageComponent.openModal(idAnagrafica, message);
    }
  }

  //FUNZIONI TABELLA
  loadTable() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm('anagrafica/getAllAnagraficaByIdRichiestaForEleCompOrgAm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  attivaRicerca() {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm('anagrafica/getAllAnagraficaByIdRichiestaForEleCompOrgAm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPagina(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm('anagrafica/getAllAnagraficaByIdRichiestaForEleCompOrgAm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm('anagrafica/getAllAnagraficaByIdRichiestaForEleCompOrgAm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPage(index: number) {
    const params = new HttpParams()
      .set('searchText', this.searchTextTable)
      .set('indexPage', index - 2)
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getAllAnagraficaByIdRichiestaForEleCompOrgAm('anagrafica/getAllAnagraficaByIdRichiestaForEleCompOrgAm', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  getModalChildren(event: any) {
    this.isModifica = false;
    this.sharedService.onUpdateMenu();
    this.loadTable();
    this.loadIsConvalidato();
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams()
        .set('idRichiesta', this.idRichiesta)
        .set('idAnagrafica', event.idRitorno);

      this.serviceME
        .deleteCompOrgAm('anagrafica/deleteCompOrgAm', params)
        .subscribe({
          next: (res: any) => {
            this.isModifica = false;
            this.loadTable();
            this.loadIsConvalidato();
            this.sharedService.onMessage('success', "La cancellazione è avvenuta con successo!");
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
    }

  }

  loadIsConvalidato() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getStatusElencoRapLRespOrg('status/getStatusElenCompOrgAm', params)
      .subscribe({
        next: (res: any) => {
          this.isConvalidato = res.isConvalidato;
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  openPdfFile() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idAnagrafica', this.idAnagrafica);

    this.serviceME.getAnteprimaFileAutocertificazioneReqOno('pdf/getAnteprimaFileSchedeMediatoriCompOrgAm', params)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, '_blank');
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })
  }

  // Per formattare byte in file pdf
  convertiStringaBlobAFile(dati: string): File {
    let byteCharacters = atob(dati);
    let byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    let byteArray = new Uint8Array(byteNumbers);
    let blobFile = new Blob([byteArray], { type: 'application/pdf' });
    return new File([blobFile], 'file');
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validazioneElencoCompOrgAmAndCompSoc('status/validazioneElencoCompOrgAmAndCompSoc', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTable();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', "Non è stato possibile proseguire con la convalidazione dell'elenco");
        }
      });
  }

  annullazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .annullaElencoCompOrgAmAndCompSoc('status/annullaElencoCompOrgAmAndCompSoc', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTable();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', "Non è stato possibile proseguire con l'annullamento dell'elenco");
        }
      });
  }
  

}
