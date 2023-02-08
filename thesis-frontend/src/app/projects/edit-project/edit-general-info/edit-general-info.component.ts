import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FileUploadComponent } from '../../../shared/components/file-upload/file-upload.component';
import { ErrorComponent } from '../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-edit-general-info',
  standalone: true,
  imports: [
    CommonModule,
    ErrorComponent,
    FileUploadComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-general-info.component.html',
})
export class EditGeneralInfoComponent {
  @Input() controls: any;
  @Input() editProjectForm!: FormGroup;

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
