import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { SwitchComponent } from '../../../../shared/components/switch/switch.component';

@Component({
  selector: 'bvr-edit-general-settings',
  standalone: true,
  imports: [
    CommonModule,
    FormFieldComponent,
    ReactiveFormsModule,
    SwitchComponent,
  ],
  templateUrl: './edit-general-settings.component.html',
})
export class EditGeneralSettingsComponent {
  @Input() editGlobalSettingsForm!: FormGroup;

  constructor() {}
}
