import { Component, OnInit } from '@angular/core';

import { CargosService } from 'src/app/services/cargos.service';
import { ICargoResponse } from 'src/app/interfaces/cargo-response';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-exibir-deletar',
  templateUrl: './exibir-deletar.component.html',
  styleUrls: ['./exibir-deletar.component.css']
})
export class CargoExibirDeletarComponent implements OnInit {

  cargos: ICargoResponse[] = [];

  constructor(
    private service: CargosService,
  ) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.service.buscar('', '', '').subscribe((result) => {
      this.cargos = result;
    }, error => {
      alert('Erro ao buscar a lista de cargos')
      console.error(error);
    });
  }

  deletar(id: number) {
    Swal.fire({
      title: 'Tem certeza que deseja remover este cargo?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Remover',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deletar(id).subscribe(() => {
          Swal.fire({
            title: 'Cargo removido com sucesso!',
            icon: 'success'
          }).then(() => this.listarTodos());
        }, error => {
          console.error(error);
        });
      }
    });
  }
}
