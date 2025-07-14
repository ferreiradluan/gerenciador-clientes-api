import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
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

  displayedColumns: string[] = ['id', 'nome', 'cpf', 'emails', 'acoes'];
  dataSource = new MatTableDataSource<Cliente>();
  totalElements = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private clientService: ClientService, private router: Router) { }

  ngOnInit(): void {
    // Inicialização básica - carregamento inicial será feito no ngAfterViewInit
  }

  ngAfterViewInit(): void {
    // Configurar paginador e ordenador
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    
    // Carregar dados iniciais
    this.loadClients();
    
    // Inscrever-se nos eventos de mudança do paginador
    this.paginator.page.subscribe(() => {
      this.loadClients();
    });
    
    // Inscrever-se nos eventos de mudança do ordenador
    this.sort.sortChange.subscribe(() => {
      // Resetar para primeira página quando mudar ordenação
      this.paginator.pageIndex = 0;
      this.loadClients();
    });
  }

  loadClients(): void {
    const page = this.paginator?.pageIndex || 0;
    const size = this.paginator?.pageSize || 10;
    const sortField = this.sort?.active || 'nome';
    const sortDirection = this.sort?.direction || 'asc';
    const sort = `${sortField},${sortDirection}`;
    
    this.clientService.getClientes(page, size, sort).subscribe({
      next: (data) => {
        this.dataSource.data = data.content;
        this.totalElements = data.totalElements;
        this.paginator.length = data.totalElements;
      },
      error: (err) => {
        console.error('Erro ao carregar clientes:', err);
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
    if (confirm('Tem certeza que deseja excluir este cliente?')) {
      this.clientService.deleteCliente(id).subscribe({
        next: () => {
          console.log('Cliente excluído com sucesso');
          this.loadClients();
        },
        error: (err) => {
          console.error('Erro ao excluir cliente:', err);
        }
      });
    }
  }
}
