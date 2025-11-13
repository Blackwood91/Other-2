import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MediazioneService {
  constructor(private http: HttpClient) { }

  public getAllSocietaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllTipoRichiedente(url: string): any {
    return this.http.get(url);
  }

  public getAllTipologiaPdg(url: string): any {
    return this.http.get(url);
  }
  
  public getAllAnagrafica(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEmissionePdgOdmForTable(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriA(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriB(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriC(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaByIdRichiesta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAttoCostNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllStatutoOrgNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaByIdRichiestaForEleCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaPrestatori(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getRichiestaDomIscr(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getNomeSocieta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getRichiestaForSocieta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllRegioni(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllProvince(url: string, codiceRegione: number): Observable<any> {
    return this.http.get(url + `?codiceRegione=${codiceRegione}`);
  }

  public getAllNaturaSocietaria(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllSoggettoRichiedente(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllComune(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllComuni(url: string, nomeComune: string): Observable<any> {
    return this.http.get(url + `?nomeComune=${nomeComune}`);
  }

  public getComune(url: string, idComune: number): Observable<any> {
    return this.http.get(url + `?idComune=${idComune}`);
  }

  public getAllTitoliAnagrafiche(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllNaturaGiuridica(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllQualifiche(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllQualificaCompOrgAmAndCompSoc(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllOrdiniCollegi(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllSediOperativeByRichiesta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaByIdRichiestaForRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnagraficaCloneRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllRappresentantiAutocertificazioni(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllSelectAutocertReqOnoForCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllTitoloDetenzione(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getAllModalitaCostituzioneOrganismo(url: string): Observable<any> {
    return this.http.get(url);
  }

  public getStatusOnorabilita(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusRquisitiOnorabilita(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusPolizza(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusRapLegOrRespOrOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoMediatori(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAttoCostOdm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaVar(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllFormatoriPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllModuloAnnulato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getRichiestaIntegrazione(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllMediatoriPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEntiFormatoriPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllOdmPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllOdmSediPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllOdmPdgPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEfSediPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEfPdgPerAlbo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEfPdgPerAlboByNumReg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEfSediPerAlboByNumReg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllOdmPdgPerAlboByRom(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllOdmSediPerAlboByRom(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFormatorePerAlboById(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getMediatorePerAlboById(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllStatutoOrg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllRegProcedura(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllCodiceEtico(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllBilancio(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllSpeseMed(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getSedeOperativa(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getSocietaById(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFileAttoCostOdm(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getAnteprimaFileAppeA(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileAppeB(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileAppeC(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileAutocertificazioneReqOno(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFilePolizzaAss(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileElencoMediatori(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }
  
  public getAnteprimaFilePrestaSerOpe(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}` ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileStatutoOrg(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getFileRegProcedura(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getFileCodiceEtico(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getFileBilancio(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getFileSpeseMed(url: string, id: number): Observable<any> {
    return this.http.get(url + `?id=${id}`);
  }

  public getFileDomandaIscrizione(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getCheckConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFilePlanimetria(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}` ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileCopiaContratto(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}` ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getAnteprimaFileAttoRiepOdm(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}` ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileRappresentante(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters } ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters } ).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getSezionePrimaDOMODMP(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getSezioneSecondaDOMODMP(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getSezioneQuartaDOMODMP(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getPolizzaAssicurativaDOMODMP(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getStatusAttoRODMI(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnagraficaById(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getSedeById(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public activeInviaRic(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public existRichiestaIntegrazione(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public existSedeLegale(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getModuloIsConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusSedi(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoRapLRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoMedGen(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoMedInt(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoMedCons(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }
  
  public getStatusEleCompOrgAmm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusAttoRODM(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllModuloConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public richiestaNaturaIsAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusAppendiceA(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusAppendiceB(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusAppendiceC(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public downloadFileRI(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFilePdg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFileAnteprimaORM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileAnteprimaSpesa(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public updateStatusInValidazione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }


  public insertSocieta(url: string, parameters: {}): Observable<any> {
    const headers = { 'content-type': 'application/json' };
    const body = JSON.stringify(parameters);
    return this.http.post(url, body, { headers: headers });
  }

  public updateSocieta(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public insertRichiestaODM(url: string, parameters: {}): Observable<any> {
    const headers = { 'content-type': 'application/json' };
    const body = JSON.stringify(parameters);
    return this.http.post(url, body, { headers: headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public insertRichiestaEF(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public insertDomandaIscODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveSezionePrima(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveSezioneSeconda(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveSezioneQuarta(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public invioRichiestaIntegrazione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public insertSede(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public emissionePdgOdm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public updateSede(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public updateAnagrafica(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public updateAnagraficaViaRichiesta(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveAnagrafica(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveAnagraficaPrestatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }
  

  public saveRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveAnagraficaMedGen(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public savePolizzaAssicurativa(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public deleteSedeOperativa(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public deleteRappresentante(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public deleteCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public deleteAnagraficaPrestatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileAttoCostitutivoOdm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileStatutoOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileAttoCostitutivoOdmNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileStatutoOrgNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileRegProcedura(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileCodiceEtico(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileAttoCostNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileStatutoOrgNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileBilancio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileSpeseMed(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneDomandaIscrizione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneSedi(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneAttoCostNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneStatutoOrgNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneAttoRiepilogativoODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneRequisitiOnorabilitaCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneRquisitiOnorabilita(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneElencoRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneElencoMediatori(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneDichiarazionePolizzaAss(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaSediAttoRiepilogativo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }
  
  public validaSedeAttoRiepilogativo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazionePolizza(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneBilancio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneCodiceEtico(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneRegolamentoProcedura(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneStatutoOrganismoNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneAttoCostODMNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneStatutoOrganismo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneAttoCostODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneSpeseMediazione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneElencoCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneElencoMediatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazionePolizzaAssicurativa(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazionePrestatoreServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneRequisitiOnorabilitaAppendici(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneDisponibilitaA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaFormazioneSpecificaB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaFormazioneSpecificaC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneUlterioriRequisitiA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneUlterioriRequisitiB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneUlterioriRequisitiC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneFormazioneInizialeA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneFormazioneInizialeB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneFormazioneInizialeC (url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneElencoPrestServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneCertificazioneB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneCertificazioneC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validazioneMediatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaElencoMediatoreA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaElencoMediatoreB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public validaElencoMediatoreC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaDomandaIscrizione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaAttoCostODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaStatutoOrganismo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaAttoCostODMNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaStatutoOrganismoNA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaRegolamentoProcedura(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaCodiceEtico(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaBilancio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaAttoCostNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaStatutoOrgNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaAttoRiepilogativoODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaSediAttoRiepilogativo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaSedeAttoRiepilogativo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaPolizza(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaSpeseMediazione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaDichiarazionePolizzaAss(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }


  public annullaElencoCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoPrestServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaPrestatoreServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaRequisitiOnorabilitaAppendici(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaRequisitiOnorabilitaCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }


  public annullaDisponibilitaA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaDisponibilitaB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaDisponibilitaC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneUlteReqMediatoriA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneUlteReqMediatoriB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneUlteReqMediatoriC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaCertificaMediatoriB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaCertificaMediatoriC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneFormazioneInizialeA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneFormazioneInizialeB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullazioneFormazioneInizialeC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  
  public annullaFormazioneSpecificaB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  
  public annullaFormazioneSpecificaC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaMediatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoMediatori(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoMediatoreA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoMediatoreB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public annullaElencoMediatoreC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileFormazioneInizialeA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileFormazioneInizialeB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileFormazioneInizialeC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileFormazioneSpecificaB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileFormazioneSpecificaC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileCertificazioneB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileCertificazioneC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileUlterioriRequisitiA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileUlterioriRequisitiB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileUlterioriRequisitiC(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public approvaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.put(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public rifutaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.put(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public abilitaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.put(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public disabilitaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.put(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getAllRegistrazioniUtente(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getAllUtenti(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getAllUtentiRichiesta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public verificaUtente(url: string, parameters: {}): Observable<any> {
    return this.http.put(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public caricaFileCsv(url: string, parameters: {}): Observable<any> {
	  return this.http.post(url, parameters).pipe(
	    catchError((error: HttpErrorResponse) => {
	      return throwError(error.error);
	    })
	  )
	}

  public getElencoAutocerficatiRequisitiOnorabilitApp(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }
}
