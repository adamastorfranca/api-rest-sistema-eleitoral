export interface IEleitorResponse {
  id: number;
  nome: string;
  cpf: string;
  ativo: boolean;
  criadoEm: Date;
  alteradoEm: Date;
  desativadoEm: Date;
}
