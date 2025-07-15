import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CpfUtilService {

  constructor() { }

  /**
   * Remove a máscara do CPF
   * @param cpf CPF com ou sem máscara
   * @returns CPF apenas com números
   */
  removerMascara(cpf: string): string {
    if (!cpf) return '';
    return cpf.replace(/\D/g, '');
  }

  /**
   * Aplica máscara no CPF
   * @param cpf CPF sem máscara (apenas números)
   * @returns CPF formatado (XXX.XXX.XXX-XX)
   */
  aplicarMascara(cpf: string): string {
    if (!cpf || cpf.length !== 11) return cpf;
    return cpf.substring(0, 3) + '.' + 
           cpf.substring(3, 6) + '.' + 
           cpf.substring(6, 9) + '-' + 
           cpf.substring(9, 11);
  }

  /**
   * Valida se o CPF tem formato correto (apenas números)
   * @param cpf CPF a ser validado
   * @returns true se válido
   */
  validarFormato(cpf: string): boolean {
    const cpfLimpo = this.removerMascara(cpf);
    return cpfLimpo.length === 11 && /^\d{11}$/.test(cpfLimpo);
  }
}
