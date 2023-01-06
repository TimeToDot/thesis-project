import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../../projects/models/project.model';
import { Account } from '../../shared/models/account.model';
import { Employee } from '../../shared/models/employee.model';
import { ProjectApproval } from '../../tracker/models/project-approval.model';

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

  getEmployeeProjects(id: string): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.url}/${id}/projects`);
  }

  getProjectsToApprove(employeeId: string): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${this.url}/${employeeId}/approve`
    );
  }
}
