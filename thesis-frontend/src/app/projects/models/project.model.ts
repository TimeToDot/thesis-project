import { Account } from '../../shared/models/account.model';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

export interface Project {
  id: string;
  name: string;
  image: string;
  description?: string;
  moderator: Account;
  employeesCount: number;
  creationDate: string;
  archiveDate?: string;
  billingPeriod: DropdownOption;
  overtimeModifier?: number;
  bonusModifier?: number;
  nightModifier?: number;
  holidayModifier?: number;
  active: boolean;
  moderatorId?: string;
}
