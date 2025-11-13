import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { AccessibilitaService } from 'src/accessibilita.service';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { ModalMediatoriComponent } from 'src/app/modals/modal-mediatori/modal-mediatori.component';
import { UpdateMediatoreComponent } from 'src/app/modals/update-mediatore/update-mediatore.component';
import { ConfirmMessageComponent } from 'src/app/principal-components/confirm-message/confirm-message.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-gestione-mediatori-a',
  templateUrl: './gestione-mediatori-a.component.html',
  styleUrls: ['./gestione-mediatori-a.component.css']
})
export class GestioneMediatoriAComponent implements OnInit {
  @Input()
  idRichiesta = 0;
  @Input()
  component = '';
  @ViewChild(ModalMediatoriComponent) modalMediatoriComponent!: ModalMediatoriComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(UpdateMediatoreComponent) updateMediatoreComponent!: UpdateMediatoreComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;

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
  fileDocumento: any = null;

  viewOnly: boolean = false;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router,
              private sharedService: SharedService) { }
    
  ngOnInit(): void {
    this.getAllMediatori();
    this.loadIsConvalidato();
  }

  getAllMediatori() {
    // TO DO PAGABLE
    const params = new HttpParams()
    .set('indexPage', this.indexPage - 1)
    .set('idRichiesta', this.idRichiesta);

    this.serviceME.getAllAnagrafica('anagrafica/getAllAnagraficaMediatoriA', params).subscribe({
      next: (res: any) => {
        this.tableResult = res.result;
      }
    });
  }

  loadIsConvalidato() {
    // TO DO PAGABLE
    const params = new HttpParams()
    .set('idModulo', 38)
    .set('idRichiesta', this.idRichiesta);

    this.serviceME.getModuloIsConvalidato('status/isConvalidatoAllModuli', params).subscribe({
      next: (res: any) => {
        this.isConvalidato = res.isConvalidato;
      }
    });
  }

  anteprimaPdf() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getAnteprimaFileAppeA('pdf/getAnteprimaFileAppeA', params)
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

  public getRowsGen() {
    return this.tableResult.filter(i => i.idTipoAnagrafica == '4').length;
  }

  viewAnagrafica(idAnagrafica: number) {
    this.modalMediatoriComponent.openModal(idAnagrafica);
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  updateAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModal(idAnagrafica, this.idRichiesta, componente);
  }

  riepilogoAnagrafica(idAnagrafica: number, componente: string) {
    this.updateMediatoreComponent.openModalRiepilogo(idAnagrafica, this.idRichiesta, componente);
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

  openModalConfirmMessage(idAnagrafica: number, tipoRap: string, nomeRap: string) {
    let message = "Si vuole confermare la cancellazione del " + tipoRap + " " + nomeRap + "?";
    this.confirmMessageComponent.openModal(idAnagrafica, message); 
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
            this.sharedService.onMessage('success', "La cancellazione è avvenuta con successo!");
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
    }

    this.isModifica = false;
  }

  getModalChildrenUpdate(event: any) {
    this.sharedService.onUpdateMenu();
    this.getAllMediatori();
    this.loadIsConvalidato();
    
    this.isModifica = false;
  }

  convalidazione() {
    const params = new HttpParams()
                   .set('idRichiesta', this.idRichiesta);

    this.serviceME.validazioneElencoPrestServizio('status/validazioneElencoPrestServizio', params)
    .subscribe({
      next: (res: any) => {
        this.sharedService.onUpdateMenu();
        this.loadIsConvalidato();
        this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        return;
      },
      error: (error: any) => {
        this.sharedService.onMessage('error', "Non è stato possibile proseguire con la convalidiazione dell'elenco");
        this.extraInfoComponent.openModal("Impossibile proseguire con la convalidazione:", error)
        return;
      }
    })
  }

  getModalChildren(event: any) {
    this.isModifica = false;
    this.sharedService.onUpdateMenu();
    this.getAllMediatori()
    this.loadIsConvalidato();
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .validaElencoMediatoreA('status/validaElencoMediatoreA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.getAllMediatori();
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
      .annullaElencoMediatoreA('status/annullaElencoMediatoreA', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.getAllMediatori();
          this.sharedService.onMessage('success', "L'annullamento è avvenuto con successo !");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', "Non è stato possibile proseguire con l'annullamento dell'elenco");
        }
      });
  }

}
