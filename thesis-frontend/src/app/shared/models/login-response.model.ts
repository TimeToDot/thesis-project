export interface LoginResponse {
  id: string;
  username: string;
  email: string;
  globalAuthorities: string[];
  projectPrivileges: { [key: string]: string[] };
}
