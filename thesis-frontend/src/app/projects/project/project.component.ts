import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LinkOption } from '../../shared/model/link-option.model';
import { ProjectPermissions } from '../../shared/model/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

@Component({
  selector: 'bvr-project',
  templateUrl: './project.component.html',
  styles: [],
})
export class ProjectComponent implements OnInit {
  private projectId: string | null = null;

  navbarOptions: LinkOption[] = [];

  constructor(
    private route: ActivatedRoute,
    private permissionsService: PermissionsService
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
      this.getUsersOption(permissions);
      this.getApprovalsOption(permissions);
      this.getSettingsOption(permissions);
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

  getUsersOption(permissions: ProjectPermissions): void {
    if (permissions.canManageProjectUsers) {
      this.navbarOptions.push({ name: 'Users', path: 'users' });
    }
  }

  getApprovalsOption(permissions: ProjectPermissions): void {
    if (permissions.canManageApprovals) {
      this.navbarOptions.push({ name: 'Approvals', path: 'approvals' });
    }
  }

  getSettingsOption(permissions: ProjectPermissions): void {
    if (permissions.canAdminProjects) {
      this.navbarOptions.push({ name: 'Settings', path: 'settings' });
    }
  }
}
