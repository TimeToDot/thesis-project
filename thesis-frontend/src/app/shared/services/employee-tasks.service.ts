import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
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

  // getEmployeeTask(id: string): Observable<EmployeeTask> {
  //   const task = this._employeeTasks.find(task => task.id === id);
  //   return of(task as EmployeeTask);
  // }

  // getEmployeeTasks(): Observable<EmployeeTask[]> {
  //   return of(this._employeeTasks);
  // }
}
