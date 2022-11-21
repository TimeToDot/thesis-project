import { ProjectPermissions } from './project-permissions.model';

export interface Permissions {
  projects: ProjectPermissions[];
  canAdminUsers: boolean;
  canAdminSettings: boolean;
  canAdminPositions: boolean;
}
