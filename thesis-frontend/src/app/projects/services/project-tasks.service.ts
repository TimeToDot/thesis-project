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
    },
    {
      id: '2',
      name: 'Task B',
      projectId: '2',
      description: 'test',
      creationDate: '2022-08-15',
    },
    {
      id: '3',
      name: 'Task C',
      projectId: '1',
      description: 'test',
      creationDate: '2022-04-31',
    },
    {
      id: '4',
      name: 'Task D',
      projectId: '2',
      description: 'test',
      creationDate: '2022-02-04',
    },
    {
      id: '5',
      name: 'Task E',
      projectId: '3',
      description: 'test',
      creationDate: '2022-01-12',
    },
  ];

  constructor() {}

  getProjectTasks(projectId: string): ProjectTask[] {
    return this._tasks.filter(element => element.projectId === projectId);
  }
}
