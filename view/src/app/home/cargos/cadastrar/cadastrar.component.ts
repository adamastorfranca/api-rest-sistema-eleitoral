import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ICargoCadastro } from 'src/app/interfaces/cargo-cadastro';
import { CargosService } from 'src/app/services/cargos.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cadastrar',
  templateUrl: './cadastrar.component.html',
  styleUrls: ['./cadastrar.component.css']
})
export class CargoCadastrarComponent implements OnInit {

  emptyCargo: ICargoCadastro = {
    nome: '',
    ativo: true
  }

  formCargoCadastro: FormGroup = this.preencherFormGroup(this.emptyCargo);

  constructor(
    private formBuilder: FormBuilder,
    private service: CargosService,
    private router: Router,
    private activedRoute: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const id = Number(this.activedRoute.snapshot.paramMap.get('id'));
    if (id) {
      this.service.listarTodos();
    }


    this.formCargoCadastro = this.formBuilder.group({
    nome: ['', [Validators.required]],
    ativo: [true, [Validators.required]]
    });
  }

  cadastrar() {
    const cargo = this.formCargoCadastro.getRawValue() as ICargoCadastro;
    this.service.cadastrar(cargo).subscribe((result) => {
      Swal.fire('Sucesso', 'Cadastrao com sucesso!', 'success').then(() => this.router.navigate(['cargos']));
    },
    (error) => {
      alert('Erro ao cadastrar cargo!')
      console.error(error);
    });
  }

  preencherFormGroup(cargo: ICargoCadastro): FormGroup {
    return new FormGroup({
      nome: new FormControl(cargo.nome, Validators.required),
      ativo: new FormControl(cargo.ativo, Validators.required)
    });
  }
}
