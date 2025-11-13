import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  constructor() { }

  private observableSourceMessage = new Subject<{ type: string, description: string }>();
  observableMessage$: Observable<{ type: string, description: string }> = this.observableSourceMessage.asObservable();
  onMessage(type: string, description: string) {
    this.observableSourceMessage.next({ type, description });
  }

  private observableSourceSelezionaSocieta = new Subject<{ nomeSocieta: string }>();
  observableSelezionaSocieta$: Observable<{ nomeSocieta: string }> = this.observableSourceSelezionaSocieta.asObservable();
  onChangeNomeSocieta(nomeSocieta: string) {
    this.observableSourceSelezionaSocieta.next({ nomeSocieta });
  }

  private observableSourceChangeViewMenuODM = new Subject<{ nomeComponent: string }>();
  observableSourceChangeViewMenuODM$: Observable<{ nomeComponent: string }> = this.observableSourceChangeViewMenuODM.asObservable();
  onChangeViewMenuODM(nomeComponent: string) {
    this.observableSourceChangeViewMenuODM.next({ nomeComponent });
  }

  private observableSourceUpdateMenuODM = new Subject<{}>();
  observableSourceUpdateMenuODM$: Observable<{}> = this.observableSourceUpdateMenuODM.asObservable();
  onUpdateMenu() {
    this.observableSourceUpdateMenuODM.next({});
  }

  // Funzioni component caricamento 
  private observableSourceLoad = new Subject<{}>();
  observableLoad$: Observable<{}> = this.observableSourceLoad.asObservable();
  onLoad() {
    this.observableSourceLoad.next({});
  }

  private observableSourceLoadService = new Subject<{}>();
  observableLoadService$: Observable<{}> = this.observableSourceLoadService.asObservable();
  onLoadService() {
    this.observableSourceLoadService.next({});
  }

  private observableSourceOffLoad = new Subject<{}>();
  observableOffLoad$: Observable<{}> = this.observableSourceOffLoad.asObservable();
  offLoad() {
    this.observableSourceOffLoad.next({});
  }

  private observableSourceNameSocietaHeader = new Subject<{ nomeSocieta: string }>();
  observableNameSocietaHeader$: Observable<{nomeSocieta: string}> = this.observableSourceNameSocietaHeader.asObservable();
  onNameSocietaHeader(nomeSocieta: string) {    
    this.observableSourceNameSocietaHeader.next({ nomeSocieta });
  }

  private observableSourceOnNotification = new Subject<{ descriptionMessage: string }>();
  observableOnNotification$: Observable<{descriptionMessage: string}> = this.observableSourceOnNotification.asObservable();
  onNotification(descriptionMessage: string) {    
    this.observableSourceOnNotification.next({ descriptionMessage });
  }

}