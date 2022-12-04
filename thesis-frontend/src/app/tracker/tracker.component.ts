import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CalendarComponent } from '../calendar/calendar.component';
import { ButtonComponent } from '../shared/components/button/button.component';
import { TabsComponent } from '../shared/components/tabs/tabs.component';
import { LinkOption } from '../shared/model/link-option.model';

@Component({
  selector: 'bvr-tracker',
  templateUrl: './tracker.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CalendarComponent,
    CommonModule,
    RouterModule,
    TabsComponent,
  ],
})
export class TrackerComponent implements OnInit {
  navbarOptions: LinkOption[] = [
    { name: 'Tasks List', path: 'tasks-list' },
    { name: 'Add New Task', path: 'add-new-task' },
  ];
  taskListVisible: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  showTaskList(value: boolean): void {
    this.taskListVisible = value;
  }
}
