import { Employee } from '../../shared/models/employee.model';

export interface ProjectEmployee {
  id: string;
  projectId: string;
  employee: Employee;
  workingTime: number;
  salaryModifier?: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
