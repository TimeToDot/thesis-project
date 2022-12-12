import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from 'src/app/shared/components/form-field/form-field.component';
import { DropdownSearchEmployeeComponent } from '../dropdown-search-employee/dropdown-search-employee.component';
import { Employee } from '../../../shared/model/employee.model';
import { EmployeesService } from '../../../admin/services/employees.service';

@Component({
  selector: 'bvr-find-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './find-employee.component.html',
})
export class FindEmployeeComponent implements OnInit {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  employees: Employee[] = [];

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    this.employees = this.employeesService.getEmployees().slice(0, 7);
    setTimeout(() => this.observeIdSelection(), 0);
  }

  observeIdSelection(): void {
    this.addProjectEmployeeForm
      .get(['userInfo', 'id'])
      ?.valueChanges.subscribe(() => {
        this.nextStepChange.emit();
      });
  }

  isRequired(name: string): boolean {
    return this.addProjectEmployeeForm
      .get(['userInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
