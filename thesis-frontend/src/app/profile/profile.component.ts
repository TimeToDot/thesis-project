import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { first } from 'rxjs';
import { EmployeesService } from '../admin/services/employees.service';
import { ButtonComponent } from '../shared/components/button/button.component';
import { Account } from '../shared/models/account.model';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
})
export class ProfileComponent implements OnInit {
  currentEmployee: Account = {
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

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    this.employeesService
      .getEmployee('1')
      .pipe(first())
      .subscribe(employee => {
        console.log(employee);
        this.currentEmployee = employee;
      });
  }
}
