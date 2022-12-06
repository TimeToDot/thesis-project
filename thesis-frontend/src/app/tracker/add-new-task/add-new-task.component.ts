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

@Component({
  selector: 'bvr-add-new-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DatePickerComponent,
    DropdownListComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    TimePickerComponent,
  ],
  templateUrl: './add-new-task.component.html',
})
export class AddNewTaskComponent implements OnInit {
  createTaskForm!: FormGroup;
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
    private employeeProjectService: EmployeeProjectsService
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
    this.createTaskForm = this.fb.group({
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

  isRequired(name: string): boolean {
    return this.createTaskForm.get(name)?.hasValidator(Validators.required)
      ? true
      : false;
  }

  observeProjectChange(): void {
    this.createTaskForm.get('project')?.valueChanges.subscribe(projectId => {
      this.tasks = this.projectTasksService.getProjectTasks(projectId);
      this.createTaskForm.get('task')?.enable();
      this.createTaskForm.get('task')?.setValue('');
    });
  }
}
