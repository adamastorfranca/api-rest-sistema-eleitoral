import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';
import { EleitoresService } from 'src/app/services/eleitores.service';
import Swal from 'sweetalert2';

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
    private service: EleitoresService,
  ) { }

  ngOnInit(): void {
  }

  buscarEleitor() {
    const cpf = this.formulario.get('cpf')?.value;
    this.service.buscar('', '', cpf, true).subscribe((result) => {
      this.eleitor = result[0];
      if(this.eleitor !== undefined) {
        if(this.eleitor.votou){
          Swal.fire({
            title: 'Eleitor com CPF informado já votou',
            icon: 'warning',
          }).then(() => {
            this.formulario.reset();
          });
        } else {
          Swal.fire({
            title: this.eleitor.nome,
            icon: 'success',
            showCancelButton: true,
            confirmButtonText: 'Confimar',
            cancelButtonText: 'Cancelar'
          }).then((result) => {
            if (result.isConfirmed) {
              this.service.eleitor = this.eleitor;
              this.router.navigate(['eleitores/votar']);
            } else {
              this.formulario.reset();
            }
          });
        }
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
