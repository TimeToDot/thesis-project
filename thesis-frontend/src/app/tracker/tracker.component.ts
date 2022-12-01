import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CalendarComponent } from '../calendar/calendar.component';
import { ButtonComponent } from '../shared/components/button/button.component';
import { AddTaskComponent } from './add-task/add-task.component';
import { TasksListComponent } from './tasks-list/tasks-list.component';

@Component({
  selector: 'bvr-tracker',
  templateUrl: './tracker.component.html',
  standalone: true,
  imports: [
    AddTaskComponent,
    ButtonComponent,
    CalendarComponent,
    CommonModule,
    TasksListComponent,
  ],
})
export class TrackerComponent implements OnInit {
  taskListVisible: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  showTaskList(value: boolean): void {
    this.taskListVisible = value;
  }
}
