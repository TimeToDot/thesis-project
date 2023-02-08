import { Employee } from '../../shared/models/employee.model';

export interface ProjectEmployee {
  projectEmployeeId: string;
  projectId: string;
  employee: Employee;
  workingTime: number;
  salaryModifier?: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
  employeeId?: string;
}
