import { Injectable } from '@angular/core';
import { ProjectTask } from '../model/project-task.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectTasksService {
  private _tasks: ProjectTask[] = [
    { id: '1', name: 'Task A', projectId: '1' },
    { id: '2', name: 'Task B', projectId: '2' },
    { id: '3', name: 'Task C', projectId: '1' },
    { id: '4', name: 'Task D', projectId: '2' },
    { id: '5', name: 'Task E', projectId: '3' },
  ];

  constructor() {}

  getProjectTasks(projectId: string): ProjectTask[] {
    return this._tasks.filter(element => element.projectId === projectId);
  }
}
