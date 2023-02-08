import { Day } from '../../calendar/models/day.model';
import { Employee } from '../../shared/models/employee.model';

export interface ProjectApproval {
  projectEmployeeId: string;
  employeeId: string;
  tasks: Day[];
  employee?: Employee;
  lastRequest?: string;
}
