import { Employee } from '../../shared/models/employee.model';

export interface ProjectEmployee {
  id: string;
  employee: Employee;
  workingTime: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
