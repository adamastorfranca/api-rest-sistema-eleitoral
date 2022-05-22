import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { EleitoresService } from 'src/app/services/eleitores.service';
import { IEleitorResponse } from 'src/app/interfaces/eleitor-response';
import Swal from 'sweetalert2';
import { faXmark } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-exibir-deletar',
  templateUrl: './exibir-deletar.component.html',
  styleUrls: ['./exibir-deletar.component.css']
})
export class EleitorExibirDeletarComponent implements OnInit {

  eleitores: IEleitorResponse[] = [];
  filterNome: string = '';
  filterCpf: string = '';
  blFilterCpf: boolean = false;
  blFilterNome: boolean = false;
  faX = faXmark;

  constructor(
    private service: EleitoresService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.service.buscar('', '', '', '').subscribe((result) => {
      this.eleitores = result;
    }, error => {
      alert('Erro ao buscar a lista de eleitores')
      console.error(error);
    });
  }

  deletar(id: number) {
    Swal.fire({
      title: 'Tem certeza que deseja remover este eleitor?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Remover',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deletar(id).subscribe(() => {
          Swal.fire({
            title: 'Eleitor removido com sucesso!',
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

  votar(idEleitor: number) {
    this.service.buscar(idEleitor, '', '', '').subscribe((result) => {
      this.service.eleitor = result[0];
      this.router.navigate(['eleitores/votar']);
    });
  }
}
