import { EnderecoRequest, EnderecoResponse } from './endereco.model';
import { TelefoneRequest, TelefoneResponse } from './telefone.model';
import { EmailRequest, EmailResponse } from './email.model';

export interface Cliente {
  id?: number;
  nome: string;
  cpf: string;
  enderecos: EnderecoRequest[];
  telefones: TelefoneRequest[];
  emails: EmailRequest[];
}

export interface ClienteRequest {
  nome: string;
  cpf: string;
  enderecos: EnderecoRequest[];
  telefones: TelefoneRequest[];
  emails: EmailRequest[];
}

export interface ClienteResponse {
  id: number;
  nome: string;
  cpf: string;
  enderecos: EnderecoResponse[];
  telefones: TelefoneResponse[];
  emails: EmailResponse[];
}
