import { DropdownOption } from '../../shared/models/dropdown-option.model';

export interface GlobalSettings {
  requireConfirmationOnTaskSubmission: boolean;
  showDashboards: boolean;
  showProjects: boolean;
  defaultPage: DropdownOption;
  companyName: string;
  regon: string;
  nip: string;
  krs: string;
  image?: string;
  description?: string;
  street: string;
  houseNumber: string;
  apartmentNumber?: string;
  city: string;
  postalCode: string;
  country: DropdownOption;
  phoneNumber: string;
  email: string;
  website: string;
}
