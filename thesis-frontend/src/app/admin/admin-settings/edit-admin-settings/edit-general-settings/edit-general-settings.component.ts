import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-general-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-general-settings.component.html',
})
export class EditGeneralSettingsComponent {
  @Input() editGlobalSettingsForm!: FormGroup;
}
