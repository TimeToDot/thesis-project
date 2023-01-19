import { Status } from '../../shared/enum/status.enum';
import { Employee } from '../../shared/models/employee.model';

export interface ProjectApproval {
  projectEmployeeId: string;
  projectId: string;
  employee: Employee;
  status: Status;
  lastRequest: string;
}
