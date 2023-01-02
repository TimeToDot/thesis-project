import { Component } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { Account } from '../../../shared/models/account.model';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
} from '@angular/router';
import { AccountsService } from '../../services/accounts.service';
import { first, Subject } from 'rxjs';
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
import { TabsComponent } from '../../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../../shared/models/link-option.model';
import { EditPersonalInfoComponent } from './edit-personal-info/edit-personal-info.component';
import { EditAddressInfoComponent } from './edit-address-info/edit-address-info.component';
import { EditEmploymentInfoComponent } from './edit-employment-info/edit-employment-info.component';
import { EditAccountInfoComponent } from './edit-account-info/edit-account-info.component';
import { tabAnimation } from '../../../shared/animations/tab.animation';

@Component({
  selector: 'bvr-edit-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    EditPersonalInfoComponent,
    EditAddressInfoComponent,
    EditEmploymentInfoComponent,
    EditAccountInfoComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    TabsComponent,
    ToastComponent,
  ],
  templateUrl: './edit-employee.component.html',
  animations: [tabAnimation],
})
export class EditEmployeeComponent {
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
  editEmployeeForm!: FormGroup;
  enableFormButtons: boolean = true;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];
  positions: Position[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  tabIndex: number = 0;

  constructor(
    private accountsService: AccountsService,
    private contexts: ChildrenOutletContexts,
    private fb: FormBuilder,
    private location: Location,
    private positionsService: PositionsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getCurrentTab();
    this.createForm();
    this.getNavbarOptions();
    this.getAccount();
    this.getPositions();
  }

  getCurrentTab(): void {
    this.tabIndex =
      this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }

  createForm(): void {
    this.editEmployeeForm = this.fb.group({
      personalInfo: this.fb.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        middleName: ['', []],
        sex: ['', [Validators.required]],
        birthDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        birthPlace: ['', [Validators.required]],
        idCardNumber: ['', [Validators.required]],
        pesel: ['', []],
      }),
      addressInfo: this.fb.group({
        street: ['', [Validators.required]],
        houseNumber: ['', [Validators.required]],
        apartmentNumber: ['', []],
        city: ['', [Validators.required]],
        postalCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
        phoneNumber: ['', [Validators.required]],
        privateEmail: ['', [Validators.required]],
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
      }),
    });
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'Personal', path: 'personal-info' });
    this.navbarOptions.push({ name: 'Address', path: 'address-info' });
    this.navbarOptions.push({ name: 'Employment', path: 'employment-info' });
    this.navbarOptions.push({ name: 'Account', path: 'account-info' });
  }

  getAccount(): void {
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

  getPositions(): void {
    this.positionsService
      .getPositions()
      .pipe(first())
      .subscribe(positions => (this.positions = positions));
  }

  updateTabIndex(index: number): void {
    this.tabIndex = index;
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

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.account.firstName} ${this.account.lastName}? This action cannot be undone.`;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
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
    this.disableGuard(true);
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
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

  save(value: boolean): void {
    this.disableGuard(true);
    if (value) {
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

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
