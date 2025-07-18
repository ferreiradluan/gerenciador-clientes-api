import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ClientService } from '../client.service';
import { Cliente } from '../../../shared/models/cliente.model';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.scss'],
  standalone: false
})
export class ClientListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['id', 'nome', 'cpf', 'acoes'];
  dataSource = new MatTableDataSource<Cliente>();
  totalElements = 0;
  isLoading = false;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private clientService: ClientService,
    private router: Router,
    private snackBar: MatSnackBar,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    // Inicialização básica - carregamento inicial será feito no ngAfterViewInit
  }

  ngAfterViewInit(): void {
    // Configurar paginador e ordenador se estiverem disponíveis
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
      
      // Inscrever-se nos eventos de mudança do paginador
      this.paginator.page.subscribe(() => {
        this.loadClients();
      });
    }
    
    if (this.sort) {
      this.dataSource.sort = this.sort;
      
      // Inscrever-se nos eventos de mudança do ordenador
      this.sort.sortChange.subscribe(() => {
        // Resetar para primeira página quando mudar ordenação
        if (this.paginator) {
          this.paginator.pageIndex = 0;
        }
        this.loadClients();
      });
    }
    
    // Carregar dados iniciais
    this.loadClients();
  }

  loadClients(): void {
    this.isLoading = true;
    this.cdr.detectChanges(); // Força detecção de mudanças
    
    const page = this.paginator?.pageIndex || 0;
    const size = this.paginator?.pageSize || 10;
    const sortField = this.sort?.active || 'nome';
    const sortDirection = this.sort?.direction || 'asc';
    
    this.clientService.getClientes(page, size, sortField, sortDirection, {}).subscribe({
      next: (data) => {
        this.dataSource.data = data.content;
        this.totalElements = data.totalElements;
        this.paginator.length = data.totalElements;
        this.isLoading = false;
        this.cdr.detectChanges(); // Força detecção de mudanças
      },
      error: (err) => {
        console.error('Erro ao carregar clientes:', err);
        this.snackBar.open('Erro ao carregar clientes.', 'Fechar', { duration: 3000 });
        this.isLoading = false;
        this.cdr.detectChanges(); // Força detecção de mudanças
      }
    });
  }

  addNewClient(): void {
    this.router.navigate(['/clientes/novo']);
  }

  editClient(id: number): void {
    this.router.navigate([`/clientes/editar/${id}`]);
  }

  deleteClient(id: number): void {
    if (confirm('Tem certeza que deseja excluir este cliente? Esta ação não pode ser desfeita.')) {
      this.clientService.deleteCliente(id).subscribe({
        next: () => {
          this.snackBar.open('Cliente deletado com sucesso!', 'Fechar', { duration: 3000 });
          this.loadClients();
        },
        error: (err) => {
          this.snackBar.open('Erro ao deletar cliente.', 'Fechar', { duration: 3000 });
          console.error('Erro ao excluir cliente:', err);
        }
      });
    }
  }
}
