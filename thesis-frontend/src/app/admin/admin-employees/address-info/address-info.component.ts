import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ValidationService } from '../../../shared/services/validation.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { CountriesService } from '../../services/countries.service';
import { first } from 'rxjs';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../shared/models/dropdown-option.model';

@Component({
  selector: 'bvr-address-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './address-info.component.html',
})
export class AddressInfoComponent implements OnInit {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  countries: DropdownOption[] = [];

  constructor(
    private countriesService: CountriesService,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getCountries();
  }

  getCountries(): void {
    this.countriesService
      .getAllCountries()
      .pipe(first())
      .subscribe(countries => {
        this.countries = countries;
      });
  }

  nextStep(): void {
    if (this.addEmployeeForm.get('addressInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addEmployeeForm.get('addressInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'addressInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'addressInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
