import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { EmployeeTask } from '../../model/employee-task.model';
import { Project } from '../../../projects/models/project.model';
import { Status } from '../../enum/status.enum';

@Component({
  selector: 'bvr-employee-tasks',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './employee-tasks.component.html',
})
export class EmployeeTasksComponent {
  @Input() employeeTasks: EmployeeTask[] = [];
  @Input() project: Project = {
    id: '',
    name: '',
    image: '',
  };

  @Output() edit: EventEmitter<EmployeeTask> = new EventEmitter();
  @Output() delete: EventEmitter<EmployeeTask> = new EventEmitter();

  canEdit(employeeTask: EmployeeTask): boolean {
    return (
      employeeTask.status === Status.Logged ||
      employeeTask.status === Status.Rejected
    );
  }
}
