import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Project } from '../../projects/models/project.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  private url: string = 'http://localhost:3000/projects';

  constructor(private http: HttpClient) {}

  getProject(id: string): Observable<Project> {
    return this.http.get<Project>(`${this.url}/${id}`);
  }

  addProject(project: Project): Observable<Project> {
    return this.http.post<Project>(this.url, project);
  }

  updateProject(project: Project): Observable<Project> {
    return this.http.put<Project>(`${this.url}/${project.id}`, project);
  }

  archiveProject(project: Project): Observable<Project> {
    project.active = false;
    project.archiveDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<Project>(`${this.url}/${project.id}`, project);
  }

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.url}?active=true`);
  }

  getArchivedProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.url}?active=false`);
  }
}
