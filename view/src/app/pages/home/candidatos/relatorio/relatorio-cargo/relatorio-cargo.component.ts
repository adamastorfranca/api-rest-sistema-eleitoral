import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelatorioVotacaoResponse } from 'src/app/interfaces/relatorio-votacao-response';
import { CargosService } from 'src/app/services/cargos.service';

@Component({
  selector: 'app-relatorio-cargo',
  templateUrl: './relatorio-cargo.component.html',
  styleUrls: ['./relatorio-cargo.component.css']
})
export class RelatorioCargoComponent implements OnInit {

  relatorio: IRelatorioVotacaoResponse[] = [];
  totalDeVotos: number = 0;

  constructor(
    private service: CargosService,
    private activedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = parseFloat(this.activedRoute.snapshot.paramMap.get('id') as string);
    this.service.relatorio(id).subscribe((result) => {
      this.relatorio = result;
      this.calcularTotalDeVotos();
    }, (error) => {
      alert('Erro ao buscar relatório');
      console.error(error);
    });
    this.calcularTotalDeVotos();
  }

  calcularTotalDeVotos() {
    for(let i of this.relatorio) {
      this.totalDeVotos = this.totalDeVotos + i.votos;
    }
  }
}
