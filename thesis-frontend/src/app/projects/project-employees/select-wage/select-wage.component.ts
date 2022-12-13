import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { EmployeesService } from '../../../admin/services/employees.service';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';

@Component({
  selector: 'bvr-select-wage',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './select-wage.component.html',
})
export class SelectWageComponent {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  contractTypes: { id: string; name: string }[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private employeesService: EmployeesService,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  previousStep(): void {
    this.previousStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.addProjectEmployeeForm
      .get(['employmentInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openAddModal(): void {
    this.isAddModalOpen = true;
    const employeeId = this.addProjectEmployeeForm.get([
      'userInfo',
      'id',
    ])?.value;
    const employee = this.employeesService.getEmployee(employeeId);
    this.modalDescription = `Are you sure you want to add ${employee.firstName} ${employee.lastName} to the Project X?`;
  }

  cancel(): void {
    new Promise((resolve, _) => {
      this.location.back();
      resolve('done');
    }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Error, 'Error message'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  confirm(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Employee created'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }
}
