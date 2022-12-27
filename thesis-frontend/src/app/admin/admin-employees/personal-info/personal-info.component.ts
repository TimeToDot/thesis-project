import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ValidationService } from '../../../shared/services/validation.service';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../shared/models/dropdown-option.model';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-personal-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './personal-info.component.html',
})
export class PersonalInfoComponent {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  modalDescription: string = '';
  sexes: DropdownOption[] = [
    { id: '1', name: 'male' },
    { id: '2', name: 'female' },
    { id: '3', name: 'other' },
  ];

  constructor(
    private location: Location,
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

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
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
