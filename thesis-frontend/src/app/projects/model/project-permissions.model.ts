export interface ProjectPermissions {
  id: string;
  canReadProject: boolean;
  canManageTasks: boolean;
  canManageProjectEmployees: boolean;
  canManageApprovals: boolean;
  canAdminProjects: boolean;
  canAddProjectEmployee: boolean;
}
