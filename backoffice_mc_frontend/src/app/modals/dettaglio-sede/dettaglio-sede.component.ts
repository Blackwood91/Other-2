import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import * as moment from 'moment';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Component({
  selector: 'app-dettaglio-sede',
  templateUrl: './dettaglio-sede.component.html',
  styleUrls: ['../../../style/bootstrap-italia/css/bootstrap-italia.min.css',
  '../../../style/bootstrap-italia/assets/docs.min.css', './dettaglio-sede.component.css']
}) 
export class DettaglioSedeComponent implements OnInit {
  @Output() eventSedi: EventEmitter<any> = new EventEmitter();

  isModifica = false;
  isEstero: string = "false";
  stato: string = "";
  validStato: boolean = true;
  validComuneScelto: boolean = true;
  validComuneEstero: boolean = true;
  comuneEstero: string = "";
  autonomo: boolean = false;
  isLegale: boolean = false;
  idSede: number = 0;
  indirizzo: string = "";
  numeroCivico: string = "";
  cap: string = "";
  idComune: number = 0;
  telefono: string = "";
  fax: string = "";
  pec: string = "";
  email: string = "";
  idTitoloDetezione: number = 0;
  sitoWeb: string = "";
  dataContratto: string = "";
  durataContratto: string = "";
  registrazioneContratto: string = "";
  struttOrganizzativa: string = "";
  allegatoCopContratto: File | null = null;
  allegatoPlanimetria: File | null = null;
  idTitoloDefinizione: number = 0;
  dataInserimentoSede: string = "";
  dataCancellazione: string = "";
  // Dati di riferimento
  comuni: any = [];
  comuneScelto: string = "";
  showListComuni: boolean = false;
  struttOrganizzativaInput: boolean = true;
  titoliDetenzione: any = [];

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {
  }


  openModal(idRichiesta: number, idSede: number, idComune: number, nomeComune: string, siglaProvincia: string) {
    this.idSede = idSede;
  
    const params = new HttpParams()
      .set('idSede', idSede)

    this.serviceME
      .getSedeOperativa('sede/getSedeOperativa', params)
      .subscribe((res: any) => {
        this.isLegale = res.sedeLegale == "1" ? false : true;
        this.idTitoloDetezione = res.idTitoloDefinizione;
        this.indirizzo = res.indirizzo;
        this.numeroCivico = res.numeroCivico;
        this.cap = res.cap;
        this.idComune = res.idComune;
        this.telefono = res.telefono;
        this.fax = res.fax;
        this.pec = res.pec;
        this.email = res.email;
        this.sitoWeb = res.sitoWebSede;
        this.dataContratto = !!res.dataContratto ?  (moment(res.dataContratto)).format('YYYY-MM-DD') : "";
        this.durataContratto = res.durataContratto;
        this.registrazioneContratto = res.registrazione;
        this.struttOrganizzativaInput =
          res.strutturaOrgSegreteria === null ? true : false;
        this.struttOrganizzativa = res.strutturaOrgSegreteria;         
        this.idTitoloDefinizione = res.idTitoloDefinizione;
        this.dataContratto = (moment(res.dataContratto)).format('YYYY-MM-DD');
        this.dataInserimentoSede = (moment(res.dataInserimentoSede)).format('YYYY-MM-DD');
        this.dataCancellazione = (moment(res.dataCancellazione)).format('YYYY-MM-DD');  

        this.selectComune(idComune.toString(), nomeComune + ' (' + siglaProvincia + ')');
        this.loadSediTitoloDetenzione();  
      });
      
    const buttonActiveModal = document.getElementById("activeModalSede");
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }

  loadComune(nomeComune: any) {
    if (nomeComune.target.value.trim().length !== 0) {
      this.serviceME.getAllComuni('comune/getAllComuneByNome', nomeComune.target.value)
        .subscribe({
          next: (res: any) => {
            this.comuni = res;
            this.showListComuni = true;
          }
        })
    }
    else {
      this.showListComuni = false;
    }
  }

  selectComune(idComune: string, comune: string) {
    this.idComune = parseInt(idComune);
    this.comuneScelto = comune;
    this.showListComuni = false;
  }

  loadSediTitoloDetenzione() {
    this.serviceME.getAllTitoloDetenzione('sedeDetenzioneTitolo/getAllSedeDetezioneTitolo')
      .subscribe({
        next: (res: any) => {
          this.titoliDetenzione = res;
        }
      })
  }

  struttOrganizzativaCheckboxChange(select: boolean): any {
    this.struttOrganizzativaInput = select;
  }

  onFileAllegatoCopContrat(event: any) {
    this.allegatoCopContratto = event.target.files[0];
  }

  onFileAllegatoPlanimetria(event: any) {
    this.allegatoPlanimetria = event.target.files[0];
  }

  anteprimaPdf() {
    let formData: FormData = new FormData();
    formData.append('allegatoCopContratto', this.allegatoCopContratto!, "copia_contratto");
    formData.append('allegatoPlanimetria', this.allegatoPlanimetria!, "allegato_planimetria");

    this.serviceME.getFileAnteprimaORM('pdf/anteprimaORM', formData)
      .subscribe({
        next: (res: any) => {
          var file = new Blob([this.convertiStringaBlobAFile(res.file)], { type: 'application/pdf' });
          var fileURL = URL.createObjectURL(file);
          window.open(fileURL, '_blank');
        },
        error: (error: any) => { }
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

  titoloDetenzione(id: number) {
    switch(id) {
      case 1: return 'Proprietà';
      case 2: return 'Locazione';
      case 3: return "Comodato d'uso";
      default: return 'errore';
    }
  }
  
  closeModalUpdateSede() {
    const closeModal = document.getElementById('closeModal');
    closeModal!.click(); 
    this.eventSedi.emit("prova");
  }

}
