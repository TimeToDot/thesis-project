import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastService } from '../../../../shared/services/toast.service';
import { ToastState } from '../../../../shared/enum/toast-state';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { ToastComponent } from '../../../../shared/components/toast/toast.component';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { PositionsService } from '../../../services/positions.service';
import { first } from 'rxjs';
import { Position } from '../../../models/position.model';
import { DatePickerComponent } from '../../../../shared/components/date-picker/date-picker.component';
import { ContractTypesService } from '../../../services/contract-types.service';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ErrorComponent } from '../../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-employment-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    InputNumberComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './employment-info.component.html',
})
export class EmploymentInfoComponent implements OnInit {
  @Input() addEmployeeForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  contractTypes: DropdownOption[] = [];
  positions: Position[] = [];

  constructor(
    private contractTypesService: ContractTypesService,
    private positionsService: PositionsService,
    private toastService: ToastService
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
    if (this.controls.employmentInfo?.valid) {
      this.nextStepChange.emit();
    } else {
      this.controls.employmentInfo?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  isRequired(control: AbstractControl | null): boolean {
    return control && control?.hasValidator(Validators.required) ? true : false;
  }
}
