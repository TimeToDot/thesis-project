import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanAddUserGuard } from '../admin/guards/can-add-user.guard';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { UserComponent } from '../user/user.component';
import { CanAddProjectUserGuard } from './guards/can-add-project-user.guard';
import { CanAdminProjectGuard } from './guards/can-admin-project.guard';
import { CanManageApprovalsGuard } from './guards/can-manage-approvals.guard';
import { CanManageProjectUsersGuard } from './guards/can-manage-project-users.guard';
import { CanManageTasksGuard } from './guards/can-manage-tasks.guard';
import { CanReadProjectGuard } from './guards/can-read-project.guard';
import { ProjectApprovalsComponent } from './project-approvals/project-approvals.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ProjectSettingsComponent } from './project-settings/project-settings.component';
import { ProjectTasksComponent } from './project-tasks/project-tasks.component';
import { AddProjectUserComponent } from './project-users/add-project-user/add-project-user.component';
import { ProjectUsersComponent } from './project-users/project-users.component';
import { ProjectComponent } from './project/project.component';
import { ProjectsComponent } from './projects.component';

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
            path: 'users',
            component: ProjectUsersComponent,
            canActivate: [CanManageProjectUsersGuard],
          },
          {
            path: 'users/add',
            component: AddProjectUserComponent,
            canActivate: [CanAddProjectUserGuard],
          },
          {
            path: 'users/:id',
            component: UserComponent,
            canActivate: [CanManageProjectUsersGuard],
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
