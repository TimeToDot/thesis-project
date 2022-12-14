import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeTasksService } from '../../shared/services/employee-tasks.service';
import { EmployeeTask } from '../../shared/model/employee-task.model';
import { EmployeeTasksComponent } from '../../shared/components/employee-tasks/employee-tasks.component';
import { EmployeeProjectTask } from '../../shared/model/employee-project-task.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'bvr-tasks-list',
  standalone: true,
  imports: [CommonModule, EmployeeTasksComponent],
  templateUrl: './tasks-list.component.html',
})
export class TasksListComponent implements OnInit {
  employeeTasks: EmployeeTask[] = [];
  employeeProjectTasks: EmployeeProjectTask[] = [];

  constructor(
    private employeeTasksService: EmployeeTasksService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getEmployeeTasks();
    this.getEmployeeProjectTasks();
    this.sortByProjectName();
  }

  getEmployeeTasks(): void {
    this.employeeTasks = this.employeeTasksService.getEmployeeTasks();
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

  editTask(task: EmployeeTask): void {
    this.router.navigate(['../edit-task', task.id], { relativeTo: this.route });
  }
}
