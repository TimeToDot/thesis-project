import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProjectTask } from '../models/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  constructor(private http: HttpClient) {}

  getProjectTask(taskId: string): Observable<ProjectTask> {
    return this.http.get<ProjectTask>(
      `${environment.apiUrl}/project/task/${taskId}`
    );
  }

  addProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.post<ProjectTask>(
      `${environment.apiUrl}/project/task`,
      task
    );
  }

  updateProjectTask(task: ProjectTask): Observable<ProjectTask> {
    return this.http.put<ProjectTask>(
      `${environment.apiUrl}/project/task`,
      task
    );
  }

  archiveProjectTask(task: ProjectTask): Observable<ProjectTask> {
    task.active = false;
    return this.http.put<ProjectTask>(
      `${environment.apiUrl}/project/tasks`,
      task
    );
  }

  getProjectTasks(): Observable<ProjectTask[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/project/tasks?active=true`)
      .pipe(map(value => value.tasks));
  }

  getArchivedProjectTasks(): Observable<ProjectTask[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/project/tasks?active=false`)
      .pipe(map(value => value.tasks));
  }
}
