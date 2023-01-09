import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ValidationService } from '../../../shared/services/validation.service';
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
  @Input() editProjectForm!: FormGroup;

  constructor(private validationService: ValidationService) {}

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editProjectForm, [
      'generalInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editProjectForm, [
          'generalInfo',
          name,
        ])
      : this.validationService.showErrors(this.editProjectForm, []);
  }
}
