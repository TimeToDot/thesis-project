import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { TabsComponent } from '../../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../../shared/models/link-option.model';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { first, Subject } from 'rxjs';
import { GlobalSettingsService } from '../../services/global-settings.service';
import { GlobalSettings } from '../../models/global-settings.model';
import { ActivatedRoute } from '@angular/router';
import { EditCompanySettingsComponent } from './edit-company-settings/edit-company-settings.component';
import { EditContactSettingsComponent } from './edit-contact-settings/edit-contact-settings.component';
import { EditGeneralSettingsComponent } from './edit-general-settings/edit-general-settings.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { tabAnimation } from '../../../shared/animations/tab.animation';

@Component({
  selector: 'bvr-edit-admin-settings',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    EditCompanySettingsComponent,
    EditContactSettingsComponent,
    EditGeneralSettingsComponent,
    ModalComponent,
    ReactiveFormsModule,
    TabsComponent,
    ToastComponent,
  ],
  templateUrl: './edit-admin-settings.component.html',
  animations: [tabAnimation],
})
export class EditAdminSettingsComponent implements OnInit {
  editGlobalSettingsForm!: FormGroup;
  enableFormButtons: boolean = true;
  globalSettings!: GlobalSettings;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  tabIndex: number = 0;

  constructor(
    private fb: FormBuilder,
    private globalSettingsService: GlobalSettingsService,
    private location: Location,
    private route: ActivatedRoute,
    private toastService: ToastService
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

  updateTabIndex(index: number): void {
    this.tabIndex = index;
  }

  getCurrentTab(): string {
    return this.navbarOptions.find(
      option =>
        option.path === this.route.snapshot.firstChild?.routeConfig?.path
    )?.name as string;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editGlobalSettingsForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = 'Are you sure you want to save changes?';
    } else {
      this.editGlobalSettingsForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  cancel(value: boolean): void {
    if (this.isFromGuard) {
      this.redirectSubject.next(value);
    } else {
      this.disableGuard(value);
      if (value) {
        this.location.back();
      }
    }
  }

  save(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      new Promise((resolve, _) => {
        this.location.back();
        resolve('done');
      }).then(() => {
        setTimeout(
          () =>
            this.toastService.showToast(ToastState.Success, 'Employee edited'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
    }
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
