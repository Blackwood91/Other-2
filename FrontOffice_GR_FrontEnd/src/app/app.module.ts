import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './principal-components/header/header.component';
import { MediatoriComponent } from './mediatori/mediatori.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmMessageComponent } from './principal-components/confirm-message/confirm-message.component';
import { TopPageComponent } from './principal-components/top-page/top-page.component';
import { MessageComponent } from './principal-components/message/message.component';
import { FooterComponent } from './principal-components/footer/footer.component';
import { MediatoriInternoComponent } from './mediatori-interno/mediatori-interno.component';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
import { ApiUrlInterceptor } from './interceptor/api-url.interceptor';
import { ModalMediatoriComponent } from './modal-mediatori/modal-mediatori.component';

 

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MediatoriComponent,
    ConfirmMessageComponent,
    TopPageComponent,
    MessageComponent,
    FooterComponent,
    MediatoriInternoComponent,
    AccessoComponent,
    UscitaComponent,
    LoadPageComponent,
    ModalMediatoriComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    // Necessario per la gestione di tutte le richieste e per la gestione del token
    { provide: HTTP_INTERCEPTORS, useClass: ApiUrlInterceptor, multi: true }, 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }