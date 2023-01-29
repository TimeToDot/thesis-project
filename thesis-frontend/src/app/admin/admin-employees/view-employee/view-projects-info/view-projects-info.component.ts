import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesService } from '../../../services/employees.service';
import { first } from 'rxjs';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';
import { EmployeeProject } from '../../../../shared/models/employee-project.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'bvr-view-projects-info',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-projects-info.component.html',
})
export class ViewProjectsInfoComponent implements OnInit {
  employeeProjects!: EmployeeProject[];

  constructor(
    private employeesService: EmployeesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects(): void {
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (employeeId) {
      this.employeesService
        .getEmployeeProjects(employeeId)
        .pipe(first())
        .subscribe(employeeProjects => {
          this.employeeProjects = employeeProjects;
        });
    }
  }
}
