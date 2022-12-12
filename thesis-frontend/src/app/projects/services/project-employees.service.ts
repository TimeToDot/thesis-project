import { Injectable } from '@angular/core';
import { ProjectEmployee } from '../model/project-employee.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectEmployeesService {
  private _projectEmployees: ProjectEmployee[] = [
    {
      id: '0',
      firstName: 'Robert',
      lastName: 'Skrzypczak',
      email: 'robert.skrzypczak@gmail.com',
      image: 'assets/icons/icon14.png',
      position: 'Frontend Developer',
      employmentDate: '2021-08-15',
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      active: true,
    },
    {
      id: '1',
      firstName: 'Beata',
      lastName: 'Iwan',
      email: 'beata.iwan@gmail.com',
      image: 'assets/icons/icon4.png',
      position: 'Product Designer',
      employmentDate: '2022-07-01',
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      active: true,
    },
    {
      id: '3',
      firstName: 'Franciszek',
      lastName: 'Migas',
      email: 'franciszek.migas@gmail.com',
      image: 'assets/icons/icon17.png',
      position: 'Forklift Operator',
      employmentDate: '2020-11-27',
      contractType: 'B2B',
      workingTime: 40,
      wage: 35,
      joinDate: '2021-09-10',
      exitDate: '2022-04-23',
      active: false,
    },
    {
      id: '4',
      firstName: 'Edyta',
      lastName: 'Poręba',
      email: 'edyta.poreba@gmail.com',
      image: 'assets/icons/icon1.png',
      position: 'Warehouseman',
      employmentDate: '2022-07-01',
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
