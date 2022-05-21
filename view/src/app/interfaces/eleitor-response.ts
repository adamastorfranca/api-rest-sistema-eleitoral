export interface IEleitorResponse {
  id: number;
  nome: string;
  cpf: string;
  ativo: boolean;
  votou: boolean;
  criadoEm: Date;
  alteradoEm: Date;
  desativadoEm: Date;
}
