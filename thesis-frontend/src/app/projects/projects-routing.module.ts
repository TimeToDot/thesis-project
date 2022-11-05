import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { ProjectApprovalsComponent } from './project-approvals/project-approvals.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ProjectTasksComponent } from './project-tasks/project-tasks.component';
import { ProjectUsersComponent } from './project-users/project-users.component';
import { ProjectComponent } from './project/project.component';
import { ProjectsComponent } from './projects.component';

const projectsRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'projects',
        component: ProjectsComponent,
      },
      {
        path: 'projects/:id',
        component: ProjectComponent,
        children: [
          {
            path: 'dashboard',
            component: ProjectDashboardComponent,
          },
          {
            path: 'tasks',
            component: ProjectTasksComponent,
          },
          {
            path: 'users',
            component: ProjectUsersComponent,
          },
          {
            path: 'approvals',
            component: ProjectApprovalsComponent,
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
