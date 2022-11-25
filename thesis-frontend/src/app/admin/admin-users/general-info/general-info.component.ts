import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent {
  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  nextStep(): void {
    this.nextStepChange.emit();
  }
}
