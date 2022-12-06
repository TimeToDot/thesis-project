import { Injectable } from '@angular/core';
import { Status } from '../../shared/enum/status.enum';
import { EmployeeApproval } from '../model/employee-approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesApprovalsService {
  private _employeesApprovals: EmployeeApproval[] = [
    {
      id: '0',
      firstName: 'Robert',
      lastName: 'Skrzypczak',
      status: Status.Pending,
      lastRequest: '2021-09-15',
    },
    {
      id: '1',
      firstName: 'Beata',
      lastName: 'Iwan',
      status: Status.Rejected,
      lastRequest: '2022-07-01',
    },
  ];

  constructor() {}

  getEmployeesApprovals(): EmployeeApproval[] {
    return this._employeesApprovals;
  }
}
