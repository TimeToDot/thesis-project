import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { AuthGuard } from './shared/guards/auth.guard';
import { LoginGuard } from './shared/guards/login.guard';
import { AddNewTaskComponent } from './tracker/add-new-task/add-new-task.component';
import { RequestApprovalComponent } from './tracker/request-approval/request-approval.component';
import { TasksListComponent } from './tracker/tasks-list/tasks-list.component';
import { TrackerComponent } from './tracker/tracker.component';
import { NotFoundComponent } from './utils/not-found/not-found.component';

export const appRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: { sidenavTabs: 0 },
      },
      {
        path: 'tracker/request-approval',
        component: RequestApprovalComponent,
        data: { sidenavTabs: 1 },
      },
      {
        path: 'tracker',
        component: TrackerComponent,
        data: { sidenavTabs: 1 },
        children: [
          { path: '', redirectTo: 'add-new-task', pathMatch: 'full' },
          {
            path: 'tasks-list',
            component: TasksListComponent,
            data: { sidenavTabs: 1 },
          },
          {
            path: 'add-new-task',
            component: AddNewTaskComponent,
            data: { sidenavTabs: 1 },
          },
          {
            path: 'edit-task/:id',
            component: AddNewTaskComponent,
            data: { sidenavTabs: 1 },
          },
        ],
      },
      {
        path: 'projects',
        data: { sidenavTabs: 2 },
        loadChildren: () =>
          import('./projects/projects.routes').then(mod => mod.projectsRoutes),
      },
      {
        path: 'admin',
        loadChildren: () =>
          import('./admin/admin.routes').then(mod => mod.adminRoutes),
      },
      { path: 'profile', component: ProfileComponent },
      { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    ],
  },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: '**', component: NotFoundComponent },
];
