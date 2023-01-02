import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ValidationService } from '../../../../shared/services/validation.service';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { DatePickerComponent } from '../../../../shared/components/date-picker/date-picker.component';

@Component({
  selector: 'bvr-edit-personal-info',
  standalone: true,
  imports: [
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-personal-info.component.html',
})
export class EditPersonalInfoComponent {
  @Input() editEmployeeForm!: FormGroup;

  sexes: DropdownOption[] = [
    { id: '1', name: 'Male' },
    { id: '2', name: 'Female' },
    { id: '3', name: 'Other' },
  ];

  constructor(private validationService: ValidationService) {}

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editEmployeeForm, [
      'personalInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editEmployeeForm, [
          'personalInfo',
          name,
        ])
      : this.validationService.showErrors(this.editEmployeeForm, []);
  }
}
