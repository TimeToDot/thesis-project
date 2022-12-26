import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-contact-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './contact-info.component.html',
})
export class ContactInfoComponent {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private location: Location,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  nextStep(): void {
    if (this.addEmployeeForm.get('contactInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addEmployeeForm.get('contactInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'contactInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'contactInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
