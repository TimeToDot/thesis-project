import { Routes } from '@angular/router';
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
import { ViewPositionComponent } from './admin-positions/view-position/view-position.component';
import { ViewEmployeeComponent } from './admin-employees/view-employee/view-employee.component';

export const adminRoutes: Routes = [
  {
    path: 'employees',
    component: AdminEmployeesComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 'employees' },
  },
  {
    path: 'employees/create',
    component: CreateEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 'employees' },
  },
  {
    path: 'employees/:id',
    component: ViewEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 'employees' },
  },
  {
    path: 'employees/:id/edit',
    component: EditEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 'employees' },
  },
  {
    path: 'positions',
    component: AdminPositionsComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 'positions' },
  },
  {
    path: 'positions/create',
    component: CreatePositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 'positions' },
  },
  {
    path: 'positions/:id',
    component: ViewPositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 'positions' },
  },
  {
    path: 'positions/:id/edit',
    component: EditPositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 'positions' },
  },
  {
    path: 'settings',
    component: AdminSettingsComponent,
    canActivate: [CanAdminSettingsGuard],
    data: { sidenavTabs: 'settings' },
  },
];
