import { HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MediazioneService } from 'src/app/mediazione.service';
import { SharedService } from 'src/app/shared.service';
import * as moment from 'moment';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-save-sede',
  templateUrl: './save-sede.component.html',
  styleUrls: ['./save-sede.component.css']
})
export class SaveSedeComponent implements OnInit {
  @Output() eventSedi: EventEmitter<any> = new EventEmitter();
  idRichiesta: number = 0;

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
  // Dati di riferimento
  comuni: any = [];
  comuneScelto: string = "";
  showListComuni: boolean = false;
  struttOrganizzativaInput: boolean = true;
  titoliDetenzione: any = [];

  constructor(private serviceME: MediazioneService, private sharedService: SharedService) { }

  ngOnInit(): void {
  }


  openModalUpdate(idRichiesta: number, idSede: number, idComune: number, nomeComune: string, siglaProvincia: string) {
    this.idRichiesta = idRichiesta;
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
        this.struttOrganizzativaInput = res.strutturaOrgSegreteria === null ? true : false;
        this.struttOrganizzativa = res.strutturaOrgSegreteria; 

        this.selectComune(idComune.toString(), nomeComune + ' (' + siglaProvincia + ')');
        this.loadSediTitoloDetenzione();  
      });
      
    const buttonActiveModal = document.getElementById("activeModalSaveSede");
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
  
  closeModalUpdateSede() {
    const closeModal = document.getElementById('closeModal');
    closeModal!.click(); 
    this.eventSedi.emit("prova");
  }

  updateSede() {
    let sedeLegale: {
      idSede: number, idRichiesta: number, indirizzo: string, numeroCivico: string, cap: string, idComune: number,
      telefono: string, fax: string, pec: string, email: string, idTitoloDetenzione: number, sitoWeb: string, dataContratto: string, durataContratto: string
      registrazione: string, strutOrgSeg: string, isSedeLegale: string
    } = {
      idSede: this.idSede, idRichiesta: this.idRichiesta, indirizzo: this.indirizzo, numeroCivico: this.numeroCivico,
      cap: this.cap, idComune: this.idComune, telefono: this.telefono, fax: this.fax, pec: this.pec, email: this.email,
      idTitoloDetenzione: this.idTitoloDetezione, sitoWeb: this.sitoWeb, dataContratto: this.dataContratto, durataContratto: this.durataContratto,
      registrazione: this.registrazioneContratto, strutOrgSeg: this.struttOrganizzativa, isSedeLegale: this.isLegale == true ? "0" : "1"
    }

    let formData: FormData = new FormData();
    formData.append('sedeLegale', new Blob([JSON.stringify(sedeLegale)], { type: 'application/json' }));
    // Solo se ineriti verrà creato il form apposito, omettendoli si stanno passando come null al server
    if (this.allegatoCopContratto != null) {
      formData.append('allegatoCopContratto', this.allegatoCopContratto!, "copia_contratto");
    }
    if(this.allegatoPlanimetria != null) {
      formData.append('allegatoPlanimetria', this.allegatoPlanimetria!, "allegato_planimetria");
    }

    this.serviceME.updateSede('sede/updateSede', formData)
      .subscribe({
        next: (res: any) => {
          this.sharedService.onMessage('success', "La modifica è avvenuta con successo!");
          this.closeModalUpdateSede();
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error.message);
        }
      })

  }

  checkStato() {
    if(this.stato.length > 0) {
      this.validStato = true;
      return true;
    } else {
      this.validStato = false;
      return false;
    }
  }

  checkComuneEstero() {
    if(this.comuneEstero.length > 0) {
      this.validComuneEstero = true;
      return true;
    } else {
      this.validComuneEstero = false;
      return false;
    }
  }

  checkComuneScelto() {
    if(this.comuneScelto != undefined && this.comuneScelto != null && this.comuneScelto != '') {
      this.validComuneScelto = true;
      return true;
    } else {
      this.validComuneScelto = false;
      return false;
    }
  }

  validazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idSede', this.idSede);

    this.serviceME
      .validaSedeAttoRiepilogativo('status/validaSedeAttoRiepilogativo', params)
      .subscribe({
        next: (res: any) => {
          this.closeModalUpdateSede();
          this.sharedService.onMessage('success', "La convalidazione è avvenuta con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

  annullazione() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)
      .set('idSede', this.idSede);

    this.serviceME
      .annullaSedeAttoRiepilogativo('status/annullaSedeAttoRiepilogativo', params)
      .subscribe({
        next: (res: any) => {
          this.closeModalUpdateSede();
          this.sharedService.onMessage('success', "l'annullamento  è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      });
  }

}
