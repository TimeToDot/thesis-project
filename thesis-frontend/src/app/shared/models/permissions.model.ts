import { ProjectPermissions } from '../../projects/models/project-permissions.model';

export interface Permissions {
  projects: ProjectPermissions[];
  canAddEmployee: boolean;
  canAddProject: boolean;
  canAdminEmployees: boolean;
  canAdminSettings: boolean;
  canAdminPositions: boolean;
}
