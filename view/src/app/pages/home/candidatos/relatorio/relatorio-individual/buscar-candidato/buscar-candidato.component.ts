import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';
import { CandidatosService } from 'src/app/services/candidatos.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-buscar-candidato',
  templateUrl: './buscar-candidato.component.html',
  styleUrls: ['./buscar-candidato.component.css']
})
export class BuscarCandidatoComponent implements OnInit {

  candidato!: ICandidatoResponse;
  formulario: FormGroup = new FormGroup({
    numero: new FormControl('', Validators.required),
  });

  constructor(
    private router: Router,
    private service: CandidatosService,
  ) { }

  ngOnInit(): void {
  }

  buscarEleitor() {
    const numero = this.formulario.get('numero')?.value;
    this.service.buscar('', '', '', '', numero, '', true).subscribe((result) => {
      this.candidato = result[0];
      if(this.candidato !== undefined) {
        Swal.fire({
          title: this.candidato.nome,
          text: 'Partido: ' + this.candidato.legenda,
          icon: 'success',
          showCancelButton: true,
          confirmButtonText: 'Confimar',
          cancelButtonText: 'Cancelar'
        }).then((result) => {
          if (result.isConfirmed) {
            this.service.candidato = this.candidato;
            this.router.navigate(['/resultados/individual', this.candidato.id]);
          } else {
            this.formulario.reset();
          }
        });
      } else {
        Swal.fire({
          title: 'Nenhum candidato encontrado com este número',
          icon: 'warning',
        }).then(() => {
          this.formulario.reset();
        });
      }
    });
  }
}
