import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UserTask } from '../../shared/model/user-task.model';

@Component({
  selector: 'bvr-add-task',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-task.component.html',
})
export class AddTaskComponent implements OnInit {
  addTaskForm!: FormGroup;
  task: UserTask = {
    startDate: '2012-12-21',
    endDate: '2012-12-21',
    startTime: '12:00',
    endTime: '15:00',
    project: '',
    task: '',
  };

  constructor(private fb: FormBuilder) {
    console.log(formatDate(this.task.startDate, 'yyyy-MM-dd', 'en'));
  }

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.addTaskForm = this.fb.group({
      startDate: [
        formatDate(this.task.startDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      endDate: [
        formatDate(this.task.endDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      startTime: [this.task.startTime, [Validators.required]],
      endTime: [this.task.endTime, [Validators.required]],
      project: ['', [Validators.required]],
      task: ['', [Validators.required]],
    });
  }
}
