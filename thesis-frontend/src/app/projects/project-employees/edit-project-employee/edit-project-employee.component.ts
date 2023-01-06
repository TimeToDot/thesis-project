import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ProjectEmployee } from '../../models/project-employee.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { first, Subject } from 'rxjs';
import { ValidationService } from '../../../shared/services/validation.service';
import { InputNumberComponent } from '../../../shared/components/input-number/input-number.component';

@Component({
  selector: 'bvr-edit-project-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    FormsModule,
    InputNumberComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './edit-project-employee.component.html',
})
export class EditProjectEmployeeComponent implements OnInit {
  contractTypes: { id: string; name: string }[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];
  editProjectEmployeeForm!: FormGroup;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  projectEmployee!: ProjectEmployee;
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private projectEmployeesService: ProjectEmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getProjectEmployee();
  }

  createForm(): void {
    this.editProjectEmployeeForm = this.fb.group({
      workingTime: ['', [Validators.required]],
    });
  }

  getProjectEmployee(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    const projectEmployeeId = this.route.snapshot.paramMap.get('id');
    if (projectId && projectEmployeeId) {
      this.projectEmployeesService
        .getProjectEmployee(projectId, projectEmployeeId)
        .pipe(first())
        .subscribe(projectEmployee => {
          this.projectEmployee = projectEmployee;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.editProjectEmployeeForm.controls).forEach(field => {
      this.editProjectEmployeeForm
        .get(field)
        ?.setValue(this.projectEmployee[field as keyof ProjectEmployee]);
    });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.projectEmployee.employee.firstName} ${this.projectEmployee.employee.lastName}? This action cannot be undone.`;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editProjectEmployeeForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = 'Are you sure you want to save changes?';
    } else {
      this.editProjectEmployeeForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  archive(): void {
    this.disableGuard(true);
    this.router.navigate(['../..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Info, 'Employee archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
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
            this.toastService.showToast(ToastState.Success, 'Employee edited'),
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

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.editProjectEmployeeForm, [
      name,
    ]);
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.editProjectEmployeeForm, [
      name,
    ]);
  }
}
