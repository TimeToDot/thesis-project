import { Position } from '../../admin/models/position.model';
import { ContractType } from '../../projects/models/contract-type.model';

export interface Account {
  id: string;
  firstName: string;
  middleName: string;
  lastName: string;
  sex: { id: string; name: string };
  birthDate: string;
  birthPlace: string;
  idCardNumber: string;
  pesel: number;
  street: string;
  houseNumber: string;
  apartmentNumber: string;
  city: string;
  postalCode: string;
  country: { id: string; name: string };
  phoneNumber: string;
  privateEmail: string;
  position: Position;
  employmentDate: string;
  exitDate?: string;
  contractType: ContractType;
  workingTime: number;
  rate: number;
  payday: number;
  accountNumber: string;
  email: string;
  password: string;
  image: string;
  active: boolean;
}
