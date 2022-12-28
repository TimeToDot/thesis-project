import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ValidationService } from '../../../shared/services/validation.service';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { EditPasswordComponent } from '../edit-password/edit-password.component';

@Component({
  selector: 'bvr-edit-account-info',
  standalone: true,
  imports: [
    CommonModule,
    EditPasswordComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-account-info.component.html',
})
export class EditAccountInfoComponent {
  @Input() editEmployeeForm!: FormGroup;

  constructor(private validationService: ValidationService) {}

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editEmployeeForm, [
      'accountInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editEmployeeForm, [
          'accountInfo',
          name,
        ])
      : this.validationService.showErrors(this.editEmployeeForm, []);
  }
}
