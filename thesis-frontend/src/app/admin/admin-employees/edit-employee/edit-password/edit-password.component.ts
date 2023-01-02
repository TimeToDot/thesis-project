import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ValidationService } from '../../../../shared/services/validation.service';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { ModalComponent } from '../../../../shared/components/modal/modal.component';
import { ToastState } from '../../../../shared/enum/toast-state';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-edit-password',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './edit-password.component.html',
})
export class EditPasswordComponent {
  accordionEnabled: boolean = false;
  editPasswordForm!: FormGroup;
  isConfirmModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private fb: FormBuilder,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.editPasswordForm = this.fb.group({
      password: ['', [Validators.required]],
      repeatPassword: ['', [Validators.required]],
    });
  }

  openConfirmModal(): void {
    if (this.editPasswordForm.valid) {
      this.isConfirmModalOpen = true;
      this.modalDescription = 'Are you sure you want to change password?';
    } else {
      this.editPasswordForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  confirm(): void {
    this.toggleAccordion();
    this.toastService.showToast(ToastState.Success, 'Password updated');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editPasswordForm, [name])
      : this.validationService.showErrors(this.editPasswordForm, []);
  }

  toggleAccordion(): void {
    this.accordionEnabled = !this.accordionEnabled;
  }
}
