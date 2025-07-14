import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { ClientListComponent } from './client-list/client-list.component';
import { ClientFormComponent } from './client-form/client-form.component';

// Importações do Angular Material
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';

const routes: Routes = [
  { path: '', component: ClientListComponent },
  { path: 'novo', component: ClientFormComponent },
  { path: 'editar/:id', component: ClientFormComponent }
];

@NgModule({
  declarations: [
    ClientListComponent,
    ClientFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    // Angular Material
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  exports: [
    ClientListComponent,
    ClientFormComponent
  ]
})
export class ClientsModule { }
