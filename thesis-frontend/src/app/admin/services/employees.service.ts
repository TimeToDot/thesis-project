import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { valueOrDefault } from 'chart.js/dist/helpers/helpers.core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { Employee } from '../../shared/models/employee.model';
import { Approval } from '../../tracker/models/approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  constructor(private http: HttpClient) {}

  getEmployee(id: string): Observable<Account> {
    return this.http.get<Account>(`${environment.apiUrl}/employee/${id}`);
  }

  addEmployee(employee: Account): Observable<Account> {
    return this.http.post<Account>(`${environment.apiUrl}/employee`, employee);
  }

  updateEmployee(employee: Account): Observable<Account> {
    return this.http.put<Account>(
      `${environment.apiUrl}/employees/${employee.id}`,
      employee
    );
  }

  archiveEmployee(employee: Account): Observable<Account> {
    employee.active = false;
    employee.exitDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    employee.birthDate = formatDate(employee.birthDate, 'yyyy-MM-dd', 'en');
    employee.employmentDate = formatDate(
      employee.employmentDate,
      'yyyy-MM-dd',
      'en'
    );
    return this.http.put<Account>(
      `${environment.apiUrl}/employees/${employee.id}`,
      employee
    );
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(
      `${environment.apiUrl}/employees?active=true`
    );
  }

  getArchivedEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(
      `${environment.apiUrl}/employees?active=false`
    );
  }

  getActiveEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${environment.apiUrl}/employees/${employeeId}/projects?active=true`
    );
  }

  getEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${environment.apiUrl}/employees/${employeeId}/projects`
    );
  }

  getProjectsToApprove(employeeId: string): Observable<Approval[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/employees/${employeeId}/approve`)
      .pipe(map(value => value.projects));
  }
}
