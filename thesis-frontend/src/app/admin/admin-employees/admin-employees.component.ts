import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { Employee } from '../../shared/model/employee.model';
import { EmployeesService } from '../services/employees.service';

@Component({
  selector: 'bvr-admin-employees',
  templateUrl: './admin-employees.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
  ],
})
export class AdminEmployeesComponent implements OnInit {
  dataSource: Employee[] = [];
  displayedColumns: string[] = [
    'person',
    'position',
    'employmentDate',
    'actions',
  ];
  query: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.dataSource = this.employeesService.getEmployees();
  }

  editEmployee(event: Event, row: Employee): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  showEmployeeDetails(row: Employee): void {
    console.log(row.id);
    this.router.navigate([row.id], { relativeTo: this.route });
  }
}
