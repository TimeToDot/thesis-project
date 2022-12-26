import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-personal-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './personal-info.component.html',
})
export class PersonalInfoComponent {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private location: Location,
    private validationService: ValidationService
  ) {}

  nextStep(): void {
    this.nextStepChange.emit();
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
      'personalInfo',
      name,
    ]);
  }
}
