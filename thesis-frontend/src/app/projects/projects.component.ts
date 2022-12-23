import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { first } from 'rxjs';
import { ButtonComponent } from '../shared/components/button/button.component';
import { ProjectsService } from '../shared/services/projects.service';
import { Project } from './models/project.model';

@Component({
  selector: 'bvr-projects',
  templateUrl: './projects.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
})
export class ProjectsComponent implements OnInit {
  projects: Project[] = [];

  constructor(private projectsService: ProjectsService) {}

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects(): void {
    this.projectsService
      .getProjects()
      .pipe(first())
      .subscribe(projects => (this.projects = projects));
  }
}
