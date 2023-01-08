import { Project } from '../../projects/models/project.model';
import { Status } from '../../shared/enum/status.enum';

export interface Approval {
  approve: boolean;
  project: Project;
  taskCount: number;
  status: Status;
  updateDate: string;
}
