import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { Task } from '../model/task.model';

@Component({
  selector: 'bvr-project-tasks',
  templateUrl: './project-tasks.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
  ],
})
export class ProjectTasksComponent {
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
}
