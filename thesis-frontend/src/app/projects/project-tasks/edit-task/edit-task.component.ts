import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
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
import { first, Subject } from 'rxjs';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-edit-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './edit-task.component.html',
})
export class EditTaskComponent {
  editProjectTaskForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();
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
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getTask();
  }

  createForm(): void {
    this.editProjectTaskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  getTask(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.projectTasksService
        .getProjectTask(taskId)
        .pipe(first())
        .subscribe(projectTask => {
          this.task = projectTask;
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

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
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

  cancel(value: boolean): void {
    if (this.isFromGuard) {
      this.redirectSubject.next(value);
    } else {
      this.disableGuard();
      this.location.back();
    }
  }

  save(): void {
    this.disableGuard();
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
    this.disableGuard();
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Task archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  disableGuard(): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(true);
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editProjectTaskForm, [name]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.editProjectTaskForm, [name]);
  }
}
