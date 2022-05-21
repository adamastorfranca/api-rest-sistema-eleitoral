import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelatorioVotacaoResponse } from 'src/app/interfaces/relatorio-votacao-response';
import { CandidatosService } from 'src/app/services/candidatos.service';

@Component({
  selector: 'app-relatorio-individual',
  templateUrl: './relatorio-individual.component.html',
  styleUrls: ['./relatorio-individual.component.css']
})
export class RelatorioIndividualComponent implements OnInit {

  relatorio!: IRelatorioVotacaoResponse;

  constructor(
    private service: CandidatosService,
    private activedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = parseFloat(this.activedRoute.snapshot.paramMap.get('id') as string);
    this.service.relatorioIndividual(id).subscribe((result) => {
      this.relatorio = result;
    }, (error) => {
      alert('Erro ao buscar relatório');
      console.error(error);
    });
  }
}
