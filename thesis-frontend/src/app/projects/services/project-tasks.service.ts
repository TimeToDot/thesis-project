import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProjectTask } from '../models/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  constructor(private http: HttpClient) {}

  getProjectTask(projectId: string, taskId: string): Observable<ProjectTask> {
    return this.http.get<ProjectTask>(
      `${environment.apiUrl}/projects/${projectId}/tasks/${taskId}`
    );
  }

  addProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.post<ProjectTask>(
      `${environment.apiUrl}/projects/${task.projectId}/tasks`,
      task
    );
  }

  updateProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.put<ProjectTask>(
      `${environment.apiUrl}/projects/${task.projectId}/tasks/${task.id}`,
      task
    );
  }

  archiveProjectTask(task: ProjectTask): Observable<ProjectTask> {
    task.active = false;
    task.archiveDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    console.log(task);
    return this.http.put<ProjectTask>(
      `${environment.apiUrl}/projects/${task.projectId}/tasks/${task.id}`,
      task
    );
  }

  getProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${environment.apiUrl}/projects/${projectId}/tasks?active=true`
    );
  }

  getArchivedProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return this.http.get<ProjectTask[]>(
      `${environment.apiUrl}/projects/${projectId}/tasks?active=false`
    );
  }
}
