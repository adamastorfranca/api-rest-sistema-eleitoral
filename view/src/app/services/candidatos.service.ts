import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

import { ICandidatoRequest } from '../interfaces/candidato-request';
import { ICandidatoResponse } from '../interfaces/candidato-response';
import { IRelatorioVotacaoResponse } from '../interfaces/relatorio-votacao-response';

@Injectable({
  providedIn: 'root'
})
export class CandidatosService {

  api = environment.api;
  endpoint = 'candidatos';

  candidato!: ICandidatoResponse;

  constructor(
    private http: HttpClient
  ) { }

  cadastrar(candidato: ICandidatoRequest) {
    return this.http.post(`${this.api}/${this.endpoint}/`, candidato);
  }

  editar(id: number, candidato: ICandidatoRequest) {
    return this.http.put(`${this.api}/${this.endpoint}/${id}`, candidato);
  }

  deletar(id: number) {
    return this.http.delete(`${this.api}/${this.endpoint}/${id}`);
  }

  buscar(id: number | string, nome: string, cpf: string, cargoId: number | string, numero: number | string, legenda: string, ativo: boolean | string): Observable<ICandidatoResponse[]> {
    let params = new HttpParams();
    params = params.append('id', id);
    params = params.append('nome', nome);
    params = params.append('cpf', cpf);
    params = params.append('cargo', cargoId);
    params = params.append('numero', numero);
    params = params.append('legenda', legenda);
    params = params.append('ativo', ativo);
    return this.http.get<ICandidatoResponse[]>(`${this.api}/${this.endpoint}`, {params: params});
  }

  relatorioGeral() {
    return this.http.get<IRelatorioVotacaoResponse[]>(`${this.api}/${this.endpoint}/relatorio`);
  }

  relatorioIndividual(idCandidato: number) {
    return this.http.get<IRelatorioVotacaoResponse>(`${this.api}/${this.endpoint}/relatorio/${idCandidato}`);
  }
}
