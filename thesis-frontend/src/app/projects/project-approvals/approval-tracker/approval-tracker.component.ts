import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { CalendarComponent } from '../../../calendar/calendar.component';
import { ApprovalTrackerListComponent } from '../approval-tracker-list/approval-tracker-list.component';
import { TasksToRejectService } from '../../../shared/services/tasks-to-reject.service';
import { Subscription } from 'rxjs';
import { DropdownSearchEmployeeComponent } from '../../project-employees/dropdown-search-employee/dropdown-search-employee.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { Employee } from '../../../shared/models/employee.model';

@Component({
  selector: 'bvr-approval-tracker',
  standalone: true,
  imports: [
    ApprovalTrackerListComponent,
    ButtonComponent,
    CalendarComponent,
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
  ],
  templateUrl: './approval-tracker.component.html',
})
export class ApprovalTrackerComponent implements OnInit, OnDestroy {
  employees: Employee[] = [];

  private tasksToRejectSubscribtion: Subscription = new Subscription();

  constructor(
    private projectEmployeesService: ProjectEmployeesService,
    private tasksToRejectService: TasksToRejectService
  ) {}

  ngOnInit(): void {
    this.tasksToRejectSubscribtion =
      this.tasksToRejectService.tasksToReject.subscribe(tasks => {
        console.log(tasks);
      });
    this.getProjectEmployees();
  }

  getProjectEmployees(): void {
    this.employees = this.projectEmployeesService
      .getProjectEmployees()
      .map(projectEmployee => projectEmployee.employee);
  }

  ngOnDestroy(): void {
    this.tasksToRejectSubscribtion.unsubscribe();
  }
}
