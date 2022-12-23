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
        data: { sidenavTabs: 'dashboard' },
      },
      {
        path: 'tracker/request-approval',
        component: RequestApprovalComponent,
        data: { sidenavTabs: 'tracker' },
      },
      {
        path: 'tracker',
        component: TrackerComponent,
        data: { sidenavTabs: 'tracker' },
        children: [
          { path: '', redirectTo: 'add-new-task', pathMatch: 'full' },
          {
            path: 'tasks-list',
            component: TasksListComponent,
            data: { sidenavTabs: 'tracker' },
          },
          {
            path: 'add-new-task',
            component: AddNewTaskComponent,
            data: { sidenavTabs: 'tracker' },
          },
          {
            path: 'edit-task/:id',
            component: AddNewTaskComponent,
            data: { sidenavTabs: 'tracker' },
          },
        ],
      },
      {
        path: 'projects',
        data: { sidenavTabs: 'projects' },
        loadChildren: () =>
          import('./projects/projects.routes').then(mod => mod.projectsRoutes),
      },
      {
        path: 'admin',
        data: { sidenavTabs: 'admin' },
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
