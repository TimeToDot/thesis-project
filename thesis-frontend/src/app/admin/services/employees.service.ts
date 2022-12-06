import { Injectable } from '@angular/core';
import { Employee } from '../../shared/model/employee.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  private _employees: Employee[] = [
    {
      id: '0',
      firstName: 'Robert',
      lastName: 'Skrzypczak',
      position: 'Frontend Developer',
      employmentDate: '2021-08-15',
      active: true,
    },
    {
      id: '1',
      firstName: 'Beata',
      lastName: 'Iwan',
      position: 'Product Designer',
      employmentDate: '2022-07-01',
      active: true,
    },
  ];

  constructor() {}

  getEmployees(): Employee[] {
    return this._employees;
  }
}
