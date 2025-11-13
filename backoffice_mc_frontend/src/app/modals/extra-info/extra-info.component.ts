import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-extra-info',
  templateUrl: './extra-info.component.html',
  styleUrls: ['./extra-info.component.css']
})
export class ExtraInfoComponent implements OnInit {
  titolo = "";
  descrizione = "";
  extraDescrizione = "";

  constructor() { }

  ngOnInit(): void {
  }

  //Campi non validi per il convalida
  openModal(titolo: string, descrizione: string) {
    this.titolo = titolo;
    const contaPosizione = this.contaPosizioneCarattere(descrizione, "-");
    // Il contaPosizioneCarattere serve per sapere se ci sono almeno 6 - e in caso suddividere la descrizione
    if (contaPosizione != 0) {
      this.descrizione = descrizione.substring(0, (contaPosizione - 4)) + "...";
      this.extraDescrizione = descrizione;
    }
    else if (descrizione.length >= 590) {
      this.descrizione = descrizione.substring(0, 590) + "...";
      this.extraDescrizione = descrizione;
    }
    else {
      this.descrizione = descrizione;
    }

    // IN CASSO LA MODALE SIA GIA' APERTA NON VERRA' RIESEGUITO IL CLICK, PER EVITARE LA CHIUSURA 
    let divContenitore = document.getElementById("collapseCard")!;
    if (divContenitore.classList.contains("show")) {
      //  console.log("L'elemento ha la classe specificata.");
    } else {
      const buttonActiveModal = document.getElementById("buttonOpencollapseCard");
      buttonActiveModal!.click();
    }

  }

  //Campi non validi per il convalida
  closeModal() {
    let divContenitore = document.getElementById("collapseCard")!;
    if (divContenitore.classList.contains("show")) {
      const buttonActiveModal = document.getElementById("buttonOpencollapseCard");
      buttonActiveModal!.click();
    } 
  }

  contaPosizioneCarattere(stringa: string, carattere: string) {
    let conteggio = 0;
    let indiceSesto = 0;

    for (let i = 0; i < stringa.length; i++) {
      if (stringa[i] === carattere) {
        conteggio++;
        if (conteggio === 6) {
          indiceSesto = i;
          break;
        }
      }
    }
    // Torna la posizione dove è posizionato il sesto carattere "-" 
    return indiceSesto;
  }

}
