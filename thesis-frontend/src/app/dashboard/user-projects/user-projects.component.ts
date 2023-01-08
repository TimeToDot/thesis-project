import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { AuthService } from '../../shared/services/auth.service';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-user-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-projects.component.html',
})
export class UserProjectsComponent implements OnInit {
  employee!: Account;
  employeeProjects: EmployeeProject[] = [];

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.getEmployee();
    this.getEmployeeProjects();
  }

  getEmployee(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => {
          this.employee = employee;
        });
    }
  }

  getEmployeeProjects(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
    if (employeeId) {
      this.employeesService
        .getActiveEmployeeProjects(employeeId)
        .pipe(first())
        .subscribe(employeeProjects => {
          this.employeeProjects = employeeProjects;
        });
    }
  }
}
