import { Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { CanCreateProjectEmployeeGuard } from './guards/can-create-project-employee.guard';
import { CanAdminProjectGuard } from './guards/can-admin-project.guard';
import { CanManageApprovalsGuard } from './guards/can-manage-approvals.guard';
import { CanManageTasksGuard } from './guards/can-manage-tasks.guard';
import { CanReadProjectGuard } from './guards/can-read-project.guard';
import { ProjectApprovalsComponent } from './project-approvals/project-approvals.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ProjectSettingsComponent } from './project-settings/project-settings.component';
import { CreateTaskComponent } from './project-tasks/create-task/create-task.component';
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

export const projectsRoutes: Routes = [
  {
    path: '',
    component: ProjectsComponent,
  },
  {
    path: ':id',
    component: ProjectComponent,
    children: [
      {
        path: 'dashboard',
        component: ProjectDashboardComponent,
        canActivate: [CanReadProjectGuard],
        data: { projectTabs: 'dashboard' },
      },
      {
        path: 'tasks',
        component: ProjectTasksComponent,
        canActivate: [CanManageTasksGuard],
        data: { projectTabs: 'tasks' },
      },
      {
        path: 'tasks/create',
        component: CreateTaskComponent,
        canActivate: [CanManageTasksGuard],
        data: { projectTabs: 'tasks' },
      },
      {
        path: 'tasks/:id',
        component: ViewTaskComponent,
        canActivate: [CanManageTasksGuard],
        data: { projectTabs: 'tasks' },
      },
      {
        path: 'tasks/:id/edit',
        component: EditTaskComponent,
        canActivate: [CanManageTasksGuard],
        data: { projectTabs: 'tasks' },
      },
      {
        path: 'tasks/edit',
        component: EditTaskComponent,
        canActivate: [CanManageTasksGuard],
        data: { projectTabs: 'tasks' },
      },
      {
        path: 'employees',
        component: ProjectEmployeesComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        data: { projectTabs: 'employees' },
      },
      {
        path: 'employees/add',
        component: AddProjectEmployeeComponent,
        canActivate: [CanCreateProjectEmployeeGuard],
        data: { projectTabs: 'employees' },
      },
      {
        path: 'employees/:id',
        component: ViewProjectEmployeeComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        data: { projectTabs: 'employees' },
      },
      {
        path: 'employees/:id/edit',
        component: EditProjectEmployeeComponent,
        canActivate: [CanManageProjectEmployeesGuard],
        data: { projectTabs: 'employees' },
      },
      {
        path: 'approvals',
        component: ProjectApprovalsComponent,
        canActivate: [CanManageApprovalsGuard],
        data: { projectTabs: 'approvals' },
      },
      {
        path: 'approvals/:id',
        component: ApprovalTrackerComponent,
        canActivate: [CanManageApprovalsGuard],
        data: { projectTabs: 'approvals' },
      },
      {
        path: 'settings',
        component: ProjectSettingsComponent,
        canActivate: [CanAdminProjectGuard],
        data: { projectTabs: 'settings' },
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
    ],
  },
];
