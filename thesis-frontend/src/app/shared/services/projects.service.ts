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

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.url}?active=true`);
  }

  getArchivedProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.url}?active=false`);
  }
}
