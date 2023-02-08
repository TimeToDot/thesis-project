import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProjectEmployee } from '../models/project-employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  constructor(private http: HttpClient) {}

  getProjectEmployee(
    projectId: string,
    employeeId: string
  ): Observable<ProjectEmployee> {
    return this.http
      .get<any>(
        `${environment.apiUrl}/projects/${projectId}/employees/${employeeId}`
      )
      .pipe(
        map(value => {
          delete Object.assign(value.employee, {
            ['id']: value.employee['employeeId'],
          })['employeeId'];
          return value;
        })
      );
  }

  addProjectEmployee(employee: ProjectEmployee): Observable<ProjectEmployee> {
    return this.http.post<ProjectEmployee>(
      `${environment.apiUrl}/projects/${employee.projectId}/employees`,
      employee
    );
  }

  updateProjectEmployee(
    employee: ProjectEmployee
  ): Observable<ProjectEmployee> {
    return this.http.put<ProjectEmployee>(
      `${environment.apiUrl}/projects/${employee.projectId}/employees/${employee.projectEmployeeId}`,
      employee
    );
  }

  archiveProjectEmployee(
    employee: ProjectEmployee
  ): Observable<ProjectEmployee> {
    employee.active = false;
    employee.exitDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<ProjectEmployee>(
      `${environment.apiUrl}/projects/${employee.projectId}/employees/${employee.projectEmployeeId}`,
      employee
    );
  }

  getProjectEmployees(projectId: string): Observable<ProjectEmployee[]> {
    return this.http
      .get<any>(
        `${environment.apiUrl}/projects/${projectId}/employees?active=true`
      )
      .pipe(
        map(value => {
          value.employees.forEach((employee: any) => {
            delete Object.assign(employee.employee, {
              ['id']: employee.employee['employeeId'],
            })['employeeId'];
          });
          return value.employees;
        })
      );
  }

  getArchivedProjectEmployees(
    projectId: string
  ): Observable<ProjectEmployee[]> {
    return this.http
      .get<any>(
        `${environment.apiUrl}/projects/${projectId}/employees?active=false`
      )
      .pipe(
        map(value => {
          value.employees.forEach((employee: any) => {
            delete Object.assign(employee.employee, {
              ['id']: employee.employee['employeeId'],
            })['employeeId'];
          });
          return value.employees;
        })
      );
  }
}
