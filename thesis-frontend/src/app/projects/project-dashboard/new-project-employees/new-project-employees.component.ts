import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectEmployee } from '../../models/project-employee.model';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { first } from 'rxjs';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-new-project-employees',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './new-project-employees.component.html',
})
export class NewProjectEmployeesComponent {
  projectEmployees: ProjectEmployee[] = [];

  constructor(
    private projectEmployeesService: ProjectEmployeesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProjectEmployees();
  }

  getProjectEmployees(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectEmployeesService
        .getProjectEmployees(projectId)
        .pipe(first())
        .subscribe(projectEmployees => {
          this.projectEmployees = projectEmployees.slice(0, 5);
        });
    }
  }
}
