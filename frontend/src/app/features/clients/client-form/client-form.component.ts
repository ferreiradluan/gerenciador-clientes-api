import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../client.service';
import { Cliente } from '../../../shared/models/cliente.model';

@Component({
  selector: 'app-client-form',
  templateUrl: './client-form.component.html',
  styleUrls: ['./client-form.component.scss'],
  standalone: false
})
export class ClientFormComponent implements OnInit {
  clientForm!: FormGroup;
  isEditMode = false;
  clientId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private clientService: ClientService
  ) { }

  ngOnInit(): void {
    // Inicializar o formulário
    this.clientForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', Validators.required],
      telefones: this.fb.array([]),
      emails: this.fb.array([]),
      enderecos: this.fb.array([])
    });

    // Verificar se está em modo de edição
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.clientId = +params['id'];
        this.loadClientData(this.clientId);
      }
    });
  }

  loadClientData(id: number): void {
    this.clientService.getClienteById(id).subscribe(client => {
      // Preencher os campos principais
      this.clientForm.patchValue({
        nome: client.nome,
        cpf: client.cpf
      });

      // Popular FormArray de telefones
      if (client.telefones) {
        client.telefones.forEach(telefone => {
          this.addTelefone(telefone);
        });
      }

      // Popular FormArray de emails
      if (client.emails) {
        client.emails.forEach(email => {
          this.addEmail(email);
        });
      }

      // Popular FormArray de endereços
      if (client.enderecos) {
        client.enderecos.forEach(endereco => {
          this.addEndereco(endereco);
        });
      }
    });
  }

  // Getters para facilitar o acesso aos FormArrays no template
  get telefones(): FormArray {
    return this.clientForm.get('telefones') as FormArray;
  }

  get emails(): FormArray {
    return this.clientForm.get('emails') as FormArray;
  }

  get enderecos(): FormArray {
    return this.clientForm.get('enderecos') as FormArray;
  }

  // Métodos para gerenciar telefones
  addTelefone(telefone?: any): void {
    const telefoneGroup = this.fb.group({
      numero: [telefone?.numero || '', Validators.required],
      tipo: [telefone?.tipo || '', Validators.required]
    });
    this.telefones.push(telefoneGroup);
  }

  removeTelefone(index: number): void {
    this.telefones.removeAt(index);
  }

  // Métodos para gerenciar emails
  addEmail(email?: any): void {
    const emailGroup = this.fb.group({
      endereco: [email?.endereco || '', [Validators.required, Validators.email]],
      tipo: [email?.tipo || '', Validators.required]
    });
    this.emails.push(emailGroup);
  }

  removeEmail(index: number): void {
    this.emails.removeAt(index);
  }

  // Métodos para gerenciar endereços
  addEndereco(endereco?: any): void {
    const enderecoGroup = this.fb.group({
      logradouro: [endereco?.logradouro || '', Validators.required],
      numero: [endereco?.numero || '', Validators.required],
      bairro: [endereco?.bairro || '', Validators.required],
      cidade: [endereco?.cidade || '', Validators.required],
      estado: [endereco?.estado || '', Validators.required],
      cep: [endereco?.cep || '', Validators.required],
      tipo: [endereco?.tipo || '', Validators.required]
    });
    this.enderecos.push(enderecoGroup);
  }

  removeEndereco(index: number): void {
    this.enderecos.removeAt(index);
  }

  onSubmit(): void {
    if (this.clientForm.invalid) {
      console.log('Formulário inválido');
      return;
    }

    const clientData: Cliente = this.clientForm.value;

    if (this.isEditMode && this.clientId) {
      this.clientService.updateCliente(this.clientId, clientData).subscribe({
        next: () => {
          console.log('Cliente atualizado com sucesso');
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          console.error('Erro ao atualizar cliente:', err);
        }
      });
    } else {
      this.clientService.createCliente(clientData).subscribe({
        next: () => {
          console.log('Cliente criado com sucesso');
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          console.error('Erro ao criar cliente:', err);
        }
      });
    }
  }
}
