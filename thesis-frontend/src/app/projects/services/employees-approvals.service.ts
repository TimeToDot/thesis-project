import { Injectable } from '@angular/core';
import { Status } from '../../shared/enum/status.enum';
import { EmployeeApproval } from '../models/employee-approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesApprovalsService {
  private _employeesApprovals: EmployeeApproval[] = [
    {
      id: '0',
      employee: {
        id: '0',
        firstName: 'Robert',
        lastName: 'Skrzypczak',
        email: 'robert.skrzypczak@gmail.com',
        image: 'assets/icons/icon14.png',
        position: 'Frontend Developer',
        employmentDate: '2021-08-15',
        workingTime: 40,
        active: true,
      },
      status: Status.Pending,
      lastRequest: '2021-09-15',
    },
    {
      id: '1',
      employee: {
        id: '1',
        firstName: 'Beata',
        lastName: 'Iwan',
        email: 'beata.iwan@gmail.com',
        image: 'assets/icons/icon4.png',
        position: 'Product Designer',
        employmentDate: '2022-07-01',
        workingTime: 40,
        active: true,
      },
      status: Status.Rejected,
      lastRequest: '2022-07-01',
    },
    {
      id: '2',
      employee: {
        id: '2',
        firstName: 'Joanna',
        lastName: 'Malawska',
        email: 'joanna.malawska@gmail.com',
        image: 'assets/icons/icon8.png',
        position: 'Product Designer',
        employmentDate: '2022-07-01',
        exitDate: '2022-10-10',
        workingTime: 40,
        active: false,
      },
      status: Status.Approved,
      lastRequest: '2022-07-01',
    },
  ];

  constructor() {}

  getEmployeesApprovals(): EmployeeApproval[] {
    return this._employeesApprovals.filter(
      employeeApproval => employeeApproval.employee.active
    );
  }

  getArchivedEmployeesApprovals(): EmployeeApproval[] {
    return this._employeesApprovals.filter(
      employeeApproval => !employeeApproval.employee.active
    );
  }
}
