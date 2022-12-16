import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { DatePickerComponent } from '../../shared/components/date-picker/date-picker.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import * as dayjs from 'dayjs';
import { ProjectApproval } from '../models/project-approval.model';
import { ProjectApprovalsService } from '../services/project-approvals.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { ToastService } from '../../shared/services/toast.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-request-approval',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterModule,
    ToastComponent,
  ],
  templateUrl: './request-approval.component.html',
})
export class RequestApprovalComponent implements OnInit {
  isCancelModalOpen: boolean = false;
  isSendModalOpen: boolean = false;
  modalDescription: string = '';
  projectApprovals: ProjectApproval[] = [];
  requestApprovalForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private projectApprovalsService: ProjectApprovalsService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getProjectApprovals();
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

  getProjectApprovals(): void {
    this.projectApprovalsService
      .getProjectApprovals()
      .pipe(first())
      .subscribe(
        projectApprovals => (this.projectApprovals = projectApprovals)
      );
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
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

  cancel(): void {
    this.location.back();
  }

  send(): void {
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

  isRequired(name: string): boolean {
    return this.requestApprovalForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  showErrors(name?: string): boolean {
    if (name) {
      return !!(
        this.requestApprovalForm.get(name)?.invalid &&
        this.requestApprovalForm.get(name)?.errors &&
        (this.requestApprovalForm.get(name)?.dirty ||
          this.requestApprovalForm.get(name)?.touched)
      );
    } else {
      return !!(
        this.requestApprovalForm.invalid &&
        this.requestApprovalForm.errors &&
        (this.requestApprovalForm.dirty || this.requestApprovalForm.touched)
      );
    }
  }
}
