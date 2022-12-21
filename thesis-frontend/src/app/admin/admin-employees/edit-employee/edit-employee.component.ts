import { Component } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { Account } from '../../../shared/models/account.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountsService } from '../../services/accounts.service';
import { first } from 'rxjs';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';
import { PositionsService } from '../../services/positions.service';
import { Position } from '../../models/position.model';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-edit-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './edit-employee.component.html',
})
export class EditEmployeeComponent {
  account: Account = {
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
  editEmployeeForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  positions: Position[] = [];

  constructor(
    private accountsService: AccountsService,
    private fb: FormBuilder,
    private location: Location,
    private positionsService: PositionsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getAccount();
    this.getPositions();
    this.loadAccountToEdit();
  }

  createForm(): void {
    this.editEmployeeForm = this.fb.group({
      generalInfo: this.fb.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        email: ['', [Validators.required]],
        position: ['', [Validators.required]],
        employmentDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        workingTime: ['', [Validators.required]],
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

  getAccount(): void {
    const accountId = this.route.snapshot.paramMap.get('id');
    if (accountId) {
      this.accountsService
        .getAccount(accountId)
        .pipe(first())
        .subscribe(account => (this.account = account));
    }
  }

  getPositions(): void {
    this.positionsService
      .getPositions()
      .pipe(first())
      .subscribe(positions => (this.positions = positions));
  }

  loadAccountToEdit(): void {
    const accountId = this.route.snapshot.paramMap.get('id');
    if (accountId) {
      this.accountsService
        .getAccount(accountId)
        .pipe(first())
        .subscribe(account => {
          this.account = account;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.editEmployeeForm.controls).forEach(group => {
      Object.keys(
        (this.editEmployeeForm.get(group) as FormGroup<any>).controls
      ).forEach(field => {
        this.editEmployeeForm
          .get([group, field])
          ?.setValue(this.account[field as keyof Account]);
      });
    });
  }

  isRequired(group: string, name: string): boolean {
    return this.editEmployeeForm
      .get([group, name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }

  showErrors(group?: string, name?: string): boolean {
    if (name && group) {
      return !!(
        this.editEmployeeForm.get([group, name])?.invalid &&
        this.editEmployeeForm.get([group, name])?.errors &&
        (this.editEmployeeForm.get([group, name])?.dirty ||
          this.editEmployeeForm.get([group, name])?.touched)
      );
    } else {
      return !!(
        this.editEmployeeForm.invalid &&
        this.editEmployeeForm.errors &&
        (this.editEmployeeForm.dirty || this.editEmployeeForm.touched)
      );
    }
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.account.firstName} ${this.account.lastName}? This action cannot be undone.`;
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editEmployeeForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = 'Are you sure you want to save changes?';
    } else {
      this.editEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  archive(): void {
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Employee archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  cancel(): void {
    this.location.back();
  }

  save(): void {
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
