import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ButtonComponent } from '../shared/components/button/button.component';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { RequestsStatusComponent } from './requests-status/requests-status.component';
import { UserProjectsComponent } from './user-projects/user-projects.component';
import { HoursInProjectsComponent } from './hours-in-projects/hours-in-projects.component';
import { TopPositionsComponent } from './top-positions/top-positions.component';
import { PermissionsService } from '../shared/services/permissions.service';
import { Permissions } from '../shared/models/permissions.model';
import { NewEmployeesComponent } from './new-employees/new-employees.component';

@Component({
  selector: 'bvr-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    HoursInProjectsComponent,
    LastTasksComponent,
    NewEmployeesComponent,
    RequestsStatusComponent,
    TopPositionsComponent,
    UserProjectsComponent,
  ],
})
export class DashboardComponent implements OnInit {
  permissions!: Permissions;

  constructor(private permissionsService: PermissionsService) {}

  ngOnInit(): void {
    this.getPermissions();
  }

  getPermissions(): void {
    this.permissions = this.permissionsService.getEmployeePermissions();
  }
}
