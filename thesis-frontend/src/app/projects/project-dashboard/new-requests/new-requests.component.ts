import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectApproval } from '../../models/project-approval.model';
import { ProjectApprovalsService } from '../../services/project-approvals.service';
import { first } from 'rxjs';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-new-requests',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
  templateUrl: './new-requests.component.html',
})
export class NewRequestsComponent {
  projectApprovals: ProjectApproval[] = [];

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
        .subscribe(projectApprovals => {
          this.projectApprovals = projectApprovals.slice(0, 6);
        });
    }
  }
}
