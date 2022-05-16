import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ICargoCadastro } from 'src/app/interfaces/cargo-cadastro';
import { CargosService } from 'src/app/services/cargos.service';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.css']
})
export class CargoCadastrarComponent implements OnInit {

  formCargoCadastro!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: CargosService
  ) { }

  ngOnInit(): void {
    this.formCargoCadastro = this.formBuilder.group({
    nome: ['', [Validators.required]],
    ativo: [true, [Validators.required]]
    });
  }

  cadastrar() {
    const cargo = this.formCargoCadastro.getRawValue() as ICargoCadastro;
    this.service.cadastrar(cargo).subscribe((result) => {
    },
    (error) => {
      alert('Erro ao cadastrar cargo!')
      console.error(error);
    });
  }
}
