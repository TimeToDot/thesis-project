import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminUsersComponent } from './admin-users.component';
import { FormsModule } from '@angular/forms';
import { CdkTableModule } from '@angular/cdk/table';
import { ButtonComponent } from '../../shared/components/button/button.component';

@NgModule({
  declarations: [AdminUsersComponent],
  imports: [ButtonComponent, CdkTableModule, CommonModule, FormsModule],
})
export class AdminUsersModule {}
