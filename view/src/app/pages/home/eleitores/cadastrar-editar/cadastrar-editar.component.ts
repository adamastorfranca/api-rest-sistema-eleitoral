import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { EleitoresService } from 'src/app/services/eleitores.service';
import { IEleitorRequest } from 'src/app/interfaces/eleitor-request';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar-editar',
  templateUrl: './cadastrar-editar.component.html',
  styleUrls: ['./cadastrar-editar.component.css']
})
export class EleitorCadastrarEitarComponent implements OnInit {

  eleitorTemp: IEleitorRequest = {
    nome: '',
    cpf: '',
    ativo: true
  }
  id!: number;
  formulario: FormGroup = this.preencherFormGroup(this.eleitorTemp);
  cpfEstaDesabilitado = false;

  constructor(
    private service: EleitoresService,
    private router: Router,
    private activedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = Number(this.activedRoute.snapshot.paramMap.get('id'));
    if (this.id) {
      this.formulario.get('cpf')?.disable();
      this.cpfEstaDesabilitado = true;
      this.service.buscar(this.id, '', '', '').subscribe((result) => {
        this.eleitorTemp = result[0]
        this.formulario = this.preencherFormGroup(this.eleitorTemp);
      }, error => {
        alert('Erro ao preencher formulário de edição')
        console.error(error);
      });
    }
  }

  enviar() {
    const eleitor = this.formulario.getRawValue() as IEleitorRequest;

    if (!this.estaEditando()) {
      this.service.cadastrar(eleitor).subscribe(() => {
        Swal.fire('Sucesso', 'Cadastrado com sucesso!', 'success').then(() => this.router.navigate(['eleitores']));
      },
        (error) => {
          alert('Erro ao cadastrar eleitor!')
          console.error(error);
        });
    } else {
      this.service.editar(this.id, eleitor).subscribe(() => {
        Swal.fire('Sucesso', 'Editado com sucesso!', 'success').then(() => this.router.navigate(['eleitores']));
      },
        (error) => {
          alert('Erro ao editar eleitor!')
          console.error(error);
        });
    }
  }

  preencherFormGroup(eleitor: IEleitorRequest): FormGroup {
    return new FormGroup({
      nome: new FormControl(eleitor.nome, Validators.required),
      cpf: new FormControl(eleitor.cpf, Validators.required),
      ativo: new FormControl(eleitor.ativo, Validators.required)
    });
  }

  estaEditando() {
    return this.id !== 0;
  }
}

