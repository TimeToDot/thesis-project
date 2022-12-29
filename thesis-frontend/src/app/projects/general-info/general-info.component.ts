import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent {
  @Input() addProjectForm!: FormGroup;
  @Input() enableFormButtons: boolean = false;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  nextStep(): void {
    // if (this.addEmployeeForm.get('personalInfo')?.valid) {
    this.nextStepChange.emit();
    // } else {
    //   this.addEmployeeForm.get('personalInfo')?.markAllAsTouched();
    //   this.toastService.showToast(ToastState.Error, 'Form invalid');
    //   setTimeout(() => this.toastService.dismissToast(), 3000);
    // }
  }
}
