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

  public getAllAnagrafica(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriMedGen(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriMedInter(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllAnagraficaMediatoriMatCons(url: string, parameters: {}): Observable<any> {
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

  public getAllRichiestaInviata(url: string, parameters: {}): Observable<any> {
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

  public getStatusElenRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
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

  public getFileAttoCostOdm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
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

  public getAnteprimaFileAutocertificazioneRapLRespO(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileAutocertificazioneReqOnoCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileSchedeRapLegRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFilePolizzaAss(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileElencoMediatori(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFileSchedeMediatoriCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAnteprimaFilePrestaSerOpe(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}`).pipe(
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

  public getFileSpeseMed(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, parameters);
  }

  public getFileDomandaIscrizione(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`);
  }

  public getCheckConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFilePlanimetria(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}`).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileCopiaContratto(url: string, idRichiesta: number, idSede: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}&idSede=${idSede}`).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getAnteprimaFileAttoRiepOdm(url: string, idRichiesta: number): Observable<any> {
    return this.http.get(url + `?idRichiesta=${idRichiesta}`).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileRappresentante(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public rapLegaleIsCompletato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public getFileCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters }).pipe(
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

  public existSedeLegale(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public existModulo(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public statusJobRichiesta(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public isConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getModuloIsConvalidato(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getModuloIsConvalidatoAdPersonam(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public isConvalidatoAllModuli(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusSedi(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getStatusElencoRapLRespOrg(url: string, parameters: {}): Observable<any> {
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

  public getAllRichieste(url: string, parameters: {}): Observable<any> {
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

  public insertSocieta(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
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

  public insertSede(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public inviaRichiestaODM(url: string, parameters: {}): Observable<any> {
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

  public deleteCertificazione(url: string, parameters: {}): Observable<any> {
    return this.http.delete(url, parameters).pipe(
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

  public convalidazioneDomandaIscrizione(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneSedi(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneDisponibilitaA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneAttoCostNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneStatutoOrgNonAutonomo(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneAttoRiepilogativoODM(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneRquisitiOnorabilita(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneRequisitiOnorabilitaCompOrgAm(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneElencoRapLegAndRespOrg(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneElencoCompOrgAmAndCompSoc(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneElencoMediatore(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazionePolizzaAssicurativa(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazionePrestatoreServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public convalidazioneElencoPrestServizio(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileDisponibilitaA(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileDisponibilitaB(url: string, parameters: {}): Observable<any> {
    return this.http.post(url, parameters).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error.error);
      })
    )
  }

  public saveFileDisponibilitaC(url: string, parameters: {}): Observable<any> {
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

  public convalidaFinalizza(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllEmissionePdgOdmForTable(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getFilePdg(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public getAllTipologiaPdg(url: string): any {
    return this.http.get(url);
  }

  public getElencoAutocerficatiRequisitiOnorabilitApp(url: string, parameters: {}): Observable<any> {
    return this.http.get(url, { params: parameters });
  }

  public deleteCertificazioneB(url: string, parameters: {}): Observable<any> {
    return this.http.delete(url, { params: parameters });
  }

  public deleteCertificazioneC(url: string, parameters: {}): Observable<any> {
    return this.http.delete(url, { params: parameters });
  }

}
