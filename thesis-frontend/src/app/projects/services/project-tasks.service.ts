import { Injectable } from '@angular/core';
import { ProjectTask } from '../model/project-task.model';

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

  getProjectTasks(projectId: string): ProjectTask[] {
    return this._tasks
      .filter(element => element.projectId === projectId)
      .filter(element => element.active);
  }

  getArchivedProjectTasks(projectId: string): ProjectTask[] {
    return this._tasks
      .filter(element => element.projectId === projectId)
      .filter(element => !element.active);
  }
}
