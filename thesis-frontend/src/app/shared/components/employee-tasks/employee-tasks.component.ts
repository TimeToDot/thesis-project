import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { EmployeeTask } from '../../model/employee-task.model';
import { Project } from '../../../projects/models/project.model';

@Component({
  selector: 'bvr-employee-tasks',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './employee-tasks.component.html',
})
export class EmployeeTasksComponent {
  @Input() employeeTasks: EmployeeTask[] = [];
  @Input() project: Project = {
    id: '2',
    name: 'Project 2',
    image: 'assets/companies/company2.png',
  };

  editTask(event: Event, task: EmployeeTask) {}

  openDeleteModal(event: Event, task: EmployeeTask) {}
}
