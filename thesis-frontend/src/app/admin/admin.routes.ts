import { Routes } from '@angular/router';
import { AdminPositionsComponent } from './admin-positions/admin-positions.component';
import { AddPositionComponent } from './admin-positions/add-position/add-position.component';
import { EditPositionComponent } from './admin-positions/edit-position/edit-position.component';
import { AdminSettingsComponent } from './admin-settings/admin-settings.component';
import { AdminEmployeesComponent } from './admin-employees/admin-employees.component';
import { AddEmployeeComponent } from './admin-employees/add-employee/add-employee.component';
import { EditEmployeeComponent } from './admin-employees/edit-employee/edit-employee.component';
import { CanAdminPositionsGuard } from './guards/can-admin-positions.guard';
import { CanAdminSettingsGuard } from './guards/can-admin-settings.guard';
import { CanAdminEmployeesGuard } from './guards/can-admin-employees.guard';
import { ViewPositionComponent } from './admin-positions/view-position/view-position.component';
import { ViewEmployeeComponent } from './admin-employees/view-employee/view-employee.component';
import { ViewPersonalInfoComponent } from './admin-employees/view-personal-info/view-personal-info.component';
import { ViewAddressInfoComponent } from './admin-employees/view-address-info/view-address-info.component';
import { ViewEmploymentInfoComponent } from './admin-employees/view-employment-info/view-employment-info.component';
import { ViewAccountInfoComponent } from './admin-employees/view-account-info/view-account-info.component';
import { EditPersonalInfoComponent } from './admin-employees/edit-personal-info/edit-personal-info.component';
import { EditAddressInfoComponent } from './admin-employees/edit-address-info/edit-address-info.component';
import { EditEmploymentInfoComponent } from './admin-employees/edit-employment-info/edit-employment-info.component';
import { EditAccountInfoComponent } from './admin-employees/edit-account-info/edit-account-info.component';

export const adminRoutes: Routes = [
  {
    path: 'employees',
    component: AdminEmployeesComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 3 },
  },
  {
    path: 'employees/add',
    component: AddEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 3 },
  },
  {
    path: 'employees/:id',
    component: ViewEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 3 },
    children: [
      {
        path: 'personal-info',
        component: ViewPersonalInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 1 },
      },
      {
        path: 'address-info',
        component: ViewAddressInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 2 },
      },
      {
        path: 'employment-info',
        component: ViewEmploymentInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 3 },
      },
      {
        path: 'account-info',
        component: ViewAccountInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 4 },
      },
      { path: '', redirectTo: 'personal-info', pathMatch: 'full' },
    ],
  },
  {
    path: 'employees/:id/edit',
    component: EditEmployeeComponent,
    canActivate: [CanAdminEmployeesGuard],
    data: { sidenavTabs: 3 },
    children: [
      {
        path: 'personal-info',
        component: EditPersonalInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 1 },
      },
      {
        path: 'address-info',
        component: EditAddressInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 2 },
      },
      {
        path: 'employment-info',
        component: EditEmploymentInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 3 },
      },
      {
        path: 'account-info',
        component: EditAccountInfoComponent,
        canActivate: [CanAdminEmployeesGuard],
        data: { sidenavTabs: 3, tabs: 4 },
      },
      { path: '', redirectTo: 'personal-info', pathMatch: 'full' },
    ],
  },
  {
    path: 'positions',
    component: AdminPositionsComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 4 },
  },
  {
    path: 'positions/add',
    component: AddPositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 4 },
  },
  {
    path: 'positions/:id',
    component: ViewPositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 4 },
  },
  {
    path: 'positions/:id/edit',
    component: EditPositionComponent,
    canActivate: [CanAdminPositionsGuard],
    data: { sidenavTabs: 4 },
  },
  {
    path: 'settings',
    component: AdminSettingsComponent,
    canActivate: [CanAdminSettingsGuard],
    data: { sidenavTabs: 5 },
  },
];
