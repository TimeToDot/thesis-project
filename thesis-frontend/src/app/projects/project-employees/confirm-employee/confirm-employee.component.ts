import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { Employee } from '../../../shared/model/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'bvr-confirm-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './confirm-employee.component.html',
})
export class ConfirmEmployeeComponent {
  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  employee: Employee = {
    id: '6',
    email: 'jan.kowalski@gmail.com',
    firstName: 'Jan',
    image: 'assets/icons/icon13.png',
    lastName: 'Kowalski',
    position: 'Driver',
    employmentDate: '10/06/2022',
    active: true,
  };

  nextStep(): void {
    this.nextStepChange.emit();
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }
}
