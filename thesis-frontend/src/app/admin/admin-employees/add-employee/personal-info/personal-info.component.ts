import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { DatePickerComponent } from '../../../../shared/components/date-picker/date-picker.component';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ToastState } from '../../../../shared/enum/toast-state';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { ErrorComponent } from '../../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-personal-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    InputNumberComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './personal-info.component.html',
})
export class PersonalInfoComponent {
  @Input() addEmployeeForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  sexes: DropdownOption[] = [
    { id: '1', name: 'Male' },
    { id: '2', name: 'Female' },
    { id: '3', name: 'Other' },
  ];

  constructor(private toastService: ToastService) {}

  nextStep(): void {
    if (this.controls.personalInfo?.valid) {
      this.nextStepChange.emit();
    } else {
      this.controls.personalInfo?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
