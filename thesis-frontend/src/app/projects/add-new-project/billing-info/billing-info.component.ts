import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  AbstractControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { BillingPeriodsService } from '../../services/billing-periods.service';
import { first } from 'rxjs';
import { DropdownOption } from '../../../shared/models/dropdown-option.model';
import { SwitchComponent } from '../../../shared/components/switch/switch.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorComponent } from '../../../shared/components/error/error.component';
import { InputNumberComponent } from '../../../shared/components/input-number/input-number.component';
import { Project } from '../../models/project.model';
import { ProjectsService } from '../../../shared/services/projects.service';

@Component({
  selector: 'bvr-billing-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    ErrorComponent,
    FormFieldComponent,
    InputNumberComponent,
    ModalComponent,
    ReactiveFormsModule,
    SwitchComponent,
    ToastComponent,
  ],
  templateUrl: './billing-info.component.html',
})
export class BillingInfoComponent implements OnInit {
  @Input() addProjectForm!: FormGroup;
  @Input() controls: any;
  @Input() enableFormButtons: boolean = false;

  @Output() disableGuard: EventEmitter<boolean> = new EventEmitter();
  @Output() previousStepChange: EventEmitter<void> = new EventEmitter();
  @Output() openCancelModal: EventEmitter<boolean> = new EventEmitter();

  billingPeriods: DropdownOption[] = [];
  isAddModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private billingPeriodsService: BillingPeriodsService,
    private projectsService: ProjectsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getBillingPeriods();
  }

  getBillingPeriods(): void {
    this.billingPeriodsService
      .getBillingPeriods()
      .pipe(first())
      .subscribe(billingPeriods => (this.billingPeriods = billingPeriods));
  }

  openAddModal(): void {
    if (this.addProjectForm.valid) {
      const projectName = this.controls.name.value;
      this.isAddModalOpen = true;
      this.modalDescription = `Are you sure you want to add ${projectName} to the Projects List?`;
    } else {
      this.addProjectForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  add(value: boolean): void {
    this.disableGuard.emit(true);
    if (value) {
      this.projectsService
        .addProject(this.getProjectData())
        .pipe(first())
        .subscribe(() => {
          this.redirectAfterAddition();
        });
    }
  }

  getProjectData(): Project {
    return {
      id: '',
      name: this.controls.name?.value,
      image: this.controls.image?.value,
      description: this.controls.description?.value,
      moderator: this.controls.moderator?.value,
      employeesCount: this.controls.employeesCount?.value,
      creationDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
      billingPeriod: this.controls.billingPeriod?.value,
      overtimeModifier: this.controls.overtimeModifier?.value,
      bonusModifier: this.controls.bonusModifier?.value,
      nightModifier: this.controls.nightModifier?.value,
      holidayModifier: this.controls.holidayModifier?.value,
      active: true,
    };
  }

  redirectAfterAddition(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Project added'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  enableField(control: AbstractControl | null, value: boolean): void {
    value ? control?.enable() : control?.disable();
  }

  isRequired(control: AbstractControl): boolean {
    return control?.hasValidator(Validators.required) ? true : false;
  }
}
