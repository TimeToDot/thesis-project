import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ValidationService } from '../../../shared/services/validation.service';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { SwitchComponent } from '../../../shared/components/switch/switch.component';
import { first } from 'rxjs';
import { BillingPeriodsService } from '../../services/billing-periods.service';
import { DropdownOption } from '../../../shared/models/dropdown-option.model';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ErrorComponent } from '../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-edit-billing-info',
  standalone: true,
  imports: [
    CommonModule,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    InputNumberComponent,
    ReactiveFormsModule,
    SwitchComponent,
  ],
  templateUrl: './edit-billing-info.component.html',
})
export class EditBillingInfoComponent {
  @Input() editProjectForm!: FormGroup;

  billingPeriods: DropdownOption[] = [];

  constructor(
    private billingPeriodsService: BillingPeriodsService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getBillingPeriods();
  }

  getBillingPeriods(): void {
    this.billingPeriodsService
      .getBillingPeriods()
      .pipe(first())
      .subscribe(billingPeriods => (this.billingPeriods = billingPeriods));
  }

  enableField(name: string, value: boolean): void {
    value
      ? this.editProjectForm.get(['billingInfo', name])?.enable()
      : this.editProjectForm.get(['billingInfo', name])?.disable();
  }

  isDisabled(name: string): boolean {
    return !!this.editProjectForm.get(['billingInfo', name])?.disabled;
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editProjectForm, [
      'billingInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editProjectForm, [
          'billingInfo',
          name,
        ])
      : this.validationService.showErrors(this.editProjectForm, []);
  }
}
