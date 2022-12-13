import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { EmployeeTask } from '../../shared/model/employee-task.model';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { FormFieldComponent } from '../../shared/components/form-field/form-field.component';
import { EmployeeProjectsService } from '../../shared/services/employee-projects.service';
import { ProjectTasksService } from '../../projects/services/project-tasks.service';
import { DatePickerComponent } from '../../shared/components/date-picker/date-picker.component';
import { TimePickerComponent } from '../../shared/components/time-picker/time-picker.component';
import { DropdownListComponent } from '../../shared/components/dropdown-list/dropdown-list.component';
import { DropdownOption } from '../../shared/model/dropdown-option.model';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastService } from '../../shared/services/toast.service';
import { ToastState } from '../../shared/enum/toast-state';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';

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
  isAddModalOpen: boolean = false;
  modalDescription: string = '';
  newTask: EmployeeTask = {
    id: '',
    startDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
    endDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
    startTime: this.roundToMinutes(15),
    endTime: this.roundToMinutes(15),
    projectId: '',
    taskId: '',
  };
  projects: DropdownOption[] = [];
  tasks: DropdownOption[] = [];

  constructor(
    private fb: FormBuilder,
    private projectTasksService: ProjectTasksService,
    private employeeProjectService: EmployeeProjectsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.projects = this.employeeProjectService.getEmployeeProjects();
    this.createForm();
    this.observeProjectChange();
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
    this.addTaskForm = this.fb.group({
      startDate: [
        formatDate(this.newTask.startDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      endDate: [
        formatDate(this.newTask.endDate, 'yyyy-MM-dd', 'en'),
        [Validators.required],
      ],
      startTime: [this.newTask.startTime, [Validators.required]],
      endTime: [this.newTask.endTime, [Validators.required]],
      project: [this.newTask.projectId, [Validators.required]],
      task: [
        { value: this.newTask.taskId, disabled: true },
        [Validators.required],
      ],
    });
  }

  openAddModal(): void {
    if (this.addTaskForm.valid) {
      this.isAddModalOpen = true;
      const taskName = this.addTaskForm.get(['task'])?.value;
      this.modalDescription = `Do you want to add ${taskName} to your timesheet?`;
    } else {
      this.addTaskForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  add(): void {
    this.toastService.showToast(ToastState.Success, 'Task created');
    setTimeout(() => this.toastService.dismissToast(), 3200);
  }

  isRequired(name: string): boolean {
    return this.addTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  observeProjectChange(): void {
    this.addTaskForm.get('project')?.valueChanges.subscribe(projectId => {
      this.tasks = this.projectTasksService.getProjectTasks(projectId);
      this.addTaskForm.get('task')?.enable();
      this.addTaskForm.get('task')?.setValue('');
    });
  }

  showErrors(name: string): boolean {
    return !!(
      this.addTaskForm.get(name)?.invalid &&
      this.addTaskForm.get(name)?.errors &&
      (this.addTaskForm.get(name)?.dirty || this.addTaskForm.get(name)?.touched)
    );
  }
}
