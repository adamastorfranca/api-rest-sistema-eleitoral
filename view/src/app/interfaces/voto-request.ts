export interface IVotoRequest {
  idEleitor: number;
  idCargo: number;
  idCandidato: number | null;
  emBranco: boolean;
  nulo: boolean
}
