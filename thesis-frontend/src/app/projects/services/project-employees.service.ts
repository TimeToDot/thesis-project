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
      image: 'assets/icons/icon14.png',
      position: 'Frontend Developer',
      employmentDate: '2021-08-15',
      active: true,
    },
    {
      id: '1',
      firstName: 'Beata',
      lastName: 'Iwan',
      image: "assets/icons/icon4.png",
      position: 'Product Designer',
      employmentDate: '2022-07-01',
      active: true,
    },
  ];

  constructor() {}

  getProjectEmployees(): Employee[] {
    return this._projectEmployees;
  }
}
