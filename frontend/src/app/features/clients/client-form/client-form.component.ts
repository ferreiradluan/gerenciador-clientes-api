import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
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
    public router: Router,
    private clientService: ClientService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.clientForm = this.fb.group({
      nome: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
        Validators.pattern(/^[a-zA-ZÀ-ÿ0-9\s]+$/)
      ]],
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
      } else {
        // Modo criação - adicionar itens padrão para satisfazer validações do backend
        this.addTelefone();
        this.addEmail();
        this.addEndereco();
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

  // Getter para facilitar validação do nome no template
  get nome() {
    return this.clientForm.get('nome');
  }

  // Métodos para gerenciar telefones
  addTelefone(telefone?: any): void {
    const telefoneGroup = this.fb.group({
      ddd: [telefone?.ddd || '11', Validators.required],
      numero: [telefone?.numero || '999999999', Validators.required],
      tipo: [telefone?.tipo || 'CELULAR', Validators.required]
    });
    this.telefones.push(telefoneGroup);
  }

  removeTelefone(index: number): void {
    this.telefones.removeAt(index);
  }

  // Métodos para gerenciar emails
  addEmail(email?: any): void {
    const emailGroup = this.fb.group({
      enderecoEmail: [email?.enderecoEmail || 'cliente@email.com', [Validators.required, Validators.email]]
    });
    this.emails.push(emailGroup);
  }

  removeEmail(index: number): void {
    this.emails.removeAt(index);
  }

  // Métodos para gerenciar endereços
  addEndereco(endereco?: any): void {
    const enderecoGroup = this.fb.group({
      cep: [endereco?.cep || '01001000', Validators.required],
      logradouro: [endereco?.logradouro || ''],
      bairro: [endereco?.bairro || ''],
      cidade: [endereco?.cidade || ''],
      uf: [endereco?.uf || ''],
      complemento: [endereco?.complemento || 'Apto 101']
    });
    this.enderecos.push(enderecoGroup);
  }

  removeEndereco(index: number): void {
    this.enderecos.removeAt(index);
  }

  onSubmit(): void {
    if (this.clientForm.invalid) {
      // Marcar todos os campos como touched para mostrar erros
      this.clientForm.markAllAsTouched();
      
      // Verificar erros específicos do nome
      const nomeControl = this.clientForm.get('nome');
      if (nomeControl?.hasError('required')) {
        this.snackBar.open('Nome é obrigatório.', 'Fechar', { duration: 3000 });
      } else if (nomeControl?.hasError('minlength')) {
        this.snackBar.open('Nome deve ter pelo menos 3 caracteres.', 'Fechar', { duration: 3000 });
      } else if (nomeControl?.hasError('maxlength')) {
        this.snackBar.open('Nome deve ter no máximo 100 caracteres.', 'Fechar', { duration: 3000 });
      } else if (nomeControl?.hasError('pattern')) {
        this.snackBar.open('Nome deve conter apenas letras, números e espaços.', 'Fechar', { duration: 3000 });
      } else {
        this.snackBar.open('Por favor, preencha todos os campos obrigatórios.', 'Fechar', { duration: 3000 });
      }
      return;
    }

    const clientData: Cliente = this.clientForm.value;

    if (this.isEditMode && this.clientId) {
      this.clientService.updateCliente(this.clientId, clientData).subscribe({
        next: () => {
          this.snackBar.open('Cliente atualizado com sucesso!', 'Fechar', { duration: 3000 });
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          this.snackBar.open('Erro ao atualizar cliente.', 'Fechar', { duration: 3000 });
          console.error('Erro ao atualizar cliente:', err);
        }
      });
    } else {
      this.clientService.createCliente(clientData).subscribe({
        next: () => {
          this.snackBar.open('Cliente criado com sucesso!', 'Fechar', { duration: 3000 });
          this.router.navigate(['/clientes']);
        },
        error: (err) => {
          this.snackBar.open('Erro ao criar cliente.', 'Fechar', { duration: 3000 });
          console.error('Erro ao criar cliente:', err);
        }
      });
    }
  }
}
