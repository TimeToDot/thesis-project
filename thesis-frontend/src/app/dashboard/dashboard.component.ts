import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ButtonComponent } from '../shared/components/button/button.component';
import { LastTasksComponent } from './last-tasks/last-tasks.component';
import { RequestsStatusComponent } from './requests-status/requests-status.component';
import { UserProjectsComponent } from './user-projects/user-projects.component';
import { HoursInProjectsComponent } from './hours-in-projects/hours-in-projects.component';

@Component({
  selector: 'bvr-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    HoursInProjectsComponent,
    LastTasksComponent,
    RequestsStatusComponent,
    UserProjectsComponent,
  ],
})
export class DashboardComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
