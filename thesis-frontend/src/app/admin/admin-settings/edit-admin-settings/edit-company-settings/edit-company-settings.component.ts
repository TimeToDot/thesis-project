import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-company-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-company-settings.component.html',
})
export class EditCompanySettingsComponent {
  @Input() editGlobalSettingsForm!: FormGroup;
}
