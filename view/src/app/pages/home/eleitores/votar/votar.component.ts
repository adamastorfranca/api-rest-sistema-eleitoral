import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';
import { ICargoResponse } from 'src/app/interfaces/cargo-response';
import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';
import { CargosService } from 'src/app/services/cargos.service';
import { EleitoresService } from 'src/app/services/eleitores.service';

@Component({
  selector: 'app-votar',
  templateUrl: './votar.component.html',
  styleUrls: ['./votar.component.css']
})
export class VotarComponent implements OnInit {

  idEleitor = 0;
  eleitor!: IEleitorResponse;
  candidatoAtual!: ICandidatoResponse;
  cargos: ICargoResponse[] = [];
  cargoAtual!: ICandidatoResponse;
  countCargos: number = 0;

  formulario: FormGroup = new FormGroup({
    idCandidato: new FormControl(0, Validators.required)
  })

  constructor(
    private activedRoute: ActivatedRoute,
    private eleitoresService: EleitoresService,
    private cargosService: CargosService,
  ) { }

  ngOnInit(): void {
    this.idEleitor = Number(this.activedRoute.snapshot.paramMap.get('id'));
    this.eleitoresService.buscar(this.idEleitor, '', '', '').subscribe((result) => {
      this.eleitor = result[0];
    });
    this.cargosService.buscar('', '', true).subscribe((result) => {
      this.cargos = result;
    });
  }

  votar() {

  }

}
