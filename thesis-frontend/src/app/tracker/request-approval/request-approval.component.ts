import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
import { DatePickerComponent } from '../../shared/components/date-picker/date-picker.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Approval } from '../models/approval.model';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { RouterLinkWithHref } from '@angular/router';
import { ToastService } from '../../shared/services/toast.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { first, Subject } from 'rxjs';
import { ValidationService } from '../../shared/services/validation.service';
import { EmployeesService } from '../../admin/services/employees.service';
import { CustomValidators } from '../../shared/helpers/custom-validators.helper';
import { ErrorComponent } from '../../shared/components/error/error.component';
import { TokenService } from '../../shared/services/token.service';
import { ProjectsToApprove } from '../models/projects-to-approve.model';
import { EmployeeTasksService } from '../../shared/services/employee-tasks.service';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'bvr-request-approval',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    ErrorComponent,
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
  controls: any = {};
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSendModalOpen: boolean = false;
  modalDescription: string = '';
  projectApprovals: Approval[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  requestApprovalForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService,
    private employeeTasksService: EmployeeTasksService,
    private fb: FormBuilder,
    private location: Location,
    private toastService: ToastService,
    private tokenService: TokenService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getFormControls();
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
      {
        validators: [
          CustomValidators.dateRangeValidator('startDate', 'endDate'),
        ],
      }
    );
  }

  getFormControls(): void {
    Object.keys(this.requestApprovalForm.controls).forEach(group => {
      this.controls[group] = this.requestApprovalForm.get([group]);
    });
  }

  getProjectsToApprove(): void {
    this.employeesService
      .getProjectsToApprove(this.tokenService.getEmployee())
      .pipe(first())
      .subscribe(
        projectApprovals => (this.projectApprovals = projectApprovals)
      );
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
    const employeeId = this.authService.getLoggedEmployeeId();
    this.employeeTasksService
      .sendProjectsToApproval(employeeId, this.getProjectsData())
      .pipe(first())
      .subscribe(() => {
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
      });
  }

  getProjectsData(): ProjectsToApprove {
    return {
      startDate: this.controls.startDate.value,
      endDate: this.controls.endDate.value,
      projects: this.projectApprovals
        .filter(approval => approval.approve)
        .map(value => value.project.id),
    };
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
