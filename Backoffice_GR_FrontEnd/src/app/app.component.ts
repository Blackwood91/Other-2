import { Component, ViewChild } from '@angular/core';
import '../style/bootstrap-italia/js/bootstrap-italia.bundle.min.js'
import { MessageComponent } from '../app/principal-components/message/message.component'
import { SharedService } from './shared.service';
import { Subscription } from 'rxjs';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css' ]
})
export class AppComponent {

  mostraFooter: boolean = true;

  title = 'dashboard_voto_estero';
  private subscription!: Subscription;


  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(LoadPageComponent) loadPage!: LoadPageComponent;

  ngAfterViewInit(): void {}

  constructor(private sharedService: SharedService, private router: Router){
    this.subscription = this.sharedService.observableMessage$.subscribe(data => { this.message.onMessage(data.type, data.description); });
    // Attivazione caricamento pagina
    this.sharedService.observableLoad$.subscribe(data => { this.loadPage.onLoad(); });
    this.sharedService.observableLoadService$.subscribe(data => { this.loadPage.onLoadService(); });
    // Disattivazione caricamento pagina
    this.sharedService.observableOffLoad$.subscribe(data => { this.loadPage.offLoad(); });
  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Controllo sulla base dell'URL se il footer deve essere visualizzato o nascosto
        this.mostraFooter = !event.url.includes('/logOut');
      }
    });
  }
}