import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { DatePickerComponent } from '../../../../shared/components/date-picker/date-picker.component';
import { ValidationService } from '../../../../shared/services/validation.service';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ToastState } from '../../../../shared/enum/toast-state';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { ErrorComponent } from '../../../../shared/components/error/error.component';

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
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './personal-info.component.html',
})
export class PersonalInfoComponent {
  @Input() addEmployeeForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  sexes: DropdownOption[] = [
    { id: '1', name: 'Male' },
    { id: '2', name: 'Female' },
    { id: '3', name: 'Other' },
  ];

  constructor(
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  nextStep(): void {
    if (this.addEmployeeForm.get('personalInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addEmployeeForm.get('personalInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'personalInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'personalInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
