import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { first } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import {
  ActivatedRoute,
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
    sex: '',
    birthPlace: '',
    idCardNumber: '',
    pesel: 0,
    contractType: { id: '', name: '' },
    rate: 0,
    payday: 0,
    birthDate: '',
    phoneNumber: '',
    privateEmail: '',
    street: '',
    houseNumber: '',
    apartmentNumber: '',
    city: '',
    postalCode: '',
    country: '',
    accountNumber: '',
    active: true,
  };
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];

  constructor(
    private accountsService: AccountsService,
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

  archive(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Employee archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  onOutletLoaded(component: any): void {
    component.account = this.account;
  }
}
