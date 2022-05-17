import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { IEleitorRequest } from '../interfaces/eleitor-request';
import { Observable } from 'rxjs';
import { IEleitorResponse } from '../interfaces/eleitor-response';

@Injectable({
  providedIn: 'root'
})
export class EleitoresService {

  api = environment.api;
  endpoint = 'eleitores';

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

  listarTodos(): Observable<IEleitorResponse[]> {
    return this.http.get<IEleitorResponse[]>(`${this.api}/${this.endpoint}/`);
  }

  buscar(id: number): Observable<IEleitorResponse[]> {
    let params = new HttpParams();
    params = params.append('id', id);
    return this.http.get<IEleitorResponse[]>(`${this.api}/${this.endpoint}`, {params: params});
  }
}
