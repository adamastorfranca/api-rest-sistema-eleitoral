import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { CargoCadastrarEditarComponent } from './cargos/cadastrar-editar/cadastrar-editar.component';
import { CargoExibirDeletarComponent } from './cargos/exibir-deletar/exibir-deletar.component';
import { EleitorCadastrarEitarComponent } from './eleitores/cadastrar-editar/cadastrar-editar.component';
import { EleitorExibirDeletarComponent } from './eleitores/exibir-deletar/exibir-deletar.component';

const routes: Routes = [
  {
    path:'', component: HomeComponent,
    children: [
      { path: 'cargos', component: CargoExibirDeletarComponent },
      { path: 'cargos/cadastro', component: CargoCadastrarEditarComponent },
      { path: 'cargos/editar/:id', component: CargoCadastrarEditarComponent },

      { path: 'eleitores', component: EleitorExibirDeletarComponent },
      { path: 'eleitores/cadastro', component: EleitorCadastrarEitarComponent },
      { path: 'eleitores/editar/:id', component: EleitorCadastrarEitarComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
