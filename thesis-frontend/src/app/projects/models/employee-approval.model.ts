import { Status } from '../../shared/enum/status.enum';
import { ProjectEmployee } from './project-employee.model';

export interface EmployeeApproval {
  id: string;
  projectEmployee: ProjectEmployee;
  status: Status;
  lastRequest: string;
}
