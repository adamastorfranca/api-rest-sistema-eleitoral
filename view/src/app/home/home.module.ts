import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CargoCadastrarComponent } from './cargos/cadastrar/cadastrar.component';
import { HomeRoutingModule } from './home-routing.module';
import { EleitorCadastrarComponent } from './eleitores/cadastrar/cadastrar.component';

@NgModule({
  declarations: [
    CargoCadastrarComponent,
    EleitorCadastrarComponent
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
