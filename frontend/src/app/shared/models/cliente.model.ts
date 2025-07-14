import { Endereco } from './endereco.model';
import { Telefone } from './telefone.model';
import { Email } from './email.model';

export interface Cliente {
  id: number;
  nome: string;
  cpf: string;
  telefones: Telefone[];
  emails: Email[];
  enderecos: Endereco[];
}
