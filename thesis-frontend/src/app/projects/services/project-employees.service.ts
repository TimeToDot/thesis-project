import { Injectable } from '@angular/core';
import { ProjectEmployee } from '../models/project-employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  private _projectEmployees: ProjectEmployee[] = [
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
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      active: true,
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
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      active: true,
    },
    {
      id: '3',
      employee: {
        id: '2',
        firstName: 'Joanna',
        lastName: 'Malawska',
        email: 'joanna.malawska@gmail.com',
        image: 'assets/icons/icon8.png',
        position: 'Product Designer',
        employmentDate: '2022-07-01',
        workingTime: 40,
        active: true,
      },
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      exitDate: '2022-04-23',
      active: false,
    },
    {
      id: '4',
      employee: {
        id: '3',
        firstName: 'Anna',
        lastName: 'Nowak',
        email: 'anna.nowak@gmail.com',
        image: 'assets/icons/icon7.png',
        position: 'Product Designer',
        employmentDate: '2022-07-01',
        workingTime: 40,
        exitDate: '2022-09-01',
        active: false,
      },
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      exitDate: '2022-04-23',
      active: false,
    },
  ];

  constructor() {}

  getProjectEmployee(id: string): ProjectEmployee {
    const employee = this._projectEmployees.find(
      employee => employee.id === id
    );
    return employee as ProjectEmployee;
  }

  getProjectEmployees(): ProjectEmployee[] {
    return this._projectEmployees.filter(employee => employee.active);
  }

  getArchivedProjectEmployees(): ProjectEmployee[] {
    return this._projectEmployees.filter(employee => !employee.active);
  }
}
