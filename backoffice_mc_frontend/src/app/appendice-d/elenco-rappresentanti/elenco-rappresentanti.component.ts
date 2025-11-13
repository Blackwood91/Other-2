import { HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { ExtraInfoComponent } from 'src/app/modals/extra-info/extra-info.component';
import { SchedaAnagraficaRappresentanteRiepilogoComponent } from 'src/app/modals/scheda-anagrafica-rappresentante-riepilogo/scheda-anagrafica-rappresentante-riepilogo.component';
import { SchedaAnagraficaRappresentantiComponent } from 'src/app/modals/scheda-anagrafica-rappresentanti/scheda-anagrafica-rappresentanti.component';
import { ConfirmMessageComponent } from 'src/app/principal-components/confirm-message/confirm-message.component';
import { SharedService } from 'src/app/shared.service';

@Component({
  selector: 'app-elenco-rappresentanti',
  templateUrl: './elenco-rappresentanti.component.html',
  styleUrls: ['./elenco-rappresentanti.component.css']
})
export class ElencoRappresentantiComponent implements OnInit {
  idRichiesta: number = 0
  isConvalidato: boolean = false;
  searchTextTable: string = '';
  indexPage: number = 1;
  tableResult = new Array();
  totalPage = 0;
  totalResult = 0;
  @ViewChild(SchedaAnagraficaRappresentantiComponent) schedaAnagraficaRappresentantiComponent!: SchedaAnagraficaRappresentantiComponent;
  @ViewChild(SchedaAnagraficaRappresentanteRiepilogoComponent) schedaAnagraficaRappresentanteRiepilogoComponent!: SchedaAnagraficaRappresentanteRiepilogoComponent;
  @ViewChild(ConfirmMessageComponent) confirmMessageComponent!: ConfirmMessageComponent;
  @ViewChild(ExtraInfoComponent) extraInfoComponent!: ExtraInfoComponent;
  qualifiche: any = [];
  isModifica: boolean = false;
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
  // RAPPRESENTA IL CLONE DEL RAP. LEGALE IN UN RESP. DELL'ORGANISMO 
  rapLegCloneRespOperativa: any = null;

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];
      this.loadTableSO();
      this.loadIsConvalidato();
    });

  }

  openModalUpdateScheda(idAnagrafica: number) {
    this.schedaAnagraficaRappresentantiComponent.openModal(this.idRichiesta, idAnagrafica,  this.tableResult.length > 1 ? false : true,  
        (this.rapLegCloneRespOperativa != null && idAnagrafica == this.rapLegCloneRespOperativa.idAnagrafica)); 
  }

  openModalRiepilogoScheda(idAnagrafica: number) {
    this.schedaAnagraficaRappresentanteRiepilogoComponent.openModal(this.idRichiesta, idAnagrafica, (this.rapLegCloneRespOperativa != null && idAnagrafica == this.rapLegCloneRespOperativa.idAnagrafica));
  }

  openModalConfirmMessage(idAnagrafica: number, tipoRap: string, nomeRap: string) {
    if (tipoRap != "Rappresentante Legale") {
      let message = "Si vuole confermare la cancellazione del " + tipoRap + " " + nomeRap + "?";
      this.confirmMessageComponent.openModal(idAnagrafica, message);
    }
  }

  //FUNZIONI TABELLA
  loadTableSO() {
    const params = new HttpParams()
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForRapLegAndRespOrg('anagrafica/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg', params)
      .subscribe((res: any) => {
        for (let i = 0; i < res.result.length; i++) {
          // ISTRUZIONI CHE VERRANNO FATTE SOLO IN CASO DI RAPP. LEGALE
          if (res.result[i].idQualifica == 1) {
            const params = new HttpParams()
              .set('idRichiesta', this.idRichiesta)
              .set('codiceFiscale', res.result[i].codiceFiscale);

            this.serviceME
              .getAnagraficaCloneRespOrg('anagrafica/getAnagraficaCloneRespOrg', params)
              .subscribe((res2: any) => {
                if (res2 != null) {
                  // VERRA IMPOSTATO L'ID ANAGRAFICA DEL RESPONSABILE DELL'ORGANISMO NEL RAP. LEGALE
                  res.result[i].idAnagrafica = res2.idAnagrafica;
                  this.rapLegCloneRespOperativa = res2;

                  // PER ORDINARE IL RAP.LEGALE COME PRIMO ROW 
                  for (let y = 0; y < res.result.length; y++) {
                    if(res.result[i].idAnagrafica == res.result[y].idAnagrafica &&
                       res.result[y].idQualifica == 2 ) {
                      let respOrg = res.result[y];

                      res.result.splice(y, 1);  
                      res.result.unshift(respOrg); 
                    }
                  }         
                }
              });

          }

        }

        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
        this.tableResult = res.result;
      });
  }

  attivaRicercaSO() {
    const params = new HttpParams()
      .set('indexPage', this.indexPage - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForRapLegAndRespOrg('anagrafica/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
        this.totalPage = Math.ceil(res.totalResult / 10);
        this.totalResult = res.totalResult;
      });
  }

  cambiaPaginaSO(index: number) {
    const params = new HttpParams()
      .set('indexPage', index - 1)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForRapLegAndRespOrg('anagrafica/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index;
  }

  nextPageSO(index: number) {
    const params = new HttpParams()
      .set('indexPage', index)
      .set('idRichiesta', this.idRichiesta);

    this.serviceME
      .getAllAnagraficaByIdRichiestaForRapLegAndRespOrg('anagrafica/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index + 1;
  }

  previousPageSO(index: number) {
    const params = new HttpParams()
      .set('indexPage', index - 2)
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getAllAnagraficaByIdRichiestaForRapLegAndRespOrg('anagrafica/getAllAnagraficaByIdRichiestaForRapLegAndRespOrg', params)
      .subscribe((res: any) => {
        this.tableResult = res.result;
      });

    this.indexPage = index - 1;
  }

  activeModifica() {
    this.isModifica = true;
  }

  public formatDate(date: Date) {
    return (moment(date)).format('DD-MM-YYYY')
  }

  loadElencoRap() {
    this.serviceME.getAllQualifiche('qualifica/getAll')
      .subscribe({
        next: (res: any) => {
          this.qualifiche = res;
        }
      })
  }

  getModalChildren(event: any) {
    this.isModifica = false;
    this.sharedService.onUpdateMenu();
    this.loadTableSO();
    this.loadIsConvalidato();
  }

  getModalChildrenConfirm(event: any) {
    if (event.message == "confermato") {
      const params = new HttpParams()
        .set('idRichiesta', this.idRichiesta)
        .set('idAnagrafica', event.idRitorno);

      this.serviceME
        .deleteRappresentante('anagrafica/deleteRappresentante', params)
        .subscribe({
          next: (res: any) => {
            this.isModifica = false;
            this.loadTableSO();
            this.loadIsConvalidato();
            this.sharedService.onMessage('success', "La cancellazione è avvenuta con successo!");
          },
          error: (error: any) => {
            this.sharedService.onMessage('error', error);
          }
        })
    }

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

  getQualificaBreve(idQualifica: number) {
    if (idQualifica == 1) {
      return "Rap. Legale";
    }
    else if (idQualifica == 2) {
      return "Resp. dell'Org.";
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

  loadIsConvalidato() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME
      .getStatusElencoRapLRespOrg('status/getStatusElencoRapLRespOrg', params)
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

    this.serviceME.getAnteprimaFileAutocertificazioneReqOno('pdf/getAnteprimaFileSchedeMediatori', params)
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
      .validazioneElencoRapLegAndRespOrg('status/validazioneElencoRapLegAndRespOrg', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSO();
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
      .annullaElencoRapLegAndRespOrg('status/annullaElencoRapLegAndRespOrg', params)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.loadIsConvalidato();
          this.loadTableSO();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', "Non è stato possibile proseguire con l'annullamento dell'elenco");
        }
      });
  }


}
