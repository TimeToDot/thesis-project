import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { SwitchComponent } from '../../../../shared/components/switch/switch.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';

@Component({
  selector: 'bvr-edit-general-settings',
  standalone: true,
  imports: [
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    SwitchComponent,
  ],
  templateUrl: './edit-general-settings.component.html',
})
export class EditGeneralSettingsComponent {
  @Input() controls: any;
  @Input() editGlobalSettingsForm!: FormGroup;

  options: DropdownOption[] = [
    { id: '1', name: 'Dashboard' },
    { id: '2', name: 'Tracker' },
  ];

  constructor() {}
}
