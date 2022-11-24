import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-find-user',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './find-user.component.html',
})
export class FindUserComponent {
  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  nextStep(): void {
    this.nextStepChange.emit();
  }
}
