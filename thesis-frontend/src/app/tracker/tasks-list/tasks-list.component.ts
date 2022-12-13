import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeTasksService } from '../../shared/services/employee-tasks.service';
import { EmployeeTask } from '../../shared/model/employee-task.model';
import { EmployeeTasksComponent } from '../../shared/components/employee-tasks/employee-tasks.component';

@Component({
  selector: 'bvr-tasks-list',
  standalone: true,
  imports: [CommonModule, EmployeeTasksComponent],
  templateUrl: './tasks-list.component.html',
})
export class TasksListComponent implements OnInit {
  employeeTasks: EmployeeTask[] = [];

  constructor(private employeeTasksService: EmployeeTasksService) {}

  ngOnInit(): void {
    this.getEmployeeTasks();
  }

  getEmployeeTasks(): void {
    this.employeeTasks = this.employeeTasksService.getEmployeeTasks();
  }
}
