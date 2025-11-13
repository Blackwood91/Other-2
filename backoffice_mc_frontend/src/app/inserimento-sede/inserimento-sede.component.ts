import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { MediazioneService } from '../mediazione.service';
import { HttpParams } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-inserimento-sede',
  templateUrl: './inserimento-sede.component.html',
  styleUrls: [ './inserimento-sede.component.css']
})
export class InserimentoSedeComponent implements OnInit {
  idRichiesta: number = 0;

  isModifica = false;
  isEstero: string = "false";
  stato: string = "";
  validStato: boolean = true;
  comuneEstero: string = "";
  validComuneEstero: boolean = true;
  validComuneScelto: boolean = true;
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
  legaleIsOperativa: boolean = false;
  // Dati di riferimento
  comuni: any = [];
  comuneScelto: string = "";
  showListComuni: boolean = false;
  struttOrganizzativaInput: boolean = true;
  titoliDetenzione: any = [];

  constructor(private serviceME: MediazioneService, private route: ActivatedRoute, private router: Router, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];

      this.existSedeLegale();
      this.loadSediTitoloDetenzione();
    })

  }

  loadSediTitoloDetenzione() {
    this.serviceME.getAllTitoloDetenzione('sedeDetenzioneTitolo/getAllSedeDetezioneTitolo')
      .subscribe({
        next: (res: any) => {
          this.titoliDetenzione = res;
        }
      })
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

  struttOrganizzativaCheckboxChange(select: boolean): any {
    this.struttOrganizzativaInput = select;
  }

  sedeLegaleIsOperativaCheckboxChange(select: boolean): any {
    this.legaleIsOperativa = select;
  }

  activeModifica() {
    this.isModifica = true;
  }


  existSedeLegale() {
    const paramsRequest = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.existSedeLegale('sede/existSedeLegale', paramsRequest).subscribe({
      next: (res: any) => {
        this.isLegale = res.esito;
      },
      error: (error: any) => {

      }
    })

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

  insertSede() {
    // Altri controlli oltre quelli del form
    if (this.allegatoCopContratto == null || this.allegatoPlanimetria == null) {
      this.sharedService.onMessage('error', "Inserire i file inerenti alla sede per proseguire");
      return;
    }

    let sedeLegale: {
      idSede: number, idRichiesta: number, indirizzo: string, numeroCivico: string, cap: string, idComune: number,
      telefono: string, fax: string, pec: string, email: string, idTitoloDetenzione: number, sitoWeb: string, dataContratto: string, durataContratto: string
      registrazione: string, strutOrgSeg: string, isSedeLegale: string, nomeFileCopContratto: string, nomeFilePlanimetria:string, legaleIsOperativa: boolean
    } = {
      idSede: this.idSede, idRichiesta: this.idRichiesta, indirizzo: this.indirizzo, numeroCivico: this.numeroCivico,
      cap: this.cap, idComune: this.idComune, telefono: this.telefono, fax: this.fax, pec: this.pec, email: this.email,
      idTitoloDetenzione: this.idTitoloDetezione, sitoWeb: this.sitoWeb, dataContratto: this.dataContratto, durataContratto: this.durataContratto,
      registrazione: this.registrazioneContratto, strutOrgSeg: this.struttOrganizzativa, nomeFileCopContratto: this.allegatoCopContratto.name,
      nomeFilePlanimetria: this.allegatoPlanimetria.name, isSedeLegale: this.isLegale == true ? "0" : "1",
      legaleIsOperativa: this.legaleIsOperativa
    }

    let formData: FormData = new FormData();
    formData.append('sedeLegale', new Blob([JSON.stringify(sedeLegale)], { type: 'application/json' }));
    formData.append('allegatoCopContratto', this.allegatoCopContratto, "copia_contratto");
    formData.append('allegatoPlanimetria', this.allegatoPlanimetria, "allegato_planimetria");

    this.serviceME.insertSede('sede/insertSede', formData)
      .subscribe({ 
        next: (res: any) => {
          this.sharedService.onUpdateMenu();
          this.sharedService.onMessage('success', "L'inserimento è avvenuto con successo!");
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })

  }

  routerGestioneSede() {
    this.sharedService.onChangeViewMenuODM("sedi");
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

}
