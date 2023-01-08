import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { HoursInProjectsComponent } from '../../dashboard/hours-in-projects/hours-in-projects.component';
import { LastTasksComponent } from '../../dashboard/last-tasks/last-tasks.component';
import { RequestsStatusComponent } from '../../dashboard/requests-status/requests-status.component';
import { TopPositionsComponent } from '../../dashboard/top-positions/top-positions.component';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { Permissions } from '../../shared/models/permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';
import { ProjectPermissions } from '../models/project-permissions.model';
import { NewProjectEmployeesComponent } from './new-project-employees/new-project-employees.component';
import { NewRequestsComponent } from './new-requests/new-requests.component';
import { ProjectDescriptionComponent } from './project-description/project-description.component';
import { TopTasksComponent } from './top-tasks/top-tasks.component';

@Component({
  selector: 'bvr-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    HoursInProjectsComponent,
    LastTasksComponent,
    NewProjectEmployeesComponent,
    NewRequestsComponent,
    ProjectDescriptionComponent,
    RequestsStatusComponent,
    RouterLinkWithHref,
    ToastComponent,
    TopPositionsComponent,
    TopTasksComponent,
  ],
})
export class ProjectDashboardComponent {
  projectPermissions!: ProjectPermissions;

  constructor(
    private permissionsService: PermissionsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProjectPermissions();
  }

  getProjectPermissions(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectPermissions =
        this.permissionsService.getProjectPermissions(projectId);
    }
  }
}
