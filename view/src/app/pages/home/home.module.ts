import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

import { HomeRoutingModule } from './home-routing.module';
import { CargoCadastrarEditarComponent } from './cargos/cadastrar-editar/cadastrar-editar.component';
import { CargoExibirDeletarComponent } from './cargos/exibir-deletar/exibir-deletar.component';
import { EleitorCadastrarEitarComponent } from './eleitores/cadastrar-editar/cadastrar-editar.component';
import { EleitorExibirDeletarComponent } from './eleitores/exibir-deletar/exibir-deletar.component';
import { FilterNomeEleitor } from './eleitores/exibir-deletar/filter-nome-eleitor.pipe';
import { FilterCpfEleitor } from './eleitores/exibir-deletar/filter-cpf-eleitor.pipe';
import { InicioComponent } from './inicio/inicio.component';
import { CandidatoExibirDeletarComponent } from './candidatos/exibir-deletar/exibir-deletar.component';
import { FilterNomeCandidato } from './candidatos/exibir-deletar/filter-nome-candidato.pipe';
import { FilterCpfCandidato } from './candidatos/exibir-deletar/filter-cpf-candidato.pipe';
import { CandidatoCadastrarEditarComponent } from './candidatos/cadastrar-editar/cadastrar-editar.component';
import { BuscarEleitorParaVotarComponent } from './eleitores/buscar-eleitor-para-votar/buscar-eleitor.component';
import { VotarComponent } from './eleitores/votar/votar.component';

@NgModule({
  declarations: [
    CargoCadastrarEditarComponent,
    CargoExibirDeletarComponent,
    EleitorCadastrarEitarComponent,
    EleitorExibirDeletarComponent,
    FilterNomeEleitor,
    FilterCpfEleitor,
    InicioComponent,
    CandidatoExibirDeletarComponent,
    FilterNomeCandidato,
    FilterCpfCandidato,
    CandidatoCadastrarEditarComponent,
    BuscarEleitorParaVotarComponent,
    VotarComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HomeRoutingModule,
    MatFormFieldModule,
    MatSelectModule
  ]
})
export class HomeModule { }
