import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
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
    this.dataSource = this.employeesApprovalsService.getEmployeesApprovals();
  }

  showActiveTable(value: boolean): void {
    if (value) {
      this.dataSource = this.employeesApprovalsService.getEmployeesApprovals();
    } else {
      this.dataSource =
        this.employeesApprovalsService.getArchivedEmployeesApprovals();
    }
  }
}
