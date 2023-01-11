import { Project } from '../../projects/models/project.model';

export interface EmployeeProject {
  project: Project;
  employeeId: string;
  joinDate: string;
  exitDate?: string;
}
