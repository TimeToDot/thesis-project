import { Injectable } from '@angular/core';
import { Permissions } from '../model/permissions.model';

@Injectable({
  providedIn: 'root',
})
export class PermissionsService {
  private _permissions: Permissions = {
    canReadProject: false,
    canManageTasks: true,
    canManageProjectUsers: true,
    canManageApprovals: true,
    canReadAdmin: true,
    canAdminProjects: true,
    canAdminUsers: true,
  };

  constructor() {}

  getUserPermissions(): Permissions {
    return this._permissions;
  }
}
