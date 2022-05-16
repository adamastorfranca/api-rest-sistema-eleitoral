import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CargoCadastrarComponent } from './cargos/cadastrar/cadastrar.component';
import { EleitorCadastrarComponent } from './eleitores/cadastrar/cadastrar.component';

import { HomeComponent } from './home.component';


const routes: Routes = [
  {
    path:'', component: HomeComponent,
    children: [
      { path: 'cargo-cadastro', component: CargoCadastrarComponent },
      { path: 'eleitor-cadastro', component: EleitorCadastrarComponent }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
