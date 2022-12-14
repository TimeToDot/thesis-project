import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
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

@Component({
  selector: 'bvr-request-approval',
  standalone: true,
  imports: [
    CommonModule,
    DatePickerComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './request-approval.component.html',
})
export class RequestApprovalComponent implements OnInit {
  projectApprovals: ProjectApproval[] = [];
  requestApprovalForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private projectApprovalsService: ProjectApprovalsService
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
    this.projectApprovals = this.projectApprovalsService.getProjectApprovals();
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
