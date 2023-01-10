import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastState } from '../../../../shared/enum/toast-state';
import { CountriesService } from '../../../services/countries.service';
import { first } from 'rxjs';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ErrorComponent } from '../../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-address-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './address-info.component.html',
})
export class AddressInfoComponent implements OnInit {
  @Input() addEmployeeForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  countries: DropdownOption[] = [];

  constructor(
    private countriesService: CountriesService,
    private toastService: ToastService
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
    if (this.controls.addressInfo?.valid) {
      this.nextStepChange.emit();
    } else {
      this.controls.addressInfo?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(control: AbstractControl | null): boolean {
    return control && control?.hasValidator(Validators.required) ? true : false;
  }
}
