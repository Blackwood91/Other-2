import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MediatoriComponent } from './mediatori/mediatori.component';
import { MediatoriInternoComponent } from './mediatori-interno/mediatori-interno.component';
import { UscitaComponent } from './iamg/uscita/uscita.component';
import { AccessoComponent } from './iamg/accesso/accesso.component';
import { ModalMediatoriComponent } from './modal-mediatori/modal-mediatori.component';


const routes: Routes = [
  { path: '', component: AccessoComponent },
  {path: 'login', component: AccessoComponent},
  {path: 'logout', component: UscitaComponent },

  { path: 'mediatori', component: MediatoriComponent }, //ELENCO PUBBLICO
  { path: 'mediatori-interno', component: MediatoriInternoComponent }, //ELENCO PUBBLICO
  { path: 'modalMediatori', component: ModalMediatoriComponent }, //ELENCO PUBBLICO

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }