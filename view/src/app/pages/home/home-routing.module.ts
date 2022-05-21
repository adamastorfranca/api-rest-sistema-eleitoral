import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { InicioComponent } from './inicio/inicio.component';
import { CargoCadastrarEditarComponent } from './cargos/cadastrar-editar/cadastrar-editar.component';
import { CargoExibirDeletarComponent } from './cargos/exibir-deletar/exibir-deletar.component';
import { EleitorCadastrarEitarComponent } from './eleitores/cadastrar-editar/cadastrar-editar.component';
import { EleitorExibirDeletarComponent } from './eleitores/exibir-deletar/exibir-deletar.component';
import { CandidatoExibirDeletarComponent } from './candidatos/exibir-deletar/exibir-deletar.component';
import { CandidatoCadastrarEditarComponent } from './candidatos/cadastrar-editar/cadastrar-editar.component';
import { BuscarEleitorParaVotarComponent } from './eleitores/buscar-eleitor-para-votar/buscar-eleitor.component';
import { VotarComponent } from './eleitores/votar/votar.component';
import { RelatorioGeralComponent } from './candidatos/relatorio/relatorio-geral/relatorio-geral.component';
import { RelatorioIndividualComponent } from './candidatos/relatorio/relatorio-individual/relatorio-individual.component';
import { BuscarCandidatoComponent } from './candidatos/relatorio/relatorio-individual/buscar-candidato/buscar-candidato.component';
import { SelecionarCargoComponent } from './candidatos/relatorio/relatorio-cargo/selecionar-cargo/selecionar-cargo.component';
import { RelatorioCargoComponent } from './candidatos/relatorio/relatorio-cargo/relatorio-cargo.component';

const routes: Routes = [
  {
    path:'', component: HomeComponent,
    children: [
      { path: '', component: InicioComponent },
      { path: 'cargos', component: CargoExibirDeletarComponent },
      { path: 'cargos/cadastro', component: CargoCadastrarEditarComponent },
      { path: 'cargos/editar/:id', component: CargoCadastrarEditarComponent },
      { path: 'cargos/selecionar', component: SelecionarCargoComponent },

      { path: 'candidatos', component: CandidatoExibirDeletarComponent },
      { path: 'candidatos/cadastro', component: CandidatoCadastrarEditarComponent },
      { path: 'candidatos/editar/:id', component: CandidatoCadastrarEditarComponent },
      { path: 'candidatos/buscar', component: BuscarCandidatoComponent },

      { path: 'eleitores', component: EleitorExibirDeletarComponent },
      { path: 'eleitores/cadastro', component: EleitorCadastrarEitarComponent },
      { path: 'eleitores/editar/:id', component: EleitorCadastrarEitarComponent },
      { path: 'eleitores/buscar', component: BuscarEleitorParaVotarComponent },
      { path: 'eleitores/votar', component: VotarComponent },

      { path: 'resultados/vencedores', component: RelatorioGeralComponent },
      { path: 'resultados/individual/:id', component: RelatorioIndividualComponent },
      { path: 'resultados/cargo/:id', component: RelatorioCargoComponent },

    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
