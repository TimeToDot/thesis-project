import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent {
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  previousStep(): void {
    this.previousStepChange.emit();
  }
}
