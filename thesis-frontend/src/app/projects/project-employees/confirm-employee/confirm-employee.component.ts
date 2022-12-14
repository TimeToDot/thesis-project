import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { Employee } from '../../../shared/model/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, FormsModule } from '@angular/forms';
import { EmployeesService } from '../../../admin/services/employees.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';

@Component({
  selector: 'bvr-confirm-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    FormsModule,
    ModalComponent,
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
    workingTime: 0,
    active: false,
  };
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private employeesService: EmployeesService,
    private location: Location
  ) {}

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

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
  }

  nextStep(): void {
    this.nextStepChange.emit();
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }
}
