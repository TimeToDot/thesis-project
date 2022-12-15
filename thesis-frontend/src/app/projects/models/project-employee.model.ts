import { Employee } from '../../shared/models/employee.model';

export interface ProjectEmployee {
  id: string;
  employee: Employee;
  contractType: string;
  workingTime: number;
  wage: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
