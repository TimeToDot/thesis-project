import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Position } from '../../../models/position.model';
import { ContractTypesService } from '../../../services/contract-types.service';
import { PositionsService } from '../../../services/positions.service';
import { first } from 'rxjs';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../../../shared/components/dropdown-list/dropdown-list.component';
import { DatePickerComponent } from '../../../../shared/components/date-picker/date-picker.component';
import { DropdownOption } from '../../../../shared/models/dropdown-option.model';
import { ErrorComponent } from '../../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-edit-employment-info',
  standalone: true,
  imports: [
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    InputNumberComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-employment-info.component.html',
})
export class EditEmploymentInfoComponent implements OnInit {
  @Input() controls: any;
  @Input() editEmployeeForm!: FormGroup;

  contractTypes: DropdownOption[] = [];
  positions: Position[] = [];

  constructor(
    private contractTypesService: ContractTypesService,
    private positionsService: PositionsService
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

  isRequired(control: AbstractControl | null): boolean {
    return control && control?.hasValidator(Validators.required) ? true : false;
  }
}
