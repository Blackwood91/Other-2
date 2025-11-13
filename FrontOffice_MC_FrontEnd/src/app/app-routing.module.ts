import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SocietaUtenteComponent } from './societa-utente/societa-utente.component';
import { OrganismiDiMediazionePrincipaleComponent } from './organismi-di-mediazione/organismi-di-mediazione-principale/organismi-di-mediazione-principale.component';
import { AccessoNegatoComponent } from './principal-components/accesso-negato/accesso-negato.component';
import { DomandaIscrizioneComponent } from './modulo-domanda/domanda-iscrizione/domanda-iscrizione.component';
import { AttoRiepilogativoMediazionePerEntiComponent } from './modulo-domanda/atto-riepilogativo-mediazione-per-enti/atto-riepilogativo-mediazione-per-enti.component';
import { HomePageMediazioneComponent } from './homepage/home-page-mediazione/home-page-mediazione.component';
import { ElencoSociPrestatoriServizioComponent } from './elenco-soci-prestatori-servizio/elenco-soci-prestatori-servizio.component';
import { CaricamentoFileOdmComponent } from './modulo-domanda/caricamento-file-odm/caricamento-file-odm.component';
import { SediComponent } from './modulo-domanda/sedi/sedi.component';
import { InserimentoSchedaPrestatoreServizioComponent } from './inserimento-scheda-prestatore-servizio/inserimento-scheda-prestatore-servizio.component';
import { AlboMediatoriComponent } from './homepage/albo-mediatori/albo-mediatori.component';
import { RegistroOdmComponent } from './homepage/registro-odm/registro-odm.component';
import { AlboFormatoriComponent } from './homepage/albo-formatori/albo-formatori.component';
import { AlboEntiFormazioneComponent } from './homepage/albo-enti-formazione/albo-enti-formazione.component';
import { RegistrazioneUtenteComponent } from './iamg/registrazione-utente/registrazione-utente.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { RegistroElencoSediComponent } from './homepage/registro-elenco-sedi/registro-elenco-sedi.component';
import { RegistroElencoPdgComponent } from './homepage/registro-elenco-pdg/registro-elenco-pdg.component';
import { RichiesteInviateComponent } from './richieste-inviate/richieste-inviate.component';
import { PaginaInfoComponent } from './pagina-info/pagina-info.component';


const routes: Routes = [
  { path: '', component: AccessoComponent},
  { path: 'login', component: AccessoComponent},
  { path: 'logout', component: UscitaComponent },

  { path: 'homePage', component: HomePageMediazioneComponent },
  { path: 'societa', component: SocietaUtenteComponent },
  { path: 'domandaIscrizione', component: DomandaIscrizioneComponent },
  { path: 'organismiDiMediazione', component: OrganismiDiMediazionePrincipaleComponent },
  { path: 'accessoNegato', component: AccessoNegatoComponent },
  { path: 'attoRiepilogativo', component: AttoRiepilogativoMediazionePerEntiComponent },
  { path: 'sedi', component: SediComponent },
  { path: 'elencoSociPrestatori', component: ElencoSociPrestatoriServizioComponent },
  { path: 'caricamentoFileOdmComponent', component: CaricamentoFileOdmComponent },
  { path: 'inserimentoSchedaPrestatori', component: InserimentoSchedaPrestatoreServizioComponent},
  { path: 'registrazioneUtente', component: RegistrazioneUtenteComponent},
  { path: 'alboMediatori', component: AlboMediatoriComponent},
  { path: 'registroOdm', component: RegistroOdmComponent},
  { path: 'alboFormatori', component: AlboFormatoriComponent},
  { path: 'alboEntiFormazione', component: AlboEntiFormazioneComponent},
  { path: 'registroElencoSedi', component: RegistroElencoSediComponent},
  { path: 'registroElencoPdg', component: RegistroElencoPdgComponent},
  { path: 'richiesteInviate', component: RichiesteInviateComponent},
  { path: 'paginaInfo', component: PaginaInfoComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
