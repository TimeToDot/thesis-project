import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { EmployeeTask } from '../../models/employee-task.model';
import { Project } from '../../../projects/models/project.model';
import { Status } from '../../enum/status.enum';
import { TasksToRejectService } from '../../services/tasks-to-reject.service';
import { ToastState } from '../../enum/toast-state';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'bvr-employee-tasks',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './employee-tasks.component.html',
})
export class EmployeeTasksComponent implements OnInit {
  @Input() employeeTasks: EmployeeTask[] = [];
  @Input() isApproval: boolean = false;
  @Input() project: Project = {
    id: '',
    name: '',
    image: '',
  };

  @Output() delete: EventEmitter<EmployeeTask> = new EventEmitter();
  @Output() edit: EventEmitter<EmployeeTask> = new EventEmitter();

  tasksToReject: string[] = [];

  constructor(
    private tasksToRejectService: TasksToRejectService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.tasksToRejectService.tasksToReject.subscribe(
      tasks => (this.tasksToReject = tasks)
    );
  }

  canEdit(employeeTask: EmployeeTask): boolean {
    return (
      employeeTask.status === Status.Logged ||
      employeeTask.status === Status.Rejected
    );
  }

  canReject(employeeTask: EmployeeTask): boolean {
    return (
      this.isApproval &&
      employeeTask.status === Status.Pending &&
      !this.tasksToReject.includes(employeeTask.id)
    );
  }

  canApprove(employeeTask: EmployeeTask): boolean {
    return (
      this.isApproval &&
      employeeTask.status === Status.Pending &&
      this.tasksToReject.includes(employeeTask.id)
    );
  }

  isWide(employeeTask: EmployeeTask): boolean {
    return (
      (!this.canEdit(employeeTask) && !this.canReject(employeeTask)) ||
      this.isApproval
    );
  }

  isMedium(employeeTask: EmployeeTask): boolean {
    return (
      (!this.canEdit(employeeTask) && this.canReject(employeeTask)) ||
      (this.isApproval && this.tasksToReject.includes(employeeTask.id))
    );
  }

  approveTask(employeeTaskId: string): void {
    this.tasksToRejectService.removeTask(employeeTaskId);
    this.toastService.showToast(ToastState.Info, 'Task approved');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }

  rejectTask(employeeTaskId: string): void {
    this.tasksToRejectService.addTask(employeeTaskId);
    this.toastService.showToast(ToastState.Info, 'Task rejected');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }
}
