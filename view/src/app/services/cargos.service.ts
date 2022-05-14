import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { ICargo } from '../interfaces/cargo';
import { ICargoCadastro } from '../interfaces/cargo-cadastro';

@Injectable({
  providedIn: 'root'
})
export class CargosService {

  api = environment.api;
  endpoint = 'cargos';

  constructor(
    private http: HttpClient
  ) { }

  cadastrar(cargo: ICargoCadastro){
    return this.http.post<ICargoCadastro>(`${this.api}/${this.endpoint}/cadastrar`, cargo);
  }
}
