import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ViaCepResponse, EnderecoRequest } from '../models/endereco.model';

@Injectable({
  providedIn: 'root'
})
export class CepService {
  private readonly API_URL = 'http://localhost:8080/api/cep';

  constructor(private http: HttpClient) { }

  buscarCep(cep: string): Observable<EnderecoRequest> {
    const cepLimpo = cep.replace(/\D/g, '');
    
    if (cepLimpo.length !== 8) {
      throw new Error('CEP deve ter 8 dígitos');
    }

    return this.http.get<ViaCepResponse>(`${this.API_URL}/${cepLimpo}`)
      .pipe(
        map(response => {
          if (response.erro) {
            throw new Error('CEP não encontrado');
          }
          
          return {
            cep: cepLimpo,
            logradouro: response.logradouro,
            complemento: response.complemento || '',
            bairro: response.bairro,
            cidade: response.localidade,
            uf: response.uf
          } as EnderecoRequest;
        })
      );
  }

  formatarCep(cep: string): string {
    const cepLimpo = cep.replace(/\D/g, '');
    return cepLimpo.replace(/(\d{5})(\d{3})/, '$1-$2');
  }

  removerMascara(cep: string): string {
    return cep.replace(/\D/g, '');
  }

  validarCep(cep: string): boolean {
    const cepLimpo = cep.replace(/\D/g, '');
    return cepLimpo.length === 8;
  }
}
