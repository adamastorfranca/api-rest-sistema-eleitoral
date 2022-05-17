import { Component, OnInit } from '@angular/core';

import { EleitoresService } from 'src/app/services/eleitores.service';
import { IEleitor } from 'src/app/interfaces/eleitor';

@Component({
  selector: 'app-exibir-todos',
  templateUrl: './exibir-todos.component.html',
  styleUrls: ['./exibir-todos.component.css']
})
export class ExibirTodosEleitoresComponent implements OnInit {

  eleitores: IEleitor[] = [];

  constructor(
    private service: EleitoresService
  ) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos() {
    this.service.listarTodos().subscribe((result) => {
      this.eleitores = result
    })
  }
}
