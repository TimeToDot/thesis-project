import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../shared/components/dropdown-list/dropdown-list.component';
import { ValidationService } from '../../shared/services/validation.service';
import { BillingPeriodService } from '../services/billing-period.service';
import { first } from 'rxjs';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent implements OnInit {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  billingPeriods: DropdownOption[] = [];

  constructor(
    private billingPeriodService: BillingPeriodService,
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
