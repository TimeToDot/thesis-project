import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { PersonalInfoComponent } from './personal-info/personal-info.component';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { AccountInfoComponent } from './account-info/account-info.component';
import { AddressInfoComponent } from './address-info/address-info.component';
import { EmploymentInfoComponent } from './employment-info/employment-info.component';
import { tabAnimation } from '../../../shared/animations/tab.animation';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { Subject } from 'rxjs';
import { Regex } from '../../../shared/helpers/regex.helper';
import { CustomValidators } from '../../../shared/helpers/custom-validators.helper';

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
  controls: any = {};
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
    this.getFormControls();
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
        pesel: [
          '',
          [Validators.pattern(Regex.NUMERIC), Validators.minLength(11)],
        ],
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
        workingTime: [
          '',
          [
            Validators.required,
            CustomValidators.minValue(0),
            CustomValidators.maxValue(168),
          ],
        ],
        wage: ['', [Validators.required, CustomValidators.minValue(0)]],
        payday: [
          '',
          [
            Validators.required,
            CustomValidators.minValue(1),
            CustomValidators.maxValue(31),
          ],
        ],
        accountNumber: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
      }),
      accountInfo: this.fb.group(
        {
          email: ['', [Validators.required, Validators.pattern(Regex.EMAIL)]],
          password: ['', [Validators.required, CustomValidators.password()]],
          repeatPassword: [
            '',
            [Validators.required, CustomValidators.password()],
          ],
        },
        {
          validators: [
            CustomValidators.passwordMatchingValidator(
              'password',
              'repeatPassword'
            ),
          ],
        }
      ),
    });
  }

  getFormControls(): void {
    Object.keys(this.addEmployeeForm.controls).forEach(group => {
      this.controls[group] = this.addEmployeeForm.get([group]);
      Object.keys(
        (this.addEmployeeForm.get(group) as FormGroup<any>).controls
      ).forEach(field => {
        this.controls[field] = this.addEmployeeForm.get([group, field]);
      });
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
