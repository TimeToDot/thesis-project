export interface ProjectPermissions {
  id: string;
  canReadProject: boolean;
  canManageTasks: boolean;
  canManageProjectUsers: boolean;
  canManageApprovals: boolean;
  canAdminProjects: boolean;
}
