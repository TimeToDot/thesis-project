import { Component, OnInit } from '@angular/core';
import { LinkOption } from '../../shared/model/link-option.model';
import { Permissions } from '../../shared/model/permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

@Component({
  selector: 'bvr-project',
  templateUrl: './project.component.html',
  styles: [],
})
export class ProjectComponent implements OnInit {
  navbarOptions: LinkOption[] = [];

  constructor(private permissionsService: PermissionsService) {}

  ngOnInit(): void {
    this.getNavbarOptions();
  }

  getNavbarOptions(): void {
    const permissions = this.permissionsService.getUserPermissions();

    this.getDashboardOption(permissions);
    this.getTasksOption(permissions);
    this.getUsersOption(permissions);
    this.getApprovalsOption(permissions);
    this.getSettingsOption(permissions);
  }

  getDashboardOption(permissions: Permissions): void {
    if (permissions.canReadProject) {
      this.navbarOptions.push({ name: 'Dashboard', path: 'dashboard' });
    }
  }

  getTasksOption(permissions: Permissions): void {
    if (permissions.canManageTasks) {
      this.navbarOptions.push({ name: 'Tasks', path: 'tasks' });
    }
  }

  getUsersOption(permissions: Permissions): void {
    if (permissions.canManageProjectUsers) {
      this.navbarOptions.push({ name: 'Users', path: 'users' });
    }
  }

  getApprovalsOption(permissions: Permissions): void {
    if (permissions.canManageApprovals) {
      this.navbarOptions.push({ name: 'Approvals', path: 'approvals' });
    }
  }

  getSettingsOption(permissions: Permissions): void {
    if (permissions.canAdminProjects) {
      this.navbarOptions.push({ name: 'Settings', path: 'settings' });
    }
  }
}
