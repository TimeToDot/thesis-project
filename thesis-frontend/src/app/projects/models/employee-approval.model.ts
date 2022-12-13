import { Status } from '../../shared/enum/status.enum';

export interface EmployeeApproval {
  id: string;
  firstName: string;
  lastName: string;
  image: string;
  status: Status;
  lastRequest: string;
}
