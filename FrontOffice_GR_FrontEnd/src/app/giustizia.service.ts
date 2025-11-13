import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class GiustiziaService {

  constructor(private http: HttpClient){}


public getElencoPubblico(url: string, parameters: {}): any {
    // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
      
}

public getAllUtenzeTab(url: string, parameters: {}): any {
  // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
}

public getAllCandidati(url: string, parameters: {}): any {
  // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
}

public getElezione(url: string, parameters: {}): any {
  // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
}

public getAllElezioniPost(url: string, parameters: {}): any {
  const headers = { 'content-type': 'application/json'}  
  const body = JSON.stringify(parameters);
  return this.http.post(url, body,{'headers':headers}).pipe(
    catchError((error: HttpErrorResponse) => {
      // Puoi accedere a tutte le informazioni dettagliate sull'errore qui
      console.error('Codice di stato HTTP:', error.status);
      console.error('Messaggio di errore:', error.message);
      console.error("Testo dell errore completo:", error.error); // Questo contiene i dettagli aggiuntivi
      console.error('Intestazioni HTTP:', error.headers);
      // restituisce un messaggio di errore che verra visualizzato nel subscribe
      return throwError(error.error);
    })
  )
}

public postUpdateElezioni(url: string, parameters: {}): any {
  const headers = { 'content-type': 'application/json'}  
  const body = JSON.stringify(parameters);
  return this.http.post(url, body,{'headers':headers}).pipe(
    catchError((error: HttpErrorResponse) => {
      // Puoi accedere a tutte le informazioni dettagliate sull'errore qui
      console.error('Codice di stato HTTP:', error.status);
      console.error('Messaggio di errore:', error.message);
      console.error("Testo dell errore completo:", error.error); // Questo contiene i dettagli aggiuntivi
      console.error('Intestazioni HTTP:', error.headers);
      // restituisce un messaggio di errore che verra visualizzato nel subscribe
      return throwError(error.error);
    })
  )
}

public getAllSeggi(url: string, parameters: {}): any {
    // Esegue una chiamata HTTP per recuperare un oggetto
      return this.http.get(url, {params: parameters});
}

public postUpdateUtenze(url: string, parameters: {}): any {
  const headers = { 'content-type': 'application/json'}  
  const body = JSON.stringify(parameters);
  return this.http.post(url, body,{'headers':headers})
}

public cancellaElezione(url: string, parameters: {}): any {
	  const headers = { 'content-type': 'application/json'}  
	  const body = JSON.stringify(parameters);
	  return this.http.post(url, body,{'headers':headers})
	}

public caricaFileCsv(url: string, parameters: {}): any {
	  return this.http.post(url, parameters).pipe(
	    catchError((error: HttpErrorResponse) => {
	      // restituisce un messaggio di errore che verra visualizzato nel subscribe
	      return throwError(error.error);
	    })
	  )
	}

public getExtraInfoMediatore(url: string, parameters: {}): any {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});   
} 
}
