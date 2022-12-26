import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-add-position',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './add-position.component.html',
})
export class AddPositionComponent implements OnInit {
  addPositionForm!: FormGroup;
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.addPositionForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  openAddModal(): void {
    if (this.addPositionForm.valid) {
      const name = this.addPositionForm.get('name')?.value;
      this.isAddModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${name}?`;
    } else {
      this.addPositionForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  add(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Position created'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addPositionForm, [name]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addPositionForm, [name])
      : this.validationService.showErrors(this.addPositionForm, []);
  }
}
