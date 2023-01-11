import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { FileUploadComponent } from '../../../../shared/components/file-upload/file-upload.component';
import { ErrorComponent } from '../../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-edit-company-settings',
  standalone: true,
  imports: [
    CommonModule,
    ErrorComponent,
    FileUploadComponent,
    FormFieldComponent,
    InputNumberComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-company-settings.component.html',
})
export class EditCompanySettingsComponent {
  @Input() controls: any;
  @Input() editGlobalSettingsForm!: FormGroup;

  isRequired(control: AbstractControl | null): boolean {
    return control && control?.hasValidator(Validators.required) ? true : false;
  }
}
