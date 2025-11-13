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
} 