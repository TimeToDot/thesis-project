import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
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
      `${environment.apiUrl}/${employeeId}/tasks/${taskId}`
    );
  }

  addEmployeeTask(
    employeeId: string,
    task: EmployeeTask
  ): Observable<EmployeeTask> {
    return this.http.post<EmployeeTask>(
      `${environment.apiUrl}/${employeeId}/tasks`,
      task
    );
  }

  getEmployeeLastTasks(employeeId: string): Observable<EmployeeTask[]> {
    return this.http.get<EmployeeTask[]>(
      `${environment.apiUrl}/${employeeId}/tasks`
    );
  }

  getEmployeeTasks(
    employeeId: string,
    date: string
  ): Observable<EmployeeTask[]> {
    const startDate = formatDate(date, 'yyyy-MM-dd', 'en') + 'T00:00';
    const endDate = formatDate(date, 'yyyy-MM-dd', 'en') + 'T23:59';
    return this.http.get<EmployeeTask[]>(
      `${environment.apiUrl}/employee/tasks?startDate=${startDate}&endDate=${endDate}`
    );
  }

  getEmployeeCalendar(): Observable<Day[]> {
    const date = formatDate(new Date(Date.now()), 'MM-yyyy', 'en');
    return this.http
      .get<Day[]>(`${environment.apiUrl}/employee/calendar?date=${date}`)
      .pipe(
        map(value => {
          console.log(value);
          return value;
        })
      );
  }
}
