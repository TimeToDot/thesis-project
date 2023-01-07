export interface GlobalSettings {
  requireConfirmationOnTaskSubmission: boolean;
  showDashboards: boolean;
  showProjects: boolean;
  defaultPage: string;
  companyName: string;
  regon: string;
  nip: string;
  krs: string;
  image: string;
  description?: string;
  street: string;
  houseNumber: string;
  apartmentNumber?: string;
  city: string;
  postalCode: string;
  country: { id: string; name: string };
  phoneNumber: string;
  email: string;
  website: string;
}
