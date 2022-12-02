import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent {
  @Input() createEmployeeForm!: FormGroup;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  previousStep(): void {
    this.previousStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.createEmployeeForm
      .get(['billingInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
