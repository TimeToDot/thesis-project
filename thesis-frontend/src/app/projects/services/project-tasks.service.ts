import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectTask } from '../models/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  private url: string = 'http://localhost:3000/project-tasks';

  constructor(private http: HttpClient) {}

  getProjectTask(id: string): Observable<ProjectTask> {
    return this.http.get<ProjectTask>(`${this.url}/${id}`);
  }

  getProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${this.url}?projectId=${projectId}&active=true`
    );
  }

  getArchivedProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${this.url}?projectId=${projectId}&active=false`
    );
  }
}
