import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '../layout/layout.component';
import { AuthGuard } from '../shared/guards/auth.guard';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ProjectsComponent } from './projects.component';

const projectsRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'projects', component: ProjectsComponent },
      { path: 'project/:id', component: ProjectDashboardComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectsRoutes)],
  exports: [RouterModule],
})
export class ProjectsRoutingModule {}
