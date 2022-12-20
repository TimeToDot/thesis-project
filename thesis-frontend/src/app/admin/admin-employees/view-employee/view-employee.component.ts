import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesService } from '../../services/employees.service';
import { first } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Account } from '../../../shared/models/account.model';
import { AccountsService } from '../../services/accounts.service';

@Component({
  selector: 'bvr-view-employee',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './view-employee.component.html',
})
export class ViewEmployeeComponent {
  currentAccount: Account = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    positionId: '',
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

  constructor(
    private accountsService: AccountsService,
    private route: ActivatedRoute
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
        .subscribe(account => (this.currentAccount = account));
    }
  }
}
