import { Component, OnInit } from '@angular/core';

import { IRelatorioVotacaoResponse } from 'src/app/interfaces/relatorio-votacao-response';
import { CandidatosService } from 'src/app/services/candidatos.service';

@Component({
  selector: 'app-relatorio-geral',
  templateUrl: './relatorio-geral.component.html',
  styleUrls: ['./relatorio-geral.component.css']
})
export class RelatorioGeralComponent implements OnInit {

  relatorio: IRelatorioVotacaoResponse[] = [];

  constructor(
    private service: CandidatosService
  ) { }

  ngOnInit(): void {
    this.service.relatorioGeral().subscribe((result) => {
      this.relatorio = result;
    }, (error) => {
      alert('Erro ao buscar relatório');
      console.error(error);
    });
  }
}
