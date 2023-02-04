import { Position } from '../../admin/models/position.model';
import { DropdownOption } from './dropdown-option.model';

export interface Employee {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  image: string;
  position: Position;
  employmentDate: string;
  exitDate?: string;
  workingTime: number;
  wage: number;
  contractType: DropdownOption;
  active: boolean;
  imagePath?: string;
}
