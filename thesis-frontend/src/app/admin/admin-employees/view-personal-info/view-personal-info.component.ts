import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-view-personal-info',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-personal-info.component.html',
})
export class ViewPersonalInfoComponent {
  @Input() account: Account = {
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
    country: { id: '', name: '' },
    accountNumber: '',
    active: true,
  };
}
