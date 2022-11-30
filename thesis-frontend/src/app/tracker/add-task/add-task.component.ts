import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UserTask } from '../../shared/model/user-task.model';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-add-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './add-task.component.html',
})
export class AddTaskComponent implements OnInit {
  addTaskForm!: FormGroup;
  projects: string[] = ['Project A', 'Project B', 'Project C'];
  userTask: UserTask = {
    startDate: '2012-12-21',
    endDate: '2012-12-21',
    startTime: '12:00',
    endTime: '15:00',
    project: 'Project B',
    task: 'Task C',
  };
  tasks: string[] = ['Task A', 'Task B', 'Task C'];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.addTaskForm = this.fb.group({
      startDate: [
        formatDate(this.userTask.startDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      endDate: [
        formatDate(this.userTask.endDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      startTime: [this.userTask.startTime, [Validators.required]],
      endTime: [this.userTask.endTime, [Validators.required]],
      project: [this.userTask.project, [Validators.required]],
      task: [this.userTask.task, [Validators.required]],
    });
  }

  isRequired(name: string): boolean {
    return this.addTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
