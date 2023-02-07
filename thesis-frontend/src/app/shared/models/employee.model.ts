import { DropdownOption } from './dropdown-option.model';

export interface Employee {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  image: string;
  position: string;
  employmentDate: string;
  exitDate?: string;
  workingTime: number;
  wage: number;
  contractType: DropdownOption;
  active: boolean;
}
