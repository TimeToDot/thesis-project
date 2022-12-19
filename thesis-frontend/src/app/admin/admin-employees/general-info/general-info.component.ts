import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PositionsService } from '../../services/positions.service';
import { Position } from '../../models/position.model';
import { first } from 'rxjs';
import { ProjectsService } from '../../../shared/services/projects.service';
import { Project } from '../../../projects/models/project.model';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { DatePickerComponent } from '../../../shared/components/date-picker/date-picker.component';

@Component({
  selector: 'bvr-general-info',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './general-info.component.html',
})
export class GeneralInfoComponent implements OnInit {
  @Input() createEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  positions: Position[] = [];
  projects: Project[] = [];

  constructor(
    private positionsService: PositionsService,
    private projectsService: ProjectsService
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
    this.nextStepChange.emit();
  }

  isRequired(name: string): boolean {
    return this.createEmployeeForm
      .get(['generalInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
