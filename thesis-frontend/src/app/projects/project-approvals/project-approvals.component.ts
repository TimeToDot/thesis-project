import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { first } from 'rxjs';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { EmployeeApproval } from '../models/employee-approval.model';
import { EmployeesApprovalsService } from '../services/employees-approvals.service';

@Component({
  selector: 'bvr-project-approvals',
  templateUrl: './project-approvals.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
    ToastComponent,
  ],
})
export class ProjectApprovalsComponent implements OnInit {
  dataSource: EmployeeApproval[] = [];
  displayedActiveColumns: string[] = [
    'person',
    'position',
    'status',
    'last_request',
  ];
  displayedArchivedColumns: string[] = [
    'person',
    'position',
    'exit_date',
    'last_request',
  ];
  query: string = '';
  showActive: boolean = true;

  constructor(private employeesApprovalsService: EmployeesApprovalsService) {}

  ngOnInit(): void {
    this.employeesApprovalsService
      .getEmployeesApprovals()
      .pipe(first())
      .subscribe(employeesApprovals => (this.dataSource = employeesApprovals));
  }

  showActiveTable(value: boolean): void {
    if (value) {
      this.employeesApprovalsService
        .getEmployeesApprovals()
        .pipe(first())
        .subscribe(
          employeesApprovals => (this.dataSource = employeesApprovals)
        );
    } else {
      this.employeesApprovalsService
        .getArchivedEmployeesApprovals()
        .pipe(first())
        .subscribe(
          archivedEmployeesApprovals =>
            (this.dataSource = archivedEmployeesApprovals)
        );
    }
  }
}
