import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccessibilitaService {
  // Parametri componente
  accessParameters: any = {
    isAdn: '',
    ruolo: '',
    nome: '',
    cognome: '',
    societaSelezionata: ''
  };
  constructor() { }

  cookieName: string = "cookieGR";
  expires: string = "";

  // Funzioni component-----------------------  
  setAccessParameters(ruolo: string) {
    //il ruolo verrà preso dalla risposta dell'api
    //updateParamCookie();
  }

  checkAccessoElezioni() {
    // Tutti i ruoli che possono accedere al componente 
    switch (this.accessParameters.ruolo) {
      case 'principale':
        return true;
      default:
        return false;
    }
  }

  // Funzioni cookie------------------------
  // Imposta i valori con la scadenza nel cookie
  setCookie(isAdn: string, ruolo: string, nome: string, cognome: string) {

    var currentDate = new Date();
    // Imposta la data di scadenza alla mezzanotte del giorno corrente
    var expirationDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 1, 0, 0, 0, 0);

    // La data di scadenza ora sarà la mezzanotte del giorno successivo

    /*const expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() + 40 * 60 * 1000); // Scade dopo 40 minuti*/
    this.accessParameters.isAdn = isAdn;
    this.accessParameters.ruolo = ruolo;
    this.accessParameters.nome = nome;
    this.accessParameters.cognome = cognome;
    this.accessParameters.societaSelezionata = "";
    // Serializza i dati in una stringa JSON
    const cookieValue = JSON.stringify(this.accessParameters);

    // Imposta il cookie con la scadenza
    this.expires = `expires=${expirationDate.toUTCString()}`;
    document.cookie = this.cookieName + `=${cookieValue}; ${this.expires}; path=/`;
  }

  // Per vedere se il cookie è stato impostato
  existCookieUser() {
    if (this.getCookieValue('ruolo') === "" || this.getCookieValue('ruolo') === null) {
      return false;
    }
    else {
      return true;
    }
  }

  // Prende valore singolo nel cookie
  getCookieValue(nomeParam: string) {
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
      const [name, value] = cookie.trim().split('=');

      if (name === this.cookieName) {
        const data = JSON.parse(value);

        if (data.hasOwnProperty(nomeParam)) {
          return data[nomeParam];
        }

      }
    }
    // In caso di valore non trovato
    return null;
  }

  // Aggiorna un valore nel cookie
  updateParamCookie(nomeParam: string, newValue: string) {
    // Recuper di tutti i cookie
    const cookies = document.cookie.split(';');
    let data;
    for (const cookie of cookies) {
      const [name, value] = cookie.trim().split('=');

      if (name === this.cookieName) {
        // Recupero di tutti i parametri nel cookie
        data = JSON.parse(value);
        // Cerca il nome del parametro
        if (data.hasOwnProperty(nomeParam)) {
          data[nomeParam] = newValue;
        }

      }
    }
    // Serializza i dati in una stringa JSON
    const cookieValue = JSON.stringify(data);
    this.accessParameters = data;
    // Risetta il cookie con la scadenza precedentemente inserita
    document.cookie = this.cookieName + `=${cookieValue}; ${this.expires}; path=/`;
  }

  deleteCookie() {
    document.cookie = this.cookieName + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  }

}
