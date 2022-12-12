import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProjectTask } from '../../model/project-task.model';
import { ProjectTasksService } from '../../services/project-tasks.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';

@Component({
  selector: 'bvr-edit-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './edit-task.component.html',
})
export class EditTaskComponent {
  editProjectTaskForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
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
    private fb: FormBuilder,
    private location: Location,
    private projectTasksService: ProjectTasksService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getTask();
    this.createForm();
  }

  getTask(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.task = this.projectTasksService.getProjectTask(taskId);
    }
  }

  createForm(): void {
    this.editProjectTaskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    const taskName = this.editProjectTaskForm.get(['name'])?.value;
    this.modalDescription = `Are you sure you want to archive task ${taskName}? This action cannot be undone.`;
  }

  openSaveModal(): void {
    this.isSaveModalOpen = true;
    this.modalDescription = `Are you sure you want to save changes?`;
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.editProjectTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  save(): void {
    this.location.back();
  }

  archive(): void {
    this.router.navigate(['../..'], { relativeTo: this.route });
  }
}
