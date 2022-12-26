import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PositionsService } from '../../services/positions.service';
import { Position } from '../../models/position.model';
import { first } from 'rxjs';
import { ProjectsService } from '../../../shared/services/projects.service';
import { Project } from '../../../projects/models/project.model';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ValidationService } from '../../../shared/services/validation.service';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    ToastComponent,
  ],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent implements OnInit {
  @Input() addEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  isCancelModalOpen: boolean = false;
  modalDescription: string = '';
  positions: Position[] = [];
  projects: Project[] = [];

  constructor(
    private location: Location,
    private positionsService: PositionsService,
    private projectsService: ProjectsService,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.getPositions();
    this.getProjects();
  }

  getPositions(): void {
    this.positionsService
      .getPositions()
      .pipe(first())
      .subscribe(positions => (this.positions = positions));
  }

  getProjects(): void {
    this.projectsService
      .getProjects()
      .pipe(first())
      .subscribe(projects => (this.projects = projects));
  }

  nextStep(): void {
    if (this.addEmployeeForm.get('generalInfo')?.valid) {
      this.nextStepChange.emit();
    } else {
      this.addEmployeeForm.get('generalInfo')?.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(): void {
    this.isCancelModalOpen = true;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.location.back();
  }

  isRequired(name: string): boolean {
    return this.validationService.isRequired(this.addEmployeeForm, [
      'generalInfo',
      name,
    ]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addEmployeeForm, [
          'generalInfo',
          name,
        ])
      : this.validationService.showErrors(this.addEmployeeForm, []);
  }
}
