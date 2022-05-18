import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { CandidatosService } from 'src/app/services/candidatos.service';
import { ICandidatoRequest } from 'src/app/interfaces/candidato-request';
import Swal from 'sweetalert2';
import { CargosService } from 'src/app/services/cargos.service';
import { ICargoResponse } from 'src/app/interfaces/cargo-response';

@Component({
  selector: 'app-cadastrar-editar',
  templateUrl: './cadastrar-editar.component.html',
  styleUrls: ['./cadastrar-editar.component.css']
})
export class CandidatoCadastrarEditarComponent implements OnInit {

  candidatoTemp: ICandidatoRequest = {
    nome: '',
    cpf: '',
    numero: 0,
    legenda: '',
    idCargo: 0,
    ativo: true
  }
  id: number = 0;
  formulario: FormGroup = this.preencherFormGroup(this.candidatoTemp);
  cargos: ICargoResponse[] = [];

  constructor(
    private service: CandidatosService,
    private cargosService: CargosService,
    private router: Router,
    private activedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.buscarCargos();
    this.id = Number(this.activedRoute.snapshot.paramMap.get('id'));
    if (this.id) {
      this.service.buscar(this.id).subscribe((result) => {
        this.candidatoTemp.nome = result[0].nome;
        this.candidatoTemp.cpf = result[0].cpf;
        this.candidatoTemp.numero = result[0].numero;
        this.candidatoTemp.legenda = result[0].legenda;
        this.candidatoTemp.idCargo = result[0].cargo.id;
        this.candidatoTemp.ativo = result[0].ativo;
        this.formulario = this.preencherFormGroup(this.candidatoTemp);
      }, error => {
        alert('Erro ao preencher formulário de edição')
        console.error(error);
      });
    }
  }

  enviar() {
    const candidato = this.formulario.getRawValue() as ICandidatoRequest;
    if (!this.estaEditando()) {
      this.service.cadastrar(candidato).subscribe(() => {
        Swal.fire('Sucesso', 'Cadastrado com sucesso!', 'success').then(() => this.router.navigate(['candidatos']));
      },
        (error) => {
          alert('Erro ao cadastrar candidato!')
          console.error(error);
        });
    } else {
      this.service.editar(this.id, candidato).subscribe(() => {
        Swal.fire('Sucesso', 'Editado com sucesso!', 'success').then(() => this.router.navigate(['candidatos']));
      },
        (error) => {
          alert('Erro ao editar candidato!')
          console.error(error);
        });
    }
  }

  preencherFormGroup(candidato: ICandidatoRequest): FormGroup {
    return new FormGroup({
      nome: new FormControl(candidato.nome, Validators.required),
      cpf: new FormControl(candidato.cpf, Validators.required),
      idCargo: new FormControl(candidato.idCargo, Validators.required),
      numero: new FormControl(candidato.numero, Validators.required),
      legenda: new FormControl(candidato.legenda, Validators.required),
      ativo: new FormControl(candidato.ativo, Validators.required)
    });
  }

  estaEditando() {
    return this.id !== 0;
  }

  buscarCargos() {
    this.cargosService.listarTodos().subscribe((result) => {
      this.cargos = result;
    },
    (error) => {
      alert('Erro ao buscar cargos!')
      console.error(error);
    });
  }
}
