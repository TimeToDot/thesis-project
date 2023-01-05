import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectEmployee } from '../models/project-employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  private url: string = 'http://localhost:3000/project-employees';

  constructor(private http: HttpClient) {}

  getProjectEmployee(id: string): Observable<ProjectEmployee> {
    return this.http.get<ProjectEmployee>(`${this.url}/${id}`);
  }

  getProjectEmployees(): Observable<ProjectEmployee[]> {
    return this.http.get<ProjectEmployee[]>(`${this.url}?active=true`);
  }

  getArchivedProjectEmployees(): Observable<ProjectEmployee[]> {
    return this.http.get<ProjectEmployee[]>(`${this.url}?active=false`);
  }
}
