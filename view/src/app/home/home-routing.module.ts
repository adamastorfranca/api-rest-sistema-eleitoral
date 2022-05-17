import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { CargoCadastrarComponent } from './cargos/cadastrar/cadastrar.component';
import { ExibirTodosCargosComponent } from './cargos/exibir-todos/exibir-todos.component';
import { EleitorCadastrarComponent } from './eleitores/cadastrar/cadastrar.component';
import { ExibirTodosEleitoresComponent } from './eleitores/exibir-todos/exibir-todos.component';

const routes: Routes = [
  {
    path:'', component: HomeComponent,
    children: [
      { path: 'cargos', component: ExibirTodosCargosComponent },
      { path: 'cargo-cadastro', component: CargoCadastrarComponent },

      { path: 'eleitores', component: ExibirTodosEleitoresComponent },
      { path: 'eleitor-cadastro', component: EleitorCadastrarComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
