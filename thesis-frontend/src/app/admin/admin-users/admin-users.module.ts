import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminUsersComponent } from './admin-users.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [AdminUsersComponent],
  imports: [CommonModule, FormsModule, SharedModule],
})
export class AdminUsersModule {}
