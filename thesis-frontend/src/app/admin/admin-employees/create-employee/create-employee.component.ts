import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { BillingInfoComponent } from '../billing-info/billing-info.component';
import { ContactInfoComponent } from '../contact-info/contact-info.component';
import { GeneralInfoComponent } from '../general-info/general-info.component';
import { PersonalInfoComponent } from '../personal-info/personal-info.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'bvr-create-employee',
  standalone: true,
  imports: [
    BillingInfoComponent,
    CommonModule,
    ContactInfoComponent,
    GeneralInfoComponent,
    PersonalInfoComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './create-employee.component.html',
})
export class CreateEmployeeComponent implements OnInit {
  createEmployeeForm!: FormGroup;
  step: number = 1;
  title: string[] = [
    'General Info',
    'Personal Info',
    'Contact Info',
    'Billing Info',
  ];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.createEmployeeForm = this.fb.group({
      generalInfo: this.fb.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        email: ['', [Validators.required]],
        password: ['', [Validators.required]],
        repeatPassword: ['', [Validators.required]],
        position: ['', [Validators.required]],
        workingTime: ['', [Validators.required]],
        employmentDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
      }),
      personalInfo: this.fb.group({
        birthDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
      }),
      contactInfo: this.fb.group({
        phoneNumber: ['', [Validators.required]],
        privateEmail: ['', [Validators.required]],
        street: ['', [Validators.required]],
        houseNumber: ['', [Validators.required]],
        apartmentNumber: ['', [Validators.required]],
        city: ['', [Validators.required]],
        postalCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      billingInfo: this.fb.group({
        accountNumber: ['', [Validators.required]],
      }),
    });
  }

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }
}
