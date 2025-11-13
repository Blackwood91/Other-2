import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InserimentoMediatoreComponent } from './inserimento-mediatore/inserimento-mediatore.component';
import { AggiornamentoMediatoreComponent } from './aggiornamento-mediatore/aggiornamento-mediatore.component';
import { MediatoriInternoComponent } from './mediatori-interno/mediatori-interno.component';
import { UtentiComponent } from './utenti/utenti.component';
import { InserisciUtentiComponent } from './inserisci-utenti/inserisci-utenti.component';
import { AggiornaUtentiComponent } from './aggiorna-utenti/aggiorna-utenti.component';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { HomeComponent } from './home/home.component';
import { LogOutComponent } from './log-out/log-out.component';


const routes: Routes = [
  {path: '', component: AccessoComponent},
  {path: 'login', component: AccessoComponent},
  {path: 'logout', component: UscitaComponent },

  { path: 'home', component: HomeComponent},
  { path: 'mediatori-interno', component: MediatoriInternoComponent},
  { path: 'inserimentoMediatore', component: InserimentoMediatoreComponent},
  { path: 'aggiornamentoMediatore', component: AggiornamentoMediatoreComponent},
  { path: 'utenti', component: UtentiComponent},
  { path: 'inserisciUtenti', component: InserisciUtentiComponent},
  { path: 'aggiornaUtenti', component: AggiornaUtentiComponent},
  { path: 'logOut', component: LogOutComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }