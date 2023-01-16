import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { EmployeesService } from '../../../admin/services/employees.service';
import { first } from 'rxjs';
import { Employee } from '../../../shared/models/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DropdownSearchEmployeeComponent } from '../../../shared/components/dropdown-search-employee/dropdown-search-employee.component';
import { Account } from '../../../shared/models/account.model';
import { ErrorComponent } from '../../../shared/components/error/error.component';

@Component({
  selector: 'bvr-edit-moderator-info',
  standalone: true,
  imports: [
    CommonModule,
    DropdownSearchEmployeeComponent,
    ErrorComponent,
    FormFieldComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-moderator-info.component.html',
})
export class EditModeratorInfoComponent implements OnInit {
  @Input() controls: any;
  @Input() editProjectForm!: FormGroup;

  employee!: Account;
  employees: Employee[] = [];

  constructor(private employeesService: EmployeesService) {}

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
    this.controls.moderator?.valueChanges.subscribe(() => {
      this.getEmployee();
    });
  }

  getEmployee(): void {
    const employeeId = this.controls.moderator?.value.id;
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => (this.employee = employee));
    }
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
