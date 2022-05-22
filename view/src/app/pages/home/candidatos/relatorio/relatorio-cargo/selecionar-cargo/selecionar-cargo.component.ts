import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ICargoResponse } from 'src/app/interfaces/cargo-response';
import { CargosService } from 'src/app/services/cargos.service';

@Component({
  selector: 'app-selecionar-cargo',
  templateUrl: './selecionar-cargo.component.html',
  styleUrls: ['./selecionar-cargo.component.css']
})
export class SelecionarCargoComponent implements OnInit {

  cargos: ICargoResponse[] = [];
  formulario: FormGroup = new FormGroup({
    idCargo: new FormControl(null, Validators.required)
  })

  constructor(
    private service: CargosService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.buscarCargos();
  }

  buscarCargos() {
    this.service.buscar('', '', '').subscribe((result) => {
      this.cargos = result;
    },
    (error) => {
      alert('Erro ao buscar cargos!')
      console.error(error);
    });
  }

  selecionarCargo() {
    const id = this.formulario.get('idCargo')?.value;
    this.router.navigate(['resultados/cargo', id]);
  }

}
