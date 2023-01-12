import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Day } from '../../calendar/models/day.model';
import { EmployeeTask } from '../models/employee-task.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeTasksService {
  private url: string = 'http://localhost:3000/employees';

  constructor(private http: HttpClient) {}

  getEmployeeTask(
    employeeId: string,
    taskId: string
  ): Observable<EmployeeTask> {
    return this.http.get<EmployeeTask>(
      `${this.url}/${employeeId}/tasks/${taskId}`
    );
  }

  getEmployeeTasks(employeeId: string): Observable<EmployeeTask[]> {
    return this.http.get<EmployeeTask[]>(`${this.url}/${employeeId}/tasks`);
  }

  getEmployeeCalendar(employeeId: string): Observable<Day[]> {
    return this.http.get<Day[]>(`${this.url}/${employeeId}/calendar`);
  }
}
