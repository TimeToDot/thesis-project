import { Injectable } from '@angular/core';
import { Permissions } from '../models/permissions.model';
import { ProjectPermissions } from '../../projects/models/project-permissions.model';
import { LoginResponse } from '../models/login-response.model';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class PermissionsService {
  private _permissions: Permissions = {
    projects: [],
    canAddEmployee: true,
    canAddProject: true,
    canAdminEmployees: true,
    canAdminSettings: true,
    canAdminPositions: true,
  };

  constructor(private tokenService: TokenService) {
    this.readFromLocalStorage();
  }

  readFromLocalStorage(): void {
    const permissions = this.tokenService.getPermissions();
    if (permissions) {
      this._permissions = permissions;
    }
  }

  getEmployeePermissions(): Permissions {
    return this._permissions;
  }

  getProjectPermissions(index: string | null): ProjectPermissions {
    return this._permissions.projects.find(
      project => project.id === index
    ) as ProjectPermissions;
  }

  setEmployeePermissions(loginData: LoginResponse): void {
    this._permissions = {
      projects: this.setProjectsPermissions(loginData),
      canAddEmployee: loginData.globalAuthorities.includes('CAN_CREATE_USERS'),
      canAddProject: true,
      canAdminEmployees:
        loginData.globalAuthorities.includes('CAN_ADMIN_USERS'),
      canAdminSettings:
        loginData.globalAuthorities.includes('CAN_ADMIN_SETTINGS'),
      canAdminPositions: loginData.globalAuthorities.includes(
        'CAN_ADMIN_POSITIONS'
      ),
    };
    this.tokenService.savePermissions(this._permissions);
  }

  setProjectsPermissions(loginData: LoginResponse): ProjectPermissions[] {
    const projectsPermissions: ProjectPermissions[] = [];
    Object.keys(loginData.projectPrivileges).forEach(key => {
      const projectPermissions = {
        id: key,
        canReadProject:
          loginData.projectPrivileges[key].includes('CAN_READ_PROJECT'),
        canManageTasks:
          loginData.projectPrivileges[key].includes('CAN_MANAGE_TASKS'),
        canManageProjectEmployees: loginData.projectPrivileges[key].includes(
          'CAN_MANAGE_PROJECT_USERS'
        ),
        canManageApprovals:
          loginData.projectPrivileges[key].includes('CAN_ADMIN_PROJECT'),
        canAdminProjects:
          loginData.projectPrivileges[key].includes('CAN_ADMIN_PROJECT'),
        canAddProjectEmployee:
          loginData.projectPrivileges[key].includes('CAN_ADMIN_PROJECT'),
      };
      projectsPermissions.push(projectPermissions);
    });
    console.log(projectsPermissions);
    return projectsPermissions;
  }
}
