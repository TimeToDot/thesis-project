import { Component } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { first, Subject } from 'rxjs';
import { ErrorComponent } from '../../../shared/components/error/error.component';
import { Regex } from '../../../shared/helpers/regex.helper';
import { ProjectTask } from '../../models/project-task.model';
import { ProjectTasksService } from '../../services/project-tasks.service';

@Component({
  selector: 'bvr-add-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    ErrorComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './add-task.component.html',
})
export class AddTaskComponent {
  addProjectTaskForm!: FormGroup;
  controls: any = {};
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private projectTasksService: ProjectTasksService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getFormControls();
  }

  createForm(): void {
    this.addProjectTaskForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)]],
      description: ['', [Validators.required]],
    });
  }

  getFormControls(): void {
    Object.keys(this.addProjectTaskForm.controls).forEach(control => {
      this.controls[control] = this.addProjectTaskForm.get([control]);
    });
  }

  openAddModal(): void {
    if (this.addProjectTaskForm.valid) {
      this.isAddModalOpen = true;
      const taskName = this.controls.name?.value;
      this.modalDescription = `Do you want to add ${taskName} to the Project X?`;
    } else {
      this.addProjectTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  add(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      this.projectTasksService
        .addProjectTask(this.getProjectTaskData())
        .pipe(first())
        .subscribe(() => {
          this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
            setTimeout(
              () =>
                this.toastService.showToast(ToastState.Success, 'Task added'),
              200
            );
            setTimeout(() => this.toastService.dismissToast(), 3200);
          });
        });
    }
  }

  getProjectTaskData(): ProjectTask {
    const projectId = this.route.parent?.snapshot.paramMap.get('id') as string;
    return {
      id: '',
      name: this.controls.name?.value,
      description: this.controls.description?.value,
      creationDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
      projectId: projectId,
      active: true,
    };
  }

  cancel(value: boolean): void {
    if (this.isFromGuard) {
      this.redirectSubject.next(value);
    } else {
      this.disableGuard(value);
      if (value) {
        this.location.back();
      }
    }
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
