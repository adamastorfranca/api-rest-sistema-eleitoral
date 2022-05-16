import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { IEleitorCadastro } from '../interfaces/eleitor-cadastro';

@Injectable({
  providedIn: 'root'
})
export class EleitoresService {

  api = environment.api;
  endpoint = 'eleitores';

  constructor(
    private http: HttpClient
  ) { }

  cadastrar(eleitor: IEleitorCadastro){
    return this.http.post(`${this.api}/${this.endpoint}/`, eleitor);
  }
}
