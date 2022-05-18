import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

import { ICandidatoRequest } from '../interfaces/candidato-request';
import { ICandidatoResponse } from '../interfaces/candidato-response';

@Injectable({
  providedIn: 'root'
})
export class CandidatosService {

  api = environment.api;
  endpoint = 'candidatos';

  constructor(
    private http: HttpClient
  ) { }

  cadastrar(candidato: ICandidatoRequest){
    return this.http.post(`${this.api}/${this.endpoint}/`, candidato);
  }

  editar(id: number, candidato: ICandidatoRequest){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, candidato);
  }

  deletar(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  listarTodos(): Observable<ICandidatoResponse[]> {
    return this.http.get<ICandidatoResponse[]>(`${this.api}/${this.endpoint}/`);
  }

  buscar(id: number): Observable<ICandidatoResponse[]> {
    let params = new HttpParams();
    params = params.append('id', id);
    return this.http.get<ICandidatoResponse[]>(`${this.api}/${this.endpoint}`, {params: params});
  }
}
