import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalSettings } from '../../models/global-settings.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-view-company-settings',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-company-settings.component.html',
})
export class ViewCompanySettingsComponent {
  @Input() globalSettings!: GlobalSettings;
}
