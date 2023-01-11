import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectEmployee } from '../models/project-employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  private url: string = 'http://localhost:3000/projects';

  constructor(private http: HttpClient) {}

  getProjectEmployee(
    projectId: string,
    employeeId: string
  ): Observable<ProjectEmployee> {
    return this.http.get<ProjectEmployee>(
      `${this.url}/${projectId}/employees/${employeeId}`
    );
  }

  addProjectEmployee(employee: ProjectEmployee): Observable<ProjectEmployee> {
    return this.http.post<ProjectEmployee>(
      `${this.url}/${employee.projectId}/employees`,
      employee
    );
  }

  getProjectEmployees(projectId: string): Observable<ProjectEmployee[]> {
    return this.http.get<ProjectEmployee[]>(
      `${this.url}/${projectId}/employees?active=true`
    );
  }

  getArchivedProjectEmployees(
    projectId: string
  ): Observable<ProjectEmployee[]> {
    return this.http.get<ProjectEmployee[]>(
      `${this.url}/${projectId}/employees?active=false`
    );
  }
}
