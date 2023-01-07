import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GlobalSettings } from '../../models/global-settings.model';

@Component({
  selector: 'bvr-view-company-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-company-settings.component.html',
})
export class ViewCompanySettingsComponent {
  @Input() globalSettings!: GlobalSettings;
}
