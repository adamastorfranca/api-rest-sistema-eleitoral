import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastrarComponent } from './cargos/cadastrar/cadastrar.component';

import { HomeComponent } from './home.component';


const routes: Routes = [
  {
    path:'', component: HomeComponent,
    children: [
      { path: 'cargo-cadastro', component: CadastrarComponent },

    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
