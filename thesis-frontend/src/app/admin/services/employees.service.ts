import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Project } from '../../projects/models/project.model';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { Employee } from '../../shared/models/employee.model';
import { Approval } from '../../tracker/models/approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  constructor(private http: HttpClient) {}

  getCurrentEmployee(): Observable<Account> {
    return this.http.get<Account>(`${environment.apiUrl}/employee`);
  }

  getEmployee(id: string): Observable<Account> {
    return this.http.get<Account>(`${environment.apiUrl}/employee${id}`);
  }

  addEmployee(employee: Account): Observable<Account> {
    return this.http.post<Account>(
      `${environment.apiUrl}/authorization`,
      employee
    );
  }

  updateEmployee(employee: Account): Observable<Account> {
    return this.http.put<Account>(
      `${environment.apiUrl}/${employee.id}`,
      employee
    );
  }

  archiveEmployee(employee: Account): Observable<Account> {
    return this.http.put<Account>(
      `${environment.apiUrl}/${employee.id}`,
      employee
    );
  }

  getEmployees(): Observable<Employee[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/employees?active=true`)
      .pipe(map(value => value.employees));
  }

  getArchivedEmployees(): Observable<Employee[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/employees?active=false`)
      .pipe(map(value => value.employees));
  }

  getActiveEmployeeProjects(): Observable<Project[]> {
    return this.http.get<any>(`${environment.apiUrl}/employee/projects`).pipe(
      map(value => {
        console.log(value);
        return value.projects;
      })
    );
  }

  getEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${environment.apiUrl}/${employeeId}/projects`
    );
  }

  getProjectsToApprove(employeeId: string): Observable<Approval[]> {
    const startDate = formatDate(
      new Date(Date.now()),
      'yyyy-MM-ddThh:mm',
      'en'
    );
    const date = new Date();
    const endDate =
      formatDate(
        new Date(date.getFullYear(), date.getMonth(), date.getDate() - 7),
        'yyyy-MM-dd',
        'en'
      ) + 'T23:59';
    return this.http.get<Approval[]>(
      `${environment.apiUrl}/employee/projects/toApprove?startDate=${startDate}&endDate=${endDate}`
    );
  }
}
