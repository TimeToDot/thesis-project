import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-select-wage',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './select-wage.component.html',
})
export class SelectWageComponent {
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  previousStep(): void {
    this.previousStepChange.emit();
  }
}
