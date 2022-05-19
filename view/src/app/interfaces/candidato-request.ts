export interface ICandidatoRequest {
  nome: string;
  cpf: string;
  numero: number | null;
  legenda: string;
  idCargo: number | string;
  ativo: boolean;
}
