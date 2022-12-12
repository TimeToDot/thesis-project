import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ProjectTask } from '../../model/project-task.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectTasksService } from '../../services/project-tasks.service';

@Component({
  selector: 'bvr-view-task',
  standalone: true,
  imports: [ButtonComponent, CommonModule, FormFieldComponent, RouterModule],
  templateUrl: './view-task.component.html',
})
export class ViewTaskComponent implements OnInit {
  task: ProjectTask = {
    id: '',
    name: '',
    projectId: '',
    description: '',
    creationDate: '',
    archiveDate: '',
    active: true,
  };

  constructor(
    private projectTasksService: ProjectTasksService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getTask();
  }

  getTask(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.task = this.projectTasksService.getProjectTask(taskId);
    }
  }
}
