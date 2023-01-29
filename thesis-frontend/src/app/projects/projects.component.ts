import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLinkWithHref } from '@angular/router';
import { first } from 'rxjs';
import { EmployeesService } from '../admin/services/employees.service';
import { ButtonComponent } from '../shared/components/button/button.component';
import { ToastComponent } from '../shared/components/toast/toast.component';
import { AuthService } from '../shared/services/auth.service';
import { ProjectsService } from '../shared/services/projects.service';
import { Project } from './models/project.model';

@Component({
  selector: 'bvr-projects',
  templateUrl: './projects.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
})
export class ProjectsComponent implements OnInit {
  projects: Project[] = [];
  query: string = '';
  showActive: boolean = true;

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService,
    private projectsService: ProjectsService
  ) {}

  ngOnInit(): void {
    this.getProjects();
  }

  showActiveProjects(active: boolean): void {
    active ? this.getProjects() : this.getArchivedProjects();
  }

  getProjects(): void {
    this.projectsService
      .getProjects()
      .pipe(first())
      .subscribe(projects => {
        this.projects = projects;
        this.getEmployeeProjects();
      });
  }

  getArchivedProjects(): void {
    this.projectsService
      .getArchivedProjects()
      .pipe(first())
      .subscribe(projects => {
        this.projects = projects;
        this.getEmployeeProjects();
      });
  }

  getEmployeeProjects(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    this.employeesService
      .getActiveEmployeeProjects(employeeId)
      .pipe(first())
      .subscribe(employeeProjects => {
        const ep = employeeProjects.map(
          employeeProject => employeeProject.project.id
        );
        this.projects = this.projects.filter(project =>
          ep.includes(project.id)
        );
      });
  }
}
