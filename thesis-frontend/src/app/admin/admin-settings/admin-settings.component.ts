import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  RouterLinkWithHref,
  RouterOutlet,
} from '@angular/router';
import { first } from 'rxjs';
import { tabAnimation } from '../../shared/animations/tab.animation';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { TabsComponent } from '../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../shared/models/link-option.model';
import { GlobalSettings } from '../models/global-settings.model';
import { GlobalSettingsService } from '../services/global-settings.service';

@Component({
  selector: 'bvr-admin-settings',
  templateUrl: './admin-settings.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    RouterLinkWithHref,
    RouterOutlet,
    TabsComponent,
  ],
  animations: [tabAnimation],
})
export class AdminSettingsComponent implements OnInit {
  globalSettings!: GlobalSettings;
  navbarOptions: LinkOption[] = [];

  constructor(
    private contexts: ChildrenOutletContexts,
    private globalSettingsService: GlobalSettingsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getGlobalSettings();
    this.getNavbarOptions();
  }

  getGlobalSettings(): void {
    this.globalSettingsService
      .getGlobalSettings()
      .pipe(first())
      .subscribe(globalSettings => {
        this.globalSettings = globalSettings;
      });
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'General', path: 'general' });
    this.navbarOptions.push({ name: 'Company Info', path: 'company' });
    this.navbarOptions.push({ name: 'Contact', path: 'contact' });
  }

  getCurrentTab(): string {
    return this.navbarOptions.find(
      option =>
        option.path === this.route.snapshot.firstChild?.routeConfig?.path
    )?.name as string;
  }

  onOutletLoaded(component: any): void {
    component.globalSettings = this.globalSettings;
  }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }
}
