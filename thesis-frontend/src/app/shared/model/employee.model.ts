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
  active: boolean;
}
