import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Status } from '../enum/status.enum';
import { EmployeeTask } from '../models/employee-task.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeTasksService {
  private _employeeTasks: EmployeeTask[] = [
    {
      id: '1',
      startDate: '2022-11-21',
      endDate: '2012-11-21',
      startTime: '12:00',
      endTime: '15:00',
      project: {
        id: '2',
        name: 'Project 2',
        image: 'assets/companies/company2.png',
        moderator: {
          id: '0',
          firstName: 'Robert',
          lastName: 'Skrzypczak',
          email: 'robert.skrzypczak@gmail.com',
          image: 'assets/icons/icon14.png',
          position: 'Frontend Developer',
          employmentDate: '2021-08-15',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          active: true,
        },
        billingPeriod: { id: '3', name: 'Month' },
        employeesCount: 50,
        creationDate: '2021-03-11',
        active: true,
      },
      task: {
        id: '2',
        name: 'Task B',
        projectId: '2',
        description: 'test',
        creationDate: '2022-08-15',
        active: true,
      },
      status: Status.Pending,
    },
    {
      id: '2',
      startDate: '2022-12-21',
      endDate: '2022-12-21',
      startTime: '12:00',
      endTime: '15:00',
      project: {
        id: '1',
        name: 'Project 1',
        image: 'assets/companies/company1.png',
        moderator: {
          id: '0',
          firstName: 'Robert',
          lastName: 'Skrzypczak',
          email: 'robert.skrzypczak@gmail.com',
          image: 'assets/icons/icon14.png',
          position: 'Frontend Developer',
          employmentDate: '2021-08-15',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          active: true,
        },
        billingPeriod: { id: '3', name: 'Month' },
        employeesCount: 50,
        creationDate: '2021-03-11',
        active: true,
      },
      task: {
        id: '1',
        name: 'Task A',
        projectId: '1',
        description: 'test',
        creationDate: '2022-07-22',
        active: true,
      },
      status: Status.Approved,
    },
    {
      id: '3',
      startDate: '2022-12-21',
      endDate: '2022-12-21',
      startTime: '12:00',
      endTime: '15:00',
      project: {
        id: '1',
        name: 'Project 1',
        image: 'assets/companies/company1.png',
        moderator: {
          id: '0',
          firstName: 'Robert',
          lastName: 'Skrzypczak',
          email: 'robert.skrzypczak@gmail.com',
          image: 'assets/icons/icon14.png',
          position: 'Frontend Developer',
          employmentDate: '2021-08-15',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          active: true,
        },
        billingPeriod: { id: '3', name: 'Month' },
        employeesCount: 50,
        creationDate: '2021-03-11',
        active: true,
      },
      task: {
        id: '1',
        name: 'Task A',
        projectId: '1',
        description: 'test',
        creationDate: '2022-07-22',
        active: true,
      },
      status: Status.Rejected,
    },
  ];

  constructor() {}

  getEmployeeTask(id: string): Observable<EmployeeTask> {
    const task = this._employeeTasks.find(task => task.id === id);
    return of(task as EmployeeTask);
  }

  getEmployeeTasks(): Observable<EmployeeTask[]> {
    return of(this._employeeTasks);
  }
}
