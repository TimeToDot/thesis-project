import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastState } from '../../../../shared/enum/toast-state';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { ModalComponent } from '../../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { ErrorComponent } from '../../../../shared/components/error/error.component';
import { EmployeesService } from '../../../services/employees.service';
import { Account } from '../../../../shared/models/account.model';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-account-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    ErrorComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './account-info.component.html',
})
export class AccountInfoComponent {
  @Input() addEmployeeForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() disableGuard: EventEmitter<boolean> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private employeesService: EmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  openAddModal(): void {
    if (this.controls.accountInfo?.valid) {
      const firstName = this.controls.firstName?.value;
      const lastName = this.controls.lastName?.value;
      this.isAddModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${firstName} ${lastName}?`;
    } else {
      this.addEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  add(value: boolean): void {
    this.disableGuard.emit(true);
    if (value) {
      this.employeesService
        .addAccount(this.getEmployeeData())
        .pipe(first())
        .subscribe(employee => {
          this.employeesService
            .addEmployee(employee)
            .pipe(first())
            .subscribe(() => {
              this.redirectAfterAddition();
            });
        });
    }
  }

  getEmployeeData(): Account {
    return {
      id: '',
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
      image: '',
      active: true,
    };
  }

  redirectAfterAddition(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Employee added'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
