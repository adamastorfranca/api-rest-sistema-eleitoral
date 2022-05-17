import { Component, OnInit } from '@angular/core';

import { CargosService } from 'src/app/services/cargos.service';
import { ICargo } from 'src/app/interfaces/cargo';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-exibir-todos',
  templateUrl: './exibir-todos.component.html',
  styleUrls: ['./exibir-todos.component.css']
})
export class ExibirTodosCargosComponent implements OnInit {

  cargos: ICargo[] = [];

  constructor(
    private service: CargosService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.service.listarTodos().subscribe((result) => {
      this.cargos = result;
    }, error => {
      alert('Erro ao buscar a lista de cargos')
      console.error(error);
    });
  }

  confirmar(id: number) {
    Swal.fire({
      title: 'Você está certo disso?',
      text: "Tem certeza que deseja remover este cargo?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Remover',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.remover(id).subscribe(() => {
          Swal.fire({
            title: 'Removido!',
            text: 'Cargo removido com sucesso!',
            icon: 'success'
          }).then(() => this.listarTodos());
        }, error => {
          console.error(error);
        });
      }
    });
  }
}
