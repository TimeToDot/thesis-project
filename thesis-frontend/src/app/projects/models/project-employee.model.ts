import { Employee } from '../../shared/models/employee.model';
import { ContractType } from './contract-type.model';

export interface ProjectEmployee {
  id: string;
  employee: Employee;
  contractType: ContractType;
  workingTime: number;
  wage: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
