import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';
import { TokenService } from '../../shared/services/token.service';

@Component({
  selector: 'bvr-user-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-projects.component.html',
})
export class UserProjectsComponent implements OnInit {
  @Input() isCompact: boolean = false;

  employee!: Account;
  employeeProjects: EmployeeProject[] = [];

  constructor(
    private employeesService: EmployeesService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.getEmployee();
    this.getEmployeeProjects();
  }

  getEmployee(): void {
    this.employeesService
      .getEmployee(this.tokenService.getEmployee())
      .pipe(first())
      .subscribe(employee => {
        this.employee = employee;
      });
  }

  getEmployeeProjects(): void {
    this.employeesService
      .getActiveEmployeeProjects(this.tokenService.getEmployee())
      .pipe(first())
      .subscribe(employeeProjects => {
        this.employeeProjects = employeeProjects;
      });
  }
}
