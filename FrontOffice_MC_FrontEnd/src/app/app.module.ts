import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './components/index/index.component';
import { HeaderComponent } from './principal-components/header/header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmMessageComponent } from './principal-components/confirm-message/confirm-message.component';
import { AggiornamentoSocietaComponent } from './modals/aggiornamento-societa/aggiornamento-societa.component';
import { SocietaUtenteComponent } from './societa-utente/societa-utente.component';
import { OrganismiDiMediazionePrincipaleComponent } from './organismi-di-mediazione/organismi-di-mediazione-principale/organismi-di-mediazione-principale.component';
import { MenuComponent } from './organismi-di-mediazione/menu/menu.component';
import { DomandaIscrizioneComponent } from './modulo-domanda/domanda-iscrizione/domanda-iscrizione.component';
import { AttoRiepilogativoMediazionePerEntiComponent } from './modulo-domanda/atto-riepilogativo-mediazione-per-enti/atto-riepilogativo-mediazione-per-enti.component';
import { TopPageComponent } from './principal-components/top-page/top-page.component';
import { MessageComponent } from './principal-components/message/message.component';
import { AccessoNegatoComponent } from './principal-components/accesso-negato/accesso-negato.component';
import { FooterComponent } from './principal-components/footer/footer.component';
import { SaveSedeComponent } from './modals/save-sede/save-sede.component';
import { SchedaRappLegaleComponent } from './scheda-rapp-legale/scheda-rapp-legale.component';
import { ElencoSociPrestatoriServizioComponent } from './elenco-soci-prestatori-servizio/elenco-soci-prestatori-servizio.component';
import { HomePageMediazioneComponent } from './homepage/home-page-mediazione/home-page-mediazione.component';
import { InserimentoSchedaPrestatoreServizioComponent } from './inserimento-scheda-prestatore-servizio/inserimento-scheda-prestatore-servizio.component';
import { AggiornamentoAnagraficaComponent } from './modals/aggiornamento-anagrafica/aggiornamento-anagrafica.component';
import { CaricamentoFileOdmComponent } from './modulo-domanda/caricamento-file-odm/caricamento-file-odm.component';
import { DichiarazionePolizzaAssicurativaComponent } from './polizza-assicurativa/dichiarazione-polizza-assicurativa/dichiarazione-polizza-assicurativa.component';
import { SediComponent } from './modulo-domanda/sedi/sedi.component';
import { InserimentoSedeComponent } from './inserimento-sede/inserimento-sede.component';
import { SpeseMediazioneComponent } from './modulo-domanda/spese-mediazione/spese-mediazione.component';
import { AutocertificazioneOnorabilitaComponent } from './appendice-d/autocertificazione-onorabilita/autocertificazione-onorabilita.component';
import { DocumentoIdentitaComponent } from './appendice-d/documento-identita/documento-identita.component';
import { RappresentanteLegaleComponent } from './appendice-d/rappresentante-legale/rappresentante-legale.component';
import { SaveFileComponent } from './modals/save-file/save-file.component';
import { ElencoRappresentantiComponent } from './appendice-d/elenco-rappresentanti/elenco-rappresentanti.component';
import { ElencoMediatoriComponent } from './modulo-domanda/elenco-mediatori/elenco-mediatori.component';
import { ModalMediatoriComponent } from './modals/modal-mediatori/modal-mediatori.component';
import { RegistrazioneUtenteComponent } from './iamg/registrazione-utente/registrazione-utente.component';
import { LoadPageComponent } from './principal-components/load-page/load-page.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { ApiUrlInterceptor } from './interceptor/api-url.interceptor';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { ExtraInfoComponent } from './modals/extra-info/extra-info.component';
import { ElencoComponentiOrganoAmmComponent } from './appendice-d/elenco-componenti-organo-amm/elenco-componenti-organo-amm.component';
import { AlboMediatoriComponent } from './homepage/albo-mediatori/albo-mediatori.component';
import { RegistroOdmComponent } from './homepage/registro-odm/registro-odm.component';
import { AlboFormatoriComponent } from './homepage/albo-formatori/albo-formatori.component';
import { AlboEntiFormazioneComponent } from './homepage/albo-enti-formazione/albo-enti-formazione.component';
import { RegistroElencoSediComponent } from './homepage/registro-elenco-sedi/registro-elenco-sedi.component';
import { RegistroElencoPdgComponent } from './homepage/registro-elenco-pdg/registro-elenco-pdg.component';
import { TabellaAllegatoAComponent } from './modals/tabella-allegato-a/tabella-allegato-a.component';
import { SchedaAnagraficaRappresentantiComponent } from './modals/scheda-anagrafica-rappresentanti/scheda-anagrafica-rappresentanti.component';
import { DettaglioFormatoreMediatoreComponent } from './homepage/dettaglio-formatore-mediatore/dettaglio-formatore-mediatore.component';
import { GestioneMediatoriAComponent } from './appendice-a/gestione-mediatori-a/gestione-mediatori-a.component';
import { GestioneMediatoriBComponent } from './appendice-b/gestione-mediatori-b/gestione-mediatori-b.component';
import { GestioneMediatoriCComponent } from './appendice-c/gestione-mediatori-c/gestione-mediatori-c.component';
import { SchedaAnagraficaMediatoreComponent } from './misc-appendici/scheda-anagrafica-mediatore/scheda-anagrafica-mediatore.component';
import { SchedaAnagraficaRappresentanteRiepilogoComponent } from './modals/scheda-anagrafica-rappresentante-riepilogo/scheda-anagrafica-rappresentante-riepilogo.component';
import { CaricamentoFileAppendiciComponent } from './misc-appendici/caricamento-file-appendici/caricamento-file-appendici.component';
import { OnorabilitaAppendiciComponent } from './misc-appendici/onorabilita-appendici/onorabilita-appendici.component';
import { SchedaCompOrgAmAndCompSocComponent } from './appendice-d/scheda-comp-org-am-and-comp-soc/scheda-comp-org-am-and-comp-soc.component';
import { SchedaAnagraficaCompOrgAmComponent } from './modals/scheda-anagrafica-comp-org-am/scheda-anagrafica-comp-org-am.component';
import { SchedaAnagraficaCompOrgAmRiepilogoComponent } from './modals/scheda-anagrafica-comp-org-am-riepilogo/scheda-anagrafica-comp-org-am-riepilogo.component';
import { AutocertificazioneOnorabilitaCompOrgAmComponent } from './appendice-d/autocertificazione-onorabilita-comp-org-am/autocertificazione-onorabilita-comp-org-am.component';
import { PolizzaAssicurativaComponent } from './polizza-assicurativa/polizza-assicurativa/polizza-assicurativa.component';
import { UpdateCertificazioneComponent } from './modals/update-certificazione/update-certificazione.component';
import { DettaglioSedeComponent } from './modals/dettaglio-sede/dettaglio-sede.component';
import { UpdateMediatoreComponent } from './modals/update-mediatore/update-mediatore.component';
import { NotificationMessageComponent } from './principal-components/notification-message/notification-message.component';
import { SchedaPrestatoreServizioComponent } from './modals/scheda-prestatore-servizio/scheda-prestatore-servizio.component';
import { RiepilogoSchedaPrestatoreServizioComponent } from './modals/riepilogo-scheda-prestatore-servizio/riepilogo-scheda-prestatore-servizio.component';
import { RichiesteInviateComponent } from './richieste-inviate/richieste-inviate.component';
import { FinalizzaRichiestaComponent } from './modals/finalizza-richiesta/finalizza-richiesta.component';
import { LetturaPdgComponent } from './lettura-pdg/lettura-pdg.component';
import { PaginaInfoComponent } from './pagina-info/pagina-info.component';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    HeaderComponent,
    ConfirmMessageComponent,
    SocietaUtenteComponent,
    AggiornamentoSocietaComponent,
    OrganismiDiMediazionePrincipaleComponent,
    MenuComponent,
    DomandaIscrizioneComponent,
    AttoRiepilogativoMediazionePerEntiComponent,
    TopPageComponent,
    MessageComponent,
    AccessoNegatoComponent,
    FooterComponent,
    SaveSedeComponent,
    SchedaRappLegaleComponent,
    ElencoSociPrestatoriServizioComponent,
    DichiarazionePolizzaAssicurativaComponent,
    SediComponent,
    InserimentoSedeComponent,
    HomePageMediazioneComponent,
    InserimentoSchedaPrestatoreServizioComponent,
    AggiornamentoAnagraficaComponent,
    CaricamentoFileOdmComponent,
    SpeseMediazioneComponent,
    AutocertificazioneOnorabilitaComponent,
    DocumentoIdentitaComponent,
    RappresentanteLegaleComponent,
    SaveFileComponent,
    ElencoRappresentantiComponent,
    ElencoMediatoriComponent,
    ModalMediatoriComponent,
    RegistrazioneUtenteComponent,
    LoadPageComponent,
    UscitaComponent,
    AccessoComponent,
    ExtraInfoComponent,
    ElencoComponentiOrganoAmmComponent,
    AlboMediatoriComponent,
    RegistroOdmComponent,
    AlboFormatoriComponent,
    AlboEntiFormazioneComponent,
    RegistroElencoSediComponent,
    RegistroElencoPdgComponent,
    TabellaAllegatoAComponent,
    SchedaAnagraficaRappresentantiComponent,
    DettaglioFormatoreMediatoreComponent,
    SchedaAnagraficaRappresentanteRiepilogoComponent,
    GestioneMediatoriAComponent,
    GestioneMediatoriBComponent,
    GestioneMediatoriCComponent,
    SchedaAnagraficaMediatoreComponent,
    CaricamentoFileAppendiciComponent,
    OnorabilitaAppendiciComponent,
    SchedaCompOrgAmAndCompSocComponent,
    SchedaAnagraficaCompOrgAmComponent,
    SchedaAnagraficaCompOrgAmRiepilogoComponent,
    AutocertificazioneOnorabilitaCompOrgAmComponent,
    PolizzaAssicurativaComponent,
    UpdateCertificazioneComponent,
    DettaglioSedeComponent,
    UpdateMediatoreComponent,
    NotificationMessageComponent,
    SchedaPrestatoreServizioComponent,
    RiepilogoSchedaPrestatoreServizioComponent,
    RichiesteInviateComponent,
    FinalizzaRichiestaComponent,
    LetturaPdgComponent,
    PaginaInfoComponent,
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
  bootstrap: [AppComponent],
})
export class AppModule {}
