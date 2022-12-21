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
import { ProjectTask } from '../../models/project-task.model';
import { ProjectTasksService } from '../../services/project-tasks.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { first } from 'rxjs';

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
    ToastComponent,
  ],
  templateUrl: './edit-task.component.html',
})
export class EditTaskComponent {
  editProjectTaskForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
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
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getTask();
    this.createForm();
    this.loadProjectTaskToEdit();
  }

  getTask(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.projectTasksService
        .getProjectTask(taskId)
        .pipe(first())
        .subscribe(projectTask => (this.task = projectTask));
    }
  }

  createForm(): void {
    this.editProjectTaskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  loadProjectTaskToEdit(): void {
    const projectTaskId = this.route.snapshot.paramMap.get('id');
    if (projectTaskId) {
      this.projectTasksService
        .getProjectTask(projectTaskId)
        .pipe(first())
        .subscribe(account => {
          this.task = account;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.editProjectTaskForm.controls).forEach(field => {
      this.editProjectTaskForm
        .get(field)
        ?.setValue(this.task[field as keyof ProjectTask]);
    });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    const taskName = this.editProjectTaskForm.get(['name'])?.value;
    this.modalDescription = `Are you sure you want to archive task ${taskName}? This action cannot be undone.`;
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editProjectTaskForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = `Are you sure you want to save changes?`;
    } else {
      this.editProjectTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
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
    new Promise((resolve, _) => {
      this.location.back();
      resolve('done');
    }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Task edited'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  archive(): void {
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Task archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  showErrors(name: string): boolean {
    return !!(
      this.editProjectTaskForm.get(name)?.invalid &&
      this.editProjectTaskForm.get(name)?.errors &&
      (this.editProjectTaskForm.get(name)?.dirty ||
        this.editProjectTaskForm.get(name)?.touched)
    );
  }
}
