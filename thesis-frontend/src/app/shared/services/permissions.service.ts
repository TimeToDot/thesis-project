import { Injectable } from '@angular/core';
import { Permissions } from '../models/permissions.model';
import { ProjectPermissions } from '../../projects/models/project-permissions.model';

@Injectable({
  providedIn: 'root',
})
export class PermissionsService {
  private _permissions: Permissions = {
    projects: [
      {
        id: '1',
        canReadProject: true,
        canManageTasks: true,
        canManageProjectEmployees: true,
        canManageApprovals: true,
        canAdminProjects: true,
        canAddProjectEmployee: true,
      },
      {
        id: '2',
        canReadProject: true,
        canManageTasks: false,
        canManageProjectEmployees: true,
        canManageApprovals: false,
        canAdminProjects: true,
        canAddProjectEmployee: true,
      },
      {
        id: '3',
        canReadProject: true,
        canManageTasks: true,
        canManageProjectEmployees: false,
        canManageApprovals: true,
        canAdminProjects: true,
        canAddProjectEmployee: false,
      },
    ],
    canAddEmployee: true,
    canAdminEmployees: true,
    canAdminSettings: true,
    canAdminPositions: true,
  };

  constructor() {}

  getEmployeePermissions(): Permissions {
    return this._permissions;
  }

  getProjectPermissions(index: string | null): ProjectPermissions {
    return this._permissions.projects.find(
      project => project.id === index
    ) as ProjectPermissions;
  }
}
