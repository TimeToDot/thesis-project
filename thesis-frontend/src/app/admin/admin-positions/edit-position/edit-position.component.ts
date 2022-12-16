import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';

@Component({
  selector: 'bvr-edit-position',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterModule,
    ToastComponent,
  ],
  templateUrl: './edit-position.component.html',
})
export class EditPositionComponent implements OnInit {
  editPositionForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.editPositionForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    const positionName = this.editPositionForm.get(['name'])?.value;
    this.modalDescription = `Are you sure you want to archive position ${positionName}? This action cannot be undone.`;
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editPositionForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = `Are you sure you want to save changes?`;
    } else {
      this.editPositionForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  cancel(): void {
    this.location.back();
  }

  save(): void {
    console.log('saveum');
  }

  archive(): void {
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Position archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }
}
