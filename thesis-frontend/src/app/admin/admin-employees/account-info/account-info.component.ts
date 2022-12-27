import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../../../shared/services/toast.service';
import { ValidationService } from '../../../shared/services/validation.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-account-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './account-info.component.html',
})
export class AccountInfoComponent {
  @Input() addEmployeeForm!: FormGroup;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  isAddModalOpen: boolean = false;
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

  openAddModal(): void {
    if (this.addEmployeeForm.get('accountInfo')?.valid) {
      const firstName = this.addEmployeeForm.get([
        'personalInfo',
        'firstName',
      ])?.value;
      const lastName = this.addEmployeeForm.get([
        'personalInfo',
        'lastName',
      ])?.value;
      this.isAddModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${firstName} ${lastName}?`;
    } else {
      this.addEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  cancel(): void {
    this.location.back();
  }

  add(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Employee added'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'accountInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'accountInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
