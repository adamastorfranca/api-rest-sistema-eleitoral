import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

import { ICargoRequest as ICargoRequest } from '../interfaces/cargo-request';
import { ICargoResponse } from '../interfaces/cargo-response';
import { IRelatorioVotacaoResponse } from '../interfaces/relatorio-votacao-response';

@Injectable({
  providedIn: 'root'
})
export class CargosService {

  api = environment.api;
  endpoint = 'cargos';

  constructor(
    private http: HttpClient,
  ) { }

  cadastrar(cargo: ICargoRequest){
    return this.http.post(`${this.api}/${this.endpoint}/`, cargo);
  }

  editar(id: number, cargo: ICargoRequest){
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, cargo);
  }

  deletar(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  buscar(id: number | string, nome: string, ativo: boolean | string): Observable<ICargoResponse[]> {
    let params = new HttpParams();
    params = params.append('id', id);
    params = params.append('nome', nome);
    params = params.append('ativo', ativo);
    return this.http.get<ICargoResponse[]>(`${this.api}/${this.endpoint}`, {params: params});
  }

  relatorio(idCargo: number) {
    return this.http.get<IRelatorioVotacaoResponse[]>(`${this.api}/${this.endpoint}/relatorio/${idCargo}`);
  }
}
