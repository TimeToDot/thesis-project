import { DropdownOption } from '../../shared/models/dropdown-option.model';
import { Employee } from '../../shared/models/employee.model';

export interface ProjectEmployee {
  id: string;
  employee: Employee;
  contractType: DropdownOption;
  workingTime: number;
  wage: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
