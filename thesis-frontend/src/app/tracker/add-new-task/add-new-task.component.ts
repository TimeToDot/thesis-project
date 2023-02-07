import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate, Location } from '@angular/common';
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
import { first, Subject } from 'rxjs';
import { EmployeeTask } from '../../shared/models/employee-task.model';
import { ValidationService } from '../../shared/services/validation.service';
import { AuthService } from '../../shared/services/auth.service';
import { EmployeesService } from '../../admin/services/employees.service';
import { ErrorComponent } from '../../shared/components/error/error.component';
import { CustomValidators } from '../../shared/helpers/custom-validators.helper';
import { Status } from '../../shared/enum/status.enum';

@Component({
  selector: 'bvr-add-new-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    ErrorComponent,
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
  controls: any = {};
  hasTaskToSave: boolean = false;
  isAddModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isDeleteModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isResetModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  projects: DropdownOption[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  tasks: DropdownOption[] = [];

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private projectTasksService: ProjectTasksService,
    private employeesService: EmployeesService,
    private employeeTasksService: EmployeeTasksService,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.getFormControls();
    this.getEmployeeProjects();
    this.observeProjectChange();
    this.getTask();
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
      {
        validators: [
          CustomValidators.dateRangeValidator('startDate', 'endDate'),
          CustomValidators.timeRangeValidator(
            'startDate',
            'startTime',
            'endDate',
            'endTime'
          ),
        ],
      }
    );
  }

  getFormControls(): void {
    Object.keys(this.addTaskForm.controls).forEach(control => {
      this.controls[control] = this.addTaskForm.get([control]);
    });
  }

  roundToMinutes(minutes: number): string {
    const ms = 1000 * 60 * minutes;
    return formatDate(
      new Date(Math.round(new Date().getTime() / ms) * ms),
      'HH:mm',
      'en'
    );
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

  getEmployeeProjects(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeesService
        .getActiveEmployeeProjects(employeeId)
        .pipe(first())
        .subscribe(employeeProjects => {
          this.projects = employeeProjects.map(
            employeeProject => employeeProject.project
          );
        });
    }
  }

  getTask(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    const taskId = this.route.snapshot.paramMap.get('id');
    if (employeeId && taskId) {
      this.employeeTasksService
        .getEmployeeTask(employeeId, taskId)
        .pipe(first())
        .subscribe(employeeTask => {
          this.updateFormFields(employeeTask);
          this.hasTaskToSave = true;
        });
    }
  }

  updateFormFields(task: EmployeeTask): void {
    Object.keys(this.addTaskForm.controls).forEach(field => {
      this.addTaskForm.get(field)?.setValue(task[field as keyof EmployeeTask]);
    });
  }

  openAddModal(): void {
    if (this.addTaskForm.valid) {
      this.isAddModalOpen = true;
      const task = this.addTaskForm.get(['task'])?.value;
      this.modalDescription = `Are you sure you want to add ${task.name} to your timesheet?`;
    } else {
      this.addTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openDeleteModal(): void {
    this.isDeleteModalOpen = true;
    const task = this.addTaskForm.get(['task'])?.value;
    this.modalDescription = `Are you sure you want to delete ${task.name}? This action cannot be undone.`;
  }

  openSaveModal(): void {
    if (this.addTaskForm.valid) {
      this.isSaveModalOpen = true;
      const task = this.addTaskForm.get(['task'])?.value;
      this.modalDescription = `Are you sure you want to save changes?`;
    } else {
      this.addTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  openResetModal(): void {
    this.isResetModalOpen = true;
    this.modalDescription = `Are you sure you want to reset? You will lose your changes if you continue.`;
  }

  add(value: boolean): void {
    if (value) {
      const employeeId = this.authService.getLoggedEmployeeId();
      this.employeeTasksService
        .addEmployeeTask(employeeId, this.getTaskData(employeeId))
        .pipe(first())
        .subscribe(() => {
          this.toastService.showToast(ToastState.Success, 'Task added');
          setTimeout(() => this.toastService.dismissToast(), 3000);
        });
    }
  }

  getTaskData(employeeId: string): EmployeeTask {
    return {
      id: '',
      employeeId: employeeId,
      startDate: this.controls.startDate?.value,
      endDate: this.controls.endDate?.value,
      startTime: this.controls.startTime?.value,
      endTime: this.controls.endTime?.value,
      project: this.controls.project?.value,
      task: this.controls.task?.value,
      status: Status.Logged,
    };
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

  delete(): void {
    this.disableGuard(true);
    this.router
      .navigate(['../../tasks-list'], { relativeTo: this.route })
      .then(() => {
        setTimeout(
          () => this.toastService.showToast(ToastState.Info, 'Task deleted'),
          200
        );
        setTimeout(() => this.toastService.dismissToast(), 3200);
      });
  }

  reset(): void {
    this.toastService.showToast(ToastState.Info, 'Form reset');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }

  save(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      this.router
        .navigate(['../../tasks-list'], { relativeTo: this.route })
        .then(() => {
          setTimeout(
            () =>
              this.toastService.showToast(ToastState.Success, 'Task edited'),
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
    return this.validationService.isRequired(this.addTaskForm, [name]);
  }

  showErrors(name?: string): boolean {
    return name
      ? this.validationService.showErrors(this.addTaskForm, [name])
      : this.validationService.showErrors(this.addTaskForm, []);
  }
}
