import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PositionsService } from '../../services/positions.service';
import { Position } from '../../models/position.model';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent implements OnInit {
  @Input() createEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  positions: Position[] = [];

  constructor(private positionsService: PositionsService) {}

  ngOnInit(): void {
    this.positions = this.positionsService.getPositions();
  }

  nextStep(): void {
    this.nextStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.createEmployeeForm
      .get(['generalInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
