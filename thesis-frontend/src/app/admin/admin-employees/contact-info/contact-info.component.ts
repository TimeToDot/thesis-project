import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalComponent } from '../../../shared/components/modal/modal.component';

@Component({
  selector: 'bvr-contact-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './contact-info.component.html',
})
export class ContactInfoComponent {
  @Input() createEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(private location: Location) {}

  nextStep(): void {
    this.nextStepChange.emit();
  }

  previousStep(): void {
    this.previousStepChange.emit();
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.createEmployeeForm
      .get(['contactInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
