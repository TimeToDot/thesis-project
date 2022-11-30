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
import { UserProjectsService } from '../../shared/services/user-projects.service';
import { Project } from '../../shared/model/project.model';
import { ProjectTasksService } from '../../shared/services/project-tasks.service';
import { ProjectTask } from '../../shared/model/project-task.model';

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
  newTask: UserTask = {
    startDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
    endDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
    startTime: formatDate(new Date(Date.now()), 'H:mm', 'en'),
    endTime: formatDate(new Date(Date.now()), 'H:mm', 'en'),
    projectId: '',
    taskId: '',
  };
  projects: Project[] = [];
  tasks: ProjectTask[] = [];

  constructor(
    private fb: FormBuilder,
    private projectTasksService: ProjectTasksService,
    private userProjectService: UserProjectsService
  ) {}

  ngOnInit(): void {
    this.projects = this.userProjectService.getUserProjects();
    this.createForm();
  }

  createForm(): void {
    this.addTaskForm = this.fb.group({
      startDate: [
        formatDate(this.newTask.startDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      endDate: [
        formatDate(this.newTask.endDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      startTime: [this.newTask.startTime, [Validators.required]],
      endTime: [this.newTask.endTime, [Validators.required]],
      project: [this.newTask.projectId, [Validators.required]],
      task: [
        { value: this.newTask.taskId, disabled: true },
        [Validators.required],
      ],
    });
  }

  isRequired(name: string): boolean {
    return this.addTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  selectProject(): void {
    const projectId = this.addTaskForm.get('project')?.value;
    this.tasks = this.projectTasksService.getProjectTasks(projectId);
    this.addTaskForm.get('task')?.enable();
  }
}
