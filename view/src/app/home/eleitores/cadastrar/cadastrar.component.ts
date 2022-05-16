import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { EleitoresService } from 'src/app/services/eleitores.service';
import { IEleitorCadastro } from 'src/app/interfaces/eleitor-cadastro';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.css']
})
export class EleitorCadastrarComponent implements OnInit {

  formEleitorCadastro!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: EleitoresService
  ) { }

  ngOnInit(): void {
    this.formEleitorCadastro = this.formBuilder.group({
    nome: ['', [Validators.required]],
    cpf: ['', [Validators.required]],
    ativo: [true, [Validators.required]]
    });
  }

  cadastrar() {
    const eleitor = this.formEleitorCadastro.getRawValue() as IEleitorCadastro;
    this.service.cadastrar(eleitor).subscribe((result) => {
    },
    (error) => {
      alert('Erro ao cadastrar eleitor!')
      console.error(error);
    });
  }
}
