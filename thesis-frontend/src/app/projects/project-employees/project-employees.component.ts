import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { Employee } from '../../shared/model/employee.model';
import { ProjectEmployeesService } from '../services/project-employees.service';

@Component({
  selector: 'bvr-project-employees',
  templateUrl: './project-employees.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
  ],
})
export class ProjectEmployeesComponent implements OnInit {
  dataSource: Employee[] = [];
  displayedColumns: string[] = [
    'person',
    'position',
    'employmentDate',
    'actions',
  ];
  query: string = '';
  showActive: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private projectEmployeesService: ProjectEmployeesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.dataSource = this.projectEmployeesService.getProjectEmployees();
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
