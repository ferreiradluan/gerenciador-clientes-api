import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CepUtilService {

  constructor() { }

  /**
   * Remove a máscara do CEP
   * @param cep CEP com ou sem máscara
   * @returns CEP apenas com números
   */
  removerMascara(cep: string): string {
    if (!cep) return '';
    return cep.replace(/\D/g, '');
  }

  /**
   * Aplica máscara no CEP
   * @param cep CEP sem máscara (apenas números)
   * @returns CEP formatado (XXXXX-XXX)
   */
  aplicarMascara(cep: string): string {
    if (!cep || cep.length !== 8) return cep;
    return cep.substring(0, 5) + '-' + cep.substring(5, 8);
  }

  /**
   * Valida se o CEP tem formato correto
   * @param cep CEP a ser validado
   * @returns true se válido
   */
  validarFormato(cep: string): boolean {
    const cepLimpo = this.removerMascara(cep);
    return cepLimpo.length === 8 && /^\d{8}$/.test(cepLimpo);
  }

  /**
   * Aplica máscara durante a digitação
   * @param event Evento de input
   * @returns CEP formatado
   */
  aplicarMascaraInput(event: any): string {
    let value = event.target.value.replace(/\D/g, '');
    
    if (value.length <= 8) {
      if (value.length > 5) {
        value = value.replace(/(\d{5})(\d+)/, '$1-$2');
      }
    }
    
    return value;
  }
}
