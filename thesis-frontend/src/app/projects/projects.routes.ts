import { Routes } from '@angular/router';
import { CanAddProjectEmployeeGuard } from './guards/can-add-project-employee.guard';
import { CanAdminProjectGuard } from './guards/can-admin-project.guard';
import { CanManageApprovalsGuard } from './guards/can-manage-approvals.guard';
import { CanManageTasksGuard } from './guards/can-manage-tasks.guard';
import { CanReadProjectGuard } from './guards/can-read-project.guard';
import { ProjectApprovalsComponent } from './project-approvals/project-approvals.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { AddTaskComponent } from './project-tasks/add-task/add-task.component';
import { EditTaskComponent } from './project-tasks/edit-task/edit-task.component';
import { ProjectTasksComponent } from './project-tasks/project-tasks.component';
import { AddProjectEmployeeComponent } from './project-employees/add-project-employee/add-project-employee.component';
import { EditProjectEmployeeComponent } from './project-employees/edit-project-employee/edit-project-employee.component';
import { ProjectEmployeesComponent } from './project-employees/project-employees.component';
import { ProjectComponent } from './project/project.component';
import { ProjectsComponent } from './projects.component';
import { CanManageProjectEmployeesGuard } from './guards/can-manage-project-employees.guard';
import { ViewProjectEmployeeComponent } from './project-employees/view-project-employee/view-project-employee.component';
import { ApprovalTrackerComponent } from './project-approvals/approval-tracker/approval-tracker.component';
import { ViewTaskComponent } from './project-tasks/view-task/view-task.component';
import { UnsavedChangesGuard } from '../shared/guards/unsaved-changes.guard';
import { AddNewProjectComponent } from './add-new-project/add-new-project.component';
import { CanAddProjectGuard } from './guards/can-add-project.guard';
import { EditProjectComponent } from './edit-project/edit-project.component';
import { EditGeneralInfoComponent } from './edit-general-info/edit-general-info.component';
import { EditModeratorInfoComponent } from './edit-moderator-info/edit-moderator-info.component';
import { EditBillingInfoComponent } from './edit-billing-info/edit-billing-info.component';

export const projectsRoutes: Routes = [
  {
    path: 'add',
    component: AddNewProjectComponent,
    canActivate: [CanAddProjectGuard],
    canDeactivate: [UnsavedChangesGuard],
  },
  {
    path: '',
    component: ProjectsComponent,
  },
  {
    path: ':id/edit',
    component: EditProjectComponent,
    canActivate: [CanAdminProjectGuard],
    canDeactivate: [UnsavedChangesGuard],
    children: [
      {
        path: 'general-info',
        component: EditGeneralInfoComponent,
        canActivate: [CanAdminProjectGuard],
        data: { tabs: 0 },
      },
      {
        path: 'moderator-info',
        component: EditModeratorInfoComponent,
        canActivate: [CanAdminProjectGuard],
        data: { tabs: 1 },
      },
      {
        path: 'modifiers-info',
        component: EditBillingInfoComponent,
        canActivate: [CanAdminProjectGuard],
        data: { tabs: 2 },
      },
      { path: '', redirectTo: 'general-info', pathMatch: 'full' },
    ],
  },
  {
    path: ':id',
    component: ProjectComponent,
    children: [
      {
        path: 'dashboard',
        component: ProjectDashboardComponent,
        canActivate: [CanReadProjectGuard],
        data: { tabs: 0 },
      },
      {
        path: 'tasks',
        component: ProjectTasksComponent,
        canActivate: [CanManageTasksGuard],
        data: { tabs: 1 },
      },
      {
        path: 'tasks/add',
        component: AddTaskComponent,
        canActivate: [CanManageTasksGuard],
        canDeactivate: [UnsavedChangesGuard],
        data: { tabs: 1 },
      },
      {
        path: 'tasks/:id',
        component: ViewTaskComponent,
        canActivate: [CanManageTasksGuard],
        data: { tabs: 1 },
      },
      {
        path: 'tasks/:id/edit',
        component: EditTaskComponent,
        canActivate: [CanManageTasksGuard],
        canDeactivate: [UnsavedChangesGuard],
        data: { tabs: 1 },
      },
      {
        path: 'employees',
        component: ProjectEmployeesComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        data: { tabs: 2 },
      },
      {
        path: 'employees/add',
        component: AddProjectEmployeeComponent,
        canActivate: [CanAddProjectEmployeeGuard],
        canDeactivate: [UnsavedChangesGuard],
        data: { tabs: 2 },
      },
      {
        path: 'employees/:id',
        component: ViewProjectEmployeeComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        data: { tabs: 2 },
      },
      {
        path: 'employees/:id/edit',
        component: EditProjectEmployeeComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        canDeactivate: [UnsavedChangesGuard],
        data: { tabs: 2 },
      },
      {
        path: 'approvals',
        component: ProjectApprovalsComponent,
        canActivate: [CanManageApprovalsGuard],
        data: { tabs: 3 },
      },
      {
        path: 'approvals/:id',
        component: ApprovalTrackerComponent,
        canActivate: [CanManageApprovalsGuard],
        data: { tabs: 3 },
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
    ],
  },
];
