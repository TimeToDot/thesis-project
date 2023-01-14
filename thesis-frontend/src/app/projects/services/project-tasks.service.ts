import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProjectTask } from '../models/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  private _tasks: ProjectTask[] = [
    {
      id: '1',
      name: 'Task A',
      projectId: '1',
      description: 'test',
      creationDate: '2022-07-22',
      active: true,
    },
    {
      id: '2',
      name: 'Task B',
      projectId: '2',
      description: 'test',
      creationDate: '2022-08-15',
      active: true,
    },
    {
      id: '3',
      name: 'Task C',
      projectId: '1',
      description: 'test',
      creationDate: '2022-04-31',
      archiveDate: '2022-06-03',
      active: false,
    },
    {
      id: '4',
      name: 'Task D',
      projectId: '2',
      description: 'test',
      creationDate: '2022-02-04',
      active: true,
    },
    {
      id: '5',
      name: 'Task E',
      projectId: '3',
      description: 'test',
      creationDate: '2022-01-12',
      active: true,
    },
  ];

  constructor() {}

  getProjectTask(taskId: string): Observable<ProjectTask> {
    return of(
      this._tasks.find(element => element.id === taskId) as ProjectTask
    );
  }

  getProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return of(
      this._tasks
        .filter(element => element.projectId === projectId)
        .filter(element => element.active)
    );
  }

  getArchivedProjectTasks(projectId: string): Observable<ProjectTask[]> {
    return of(
      this._tasks
        .filter(element => element.projectId === projectId)
        .filter(element => !element.active)
    );
  }
}
