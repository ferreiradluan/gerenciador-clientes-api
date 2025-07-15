import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Endereco } from '../models/endereco.model';

@Injectable({
  providedIn: 'root'
})
export class ViaCepService {
  private readonly API_URL = 'https://viacep.com.br/ws';

  constructor(private http: HttpClient) { }

  /**
   * Busca endereço pelo CEP
   * @param cep CEP sem máscara (apenas números)
   * @returns Observable com dados do endereço
   */
  buscarEnderecoPorCep(cep: string): Observable<Endereco> {
    return this.http.get<Endereco>(`${this.API_URL}/${cep}/json/`);
  }
}
