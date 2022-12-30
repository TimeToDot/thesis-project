import { DropdownOption } from '../../shared/models/dropdown-option.model';
import { Employee } from '../../shared/models/employee.model';

export interface Project {
  id: string;
  name: string;
  image: string;
  description?: string;
  moderator: Employee;
  employeesCount: number;
  creationDate: string;
  archiveDate?: string;
  billingPeriod: DropdownOption;
  overtimeModifier?: number;
  bonusModifier?: number;
  nightModifier?: number;
  holidayModifier?: number;
  active: boolean;
}
