import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { UserComponent } from '../user/user.component';
import { AdminPositionsComponent } from './admin-positions/admin-positions.component';
import { CreatePositionComponent } from './admin-positions/create-position/create-position.component';
import { EditPositionComponent } from './admin-positions/edit-position/edit-position.component';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { CreateUserComponent } from './admin-users/create-user/create-user.component';
import { EditUserComponent } from './admin-users/edit-user/edit-user.component';
import { CanAdminPositionsGuard } from './guards/can-admin-positions.guard';
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
        path: 'users/create',
        component: CreateUserComponent,
        canActivate: [CanAdminUsersGuard],
      },
      {
        path: 'users/:id',
        component: UserComponent,
        canActivate: [CanAdminUsersGuard],
      },
      {
        path: 'users/:id/edit',
        component: EditUserComponent,
        canActivate: [CanAdminUsersGuard],
      },
      {
        path: 'positions',
        component: AdminPositionsComponent,
        canActivate: [CanAdminPositionsGuard],
      },
      {
        path: 'positions/create',
        component: CreatePositionComponent,
        canActivate: [CanAdminPositionsGuard],
      },
      {
        path: 'positions/:id/edit',
        component: EditPositionComponent,
        canActivate: [CanAdminPositionsGuard],
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
