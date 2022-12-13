import { Injectable } from '@angular/core';
import { Project } from '../../projects/models/project.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeProjectsService {
  private _projects: Project[] = [
    { id: '1', name: 'Project A', image: 'assets/companies/company1.png' },
    { id: '2', name: 'Project B', image: 'assets/companies/company2.png' },
    { id: '3', name: 'Project C', image: 'assets/companies/company3.png' },
  ];

  constructor() {}

  getEmployeeProjects(): Project[] {
    return this._projects;
  }
}
