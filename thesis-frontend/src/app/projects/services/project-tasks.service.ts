import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectTask } from '../models/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  private url: string = 'http://localhost:3000/projects';

  constructor(private http: HttpClient) {}

  getProjectTask(projectId: string, taskId: string): Observable<ProjectTask> {
    return this.http.get<ProjectTask>(
      `${this.url}/${projectId}/tasks/${taskId}`
    );
  }

  addProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.post<ProjectTask>(
      `${this.url}/${task.projectId}/tasks`,
      task
    );
  }

  updateProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.put<ProjectTask>(
      `${this.url}/${task.projectId}/tasks/${task.id}`,
      task
    );
  }

  getProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${this.url}/${projectId}/tasks?active=true`
    );
  }

  getArchivedProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${this.url}/${projectId}/tasks?active=false`
    );
  }
}
