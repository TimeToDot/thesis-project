import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent {
  @Input() createEmployeeForm!: FormGroup;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  isCreateModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openCreateModal(): void {
    if (this.createEmployeeForm.get('billingInfo')?.valid) {
      const firstName = this.createEmployeeForm.get([
        'generalInfo',
        'firstName',
      ])?.value;
      const lastName = this.createEmployeeForm.get([
        'generalInfo',
        'lastName',
      ])?.value;
      this.isCreateModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${firstName} ${lastName}?`;
    } else {
      this.createEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  cancel(): void {
    this.location.back();
  }

  create(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Employee created'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.createEmployeeForm, [
      'billingInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.createEmployeeForm, [
          'billingInfo',
          name,
        ])
      : this.validationService.showErrors(this.createEmployeeForm, []);
  }
}
