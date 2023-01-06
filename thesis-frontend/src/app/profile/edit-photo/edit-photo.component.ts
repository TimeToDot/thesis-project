import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { first, Subject } from 'rxjs';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { EmployeesService } from '../../admin/services/employees.service';
import { AuthService } from '../../shared/services/auth.service';
import { Account } from '../../shared/models/account.model';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { FileUploadComponent } from '../../shared/components/file-upload/file-upload.component';
import { ValidationService } from '../../shared/services/validation.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'bvr-edit-photo',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FileUploadComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-photo.component.html',
})
export class EditPhotoComponent {
  editPhotoForm!: FormGroup;
  employee!: Account;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService,
    private fb: FormBuilder,
    private location: Location,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getEmployee();
  }

  createForm(): void {
    this.editPhotoForm = this.fb.group({
      image: [null],
    });
  }

  getEmployee(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => {
          this.employee = employee;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.editPhotoForm.controls).forEach(field => {
      this.editPhotoForm
        .get(field)
        ?.setValue(this.employee[field as keyof Account]);
    });
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    this.isSaveModalOpen = true;
    this.modalDescription = `Are you sure you want to save changes?`;
  }

  cancel(value: boolean): void {
    if (this.isFromGuard) {
      this.redirectSubject.next(value);
    } else {
      this.disableGuard(value);
      if (value) {
        this.location.back();
      }
    }
  }

  save(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      new Promise((resolve, _) => {
        this.location.back();
        resolve('done');
      }).then(() => {
        setTimeout(
          () =>
            this.toastService.showToast(ToastState.Success, 'Photo updated'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
    }
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.editPhotoForm, [name])
      : this.validationService.showErrors(this.editPhotoForm, []);
  }
}
