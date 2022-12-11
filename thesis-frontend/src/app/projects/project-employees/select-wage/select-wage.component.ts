import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';

@Component({
  selector: 'bvr-select-wage',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
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
}
