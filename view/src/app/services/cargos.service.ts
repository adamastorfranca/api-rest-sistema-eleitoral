import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { ICargoCadastro } from '../interfaces/cargo-cadastro';
import { Observable } from 'rxjs';
import { ICargo } from '../interfaces/cargo';
import { ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CargosService {

  api = environment.api;
  endpoint = 'cargos';

  constructor(
    private http: HttpClient,
    private activedRoute: ActivatedRoute
  ) { }

  cadastrar(cargo: ICargoCadastro){
    return this.http.post(`${this.api}/${this.endpoint}/`, cargo);
  }

  remover(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  listarTodos(): Observable<ICargo[]> {
    return this.http.get<ICargo[]>(`${this.api}/${this.endpoint}/`);
  }

  buscar(): Observable<ICargo[]> {
    return this.http.get<ICargo[]>(`${this.api}/${this.endpoint}`);
  }

}
