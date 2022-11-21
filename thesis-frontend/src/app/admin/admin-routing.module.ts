import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { CanAdminSettingsGuard } from './guards/can-admin-settings.guard';
import { CanAdminUsersGuard } from './guards/can-admin-users.guard';

const adminRoutes: Routes = [
  {
    path: 'admin',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'users',
        component: AdminUsersComponent,
        canActivate: [CanAdminUsersGuard],
      },
      {
        path: 'settings',
        component: AdminSettingsComponent,
        canActivate: [CanAdminSettingsGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adminRoutes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
