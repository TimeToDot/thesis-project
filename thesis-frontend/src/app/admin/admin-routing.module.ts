import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { AdminPositionsComponent } from './admin-positions/admin-positions.component';
import { CreatePositionComponent } from './admin-positions/create-position/create-position.component';
import { EditPositionComponent } from './admin-positions/edit-position/edit-position.component';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminEmployeesComponent } from './admin-employees/admin-employees.component';
import { CreateEmployeeComponent } from './admin-employees/create-employee/create-employee.component';
import { EditEmployeeComponent } from './admin-employees/edit-employee/edit-employee.component';
import { CanAdminPositionsGuard } from './guards/can-admin-positions.guard';
import { CanAdminSettingsGuard } from './guards/can-admin-settings.guard';
import { CanAdminEmployeesGuard } from './guards/can-admin-employees.guard';
import { EmployeeComponent } from '../employee/employee.component';

const adminRoutes: Routes = [
  {
    path: 'admin',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'employees',
        component: AdminEmployeesComponent,
        canActivate: [CanAdminEmployeesGuard],
      },
      {
        path: 'employees/create',
        component: CreateEmployeeComponent,
        canActivate: [CanAdminEmployeesGuard],
      },
      {
        path: 'employees/:id',
        component: EmployeeComponent,
        canActivate: [CanAdminEmployeesGuard],
      },
      {
        path: 'employees/:id/edit',
        component: EditEmployeeComponent,
        canActivate: [CanAdminEmployeesGuard],
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
