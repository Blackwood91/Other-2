import { Component, ViewChild } from '@angular/core';
import '../style/bootstrap-italia/js/bootstrap-italia.bundle.min.js'
import { MessageComponent } from '../app/principal-components/message/message.component'
import { SharedService } from './shared.service';
import { Subscription } from 'rxjs';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
 
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css' ]
})
export class AppComponent {
  title = 'dashboard_voto_estero';
  private subscription!: Subscription;

  ngOnInit() {}

  @ViewChild(MessageComponent) message!: MessageComponent;
  @ViewChild(LoadPageComponent) loadPage!: LoadPageComponent;

  ngAfterViewInit(): void {}

  constructor(private sharedService: SharedService){
    this.subscription = this.sharedService.observableMessage$.subscribe(data => { this.message.onMessage(data.type, data.description); });
    // Attivazione caricamento pagina
    this.sharedService.observableLoad$.subscribe(data => { this.loadPage.onLoad(); });
    this.sharedService.observableLoadService$.subscribe(data => { this.loadPage.onLoadService(); });
    // Disattivazione caricamento pagina
    this.sharedService.observableOffLoad$.subscribe(data => { this.loadPage.offLoad(); });
  }
}