import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';

@Component({
  selector: 'bvr-moderator-info',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './moderator-info.component.html',
})
export class ModeratorInfoComponent {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  nextStep(): void {
    // if (this.addEmployeeForm.get('addressInfo')?.valid) {
    this.nextStepChange.emit();
    // } else {
    //   this.addEmployeeForm.get('addressInfo')?.markAllAsTouched();
    //   this.toastService.showToast(ToastState.Error, 'Form invalid');
    //   setTimeout(() => this.toastService.dismissToast(), 3000);
    // }
  }
}
