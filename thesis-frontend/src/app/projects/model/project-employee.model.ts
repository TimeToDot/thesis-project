export interface ProjectEmployee {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  image: string;
  position: string;
  employmentDate: string;
  contractType: string;
  workingTime: number;
  wage: number;
  joinDate: string;
  exitDate?: string;
  active: boolean;
}
