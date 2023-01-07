import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalSettings } from '../../models/global-settings.model';

@Component({
  selector: 'bvr-view-general-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-general-settings.component.html',
})
export class ViewGeneralSettingsComponent {
  @Input() globalSettings!: GlobalSettings;
}
