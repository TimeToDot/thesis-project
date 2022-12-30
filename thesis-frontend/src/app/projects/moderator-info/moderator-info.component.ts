import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { DropdownSearchEmployeeComponent } from '../project-employees/dropdown-search-employee/dropdown-search-employee.component';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';
import { Employee } from '../../shared/models/employee.model';
import { ValidationService } from '../../shared/services/validation.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ToastService } from '../../shared/services/toast.service';
import { ToastComponent } from '../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-moderator-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './moderator-info.component.html',
})
export class ModeratorInfoComponent implements OnInit {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  employee!: Employee;
  employees: Employee[] = [];

  constructor(
    private employeesService: EmployeesService,
    private toastService: ToastService,
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
        setTimeout(() => this.observeIdSelection(), 0);
      });
  }

  observeIdSelection(): void {
    this.addProjectForm.get(['moderator'])?.valueChanges.subscribe(() => {
      this.getEmployee();
    });
  }

  getEmployee(): void {
    const employeeId = this.addProjectForm.get(['moderator'])?.value;
    this.employeesService
      .getEmployee(employeeId)
      .pipe(first())
      .subscribe(employee => (this.employee = employee));
  }

  nextStep(): void {
    // if (this.addProjectForm.get('moderator')?.valid) {
    this.nextStepChange.emit();
    // } else {
    //   this.addProjectForm.get('moderator')?.markAllAsTouched();
    //   this.toastService.showToast(ToastState.Error, 'Form invalid');
    //   setTimeout(() => this.toastService.dismissToast(), 3000);
    // }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectForm, [name]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.addProjectForm, [name]);
  }
}
