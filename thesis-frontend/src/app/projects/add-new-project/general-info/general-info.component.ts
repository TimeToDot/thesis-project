import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { FileUploadComponent } from '../../../shared/components/file-upload/file-upload.component';
import { ErrorComponent } from '../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    ErrorComponent,
    FileUploadComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent {
  @Input() addProjectForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  constructor(private toastService: ToastService) {}

  nextStep(): void {
    if (this.controls.generalInfo?.valid) {
      this.nextStepChange.emit();
    } else {
      this.controls.generalInfo?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
