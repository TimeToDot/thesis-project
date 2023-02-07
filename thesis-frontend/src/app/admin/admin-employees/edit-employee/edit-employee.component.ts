import { Component } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { Account } from '../../../shared/models/account.model';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
} from '@angular/router';
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
import { EmployeesService } from '../../services/employees.service';
import { Regex } from '../../../shared/helpers/regex.helper';
import { CustomValidators } from '../../../shared/helpers/custom-validators.helper';

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
  controls: any = {};
  employee!: Account;
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
    private contexts: ChildrenOutletContexts,
    private employeesService: EmployeesService,
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getCurrentTab();
    this.createForm();
    this.getFormControls();
    this.getNavbarOptions();
    this.getEmployee();
  }

  getCurrentTab(): void {
    this.tabIndex =
      this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }

  createForm(): void {
    this.editEmployeeForm = this.fb.group({
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
      accountInfo: this.fb.group({
        email: ['', [Validators.required, Validators.pattern(Regex.EMAIL)]],
      }),
    });
  }

  getFormControls(): void {
    Object.keys(this.editEmployeeForm.controls).forEach(group => {
      this.controls[group] = this.editEmployeeForm.get([group]);
      Object.keys(
        (this.editEmployeeForm.get(group) as FormGroup<any>).controls
      ).forEach(field => {
        this.controls[field] = this.editEmployeeForm.get([group, field]);
      });
    });
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'Personal', path: 'personal-info' });
    this.navbarOptions.push({ name: 'Address', path: 'address-info' });
    this.navbarOptions.push({ name: 'Employment', path: 'employment-info' });
    this.navbarOptions.push({ name: 'Account', path: 'account-info' });
  }

  getEmployee(): void {
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => {
          this.employee = employee;
          this.updateFormFields();
        });
    }
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
          ?.setValue(this.employee[field as keyof Account]);
      });
    });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.employee.firstName} ${this.employee.lastName}? This action cannot be undone.`;
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

  archive(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      this.employeesService
        .archiveAccount(this.getEmployeeData())
        .pipe(first())
        .subscribe(employee => {
          this.employeesService
            .archiveEmployee(employee)
            .pipe(first())
            .subscribe(() => {
              this.router
                .navigate(['../..'], { relativeTo: this.route })
                .then(() => {
                  setTimeout(
                    () =>
                      this.toastService.showToast(
                        ToastState.Info,
                        'Employee archived'
                      ),
                    200
                  );
                  setTimeout(() => this.toastService.dismissToast(), 3200);
                });
            });
        });
    }
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
      this.employeesService
        .updateAccount(this.getEmployeeData())
        .pipe(first())
        .subscribe(employee => {
          this.employeesService
            .updateEmployee(employee)
            .pipe(first())
            .subscribe(() => {
              this.redirectAfterSave();
            });
        });
    }
  }

  getEmployeeData(): Account {
    return {
      id: this.employee.id,
      firstName: this.controls.firstName?.value,
      middleName: this.controls.middleName?.value,
      lastName: this.controls.lastName?.value,
      sex: this.controls.sex?.value,
      birthDate: this.controls.birthDate?.value,
      birthPlace: this.controls.birthPlace?.value,
      idCardNumber: this.controls.idCardNumber?.value,
      pesel: this.controls.pesel?.value,
      street: this.controls.street?.value,
      houseNumber: this.controls.houseNumber?.value,
      apartmentNumber: this.controls.apartmentNumber?.value,
      city: this.controls.city?.value,
      postalCode: this.controls.postalCode?.value,
      country: this.controls.country?.value,
      phoneNumber: this.controls.phoneNumber?.value,
      privateEmail: this.controls.privateEmail?.value,
      position: this.controls.position?.value,
      employmentDate: this.controls.employmentDate?.value,
      contractType: this.controls.contractType?.value,
      workingTime: this.controls.workingTime?.value,
      wage: this.controls.wage?.value,
      payday: this.controls.payday?.value,
      accountNumber: this.controls.accountNumber?.value,
      email: this.controls.email?.value,
      password: this.controls.password?.value,
      image: this.employee.image,
      active: this.employee.active,
    };
  }

  redirectAfterSave(): void {
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

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
