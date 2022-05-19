import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';
import { ICargoResponse } from 'src/app/interfaces/cargo-response';
import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';
import Swal from 'sweetalert2';
import { EleitoresService } from 'src/app/services/eleitores.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-buscar-eleitor',
  templateUrl: './buscar-eleitor.component.html',
  styleUrls: ['./buscar-eleitor.component.css']
})
export class BuscarEleitorParaVotarComponent implements OnInit {

  eleitor!: IEleitorResponse;
  formulario: FormGroup = new FormGroup({
    cpf: new FormControl('', Validators.required),
  });

  constructor(
    private router: Router,
    private eleitoresService: EleitoresService,
  ) { }

  ngOnInit(): void {
  }

  buscarEleitor() {
    const cpf = this.formulario.get('cpf')?.value;
    this.eleitoresService.buscar('', '', cpf, '').subscribe((result) => {
      this.eleitor = result[0];

      if(this.eleitor !== undefined) {
        Swal.fire({
        title: this.eleitor.nome,
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: 'Confirmar',
        cancelButtonText: 'Voltar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.eleitoresService.eleitor = this.eleitor;
          this.router.navigate(['/votar', this.eleitor.id]);
        } else {
          this.formulario.reset();
        }
      });
      } else {
        Swal.fire({
          title: 'CPF informado inválido',
          icon: 'warning',
        }).then(() => {
          this.formulario.reset();
        });
      }
    });
  }
}
