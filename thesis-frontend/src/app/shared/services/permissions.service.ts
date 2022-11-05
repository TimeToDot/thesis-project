import { Injectable } from '@angular/core';
import { Permissions } from '../model/permissions.model';

@Injectable({
  providedIn: 'root',
})
export class PermissionsService {
  private _permissions: Permissions = {
    canReadProject: true,
    canManageTasks: true,
    canManageProjectUsers: true,
    canManageApprovals: true,
    canAdminProjects: true,
    canAdminUsers: true,
    canAdminSettings: true,
  };

  constructor() {}

  getUserPermissions(): Permissions {
    return this._permissions;
  }
}
