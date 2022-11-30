import { Injectable } from '@angular/core';
import { UserTask } from '../model/user-task.model';

@Injectable({
  providedIn: 'root',
})
export class UserTasksService {
  private _userTasks: UserTask[] = [
    {
      startDate: '2022-11-21',
      endDate: '2012-11-21',
      startTime: '12:00',
      endTime: '15:00',
      projectId: '1',
      taskId: '3',
    },
    {
      startDate: '2022-12-21',
      endDate: '2022-12-21',
      startTime: '12:00',
      endTime: '15:00',
      projectId: '2',
      taskId: '2',
    },
  ];

  constructor() {}

  getUserTasks(): UserTask[] {
    return this._userTasks;
  }
}
