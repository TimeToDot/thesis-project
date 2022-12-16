import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  private _projectApprovals: ProjectApproval[] = [
    {
      project: {
        id: '1',
        name: 'Project 1',
        image: 'assets/companies/company1.png',
      },
      taskCount: 63,
    },
    {
      project: {
        id: '2',
        name: 'Project 2',
        image: 'assets/companies/company2.png',
      },
      taskCount: 123,
    },
    {
      project: {
        id: '3',
        name: 'Project 3',
        image: 'assets/companies/company3.png',
      },
      taskCount: 49,
    },
  ];

  constructor() {}

  getProjectApprovals(): Observable<ProjectApproval[]> {
    return of(this._projectApprovals);
  }
}
