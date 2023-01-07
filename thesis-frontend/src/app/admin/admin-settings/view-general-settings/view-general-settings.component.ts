import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalSettings } from '../../models/global-settings.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { SwitchComponent } from '../../../shared/components/switch/switch.component';

@Component({
  selector: 'bvr-view-general-settings',
  standalone: true,
  imports: [CommonModule, FormFieldComponent, SwitchComponent],
  templateUrl: './view-general-settings.component.html',
})
export class ViewGeneralSettingsComponent {
  @Input() globalSettings!: GlobalSettings;
}
