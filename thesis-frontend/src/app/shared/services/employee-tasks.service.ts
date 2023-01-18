import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Day } from '../../calendar/models/day.model';
import { EmployeeTask } from '../models/employee-task.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeTasksService {
  constructor(private http: HttpClient) {}

  getEmployeeTask(
    employeeId: string,
    taskId: string
  ): Observable<EmployeeTask> {
    return this.http.get<EmployeeTask>(
      `${environment.apiUrl}/employees/${employeeId}/tasks/${taskId}`
    );
  }

  addEmployeeTask(
    employeeId: string,
    task: EmployeeTask
  ): Observable<EmployeeTask> {
    return this.http.post<EmployeeTask>(
      `${environment.apiUrl}/employees/${employeeId}/tasks`,
      task
    );
  }

  getEmployeeLastTasks(employeeId: string): Observable<EmployeeTask[]> {
    return this.http.get<EmployeeTask[]>(
      `${environment.apiUrl}/employees/${employeeId}/tasks`
    );
  }

  getEmployeeTasks(
    employeeId: string,
    date: string
  ): Observable<EmployeeTask[]> {
    date = formatDate(date, 'yyyy-MM-dd', 'en');
    return this.http.get<EmployeeTask[]>(
      `${environment.apiUrl}/employees/${employeeId}/tasks?date=${date}`
    );
  }

  getEmployeeCalendar(employeeId: string): Observable<Day[]> {
    return this.http.get<Day[]>(
      `${environment.apiUrl}/employees/${employeeId}/calendar`
    );
  }
}
