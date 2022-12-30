import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Status } from '../../shared/enum/status.enum';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  private _projectApprovals: ProjectApproval[] = [
    {
      id: '0',
      projectEmployee: {
        id: '0',
        employee: {
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
        workingTime: 40,
        joinDate: '2021-09-10',
        active: true,
      },
      status: Status.Pending,
      lastRequest: '2021-09-15',
    },
    {
      id: '1',
      projectEmployee: {
        id: '1',
        employee: {
          id: '1',
          firstName: 'Beata',
          lastName: 'Iwan',
          email: 'beata.iwan@gmail.com',
          image: 'assets/icons/icon4.png',
          position: 'Product Designer',
          employmentDate: '2022-07-01',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          active: true,
        },
        workingTime: 40,
        joinDate: '2021-09-10',
        active: true,
      },
      status: Status.Rejected,
      lastRequest: '2022-07-01',
    },
    {
      id: '2',
      projectEmployee: {
        id: '3',
        employee: {
          id: '2',
          firstName: 'Joanna',
          lastName: 'Malawska',
          email: 'joanna.malawska@gmail.com',
          image: 'assets/icons/icon8.png',
          position: 'Product Designer',
          employmentDate: '2022-07-01',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          active: true,
        },
        workingTime: 40,
        joinDate: '2021-09-10',
        exitDate: '2022-04-23',
        active: false,
      },
      status: Status.Approved,
      lastRequest: '2022-07-01',
    },
    {
      id: '3',
      projectEmployee: {
        id: '4',
        employee: {
          id: '3',
          firstName: 'Anna',
          lastName: 'Nowak',
          email: 'anna.nowak@gmail.com',
          image: 'assets/icons/icon7.png',
          position: 'Product Designer',
          employmentDate: '2022-07-01',
          contractType: { id: '4', name: 'B2B' },
          wage: 45,
          workingTime: 40,
          exitDate: '2022-09-01',
          active: false,
        },
        workingTime: 40,
        joinDate: '2021-09-10',
        exitDate: '2022-04-23',
        active: false,
      },
      status: Status.Approved,
      lastRequest: '2022-07-01',
    },
  ];

  constructor() {}

  getProjectApproval(id: string): Observable<ProjectApproval> {
    const projectApproval = this._projectApprovals.find(
      projectApproval => projectApproval.id === id
    );
    return of(projectApproval as ProjectApproval);
  }

  getProjectApprovals(): Observable<ProjectApproval[]> {
    return of(
      this._projectApprovals.filter(
        projectApproval => projectApproval.projectEmployee.active
      )
    );
  }

  getArchivedProjectApprovals(): Observable<ProjectApproval[]> {
    return of(
      this._projectApprovals.filter(
        projectApproval => !projectApproval.projectEmployee.active
      )
    );
  }
}
