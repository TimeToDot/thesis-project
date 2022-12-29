import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ToastService } from '../../../shared/services/toast.service';
import { ValidationService } from '../../../shared/services/validation.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { PositionsService } from '../../services/positions.service';
import { first } from 'rxjs';
import { Position } from '../../models/position.model';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';
import { ContractTypesService } from '../../services/contract-types.service';
import { ContractType } from '../../../projects/models/contract-type.model';

@Component({
  selector: 'bvr-employment-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './employment-info.component.html',
})
export class EmploymentInfoComponent implements OnInit {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  contractTypes: ContractType[] = [];
  positions: Position[] = [];

  constructor(
    private contractTypesService: ContractTypesService,
    private positionsService: PositionsService,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getPositions();
    this.getContractTypes();
  }

  getPositions(): void {
    this.positionsService
      .getPositions()
      .pipe(first())
      .subscribe(positions => (this.positions = positions));
  }

  getContractTypes(): void {
    this.contractTypesService
      .getContractTypes()
      .pipe(first())
      .subscribe(contractTypes => (this.contractTypes = contractTypes));
  }

  nextStep(): void {
    if (this.addEmployeeForm.get('employmentInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addEmployeeForm.get('employmentInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'employmentInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'employmentInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
