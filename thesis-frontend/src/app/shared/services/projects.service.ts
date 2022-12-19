import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Project } from '../../projects/models/project.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectsService {
  private _projects: Project[] = [
    { id: '1', name: 'Project A', image: 'assets/companies/company1.png' },
    { id: '2', name: 'Project B', image: 'assets/companies/company2.png' },
    { id: '3', name: 'Project C', image: 'assets/companies/company3.png' },
  ];

  constructor() {}

  getProjects(): Observable<Project[]> {
    return of(this._projects);
  }

  getEmployeeProjects(): Observable<Project[]> {
    return of(this._projects);
  }
}
