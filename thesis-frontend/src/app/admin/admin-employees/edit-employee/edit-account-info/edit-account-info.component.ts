import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { EditPasswordComponent } from '../edit-password/edit-password.component';
import { ErrorComponent } from '../../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-edit-account-info',
  standalone: true,
  imports: [
    CommonModule,
    EditPasswordComponent,
    ErrorComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-account-info.component.html',
})
export class EditAccountInfoComponent {
  @Input() controls: any;
  @Input() editEmployeeForm!: FormGroup;

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
