import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { first, Subject } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
  RouterLinkWithHref,
  RouterOutlet,
} from '@angular/router';
import { Account } from '../../../shared/models/account.model';
import { AccountsService } from '../../services/accounts.service';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { TabsComponent } from '../../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../../shared/models/link-option.model';
import { tabAnimation } from '../../../shared/animations/tab.animation';

@Component({
  selector: 'bvr-view-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    RouterOutlet,
    TabsComponent,
    ToastComponent,
  ],
  templateUrl: './view-employee.component.html',
  animations: [tabAnimation],
})
export class ViewEmployeeComponent {
  account: Account = {
    id: '',
    firstName: '',
    middleName: '',
    lastName: '',
    email: '',
    password: '',
    position: {
      id: '',
      name: '',
      description: '',
      creationDate: '',
      count: 0,
      archiveDate: '',
      active: true,
    },
    employmentDate: '',
    workingTime: 0,
    exitDate: '',
    image: '',
    sex: { id: '', name: '' },
    birthPlace: '',
    idCardNumber: '',
    pesel: 0,
    contractType: { id: '', name: '' },
    wage: 0,
    payday: 0,
    birthDate: '',
    phoneNumber: '',
    privateEmail: '',
    street: '',
    houseNumber: '',
    apartmentNumber: '',
    city: '',
    postalCode: '',
    country: { id: '', name: '' },
    accountNumber: '',
    active: true,
  };
  enableFormButtons: boolean = true;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private accountsService: AccountsService,
    private contexts: ChildrenOutletContexts,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getAccount();
    this.getNavbarOptions();
  }

  getAccount(): void {
    const accountId = this.route.snapshot.paramMap.get('id');
    if (accountId) {
      this.accountsService
        .getAccount(accountId)
        .pipe(first())
        .subscribe(account => {
          this.account = account;
        });
    }
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'Personal', path: 'personal-info' });
    this.navbarOptions.push({ name: 'Address', path: 'address-info' });
    this.navbarOptions.push({ name: 'Employment', path: 'employment-info' });
    this.navbarOptions.push({ name: 'Account', path: 'account-info' });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.account.firstName} ${this.account.lastName}? This action cannot be undone.`;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  archive(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Info, 'Employee archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
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

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  onOutletLoaded(component: any): void {
    component.account = this.account;
  }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
