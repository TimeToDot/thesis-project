import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { Task } from '../model/task.model';

@Component({
  selector: 'bvr-project-tasks',
  templateUrl: './project-tasks.component.html',
  standalone: true,
  imports: [ButtonComponent, CdkTableModule, CommonModule, FormsModule],
})
export class ProjectTasksComponent implements OnInit {
  dataSource: Task[] = [
    {
      name: 'Watering plants',
    },
    {
      name: 'Ironing',
    },
  ];
  displayedColumns: string[] = ['task_name', 'actions'];
  query: string = '';
  constructor() {}

  ngOnInit(): void {}
}
