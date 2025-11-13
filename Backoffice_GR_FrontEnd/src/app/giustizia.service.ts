import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class GiustiziaService {

  constructor(private http: HttpClient){}


public getElencoPubblico(url: string, parameters: {}): Observable<any> {
    // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
   // console.log(parameters)
      
}

public getUltimaModificaMediatore(url: string, parameters: {}): Observable<any> {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});
    
}

public getUltimaModificaUtente(url: string, parameters: {}): Observable<any> {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});
    
}

public getUltimoDisabilitaUtente(url: string, parameters: {}): Observable<any> {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});
    
}


public getAllUtenti(url: string, parameters: {}): Observable<any> {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});
    
}

public getAllutentiPost(url: string, parameters: {}): Observable<any> {
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


public UpdateMediatoriPost(url: string, parameters: {}): Observable<any> {
  return this.http.post(url, parameters).pipe(
    catchError((error: HttpErrorResponse) => {
      // Puoi accedere a tutte le informazioni dettagliate sull'errore qui
      console.error('Codice di stato HTTP:', error.status);
      console.error('Messaggio di errore:', error.message);
      console.error("Testo dell errore completo:", error.error); // Questo contiene i dettagli aggiuntivi
      console.error('Intestazioni HTTP:', error.headers);
      // restituisce un messaggio di errore che verra visualizzato nel subscribe
      return throwError(error.message);
    })
  )
}

public insertMediatoriPost(url: string, parameters: {}): Observable<any> {
  return this.http.post(url, parameters).pipe(
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

public UpdateUtenti(url: string, parameters: {}): Observable<any> {
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
      return throwError(error.message);
    })
  )
}

public getAllSeggi(url: string, parameters: {}): Observable<any> {
    // Esegue una chiamata HTTP per recuperare un oggetto
      return this.http.get(url, {params: parameters});
}

public postUpdateUtenze(url: string, parameters: {}): Observable<any> {
  const headers = { 'content-type': 'application/json'}  
  const body = JSON.stringify(parameters);
  return this.http.post(url, body,{'headers':headers})
}

public deleteUtente(url: string, parameters: {}): Observable<any> {
	  const headers = { 'content-type': 'application/json'}  
	  const body = JSON.stringify(parameters);
	  return this.http.post(url, body,{'headers':headers})
	}

public caricaFilePDF(url: string, parameters: {}): Observable<any> {
	  return this.http.post(url, parameters).pipe(
	    catchError((error: HttpErrorResponse) => {
	      // restituisce un messaggio di errore che verra visualizzato nel subscribe
	      return throwError(error.error);
	    })
	  )
	}

public saveMediatoriFileCsv(url: string, parameters: {}): Observable<any> {
  return this.http.post(url, parameters).pipe(
    catchError((error: HttpErrorResponse) => {
      return throwError(error.error);
    })
  )
} 

public getEnte(url: string, parameters: {}): Observable<any> {
    // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
   // console.log(parameters)
      
}

public verificaCodiceFiscale(url: string, parameters: {}): Observable<any> {
    // Esegue una chiamata HTTP per recuperare un oggetto
    return this.http.get(url, {params: parameters});
   // console.log(parameters)   
}

public verificaCodiceFiscaleUtenti(url: string, parameters: {}): Observable<any> {
  // Esegue una chiamata HTTP per recuperare un oggetto
  return this.http.get(url, {params: parameters});
 // console.log(parameters)   
}
}
