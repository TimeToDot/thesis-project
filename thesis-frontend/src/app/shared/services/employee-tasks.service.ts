import { Injectable } from '@angular/core';
import { EmployeeTask } from '../model/employee-task.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeTasksService {
  private _employeeTasks: EmployeeTask[] = [
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

  getEmployeeTasks(): EmployeeTask[] {
    return this._employeeTasks;
  }
}
