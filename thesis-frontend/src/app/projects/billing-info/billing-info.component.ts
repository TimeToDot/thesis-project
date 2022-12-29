import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();
}
