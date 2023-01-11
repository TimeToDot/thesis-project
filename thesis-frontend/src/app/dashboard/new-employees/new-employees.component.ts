import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { Employee } from '../../shared/models/employee.model';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';
import { RouterLinkWithHref } from '@angular/router';

@Component({
  selector: 'bvr-new-employees',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './new-employees.component.html',
})
export class NewEmployeesComponent implements OnInit {
  employees: Employee[] = [];

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    this.getEmployees();
  }
  getEmployees(): void {
    this.employeesService
      .getEmployees()
      .pipe(first())
      .subscribe(employees => {
        this.employees = employees.slice(0, 5);
      });
  }
}
