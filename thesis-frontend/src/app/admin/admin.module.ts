import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminUsersModule } from './admin-users/admin-users.module';

@NgModule({
  declarations: [],
  imports: [AdminRoutingModule, AdminUsersModule, CommonModule],
})
export class AdminModule {}
