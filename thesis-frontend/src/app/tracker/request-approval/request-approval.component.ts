import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { DatePickerComponent } from '../../shared/components/date-picker/date-picker.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import * as dayjs from 'dayjs';
import { ProjectApproval } from '../models/project-approval.model';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { RouterLinkWithHref } from '@angular/router';
import { ToastService } from '../../shared/services/toast.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { first, Subject } from 'rxjs';
import { ValidationService } from '../../shared/services/validation.service';
import { EmployeesService } from '../../admin/services/employees.service';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'bvr-request-approval',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    FormFieldComponent,
    FormsModule,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './request-approval.component.html',
})
export class RequestApprovalComponent implements OnInit {
  areAllSelected: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSendModalOpen: boolean = false;
  modalDescription: string = '';
  projectApprovals: ProjectApproval[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  requestApprovalForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService,
    private fb: FormBuilder,
    private location: Location,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getProjectsToApprove();
  }

  createForm(): void {
    this.requestApprovalForm = this.fb.group(
      {
        startDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        endDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
      },
      { validators: [this.dateRangeValidator()] }
    );
  }

  dateRangeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDate = control.get('startDate')?.value;
      const endDate = control.get('endDate')?.value;
      if (startDate && endDate) {
        const isRangeValid = dayjs(startDate).isAfter(dayjs(endDate));
        return !isRangeValid ? null : { dateRange: true };
      }
      return null;
    };
  }

  getProjectsToApprove(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeesService
        .getProjectsToApprove(employeeId)
        .pipe(first())
        .subscribe(
          projectApprovals => (this.projectApprovals = projectApprovals)
        );
    }
  }

  toggleProjectsSelection(value: boolean): void {
    this.projectApprovals.forEach(element => (element.approve = value));
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSendModal(): void {
    if (this.requestApprovalForm.valid) {
      this.isSendModalOpen = true;
      this.modalDescription =
        'Are you sure you want to send X tasks to approve?';
    } else {
      this.requestApprovalForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
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

  send(): void {
    this.disableGuard(true);
    new Promise((resolve, _) => {
      this.location.back();
      resolve('done');
    }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(
            ToastState.Success,
            'Approval request sent'
          ),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.requestApprovalForm, [name]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.requestApprovalForm, [name])
      : this.validationService.showErrors(this.requestApprovalForm, []);
  }
}
