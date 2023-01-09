import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ValidationService } from '../../../../shared/services/validation.service';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { CountriesService } from '../../../services/countries.service';
import { first } from 'rxjs';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ErrorComponent } from '../../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-edit-contact-settings',
  standalone: true,
  imports: [
    CommonModule,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-contact-settings.component.html',
})
export class EditContactSettingsComponent implements OnInit {
  @Input() editGlobalSettingsForm!: FormGroup;

  countries: DropdownOption[] = [];

  constructor(
    private countriesService: CountriesService,
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

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editGlobalSettingsForm, [
      'contactInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editGlobalSettingsForm, [
          'contactInfo',
          name,
        ])
      : this.validationService.showErrors(this.editGlobalSettingsForm, []);
  }
}
