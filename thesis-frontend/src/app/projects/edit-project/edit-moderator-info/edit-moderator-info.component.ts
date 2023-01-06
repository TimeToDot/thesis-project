import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { EmployeesService } from '../../../admin/services/employees.service';
import { first } from 'rxjs';
import { Employee } from '../../../shared/models/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ValidationService } from '../../../shared/services/validation.service';
import { DropdownSearchEmployeeComponent } from '../../../shared/components/dropdown-search-employee/dropdown-search-employee.component';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-edit-moderator-info',
  standalone: true,
  imports: [
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-moderator-info.component.html',
})
export class EditModeratorInfoComponent implements OnInit {
  @Input() editProjectForm!: FormGroup;

  employee!: Account;
  employees: Employee[] = [];

  constructor(
    private employeesService: EmployeesService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    this.employeesService
      .getEmployees()
      .pipe(first())
      .subscribe(employees => {
        this.employees = employees.slice(0, 7);
        this.getEmployee();
        setTimeout(() => this.observeIdSelection(), 0);
      });
  }

  observeIdSelection(): void {
    this.editProjectForm.get(['moderator'])?.valueChanges.subscribe(() => {
      this.getEmployee();
    });
  }

  getEmployee(): void {
    const employeeId = this.editProjectForm.get(['moderator'])?.value;
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => (this.employee = employee));
    }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editProjectForm, [name]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.editProjectForm, [name]);
  }
}
