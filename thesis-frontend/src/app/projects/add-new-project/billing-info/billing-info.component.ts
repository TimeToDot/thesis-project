import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ValidationService } from '../../../shared/services/validation.service';
import { BillingPeriodService } from '../../services/billing-period.service';
import { first } from 'rxjs';
import { DropdownOption } from '../../../shared/models/dropdown-option.model';
import { SwitchComponent } from '../../../shared/components/switch/switch.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    SwitchComponent,
    ToastComponent,
  ],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent implements OnInit {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() disableGuard: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  billingPeriods: DropdownOption[] = [];
  isAddModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private billingPeriodService: BillingPeriodService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getBillingPeriods();
  }

  getBillingPeriods(): void {
    this.billingPeriodService
      .getBillingPeriods()
      .pipe(first())
      .subscribe(billingPeriods => (this.billingPeriods = billingPeriods));
  }

  openAddModal(): void {
    if (this.addProjectForm.valid) {
      const projectName = this.addProjectForm.get([
        'generalInfo',
        'projectName',
      ])?.value;
      this.isAddModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${projectName} to the Projects List?`;
    } else {
      this.addProjectForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  add(value: boolean): void {
    this.disableGuard.emit(true);
    if (value) {
      this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
        setTimeout(
          () =>
            this.toastService.showToast(ToastState.Success, 'Project added'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
    }
  }

  enableField(name: string, value: boolean): void {
    value
      ? this.addProjectForm.get(['billingInfo', name])?.enable()
      : this.addProjectForm.get(['billingInfo', name])?.disable();
  }

  isDisabled(name: string): boolean {
    return !!this.addProjectForm.get(['billingInfo', name])?.disabled;
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectForm, [
      'billingInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addProjectForm, [
          'billingInfo',
          name,
        ])
      : this.validationService.showErrors(this.addProjectForm, []);
  }
}
