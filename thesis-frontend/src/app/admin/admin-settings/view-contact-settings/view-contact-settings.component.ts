import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalSettings } from '../../models/global-settings.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-view-contact-settings',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-contact-settings.component.html',
})
export class ViewContactSettingsComponent {
  @Input() globalSettings!: GlobalSettings;
}
