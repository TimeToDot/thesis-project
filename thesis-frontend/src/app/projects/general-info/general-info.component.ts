import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { ToastState } from '../../shared/enum/toast-state';
import { ToastService } from '../../shared/services/toast.service';
import { ValidationService } from '../../shared/services/validation.service';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { FileUploadComponent } from '../../shared/components/file-upload/file-upload.component';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FileUploadComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  nextStep(): void {
    if (this.addProjectForm.get('generalInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addProjectForm.get('generalInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectForm, [
      'generalInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addProjectForm, [
          'generalInfo',
          name,
        ])
      : this.validationService.showErrors(this.addProjectForm, []);
  }
}
