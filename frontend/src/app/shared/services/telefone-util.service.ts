import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TelefoneUtilService {

  constructor() { }

  /**
   * Remove a máscara do telefone
   * @param telefone Telefone com ou sem máscara
   * @returns Telefone apenas com números
   */
  removerMascara(telefone: string): string {
    if (!telefone) return '';
    return telefone.replace(/\D/g, '');
  }

  /**
   * Aplica máscara no telefone baseado no tipo
   * @param ddd DDD do telefone
   * @param numero Número sem formatação
   * @param tipo Tipo do telefone (CELULAR, RESIDENCIAL, COMERCIAL)
   * @returns Telefone formatado
   */
  aplicarMascara(ddd: string, numero: string, tipo: string): string {
    if (!ddd || !numero || ddd.length !== 2) {
      return (ddd || '') + (numero || '');
    }

    // Celular tem 9 dígitos, outros tipos têm 8
    if (tipo === 'CELULAR' && numero.length === 9) {
      return `(${ddd}) ${numero.substring(0, 5)}-${numero.substring(5)}`;
    } else if (numero.length === 8) {
      return `(${ddd}) ${numero.substring(0, 4)}-${numero.substring(4)}`;
    }
    
    // Se não conseguir formatar, retorna sem máscara
    return ddd + numero;
  }

  /**
   * Aplica máscara no telefone completo
   * @param telefoneCompleto Telefone completo (DDD + número)
   * @param tipo Tipo do telefone
   * @returns Telefone formatado
   */
  aplicarMascaraCompleta(telefoneCompleto: string, tipo: string): string {
    if (!telefoneCompleto || telefoneCompleto.length < 10) {
      return telefoneCompleto;
    }
    
    const limpo = this.removerMascara(telefoneCompleto);
    const ddd = limpo.substring(0, 2);
    const numero = limpo.substring(2);
    
    return this.aplicarMascara(ddd, numero, tipo);
  }

  /**
   * Valida se o telefone tem formato correto baseado no tipo
   * @param ddd DDD do telefone
   * @param numero Número do telefone
   * @param tipo Tipo do telefone
   * @returns true se válido
   */
  validar(ddd: string, numero: string, tipo: string): boolean {
    if (!ddd || !numero || !tipo) {
      return false;
    }
    
    const dddLimpo = this.removerMascara(ddd);
    const numeroLimpo = this.removerMascara(numero);
    
    // Valida DDD (2 dígitos)
    if (dddLimpo.length !== 2 || !/^\d{2}$/.test(dddLimpo)) {
      return false;
    }
    
    // Valida número baseado no tipo
    if (tipo === 'CELULAR') {
      return numeroLimpo.length === 9 && /^\d{9}$/.test(numeroLimpo);
    } else {
      return numeroLimpo.length === 8 && /^\d{8}$/.test(numeroLimpo);
    }
  }

  /**
   * Aplica máscara durante a digitação baseado no tipo
   * @param event Evento de input
   * @param tipo Tipo do telefone
   * @returns Número formatado
   */
  aplicarMascaraInput(event: any, tipo: string): string {
    let value = event.target.value.replace(/\D/g, '');
    
    const maxLength = tipo === 'CELULAR' ? 9 : 8;
    
    if (value.length <= maxLength) {
      if (tipo === 'CELULAR' && value.length > 5) {
        value = value.replace(/(\d{5})(\d+)/, '$1-$2');
      } else if (tipo !== 'CELULAR' && value.length > 4) {
        value = value.replace(/(\d{4})(\d+)/, '$1-$2');
      }
    }
    
    return value;
  }

  /**
   * Aplica máscara no DDD durante a digitação
   * @param event Evento de input
   * @returns DDD formatado
   */
  aplicarMascaraDdd(event: any): string {
    let value = event.target.value.replace(/\D/g, '');
    
    if (value.length <= 2) {
      return value;
    }
    
    return value.substring(0, 2);
  }
}
