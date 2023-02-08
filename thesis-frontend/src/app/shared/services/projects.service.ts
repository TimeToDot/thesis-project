import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Project } from '../../projects/models/project.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  constructor(private http: HttpClient) {}

  getProject(id: string): Observable<Project> {
    return this.http.get<Project>(`${environment.apiUrl}/projects/${id}`);
  }

  addProject(project: Project): Observable<Project> {
    return this.http.post<Project>(`${environment.apiUrl}/projects`, project);
  }

  updateProject(project: Project): Observable<Project> {
    return this.http.put<Project>(
      `${environment.apiUrl}/projects/${project.id}`,
      project
    );
  }

  archiveProject(project: Project): Observable<Project> {
    project.active = false;
    project.archiveDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<Project>(
      `${environment.apiUrl}/projects/${project.id}`,
      project
    );
  }

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(
      `${environment.apiUrl}/projects?active=true`
    );
  }

  getArchivedProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(
      `${environment.apiUrl}/projects?active=false`
    );
  }
}
