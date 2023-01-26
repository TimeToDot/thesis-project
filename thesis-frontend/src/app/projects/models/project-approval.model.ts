import { Day } from '../../calendar/models/day.model';

export interface ProjectApproval {
  projectEmployeeId: string;
  employeeId: string;
  tasks: Day[];
}
