import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Project } from '../../models/project.model';
import { ProjectsService } from '../../../shared/services/projects.service';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { first } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { PermissionsService } from '../../../shared/services/permissions.service';
import { ProjectPermissions } from '../../models/project-permissions.model';
import { EmployeesService } from '../../../admin/services/employees.service';

@Component({
  selector: 'bvr-project-description',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './project-description.component.html',
})
export class ProjectDescriptionComponent {
  project!: Project;
  projectPermissions!: ProjectPermissions;

  constructor(
    private employeesService: EmployeesService,
    private permissionsService: PermissionsService,
    private projectsService: ProjectsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProject();
    this.getProjectPermissions();
  }

  getProject(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectsService
        .getProject(projectId)
        .pipe(first())
        .subscribe(project => {
          this.project = project;
          this.getProjectModerator(project.moderatorId as string);
        });
    }
  }

  getProjectModerator(employeeId: string): void {
    this.employeesService
      .getEmployee(employeeId)
      .pipe(first())
      .subscribe(employee => {
        this.project.moderator = employee;
      });
  }

  getProjectPermissions(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectPermissions =
        this.permissionsService.getProjectPermissions(projectId);
    }
  }
}
