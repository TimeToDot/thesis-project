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
