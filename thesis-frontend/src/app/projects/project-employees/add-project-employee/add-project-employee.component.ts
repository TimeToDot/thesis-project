import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmEmployeeComponent } from '../confirm-employee/confirm-employee.component';
import { FindEmployeeComponent } from '../find-employee/find-employee.component';
import { SelectWageComponent } from '../select-wage/select-wage.component';

@Component({
  selector: 'bvr-add-project-employee',
  standalone: true,
  imports: [
    CommonModule,
    ConfirmEmployeeComponent,
    FindEmployeeComponent,
    SelectWageComponent,
  ],
  templateUrl: './add-project-employee.component.html',
})
export class AddProjectEmployeeComponent {
  step: number = 1;

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }
}
