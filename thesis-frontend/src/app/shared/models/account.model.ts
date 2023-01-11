import { Position } from '../../admin/models/position.model';
import { DropdownOption } from './dropdown-option.model';

export interface Account {
  id: string;
  firstName: string;
  middleName: string;
  lastName: string;
  sex: DropdownOption;
  birthDate: string;
  birthPlace: string;
  idCardNumber: string;
  pesel: number;
  street: string;
  houseNumber: string;
  apartmentNumber: string;
  city: string;
  postalCode: string;
  country: DropdownOption;
  phoneNumber: string;
  privateEmail: string;
  position: Position;
  employmentDate: string;
  exitDate?: string;
  contractType: DropdownOption;
  workingTime: number;
  wage: number;
  payday: number;
  accountNumber: string;
  email: string;
  password: string;
  image: string;
  active: boolean;
}
