import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeTasksComponent } from '../../../shared/components/employee-tasks/employee-tasks.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { EmployeeTask } from '../../../shared/models/employee-task.model';
import { EmployeeProjectTask } from '../../../shared/models/employee-project-task.model';
import { EmployeeTasksService } from '../../../shared/services/employee-tasks.service';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-approval-tracker-list',
  standalone: true,
  imports: [CommonModule, EmployeeTasksComponent, ToastComponent],
  templateUrl: './approval-tracker-list.component.html',
})
export class ApprovalTrackerListComponent {
  employeeTasks: EmployeeTask[] = [];
  employeeProjectTasks: EmployeeProjectTask[] = [];

  constructor(private employeeTasksService: EmployeeTasksService) {}

  ngOnInit(): void {
    this.getEmployeeTasks();
    this.getEmployeeProjectTasks();
    this.sortByProjectName();
  }

  getEmployeeTasks(): void {
    this.employeeTasksService
      .getEmployeeTasks()
      .pipe(first())
      .subscribe(employeeTasks => (this.employeeTasks = employeeTasks));
  }

  getEmployeeProjectTasks(): void {
    this.employeeTasks.forEach(employeeTask => {
      const index = this.findProjectIndex(employeeTask.project.id);
      if (index !== -1) {
        this.employeeProjectTasks[index].tasks.push(employeeTask);
      } else {
        this.employeeProjectTasks.push({
          project: employeeTask.project,
          tasks: [employeeTask],
        });
      }
    });
  }

  findProjectIndex(projectId: string) {
    return this.employeeProjectTasks.findIndex(
      employeeProjectTask => employeeProjectTask.project.id === projectId
    );
  }

  sortByProjectName(): void {
    this.employeeProjectTasks.sort((a, b) =>
      a.project.name.toLowerCase().localeCompare(b.project.name.toLowerCase())
    );
  }
}
