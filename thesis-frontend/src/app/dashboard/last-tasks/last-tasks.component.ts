import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { AuthService } from '../../shared/services/auth.service';
import { EmployeeTasksService } from '../../shared/services/employee-tasks.service';
import { first } from 'rxjs';
import { EmployeeTask } from '../../shared/models/employee-task.model';
import { RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'bvr-last-tasks',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './last-tasks.component.html',
})
export class LastTasksComponent implements OnInit {
  employeeTasks: EmployeeTask[] = [];

  constructor(
    private authService: AuthService,
    private employeeTasksService: EmployeeTasksService
  ) {}

  ngOnInit(): void {
    this.getEmployeeTasks();
  }
  getEmployeeTasks(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeeTasksService
        .getEmployeeLastTasks(employeeId)
        .pipe(first())
        .subscribe(employeeTasks => {
          this.employeeTasks = employeeTasks;
        });
    }
  }
}
