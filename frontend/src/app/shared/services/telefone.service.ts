import { Injectable } from '@angular/core';
import { TipoTelefone } from '../models/telefone.model';

@Injectable({
  providedIn:'root'
})
export class TelefoneService {

  constructor() { }

  formatarTelefone(numero: string, tipo: TipoTelefone): string {
    const numeroLimpo = numero.replace(/\D/g, '');
    
    if (tipo === TipoTelefone.CELULAR) {
      // Celular: (XX) 9XXXX-XXXX
      return numeroLimpo.replace(/(\d{2})(\d{1})(\d{4})(\d{4})/, '($1) $2$3-$4');
    } else {
      // Residencial/Comercial: (XX) XXXX-XXXX
      return numeroLimpo.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
    }
  }

  removerMascaraTelefone(numero: string): string {
    return numero.replace(/\D/g, '');
  }

  validarTelefone(numero: string, tipo: TipoTelefone): boolean {
    const numeroLimpo = numero.replace(/\D/g, '');
    
    if (tipo === TipoTelefone.CELULAR) {
      // Celular deve ter 11 dígitos e o terceiro dígito deve ser 9
      return numeroLimpo.length === 11 && numeroLimpo.charAt(2) === '9';
    } else {
      // Residencial/Comercial deve ter 10 dígitos
      return numeroLimpo.length === 10;
    }
  }

  getMascaraTelefone(tipo: TipoTelefone): string {
    if (tipo === TipoTelefone.CELULAR) {
      return '(00) 90000-0000';
    } else {
      return '(00) 0000-0000';
    }
  }

  getTipoTelefoneOptions(): { value: TipoTelefone, label: string }[] {
    return [
      { value: TipoTelefone.RESIDENCIAL, label: 'Residencial' },
      { value: TipoTelefone.COMERCIAL, label: 'Comercial' },
      { value: TipoTelefone.CELULAR, label: 'Celular' }
    ];
  }
}
