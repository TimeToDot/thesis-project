import { Injectable } from '@angular/core';
import { Project } from '../../projects/model/project.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeProjectsService {
  private _projects: Project[] = [
    { id: '1', name: 'Project A' },
    { id: '2', name: 'Project B' },
    { id: '3', name: 'Project C' },
  ];

  constructor() {}

  getEmployeeProjects(): Project[] {
    return this._projects;
  }
}
