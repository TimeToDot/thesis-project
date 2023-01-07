import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ValidationService } from '../../../../shared/services/validation.service';
import { FileUploadComponent } from '../../../../shared/components/file-upload/file-upload.component';

@Component({
  selector: 'bvr-edit-company-settings',
  standalone: true,
  imports: [
    CommonModule,
    FileUploadComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-company-settings.component.html',
})
export class EditCompanySettingsComponent {
  @Input() editGlobalSettingsForm!: FormGroup;

  constructor(private validationService: ValidationService) {}

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editGlobalSettingsForm, [
      'companyInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editGlobalSettingsForm, [
          'companyInfo',
          name,
        ])
      : this.validationService.showErrors(this.editGlobalSettingsForm, []);
  }
}
