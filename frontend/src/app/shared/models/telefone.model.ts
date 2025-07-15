export enum TipoTelefone {
  RESIDENCIAL = 'RESIDENCIAL',
  COMERCIAL = 'COMERCIAL',
  CELULAR = 'CELULAR'
}

export interface Telefone {
  id?: number;
  tipo: TipoTelefone;
  numero: string;
}

export interface TelefoneRequest {
  ddd: string;
  numero: string;
  tipo: TipoTelefone;
}

export interface TelefoneResponse {
  id?: number;
  ddd: string;
  numero: string;
  tipo: TipoTelefone;
  formatado?: string;
}
