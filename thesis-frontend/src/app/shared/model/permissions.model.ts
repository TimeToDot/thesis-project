import { ProjectPermissions } from '../../projects/models/project-permissions.model';

export interface Permissions {
  projects: ProjectPermissions[];
  canCreateEmployee: boolean;
  canAdminEmployees: boolean;
  canAdminSettings: boolean;
  canAdminPositions: boolean;
}
