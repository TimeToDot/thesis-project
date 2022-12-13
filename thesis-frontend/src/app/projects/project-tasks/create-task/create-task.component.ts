import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import {
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

@Component({
  selector: 'bvr-create-task',
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
  templateUrl: './create-task.component.html',
})
export class CreateTaskComponent {
  createProjectTaskForm!: FormGroup;
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.createProjectTaskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  openAddModal(): void {
    if (this.createProjectTaskForm.valid) {
      this.isAddModalOpen = true;
      const taskName = this.createProjectTaskForm.get(['name'])?.value;
      this.modalDescription = `Do you want to add ${taskName} to the Project X?`;
    } else {
      this.createProjectTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  add(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Task created'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.createProjectTaskForm
      .get(name)
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }

  showErrors(name: string): boolean {
    return !!(
      this.createProjectTaskForm.get(name)?.invalid &&
      this.createProjectTaskForm.get(name)?.errors &&
      (this.createProjectTaskForm.get(name)?.dirty ||
        this.createProjectTaskForm.get(name)?.touched)
    );
  }
}
