import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { AggiornamentoAnagraficaComponent } from 'src/app/modals/aggiornamento-anagrafica/aggiornamento-anagrafica.component';
import { ModalMediatoriComponent } from 'src/app/modals/modal-mediatori/modal-mediatori.component';
import { UpdateMediatoreComponent } from 'src/app/modals/update-mediatore/update-mediatore.component';
import { ConfirmMessageComponent } from 'src/app/principal-components/confirm-message/confirm-message.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-gestione-mediatori-c',
  templateUrl: './gestione-mediatori-c.component.html',
  styleUrls: ['./gestione-mediatori-c.component.css']
})
export class GestioneMediatoriCComponent implements OnInit {
  @Input()
  idRichiesta = 0;
  @Input()
  component = '';
  @ViewChild(ModalMediatoriComponent) modalMediatoriComponent!: ModalMediatoriComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(UpdateMediatoreComponent) updateMediatoreComponent!: UpdateMediatoreComponent;
  
  isModifica: boolean = false;
  idAnagrafica: number = 0;
  isConvalidato: boolean = false;
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
  comuniNascita = new Array();
  // DATI TABELLA
  tableResult = new Array();
  indexPage: number = 1;
  totalPage = 0;
  totalResult = 0;
  searchTextTable: string = '';

  viewOnly: boolean = false;

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.getAllMediatori();
    this.loadIsConvalidato();
  }

  getAllMediatori() {
    const params = new HttpParams()
    .set('indexPage', this.indexPage - 1)
    .set('idRichiesta', this.idRichiesta);

    // TO DO PAGABLE
    this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatoriC', params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result;
      }
    });
  }

  loadIsConvalidato() {
    // TO DO PAGABLE
    const params = new HttpParams()
    .set('idModulo', 52)
    .set('idRichiesta', this.idRichiesta);

    this.serviceME.getModuloIsConvalidato('status/isConvalidatoAllModuli', params).subscribe({
      next: (res: any) => {
        this.isConvalidato = res.isConvalidato;
      }
    });
  }

  public getRowsCon() {
    return this.tableResult.filter(i => i.idTipoAnagrafica == '6').length;
  }

  viewAnagrafica(idAnagrafica: number) {
    this.modalMediatoriComponent.openModal(idAnagrafica);
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  loadComuni() {
    this.serviceME.getAllComune('comune/getAllComune').subscribe({
      next: (res: any) => {
        this.comuniNascita = res;
      }
    })
  }

  filterComune(id: number) {
    let comune = this.comuniNascita.find((obj: { idCodComune: number; }) => {return obj.idCodComune === id});
    if(comune)
      return comune.nomeComune;
  }

  anteprimaPdf() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getAnteprimaFileAppeC('pdf/getAnteprimaFileAppeC', params)
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
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], { type: 'application/pdf' });

    return new File([blobFile], 'file');
  }

  openModalConfirmMessage(idAnagrafica: number, tipoRap: string, nomeRap: string) {
    let message = "Si vuole confermare la cancellazione del " + tipoRap + " " + nomeRap + "?";
    this.confirmMessageComponent.openModal(idAnagrafica, message); 
  }

  updateAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModal(idAnagrafica, this.idRichiesta, componente);
  }

  riepilogoAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModalRiepilogo(idAnagrafica, this.idRichiesta, componente);
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams()
        .set('idRichiesta', this.idRichiesta)
        .set('idAnagrafica', event.idRitorno);

      this.serviceME
        .deleteCompOrgAm('anagrafica/deleteMediatore', params)
        .subscribe({
          next: (res: any) => {
            this.sharedService.onUpdateMenu();
            this.getAllMediatori();
            this.loadIsConvalidato();

            this.isModifica = false;
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
    }

  }

  getModalChildrenUpdate(event: any) {
    this.sharedService.onUpdateMenu();
    this.getAllMediatori();
    this.loadIsConvalidato();
    
    this.isModifica = false;
  }

  activeModifica() {
    this.isModifica = true;
  }

  activeViewOnly() {
    this.viewOnly = true;
  }

  inactiveViewOnly() {
    this.viewOnly = false;
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validaElencoMediatoreC('status/validaElencoMediatoreC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.getAllMediatori();
          this.loadIsConvalidato();
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
      .annullaElencoMediatoreC('status/annullaElencoMediatoreC', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.getAllMediatori();
          this.loadIsConvalidato();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', "Non è stato possibile proseguire con l'annullamento dell'elenco");
        }
      });
  }

}
