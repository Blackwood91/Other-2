import { Component, ViewChild } from '@angular/core';
import '../style/bootstrap-italia/js/bootstrap-italia.bundle.min.js';
import { MessageComponent } from '../app/principal-components/message/message.component'
import { SharedService } from './shared.service';
import { Subscription } from 'rxjs';
import { HeaderComponent } from './principal-components/header/header.component';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
import { Router } from '@angular/router';
import { NotificationMessageComponent } from './principal-components/notification-message/notification-message.component';
import { NavigationEnd} from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  @ViewChild(HeaderComponent) header!: HeaderComponent;
  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(LoadPageComponent) loadPage!: LoadPageComponent;
  @ViewChild(NotificationMessageComponent) notificationMessageComponent!: NotificationMessageComponent;
  title = 'mediazione-civile';
  mostraFooter: boolean = true;

  ngOnInit() { 
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Controllo sulla base dell'URL se il footer deve essere visualizzato o nascosto
        this.mostraFooter = !event.url.includes('/logout');
        this.mostraFooter = !event.url.includes('/accessoNegato');

      }
    });
  }

  ngAfterViewInit(): void { }

  constructor(private sharedService: SharedService, private router: Router) {
    // Attivazione caricamento messaggio
    this.sharedService.observableMessage$.subscribe(data => { this.message.onMessage(data.type, data.description); });
    // Attivazione caricamento pagina
    this.sharedService.observableLoad$.subscribe(data => { this.loadPage.onLoad(); });
    this.sharedService.observableLoadService$.subscribe(data => { this.loadPage.onLoadService(); });
    // Disattivazione caricamento pagina
    this.sharedService.observableOffLoad$.subscribe(data => { this.loadPage.offLoad(); });
    // Attivazione cambiamento nome menu header
    this.sharedService.observableNameSocietaHeader$.subscribe(data => {
      this.header.onChangeNomeSocieta(data.nomeSocieta);
    });
    // Attivazione notifica del messaggio
    this.sharedService.observableOnNotification$.subscribe(data => { this.notificationMessageComponent.onNotification(data.descriptionMessage); });

  }




}

