import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClientService } from '../client.service';
import { CepService } from '../../../shared/services/cep.service';
import { TelefoneService } from '../../../shared/services/telefone.service';
import { ClienteRequest, ClienteResponse } from '../../../shared/models/cliente.model';
import { TipoTelefone } from '../../../shared/models/telefone.model';
import { EnderecoRequest } from '../../../shared/models/endereco.model';

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
  tipoTelefoneOptions: { value: TipoTelefone, label: string }[] = [];
  cepCarregando = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    public router: Router,
    private clientService: ClientService,
    private cepService: CepService,
    private telefoneService: TelefoneService,
    private snackBar: MatSnackBar
  ) { }

  // Validador customizado para CPF
  cpfValidator(control: AbstractControl): {[key: string]: any} | null {
    if (!control.value) {
      return null;
    }
    
    const isValid = this.clientService.validarCpf(control.value);
    return isValid ? null : { 'cpfInvalido': { value: control.value } };
  }

  // Validador customizado para CEP
  cepValidator(control: AbstractControl): {[key: string]: any} | null {
    if (!control.value) {
      return null;
    }
    
    const isValid = this.cepService.validarCep(control.value);
    return isValid ? null : { 'cepInvalido': { value: control.value } };
  }

  // Validador customizado para telefone
  telefoneValidator(control: AbstractControl): {[key: string]: any} | null {
    if (!control.value || !control.parent) {
      return null;
    }
    
    const tipoControl = control.parent.get('tipo');
    if (!tipoControl?.value) {
      return null;
    }

    const isValid = this.telefoneService.validarTelefone(control.value, tipoControl.value);
    return isValid ? null : { 'telefoneInvalido': { value: control.value } };
  }

  ngOnInit(): void {
    this.tipoTelefoneOptions = this.telefoneService.getTipoTelefoneOptions();
    this.initializeForm();

    // Verificar se está em modo de edição
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.clientId = +params['id'];
        this.loadClientData(this.clientId);
      } else {
        // Modo criação - adicionar pelo menos um telefone e email
        this.addTelefone();
        this.addEmail();
      }
    });
  }

  initializeForm(): void {
    this.clientForm = this.fb.group({
      nome: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100)
      ]],
      cpf: ['', [
        Validators.required,
        this.cpfValidator.bind(this)
      ]],
      endereco: this.fb.group({
        cep: ['', [Validators.required, this.cepValidator.bind(this)]],
        logradouro: ['', Validators.required],
        complemento: [''],
        bairro: ['', Validators.required],
        cidade: ['', Validators.required],
        uf: ['', Validators.required]
      }),
      telefones: this.fb.array([], [Validators.required, Validators.minLength(1)]),
      emails: this.fb.array([], [Validators.required, Validators.minLength(1)])
    });
  }

  loadClientData(id: number): void {
    this.clientService.getClienteById(id).subscribe({
      next: (client: ClienteResponse) => {
        // Preencher campos principais
        this.clientForm.patchValue({
          nome: client.nome,
          cpf: this.clientService.formatarCpf(client.cpf),
          endereco: {
            cep: this.cepService.formatarCep(client.enderecos[0]?.cep || ''),
            logradouro: client.enderecos[0]?.logradouro || '',
            complemento: client.enderecos[0]?.complemento || '',
            bairro: client.enderecos[0]?.bairro || '',
            cidade: client.enderecos[0]?.cidade || '',
            uf: client.enderecos[0]?.uf || ''
          }
        });

        // Popular telefones
        client.telefones.forEach(telefone => {
          this.addTelefone(telefone);
        });

        // Popular emails
        client.emails.forEach(email => {
          this.addEmail(email);
        });
      },
      error: (error) => {
        this.snackBar.open('Erro ao carregar dados do cliente', 'Fechar', { duration: 3000 });
        console.error('Erro ao carregar cliente:', error);
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

  get endereco(): FormGroup {
    return this.clientForm.get('endereco') as FormGroup;
  }

  // Métodos para gerenciar telefones
  addTelefone(telefone?: any): void {
    let numero = '';
    let tipo = TipoTelefone.CELULAR;
    
    if (telefone) {
      // Se é telefone do backend, formatá-lo corretamente
      if (telefone.ddd && telefone.numero) {
        numero = `${telefone.ddd}${telefone.numero}`;
      } else if (telefone.numero) {
        numero = telefone.numero;
      }
      tipo = telefone.tipo || TipoTelefone.CELULAR;
    }
    
    const telefoneGroup = this.fb.group({
      tipo: [tipo, Validators.required],
      numero: [numero, [Validators.required, this.telefoneValidator.bind(this)]]
    });

    // Observer para revalidar número quando tipo mudar
    telefoneGroup.get('tipo')?.valueChanges.subscribe(() => {
      telefoneGroup.get('numero')?.updateValueAndValidity();
    });

    this.telefones.push(telefoneGroup);
  }

  removeTelefone(index: number): void {
    if (this.telefones.length > 1) {
      this.telefones.removeAt(index);
    } else {
      this.snackBar.open('Pelo menos um telefone deve ser cadastrado', 'Fechar', { duration: 3000 });
    }
  }

  getTelefoneMask(index: number): string {
    const tipo = this.telefones.at(index)?.get('tipo')?.value;
    return this.telefoneService.getMascaraTelefone(tipo);
  }

  // Métodos para gerenciar emails
  addEmail(email?: any): void {
    const emailGroup = this.fb.group({
      enderecoEmail: [email?.enderecoEmail || '', [Validators.required, Validators.email]]
    });
    this.emails.push(emailGroup);
  }

  removeEmail(index: number): void {
    if (this.emails.length > 1) {
      this.emails.removeAt(index);
    } else {
      this.snackBar.open('Pelo menos um email deve ser cadastrado', 'Fechar', { duration: 3000 });
    }
  }

  // Método para buscar CEP
  onCepChange(): void {
    const cepControl = this.endereco.get('cep');
    if (!cepControl?.value || !this.cepService.validarCep(cepControl.value)) {
      return;
    }

    this.cepCarregando = true;
    this.cepService.buscarCep(cepControl.value).subscribe({
      next: (endereco: EnderecoRequest) => {
        this.endereco.patchValue({
          logradouro: endereco.logradouro,
          bairro: endereco.bairro,
          cidade: endereco.cidade,
          uf: endereco.uf
        });
        this.cepCarregando = false;
      },
      error: (error) => {
        this.snackBar.open('CEP não encontrado', 'Fechar', { duration: 3000 });
        this.cepCarregando = false;
      }
    });
  }

  onSubmit(): void {
    if (this.clientForm.invalid) {
      this.clientForm.markAllAsTouched();
      this.showValidationErrors();
      return;
    }

    const formValue = this.clientForm.value;
    
    // Debug
    console.log('Form Value:', formValue);
    
    const clientData: ClienteRequest = {
      nome: formValue.nome,
      cpf: this.clientService.removerMascaraCpf(formValue.cpf),
      enderecos: [{
        cep: this.cepService.removerMascara(formValue.endereco.cep),
        logradouro: formValue.endereco.logradouro,
        complemento: formValue.endereco.complemento || null,
        bairro: formValue.endereco.bairro,
        cidade: formValue.endereco.cidade,
        uf: formValue.endereco.uf
      }],
      telefones: formValue.telefones.map((tel: any) => {
        const numeroLimpo = this.telefoneService.removerMascaraTelefone(tel.numero);
        console.log('Telefone original:', tel.numero, 'Limpo:', numeroLimpo);
        
        // Garantir que temos DDD e número válidos
        if (numeroLimpo.length < 10) {
          console.error('Número de telefone muito curto:', numeroLimpo);
        }
        
        return {
          ddd: numeroLimpo.substring(0, 2),
          numero: numeroLimpo.substring(2),
          tipo: tel.tipo
        };
      }),
      emails: formValue.emails.map((email: any) => ({
        enderecoEmail: email.enderecoEmail
      }))
    };
    
    // Debug
    console.log('Client Data to send:', JSON.stringify(clientData, null, 2));

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
          console.error('Detalhes do erro:', err.error);
        }
      });
    }
  }

  private showValidationErrors(): void {
    if (this.clientForm.get('nome')?.hasError('required')) {
      this.snackBar.open('Nome é obrigatório', 'Fechar', { duration: 3000 });
    } else if (this.clientForm.get('cpf')?.hasError('required')) {
      this.snackBar.open('CPF é obrigatório', 'Fechar', { duration: 3000 });
    } else if (this.clientForm.get('cpf')?.hasError('cpfInvalido')) {
      this.snackBar.open('CPF inválido', 'Fechar', { duration: 3000 });
    } else if (this.endereco.get('cep')?.hasError('required')) {
      this.snackBar.open('CEP é obrigatório', 'Fechar', { duration: 3000 });
    } else if (this.endereco.get('cep')?.hasError('cepInvalido')) {
      this.snackBar.open('CEP inválido', 'Fechar', { duration: 3000 });
    } else if (this.telefones.hasError('required') || this.telefones.hasError('minlength')) {
      this.snackBar.open('Pelo menos um telefone deve ser cadastrado', 'Fechar', { duration: 3000 });
    } else if (this.emails.hasError('required') || this.emails.hasError('minlength')) {
      this.snackBar.open('Pelo menos um email deve ser cadastrado', 'Fechar', { duration: 3000 });
    } else {
      this.snackBar.open('Por favor, corrija os erros no formulário', 'Fechar', { duration: 3000 });
    }
  }
}
