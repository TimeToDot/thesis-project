import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  private _projectApprovals: ProjectApproval[] = [
    {
      approve: false,
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
      taskCount: 63,
    },
    {
      approve: false,
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
      taskCount: 123,
    },
    {
      approve: false,
      project: {
        id: '3',
        name: 'Project 3',
        image: 'assets/companies/company3.png',
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
      taskCount: 49,
    },
  ];

  constructor() {}

  getProjectApprovals(): Observable<ProjectApproval[]> {
    return of(this._projectApprovals);
  }
}
