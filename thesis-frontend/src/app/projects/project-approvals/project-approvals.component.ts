import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { first } from 'rxjs';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ProjectApproval } from '../models/project-approval.model';
import { ProjectApprovalsService } from '../services/project-approvals.service';

@Component({
  selector: 'bvr-project-approvals',
  templateUrl: './project-approvals.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterLinkWithHref,
    ToastComponent,
  ],
})
export class ProjectApprovalsComponent implements OnInit {
  dataSource: ProjectApproval[] = [];
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

  constructor(
    private projectApprovalsService: ProjectApprovalsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProjectApprovals();
  }

  getProjectApprovals(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectApprovalsService
        .getProjectApprovals(projectId)
        .pipe(first())
        .subscribe(projectApprovals => (this.dataSource = projectApprovals));
    }
  }

  getArchivedProjectApprovals(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectApprovalsService
        .getArchivedProjectApprovals(projectId)
        .pipe(first())
        .subscribe(projectApprovals => (this.dataSource = projectApprovals));
    }
  }

  showActiveTable(value: boolean): void {
    value ? this.getProjectApprovals() : this.getArchivedProjectApprovals();
  }
}
