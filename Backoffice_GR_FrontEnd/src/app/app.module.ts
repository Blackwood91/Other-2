import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './principal-components/header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InserimentoMediatoreComponent } from './inserimento-mediatore/inserimento-mediatore.component';
import { ConfirmMessageComponent } from './principal-components/confirm-message/confirm-message.component';
import { AggiornamentoMediatoreComponent } from './aggiornamento-mediatore/aggiornamento-mediatore.component';
import { TopPageComponent } from './principal-components/top-page/top-page.component';
import { MessageComponent } from './principal-components/message/message.component';
import { FooterComponent } from './principal-components/footer/footer.component';
import { MediatoriInternoComponent } from './mediatori-interno/mediatori-interno.component';
import { UtentiComponent } from './utenti/utenti.component';
import { InserisciUtentiComponent } from './inserisci-utenti/inserisci-utenti.component';
import { AggiornaUtentiComponent } from './aggiorna-utenti/aggiorna-utenti.component';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
import { ApiUrlInterceptor } from './interceptor/api-url.interceptor';
import { HomeComponent } from './home/home.component';
import { LogOutComponent } from './log-out/log-out.component';
 

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    InserimentoMediatoreComponent,
    ConfirmMessageComponent,
    AggiornamentoMediatoreComponent,
    TopPageComponent,
    MessageComponent,
    FooterComponent,
    MediatoriInternoComponent,
    UtentiComponent,
    InserisciUtentiComponent,
    AggiornaUtentiComponent,
    AccessoComponent,
    UscitaComponent,
    LoadPageComponent,
    HomeComponent,
    LogOutComponent,
  ], 
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientXsrfModule, 
  ],
  providers: [
    // Necessario per la gestione di tutte le richieste e per la gestione del token
    { provide: HTTP_INTERCEPTORS, useClass: ApiUrlInterceptor, multi: true }, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }