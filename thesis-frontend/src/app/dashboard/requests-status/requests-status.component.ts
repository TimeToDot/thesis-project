import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { RouterLinkWithHref } from '@angular/router';
import { Approval } from '../../tracker/models/approval.model';
import { AuthService } from '../../shared/services/auth.service';
import { EmployeesService } from '../../admin/services/employees.service';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-requests-status',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './requests-status.component.html',
})
export class RequestsStatusComponent implements OnInit {
  projectApprovals: Approval[] = [];

  constructor(
    private authService: AuthService,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.getProjectsApprovals();
  }

  getProjectsApprovals(): void {
    this.employeesService
      .getProjectsToApprove(this.authService.getLoggedEmployeeId())
      .pipe(first())
      .subscribe(projectApprovals => {
        this.projectApprovals = projectApprovals;
      });
  }
}
