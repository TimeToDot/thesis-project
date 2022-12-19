import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';

@Component({
  selector: 'bvr-personal-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './personal-info.component.html',
})
export class PersonalInfoComponent {
  @Input() createEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  nextStep(): void {
    this.nextStepChange.emit();
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.createEmployeeForm
      .get(['personalInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
