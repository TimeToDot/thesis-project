import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-contact-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-contact-settings.component.html',
})
export class EditContactSettingsComponent {
  @Input() editGlobalSettingsForm!: FormGroup;
}
