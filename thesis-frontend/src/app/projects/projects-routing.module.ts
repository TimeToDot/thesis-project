import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { CanAddProjectEmployeeGuard } from './guards/can-add-project-employee.guard';
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

const projectsRoutes: Routes = [
  {
    path: 'projects',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
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
          },
          {
            path: 'tasks',
            component: ProjectTasksComponent,
            canActivate: [CanManageTasksGuard],
          },
          {
            path: 'tasks/create',
            component: CreateTaskComponent,
            canActivate: [CanManageTasksGuard],
          },
          {
            path: 'tasks/edit',
            component: EditTaskComponent,
            canActivate: [CanManageTasksGuard],
          },
          {
            path: 'employees',
            component: ProjectEmployeesComponent,
            canActivate: [CanManageProjectEmployeesGuard],
          },
          {
            path: 'employees/add',
            component: AddProjectEmployeeComponent,
            canActivate: [CanAddProjectEmployeeGuard],
          },
          {
            path: 'employees/:id',
            component: ViewProjectEmployeeComponent,
            canActivate: [CanManageProjectEmployeesGuard],
          },
          {
            path: 'employees/:id/edit',
            component: EditProjectEmployeeComponent,
            canActivate: [CanManageProjectEmployeesGuard],
          },
          {
            path: 'approvals',
            component: ProjectApprovalsComponent,
            canActivate: [CanManageApprovalsGuard],
          },
          {
            path: 'settings',
            component: ProjectSettingsComponent,
            canActivate: [CanAdminProjectGuard],
          },
          {
            path: '',
            redirectTo: 'dashboard',
            pathMatch: 'full',
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectsRoutes)],
  exports: [RouterModule],
})
export class ProjectsRoutingModule {}
