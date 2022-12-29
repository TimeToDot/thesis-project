import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ConfirmEmployeeComponent } from '../confirm-employee/confirm-employee.component';
import { FindEmployeeComponent } from '../find-employee/find-employee.component';
import { SelectWageComponent } from '../select-wage/select-wage.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { EmployeesService } from '../../../admin/services/employees.service';
import { ValidationService } from '../../../shared/services/validation.service';
import { first, Subject } from 'rxjs';
import { Employee } from '../../../shared/models/employee.model';
import { DropdownSearchEmployeeComponent } from '../dropdown-search-employee/dropdown-search-employee.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-add-project-employee',
  standalone: true,
  host: {
    class: 'add-new-employee',
  },
  imports: [
    ButtonComponent,
    CommonModule,
    ConfirmEmployeeComponent,
    DropdownSearchEmployeeComponent,
    FindEmployeeComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    SelectWageComponent,
    ToastComponent,
  ],
  templateUrl: './add-project-employee.component.html',
})
export class AddProjectEmployeeComponent implements OnInit {
  addProjectEmployeeForm!: FormGroup;
  employee!: Employee;
  employees: Employee[] = [];
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private employeesService: EmployeesService,
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getEmployees();
  }

  createForm(): void {
    this.addProjectEmployeeForm = this.fb.group({
      id: ['', [Validators.required]],
      workingTime: ['', [Validators.required]],
      salaryModifier: ['', [Validators.required]],
    });
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
    this.addProjectEmployeeForm.get(['id'])?.valueChanges.subscribe(() => {
      this.getEmployee();
    });
  }

  getEmployee(): void {
    const employeeId = this.addProjectEmployeeForm.get(['id'])?.value;
    this.employeesService
      .getEmployee(employeeId)
      .pipe(first())
      .subscribe(employee => (this.employee = employee));
  }

  openAddModal(): void {
    if (this.addProjectEmployeeForm.valid) {
      const employeeId = this.addProjectEmployeeForm.get(['id'])?.value;
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => {
          this.isAddModalOpen = true;
          this.modalDescription = `Are you sure you want to add ${employee.firstName} ${employee.lastName} to the Project X?`;
        });
    } else {
      this.addProjectEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  add(): void {
    this.disableGuard();
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Employee added'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  cancel(value: boolean): void {
    this.disableGuard();
    this.isFromGuard ? this.redirectSubject.next(value) : this.location.back();
  }

  disableGuard(): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(true);
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectEmployeeForm, [
      name,
    ]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.addProjectEmployeeForm, [
      name,
    ]);
  }
}
