import { Status } from '../../shared/enum/status.enum';
import { ProjectEmployee } from './project-employee.model';

export interface ProjectApproval {
  id: string;
  projectId: string;
  projectEmployee: ProjectEmployee;
  status: Status;
  lastRequest: string;
}
