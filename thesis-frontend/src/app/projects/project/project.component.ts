import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Data,
  RouterLinkActive,
  RouterLinkWithHref,
  RouterOutlet,
} from '@angular/router';
import { TabsComponent } from '../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../shared/models/link-option.model';
import { ProjectPermissions } from '../models/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';
import { tabAnimation } from '../../shared/animations/tab.animation';
import { ButtonComponent } from '../../shared/components/button/button.component';

@Component({
  selector: 'bvr-project',
  templateUrl: './project.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    RouterOutlet,
    RouterLinkActive,
    RouterLinkWithHref,
    TabsComponent,
  ],
  animations: [tabAnimation],
})
export class ProjectComponent implements OnInit {
  private projectId: string | null = null;

  navbarOptions: LinkOption[] = [];

  constructor(
    private contexts: ChildrenOutletContexts,
    private permissionsService: PermissionsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getProjectId();
    this.getNavbarOptions();
  }

  getProjectId(): void {
    this.projectId = this.route.snapshot.paramMap.get('id');
  }

  getNavbarOptions(): void {
    const permissions = this.permissionsService.getProjectPermissions(
      this.projectId
    );

    if (permissions) {
      this.getDashboardOption(permissions);
      this.getTasksOption(permissions);
      this.getEmployeesOption(permissions);
      this.getApprovalsOption(permissions);
    }
  }

  getDashboardOption(permissions: ProjectPermissions): void {
    if (permissions.canReadProject) {
      this.navbarOptions.push({ name: 'Dashboard', path: 'dashboard' });
    }
  }

  getTasksOption(permissions: ProjectPermissions): void {
    if (permissions.canManageTasks) {
      this.navbarOptions.push({ name: 'Tasks', path: 'tasks' });
    }
  }

  getEmployeesOption(permissions: ProjectPermissions): void {
    if (permissions.canManageProjectEmployees) {
      this.navbarOptions.push({ name: 'Employees', path: 'employees' });
    }
  }

  getApprovalsOption(permissions: ProjectPermissions): void {
    if (permissions.canManageApprovals) {
      this.navbarOptions.push({ name: 'Approvals', path: 'approvals' });
    }
  }

  getRouteAnimationData(): Data {
    return this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }
}
