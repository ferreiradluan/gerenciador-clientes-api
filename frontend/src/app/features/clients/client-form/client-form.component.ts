import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  clientId?: number;

  constructor(
    private fb: FormBuilder,
    private clientService: ClientService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.checkMode();
  }

  initForm(): void {
    this.clientForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', Validators.required],
      // Adicionar FormArrays para telefones, emails e endereços se necessário
    });
  }

  checkMode(): void {
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
      this.clientForm.patchValue(client);
    });
  }

  onSubmit(): void {
    if (this.clientForm.valid) {
      const clientData: Cliente = this.clientForm.value;
      if (this.isEditMode && this.clientId) {
        this.clientService.updateCliente(this.clientId, clientData).subscribe(() => {
          this.router.navigate(['/clientes']);
        });
      } else {
        this.clientService.createCliente(clientData).subscribe(() => {
          this.router.navigate(['/clientes']);
        });
      }
    }
  }
}
