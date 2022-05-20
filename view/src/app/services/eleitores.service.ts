import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { IEleitorRequest } from '../interfaces/eleitor-request';
import { Observable } from 'rxjs';
import { IEleitorResponse } from '../interfaces/eleitor-response';
import { IVotoRequest } from '../interfaces/voto-request';

@Injectable({
  providedIn: 'root'
})
export class EleitoresService {

  api = environment.api;
  endpoint = 'eleitores';

  eleitor!: IEleitorResponse;

  constructor(
    private http: HttpClient
  ) { }

  cadastrar(eleitor: IEleitorRequest){
    return this.http.post(`${this.api}/${this.endpoint}/`, eleitor);
  }

  editar(id: number, eleitor: IEleitorRequest){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, eleitor);
  }

  deletar(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  buscar(id: number | string, nome: string, cpf: string, ativo: boolean | string): Observable<IEleitorResponse[]> {
    let params = new HttpParams();
    params = params.append('id', id);
    params = params.append('nome', nome);
    params = params.append('cpf', cpf);
    params = params.append('ativo', ativo);
    return this.http.get<IEleitorResponse[]>(`${this.api}/${this.endpoint}`, {params: params});
  }

  votar(voto: IVotoRequest) {
    return this.http.post(`${this.api}/${this.endpoint}/votar/`, voto);
  }
}
