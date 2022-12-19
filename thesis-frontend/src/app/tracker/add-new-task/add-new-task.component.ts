import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { ProjectsService } from '../../shared/services/projects.service';
import { ProjectTasksService } from '../../projects/services/project-tasks.service';
import { DatePickerComponent } from '../../shared/components/date-picker/date-picker.component';
import { TimePickerComponent } from '../../shared/components/time-picker/time-picker.component';
import { DropdownListComponent } from '../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../shared/models/dropdown-option.model';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastService } from '../../shared/services/toast.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { EmployeeTasksService } from '../../shared/services/employee-tasks.service';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-add-new-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    TimePickerComponent,
    ToastComponent,
  ],
  templateUrl: './add-new-task.component.html',
})
export class AddNewTaskComponent implements OnInit {
  addTaskForm!: FormGroup;
  hasTaskToSave: boolean = false;
  isAddModalOpen: boolean = false;
  isDeleteModalOpen: boolean = false;
  isResetModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  projects: DropdownOption[] = [];
  tasks: DropdownOption[] = [];

  constructor(
    private fb: FormBuilder,
    private projectTasksService: ProjectTasksService,
    private employeeProjectService: ProjectsService,
    private employeeTasksService: EmployeeTasksService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getEmployeeProjects();
    this.createForm();
    this.observeProjectChange();
    this.loadTaskToEdit();
  }

  getEmployeeProjects(): void {
    this.employeeProjectService
      .getEmployeeProjects()
      .pipe(first())
      .subscribe(employeeProjects => (this.projects = employeeProjects));
  }

  roundToMinutes(minutes: number): string {
    const ms = 1000 * 60 * minutes;
    return formatDate(
      new Date(Math.round(new Date().getTime() / ms) * ms),
      'HH:mm',
      'en'
    );
  }

  createForm(): void {
    this.addTaskForm = this.fb.group(
      {
        startDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        endDate: [
          formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
          [Validators.required],
        ],
        startTime: [this.roundToMinutes(15), [Validators.required]],
        endTime: [this.roundToMinutes(15), [Validators.required]],
        project: [null, [Validators.required]],
        task: [{ value: null, disabled: true }, [Validators.required]],
      },
      { validators: [this.dateRangeValidator(), this.timeRangeValidator()] }
    );
  }

  dateRangeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDate = control.get('startDate')?.value;
      const endDate = control.get('endDate')?.value;
      if (startDate && endDate) {
        const isRangeValid = dayjs(startDate).isAfter(dayjs(endDate));
        return !isRangeValid ? null : { dateRange: true };
      }
      return null;
    };
  }

  timeRangeValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDate = control.get('startDate')?.value;
      const startTime = control.get('startTime')?.value;
      const endDate = control.get('endDate')?.value;
      const endTime = control.get('endTime')?.value;
      if (startDate && startTime && endDate && endTime) {
        const isRangeValid = dayjs(`${startDate} ${startTime}`).isAfter(
          dayjs(`${endDate} ${endTime}`)
        );
        return !isRangeValid ? null : { timeRange: true };
      }
      return null;
    };
  }

  observeProjectChange(): void {
    this.addTaskForm.get('project')?.valueChanges.subscribe(project => {
      this.projectTasksService
        .getProjectTasks(project.id)
        .pipe(first())
        .subscribe(projectTasks => (this.tasks = projectTasks));
      this.addTaskForm.get('task')?.enable();
      this.addTaskForm.get('task')?.setValue('');
    });
  }

  loadTaskToEdit(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.employeeTasksService
        .getEmployeeTask(taskId)
        .pipe(first())
        .subscribe(employeeTask => {
          const task = employeeTask;
          this.addTaskForm.get('startDate')?.setValue(task.startDate);
          this.addTaskForm.get('startTime')?.setValue(task.startTime);
          this.addTaskForm.get('endDate')?.setValue(task.endDate);
          this.addTaskForm.get('endTime')?.setValue(task.endTime);
          this.addTaskForm.get('project')?.setValue(task.project);
          this.addTaskForm.get('task')?.setValue(task.task);
          this.hasTaskToSave = true;
        });
    }
  }

  openAddModal(): void {
    if (this.addTaskForm.valid) {
      this.isAddModalOpen = true;
      const task = this.addTaskForm.get(['task'])?.value;
      this.modalDescription = `Do you want to add ${task.name} to your timesheet?`;
    } else {
      this.addTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openDeleteModal(): void {
    this.isDeleteModalOpen = true;
    const task = this.addTaskForm.get(['task'])?.value;
    this.modalDescription = `Are you sure you want to delete ${task.name}? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.addTaskForm.valid) {
      this.isSaveModalOpen = true;
      const task = this.addTaskForm.get(['task'])?.value;
      this.modalDescription = `Do you want to save ${task.name}?`;
    } else {
      this.addTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openResetModal(): void {
    this.isResetModalOpen = true;
    this.modalDescription = `Are you sure you want to reset? You will lose your unsaved changes if you continue.`;
  }

  add(): void {
    this.toastService.showToast(ToastState.Success, 'Task created');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }

  delete(): void {
    this.router
      .navigate(['../../tasks-list'], { relativeTo: this.route })
      .then(() => {
        setTimeout(
          () => this.toastService.showToast(ToastState.Success, 'Task deleted'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
  }

  reset(): void {
    this.toastService.showToast(ToastState.Error, 'Form reset');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }

  save(): void {
    this.router
      .navigate(['../../tasks-list'], { relativeTo: this.route })
      .then(() => {
        setTimeout(
          () => this.toastService.showToast(ToastState.Success, 'Task saved'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
  }

  isRequired(name: string): boolean {
    return this.addTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  showErrors(name?: string): boolean {
    if (name) {
      return !!(
        this.addTaskForm.get(name)?.invalid &&
        this.addTaskForm.get(name)?.errors &&
        (this.addTaskForm.get(name)?.dirty ||
          this.addTaskForm.get(name)?.touched)
      );
    } else {
      return !!(
        this.addTaskForm.invalid &&
        this.addTaskForm.errors &&
        (this.addTaskForm.dirty || this.addTaskForm.touched)
      );
    }
  }
}
