import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DropdownSearchAccountComponent } from 'src/app/shared/components/dropdown-search-account/dropdown-search-account.component';
import { Account } from 'src/app/shared/model/account.model';
import { FormFieldComponent } from 'src/app/shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-find-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownSearchAccountComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './find-employee.component.html',
})
export class FindEmployeeComponent {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  employees: Account[] = [
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '1',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
  ];

  nextStep(): void {
    this.nextStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.addProjectEmployeeForm
      .get(['userInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
