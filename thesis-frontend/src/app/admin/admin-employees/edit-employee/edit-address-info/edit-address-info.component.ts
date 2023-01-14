import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { CountriesService } from '../../../services/countries.service';
import { first } from 'rxjs';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ErrorComponent } from '../../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-edit-address-info',
  standalone: true,
  imports: [
    CommonModule,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-address-info.component.html',
})
export class EditAddressInfoComponent implements OnInit {
  @Input() controls: any;
  @Input() editEmployeeForm!: FormGroup;

  countries: DropdownOption[] = [];

  constructor(private countriesService: CountriesService) {}

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

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
