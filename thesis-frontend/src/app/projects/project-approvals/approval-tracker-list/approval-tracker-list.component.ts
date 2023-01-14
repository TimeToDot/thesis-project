import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeTasksComponent } from '../../../shared/components/employee-tasks/employee-tasks.component';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { EmployeeTask } from '../../../shared/models/employee-task.model';
import { EmployeeProjectTask } from '../../../shared/models/employee-project-task.model';
import { EmployeeTasksService } from '../../../shared/services/employee-tasks.service';
import { first, Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { CalendarService } from '../../../shared/services/calendar.service';

@Component({
  selector: 'bvr-approval-tracker-list',
  standalone: true,
  imports: [CommonModule, EmployeeTasksComponent, ToastComponent],
  templateUrl: './approval-tracker-list.component.html',
})
export class ApprovalTrackerListComponent {
  @Input() isActive: boolean = true;
  @Input() refreshTaskList: Observable<void> = new Observable();

  employeeTasks: EmployeeTask[] = [];
  employeeProjectTasks: EmployeeProjectTask[] = [];

  constructor(
    private calendarService: CalendarService,
    private employeeTasksService: EmployeeTasksService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.observeTaskListRefresh();
    this.getEmployeeTasks();
    this.sortByProjectName();
  }

  observeTaskListRefresh(): void {
    this.refreshTaskList.subscribe(() => {
      this.getEmployeeTasks();
    });
  }

  getEmployeeTasks(): void {
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (employeeId) {
      this.calendarService.currentDay.subscribe(date => {
        this.employeeTasksService
          .getEmployeeTasks(employeeId, date)
          .pipe(first())
          .subscribe(employeeTasks => {
            this.employeeTasks = employeeTasks;
            this.getEmployeeProjectTasks();
          });
      });
    }
  }

  getEmployeeProjectTasks(): void {
    this.employeeProjectTasks = [];
    this.employeeTasks.forEach(employeeTask => {
      const index = this.findProjectIndex(employeeTask.project.id);
      if (index !== -1) {
        this.employeeProjectTasks[index].tasks.push(employeeTask);
      } else {
        this.employeeProjectTasks.push({
          project: employeeTask.project,
          tasks: [employeeTask],
        });
      }
    });
  }

  findProjectIndex(projectId: string) {
    return this.employeeProjectTasks.findIndex(
      employeeProjectTask => employeeProjectTask.project.id === projectId
    );
  }

  sortByProjectName(): void {
    this.employeeProjectTasks.sort((a, b) =>
      a.project.name.toLowerCase().localeCompare(b.project.name.toLowerCase())
    );
  }
}
