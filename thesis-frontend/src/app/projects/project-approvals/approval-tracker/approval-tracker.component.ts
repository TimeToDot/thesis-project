import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { CalendarComponent } from '../../../calendar/calendar.component';
import { ApprovalTrackerListComponent } from '../approval-tracker-list/approval-tracker-list.component';
import { EmployeeTask } from '../../../shared/models/employee-task.model';
import { TasksToRejectService } from '../../../shared/services/tasks-to-reject.service';

@Component({
  selector: 'bvr-approval-tracker',
  standalone: true,
  imports: [
    ApprovalTrackerListComponent,
    ButtonComponent,
    CalendarComponent,
    CommonModule,
  ],
  templateUrl: './approval-tracker.component.html',
})
export class ApprovalTrackerComponent implements OnInit {
  constructor(private tasksToRejectService: TasksToRejectService) {}

  ngOnInit(): void {
    this.tasksToRejectService.tasksToReject.subscribe(tasks => {
      console.log(tasks);
    });
  }
}
