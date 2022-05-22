import { Component, OnInit } from '@angular/core';

import { CandidatosService } from 'src/app/services/candidatos.service';
import { ICandidatoResponse } from 'src/app/interfaces/candidato-response';
import Swal from 'sweetalert2';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-exibir-deletar',
  templateUrl: './exibir-deletar.component.html',
  styleUrls: ['./exibir-deletar.component.css']
})
export class CandidatoExibirDeletarComponent implements OnInit {

  candidatos: ICandidatoResponse[] = [];
  filterNome: string = '';
  filterCpf: string = '';
  filterCargo: string = '';
  filterPartido: string = '';
  blFilterCpf: boolean = false;
  blFilterNome: boolean = false;
  blFilterCargo: boolean = false;
  blFilterPartido: boolean = false;
  faX = faXmark;

  constructor(
    private service: CandidatosService
  ) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.service.buscar('', '', '', '', '', '', '').subscribe((result) => {
      this.candidatos = result;
    }, error => {
      alert('Erro ao buscar a lista de candidatos')
      console.error(error);
    });
  }

  deletar(id: number) {
    Swal.fire({
      title: 'Tem certeza que deseja remover este candidato?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Remover',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deletar(id).subscribe(() => {
          Swal.fire({
            title: 'Candidato removido com sucesso!',
            icon: 'success'
          }).then(() => this.listarTodos());
        }, error => {
          console.error(error);
        });
      }
    });
  }

  nomeDigitado(target : any) {
    if(target instanceof EventTarget) {
      var elemento = target as HTMLInputElement;
      this.filterNome = elemento.value;
    }
  }

  cpfDigitado(target : any) {
    if(target instanceof EventTarget) {
      var elemento = target as HTMLInputElement;
      this.filterCpf = elemento.value;
    }
  }

  cargoDigitado(target : any) {
    if(target instanceof EventTarget) {
      var elemento = target as HTMLInputElement;
      this.filterCargo = elemento.value;
    }
  }

  partidoDigitado(target : any) {
    if(target instanceof EventTarget) {
      var elemento = target as HTMLInputElement;
      this.filterPartido = elemento.value;
    }
  }
}
