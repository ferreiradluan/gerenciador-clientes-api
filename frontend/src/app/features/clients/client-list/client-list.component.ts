import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ClientService } from '../client.service';
import { Cliente } from '../../../shared/models/cliente.model';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.scss']
})
export class ClientListComponent implements OnInit {

  displayedColumns: string[] = ['id', 'nome', 'cpf', 'acoes'];
  dataSource = new MatTableDataSource<Cliente>();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private clientService: ClientService, private router: Router) { }

  ngOnInit(): void {
    this.loadClients();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  loadClients(page = 0, size = 10, sort = 'nome', filters = {}): void {
    this.clientService.getClientes(page, size, sort, filters).subscribe(data => {
      this.dataSource.data = data.content;
      this.paginator.length = data.totalElements;
    });
  }

  editClient(id: number): void {
    this.router.navigate([`/clientes/editar/${id}`]);
  }

  deleteClient(id: number): void {
    this.clientService.deleteCliente(id).subscribe(() => this.loadClients());
  }

  navigateToNewClient(): void {
    this.router.navigate(['/clientes/novo']);
  }
}
