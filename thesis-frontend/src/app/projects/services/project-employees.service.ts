import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
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
        contractType: { id: '4', name: 'B2B' },
        wage: 45,
        workingTime: 40,
        active: true,
      },
      workingTime: 40,
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
        contractType: { id: '4', name: 'B2B' },
        wage: 45,
        workingTime: 40,
        active: true,
      },
      workingTime: 40,
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
  ];

  constructor() {}

  getProjectEmployee(id: string): Observable<ProjectEmployee> {
    const employee = this._projectEmployees.find(
      employee => employee.id === id
    );
    return of(employee as ProjectEmployee);
  }

  getProjectEmployees(): Observable<ProjectEmployee[]> {
    return of(this._projectEmployees.filter(employee => employee.active));
  }

  getArchivedProjectEmployees(): Observable<ProjectEmployee[]> {
    return of(this._projectEmployees.filter(employee => !employee.active));
  }
}
