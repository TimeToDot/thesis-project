import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
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
  @Input() controls: any;
  @Input() editProjectForm!: FormGroup;

  billingPeriods: DropdownOption[] = [];

  constructor(private billingPeriodsService: BillingPeriodsService) {}

  ngOnInit(): void {
    this.getBillingPeriods();
  }

  getBillingPeriods(): void {
    this.billingPeriodsService
      .getBillingPeriods()
      .pipe(first())
      .subscribe(billingPeriods => (this.billingPeriods = billingPeriods));
  }

  enableField(control: AbstractControl | null, value: boolean): void {
    value ? control?.enable() : control?.disable();
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
