import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { EmployeesService } from '../../../admin/services/employees.service';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { first, Subject } from 'rxjs';
import { ContractType } from '../../models/contract-type.model';
import { ValidationService } from '../../../shared/services/validation.service';

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
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './select-wage.component.html',
})
export class SelectWageComponent {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  contractTypes: ContractType[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private employeesService: EmployeesService,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  previousStep(): void {
    this.previousStepChange.emit();
  }

  openAddModal(): void {
    if (this.addProjectEmployeeForm.valid) {
      const employeeId = this.addProjectEmployeeForm.get([
        'userInfo',
        'id',
      ])?.value;
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

  cancel(value: boolean): void {
    this.disableGuard();
    this.isFromGuard ? this.redirectSubject.next(value) : this.location.back();
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

  disableGuard(): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(true);
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addProjectEmployeeForm, [
      'employmentInfo',
      name,
    ]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.addProjectEmployeeForm, [
      'employmentInfo',
      name,
    ]);
  }
}
