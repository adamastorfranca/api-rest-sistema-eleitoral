import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ICargoRequest } from 'src/app/interfaces/cargo-request';
import { CargosService } from 'src/app/services/cargos.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-editar',
  templateUrl: './cadastrar-editar.component.html',
  styleUrls: ['./cadastrar-editar.component.css']
})
export class CargoCadastrarEditarComponent implements OnInit {

  cargoTemp: ICargoRequest = {
    nome: '',
    ativo: true
  }

  id!: number;

  formulario: FormGroup = this.preencherFormGroup(this.cargoTemp);

  constructor(
    private service: CargosService,
    private router: Router,
    private activedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.id = Number(this.activedRoute.snapshot.paramMap.get('id'));
    if (this.id) {
      this.service.buscar(this.id).subscribe((result) => {
        this.cargoTemp = result[0]
        this.formulario = this.preencherFormGroup(this.cargoTemp);
      }, error => {
        alert('Erro ao preencher formulário de edição')
        console.error(error);
      });
    }
  }

  enviar() {
    const cargo = this.formulario.getRawValue() as ICargoRequest;

    if (!this.estaEditando()) {
      this.service.cadastrar(cargo).subscribe(() => {
        Swal.fire('Sucesso', 'Cadastrado com sucesso!', 'success').then(() => this.router.navigate(['cargos']));
      },
        (error) => {
          alert('Erro ao cadastrar cargo!')
          console.error(error);
        });
    } else {
      this.service.editar(this.id, cargo).subscribe(() => {
        Swal.fire('Sucesso', 'Editado com sucesso!', 'success').then(() => this.router.navigate(['cargos']));
      },
        (error) => {
          alert('Erro ao editar cargo!')
          console.error(error);
        });
    }
  }

  preencherFormGroup(cargo: ICargoRequest): FormGroup {
    return new FormGroup({
      nome: new FormControl(cargo.nome, Validators.required),
      ativo: new FormControl(cargo.ativo, Validators.required)
    });
  }

  estaEditando() {
    return this.formulario.get("nome")?.value !== '';
  }
}
