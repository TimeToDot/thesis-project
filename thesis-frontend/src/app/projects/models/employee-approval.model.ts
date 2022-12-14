import { Status } from '../../shared/enum/status.enum';
import { Employee } from '../../shared/model/employee.model';

export interface EmployeeApproval {
  id: string;
  employee: Employee;
  status: Status;
  lastRequest: string;
}
