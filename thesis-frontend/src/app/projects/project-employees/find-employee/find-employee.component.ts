import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-find-employee',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './find-employee.component.html',
})
export class FindEmployeeComponent {
  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  nextStep(): void {
    this.nextStepChange.emit();
  }
}
