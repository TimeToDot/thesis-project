import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { CalendarComponent } from '../../../calendar/calendar.component';
import { ApprovalTrackerListComponent } from '../approval-tracker-list/approval-tracker-list.component';
import { TasksToRejectService } from '../../../shared/services/tasks-to-reject.service';
import { first, Subscription } from 'rxjs';
import { DropdownSearchEmployeeComponent } from '../../project-employees/dropdown-search-employee/dropdown-search-employee.component';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { Employee } from '../../../shared/models/employee.model';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastService } from '../../../shared/services/toast.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ProjectEmployee } from '../../models/project-employee.model';

@Component({
  selector: 'bvr-approval-tracker',
  standalone: true,
  imports: [
    ApprovalTrackerListComponent,
    ButtonComponent,
    CalendarComponent,
    CommonModule,
    DropdownSearchEmployeeComponent,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './approval-tracker.component.html',
})
export class ApprovalTrackerComponent implements OnInit, OnDestroy {
  approveTasksForm!: FormGroup;
  employees: Employee[] = [];
  isResetModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  projectEmployee: ProjectEmployee = {
    id: '',
    employee: {
      id: '',
      firstName: '',
      lastName: '',
      email: '',
      image: '',
      position: '',
      employmentDate: '',
      workingTime: 0,
      exitDate: '',
      active: false,
    },
    contractType: { id: '', name: '' },
    workingTime: 0,
    wage: 0,
    joinDate: '',
    exitDate: '',
    active: false,
  };

  private tasksToRejectSubscribtion: Subscription = new Subscription();

  constructor(
    private fb: FormBuilder,
    private projectEmployeesService: ProjectEmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private tasksToRejectService: TasksToRejectService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.observeRejectedTasks();
    this.createForm();
    this.getProjectEmployee();
    this.getProjectEmployees();
  }

  observeRejectedTasks(): void {
    this.tasksToRejectSubscribtion =
      this.tasksToRejectService.tasksToReject.subscribe(tasks => {
        console.log(tasks);
      });
  }

  createForm(): void {
    this.approveTasksForm = this.fb.group({
      id: [''],
    });
  }

  getProjectEmployee(): void {
    const projectEmployeeId = this.route.snapshot.paramMap.get('id');
    if (projectEmployeeId) {
      this.projectEmployeesService
        .getProjectEmployee(projectEmployeeId)
        .pipe(first())
        .subscribe(projectEmployee => {
          this.projectEmployee = projectEmployee;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.approveTasksForm.controls).forEach(field => {
      this.approveTasksForm
        .get(field)
        ?.setValue(this.projectEmployee[field as keyof ProjectEmployee]);
    });
  }

  getProjectEmployees(): void {
    this.projectEmployeesService
      .getProjectEmployees()
      .pipe(first())
      .subscribe(projectEmployees => {
        this.employees = projectEmployees.map(
          projectEmployee => projectEmployee.employee
        );
        this.observeIdSelection();
      });
  }

  observeIdSelection(): void {
    this.approveTasksForm.get(['id'])?.valueChanges.subscribe(value => {
      this.router.navigate([`../${value}`], { relativeTo: this.route });
    });
  }

  openSaveModal(): void {
    this.isSaveModalOpen = true;
    this.modalDescription = `Are you sure you want to approve X and reject Y tasks?`;
  }

  openCancelModal(): void {
    this.isResetModalOpen = true;
    this.modalDescription = `Are you sure you want to reset? You will lose your unsaved changes if you continue.`;
  }

  cancel(): void {
    this.router.navigate(['..'], { relativeTo: this.route });
  }

  confirm(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Approval resolved'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  ngOnDestroy(): void {
    this.tasksToRejectSubscribtion.unsubscribe();
  }
}
