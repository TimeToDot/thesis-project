import { ProjectPermissions } from './project-permissions.model';

export interface Permissions {
  projects: ProjectPermissions[];
  canAddUser: boolean;
  canAdminUsers: boolean;
  canAdminSettings: boolean;
  canAdminPositions: boolean;
}
