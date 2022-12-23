import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormFieldComponent } from 'src/app/shared/components/form-field/form-field.component';
import { DropdownSearchEmployeeComponent } from '../dropdown-search-employee/dropdown-search-employee.component';
import { Employee } from '../../../shared/models/employee.model';
import { EmployeesService } from '../../../admin/services/employees.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { first } from 'rxjs';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-find-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
  ],
  templateUrl: './find-employee.component.html',
})
export class FindEmployeeComponent implements OnInit {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  employees: Employee[] = [];
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private employeesService: EmployeesService,
    private location: Location,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.employeesService
      .getEmployees()
      .pipe(first())
      .subscribe(employees => {
        this.employees = employees.slice(0, 7);
        setTimeout(() => this.observeIdSelection(), 0);
      });
  }

  observeIdSelection(): void {
    this.addProjectEmployeeForm
      .get(['userInfo', 'id'])
      ?.valueChanges.subscribe(() => {
        this.nextStepChange.emit();
      });
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectEmployeeForm, [
      'userInfo',
      name,
    ]);
  }
}
