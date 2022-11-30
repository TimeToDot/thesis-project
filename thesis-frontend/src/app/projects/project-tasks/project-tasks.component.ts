import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ProjectTask } from '../../shared/model/project-task.model';
import { ProjectTasksService } from '../../shared/services/project-tasks.service';

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
export class ProjectTasksComponent implements OnInit {
  dataSource: ProjectTask[] = [];
  displayedColumns: string[] = ['task_name', 'actions'];
  query: string = '';

  constructor(private projectTasksService: ProjectTasksService) {}

  ngOnInit(): void {
    this.dataSource = this.projectTasksService.getProjectTasks('1');
  }
}
