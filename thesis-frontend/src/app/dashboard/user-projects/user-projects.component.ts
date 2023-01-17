import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../shared/models/account.model';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';
import { Project } from '../../projects/models/project.model';

@Component({
  selector: 'bvr-user-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-projects.component.html',
})
export class UserProjectsComponent implements OnInit {
  @Input() isCompact: boolean = false;

  employee!: Account;
  employeeProjects: Project[] = [];

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    this.getEmployee();
    this.getEmployeeProjects();
  }

  getEmployee(): void {
    this.employeesService
      .getCurrentEmployee()
      .pipe(first())
      .subscribe(employee => {
        this.employee = employee;
      });
  }

  getEmployeeProjects(): void {
    this.employeesService
      .getActiveEmployeeProjects()
      .pipe(first())
      .subscribe(employeeProjects => {
        this.employeeProjects = employeeProjects;
      });
  }
}
