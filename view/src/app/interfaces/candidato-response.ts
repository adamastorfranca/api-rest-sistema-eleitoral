import { ICargoResponse } from "./cargo-response";

export interface ICandidatoResponse {
  id: number;
  nome: string;
  cpf: string;
  numero: number;
  legenda: string;
  cargo: ICargoResponse;
  ativo: boolean;
  votado: boolean;
  criadoEm: Date;
  alteradoEm: Date;
  desativadoEm: Date;
}
