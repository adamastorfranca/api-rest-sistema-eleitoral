import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CargoCadastrarComponent } from './cargos/cadastrar/cadastrar.component';
import { HomeRoutingModule } from './home-routing.module';
import { EleitorCadastrarComponent } from './eleitores/cadastrar/cadastrar.component';
import { ExibirTodosCargosComponent } from './cargos/exibir-todos/exibir-todos.component';
import { ExibirTodosEleitoresComponent } from './eleitores/exibir-todos/exibir-todos.component';

@NgModule({
  declarations: [
    CargoCadastrarComponent,
    EleitorCadastrarComponent,
    ExibirTodosCargosComponent,
    ExibirTodosEleitoresComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HomeRoutingModule
  ]
})
export class HomeModule { }
