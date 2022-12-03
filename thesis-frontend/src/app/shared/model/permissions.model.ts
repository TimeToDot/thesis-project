import { ProjectPermissions } from '../../projects/model/project-permissions.model';

export interface Permissions {
  projects: ProjectPermissions[];
  canCreateEmployee: boolean;
  canAdminEmployees: boolean;
  canAdminSettings: boolean;
  canAdminPositions: boolean;
}
