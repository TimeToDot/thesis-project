import { Project } from '../../projects/models/project.model';

export interface ProjectApproval {
  approve: boolean;
  project: Project;
  taskCount: number;
}
