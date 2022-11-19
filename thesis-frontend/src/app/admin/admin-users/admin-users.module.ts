import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminUsersComponent } from './admin-users.component';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [AdminUsersComponent],
  imports: [CommonModule, MatButtonModule, MatIconModule, MatTableModule],
})
export class AdminUsersModule {}
