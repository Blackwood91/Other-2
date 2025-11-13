import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-scheda-rapp-legale',
  templateUrl: './scheda-rapp-legale.component.html',
  styleUrls: ['./scheda-rapp-legale.component.css', '../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../style/bootstrap-italia/assets/docs.min.css']
})
export class SchedaRappLegaleComponent implements OnInit {
  // SEZIONE 1
  // Dati Richiesta
  dataAttoCosti: string = "";
  dataStatutoVig: string = "";
  codFiscSocieta: string = "";
  pIva: string = "";
  idNaturaSoc: number = 0;
  // Dati Sede
  indirizzoSP: string = "";
  numeroCivicoSP: string = "";
  capSP: string = "";
  idComune: number = 0;
  telefono: string = "";
  fax: string = "";
  pec: string = "";
  email: string = "";
  allegatoCopContratto: File | null = null;
  allegatoPlanimetria: File | null = null;
  // Dati di riferimento
  concideSedeOper: boolean = false;
  societa: { idNaturaSoc: number, descrizioneBreve: string, descrizione: string }[] = [];
  comuniSP: any = [];
  comuneSceltoSP: string = "";
  showListComuniSP: boolean = false;
  // Per controlli
  codiceFiscaleValid: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
