import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { PersonalInfoComponent } from './personal-info/personal-info.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AccountInfoComponent } from './account-info/account-info.component';
import { AddressInfoComponent } from './address-info/address-info.component';
import { EmploymentInfoComponent } from './employment-info/employment-info.component';
import { tabAnimation } from '../../../shared/animations/tab.animation';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { Subject } from 'rxjs';
import { Regex } from '../../../shared/helpers/regex.helper';

@Component({
  selector: 'bvr-add-employee',
  standalone: true,
  imports: [
    AccountInfoComponent,
    AddressInfoComponent,
    CommonModule,
    EmploymentInfoComponent,
    ModalComponent,
    PersonalInfoComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './add-employee.component.html',
  animations: [tabAnimation],
})
export class AddEmployeeComponent implements OnInit {
  addEmployeeForm!: FormGroup;
  enableFormButtons: boolean = true;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  step: number = 1;

  constructor(private fb: FormBuilder, private location: Location) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.addEmployeeForm = this.fb.group({
      personalInfo: this.fb.group({
        firstName: ['', [Validators.required, Validators.pattern(Regex.ALPHA)]],
        lastName: ['', [Validators.required, Validators.pattern(Regex.ALPHA)]],
        middleName: ['', [Validators.pattern(Regex.ALPHA)]],
        sex: ['', [Validators.required]],
        birthDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        birthPlace: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHA)],
        ],
        idCardNumber: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
        pesel: ['', [Validators.pattern(Regex.PESEL)]],
      }),
      addressInfo: this.fb.group({
        street: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
        houseNumber: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
        apartmentNumber: ['', [Validators.pattern(Regex.ALPHANUMERIC)]],
        city: ['', [Validators.required, Validators.pattern(Regex.ALPHA)]],
        postalCode: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
        country: ['', [Validators.required]],
        phoneNumber: [
          '',
          [Validators.required, Validators.pattern(Regex.PHONE)],
        ],
        privateEmail: [
          '',
          [Validators.required, Validators.pattern(Regex.EMAIL)],
        ],
      }),
      employmentInfo: this.fb.group({
        position: ['', [Validators.required]],
        employmentDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        contractType: ['', [Validators.required]],
        workingTime: ['', [Validators.required]],
        wage: ['', [Validators.required]],
        payday: ['', [Validators.required]],
        accountNumber: ['', [Validators.required]],
      }),
      accountInfo: this.fb.group({
        email: ['', [Validators.required]],
        password: ['', [Validators.required]],
        repeatPassword: ['', [Validators.required]],
      }),
    });
  }

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
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

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
