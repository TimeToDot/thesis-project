import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Project } from '../../projects/models/project.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  private _projects: Project[] = [
    {
      id: '1',
      name: 'Volkswagen',
      image: 'assets/companies/company1.png',
      employeesCount: 50,
      creationDate: '2021-03-11',
      active: true,
    },
    {
      id: '2',
      name: 'IKEA',
      image: 'assets/companies/company2.png',
      employeesCount: 50,
      creationDate: '2021-03-11',
      active: true,
    },
    {
      id: '3',
      name: 'Starbucks',
      image: 'assets/companies/company3.png',
      employeesCount: 50,
      creationDate: '2021-03-11',
      active: true,
    },
    {
      id: '4',
      name: "McDonald's",
      image: 'assets/companies/company4.png',
      employeesCount: 50,
      creationDate: '2021-03-11',
      active: false,
    },
    {
      id: '5',
      name: 'Jeep',
      image: 'assets/companies/company5.png',
      employeesCount: 50,
      creationDate: '2021-03-11',
      active: false,
    },
  ];

  constructor() {}

  getProject(id: string): Observable<Project> {
    const project = this._projects.find(project => project.id === id);
    return of(project as Project);
  }

  getProjects(): Observable<Project[]> {
    return of(this._projects.filter(project => project.active));
  }

  getArchivedProjects(): Observable<Project[]> {
    return of(this._projects.filter(project => !project.active));
  }

  getEmployeeProjects(): Observable<Project[]> {
    return of(this._projects);
  }
}
