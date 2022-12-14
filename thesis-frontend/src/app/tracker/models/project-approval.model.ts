import { Project } from '../../projects/models/project.model';

export interface ProjectApproval {
  project: Project;
  taskCount: number;
}
