import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { first } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { Account } from '../../../shared/models/account.model';
import { AccountsService } from '../../services/accounts.service';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-view-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './view-employee.component.html',
})
export class ViewEmployeeComponent {
  currentAccount: Account = {
    id: '',
    firstName: '',
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

  constructor(
    private accountsService: AccountsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getAccount();
  }

  getAccount(): void {
    const accountId = this.route.snapshot.paramMap.get('id');
    if (accountId) {
      this.accountsService
        .getAccount(accountId)
        .pipe(first())
        .subscribe(account => {
          this.currentAccount = account;
        });
    }
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.currentAccount.firstName} ${this.currentAccount.lastName}? This action cannot be undone.`;
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
}
