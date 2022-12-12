import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { Employee } from '../../../shared/model/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, FormsModule } from '@angular/forms';
import { EmployeesService } from '../../../admin/services/employees.service';

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
export class ConfirmEmployeeComponent implements OnInit {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  employee: Employee = {
    id: '',
    email: '',
    firstName: '',
    image: '',
    lastName: '',
    position: '',
    employmentDate: '',
    active: false,
  };

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    setTimeout(() => this.getEmployee(), 0);
  }

  getEmployee(): void {
    const employeeId = this.addProjectEmployeeForm.get([
      'userInfo',
      'id',
    ])?.value;
    this.employee = this.employeesService.getEmployee(employeeId);
  }

  nextStep(): void {
    this.nextStepChange.emit();
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }
}
