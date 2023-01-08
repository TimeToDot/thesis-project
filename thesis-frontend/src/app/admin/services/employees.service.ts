import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { Employee } from '../../shared/models/employee.model';
import { Approval } from '../../tracker/models/approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  private url: string = 'http://localhost:3000/employees';

  constructor(private http: HttpClient) {}

  getEmployee(id: string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/${id}`);
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=true`);
  }

  getArchivedEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=false`);
  }

  getActiveEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${this.url}/${employeeId}/projects?active=true`
    );
  }

  getEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${this.url}/${employeeId}/projects`
    );
  }

  getProjectsToApprove(employeeId: string): Observable<Approval[]> {
    return this.http.get<Approval[]>(`${this.url}/${employeeId}/approve`);
  }
}
