export interface Account {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  positionId: string;
  employmentDate: string;
  workingTime: number;
  exitDate?: string;
  image: string;
  sex: string;
  birthDate: string;
  phoneNumber: string;
  privateEmail: string;
  street: string;
  houseNumber: string;
  apartmentNumber: string;
  city: string;
  postalCode: string;
  country: string;
  accountNumber: string;
  active: boolean;
}
