import { Injectable } from '@angular/core';
import { Employee } from '../../shared/model/employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  private _projectEmployees: Employee[] = [
    {
      id: '0',
      firstName: 'Robert',
      lastName: 'Skrzypczak',
      position: 'Frontend Developer',
      employmentDate: new Date(2021, 8, 15),
      active: true,
    },
    {
      id: '1',
      firstName: 'Beata',
      lastName: 'Iwan',
      position: 'Product Designer',
      employmentDate: new Date(2022, 7, 1),
      active: true,
    },
  ];

  constructor() {}

  getProjectEmployees(): Employee[] {
    return this._projectEmployees;
  }
}
