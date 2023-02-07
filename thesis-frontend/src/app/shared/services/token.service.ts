import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly TOKEN_KEY = 'thesis';
  private readonly EMPLOYEE_KEY = 'employeeId';

  constructor(private cookieService: CookieService) {}

  signOut(): void {
    this.cookieService.delete(this.TOKEN_KEY);
  }

  saveToken(token: string): void {
    this.cookieService.set(
      this.TOKEN_KEY,
      token.split(';')[0].split('=')[1],
      new Date(token.split(';')[3].split('=')[1])
    );
  }

  getToken(): string {
    const token = this.cookieService.get(this.TOKEN_KEY);
    return token;
  }

  saveEmployee(employeeId: string): void {
    window.localStorage.removeItem(this.EMPLOYEE_KEY);
    window.localStorage.setItem(this.EMPLOYEE_KEY, JSON.stringify(employeeId));
  }

  getEmployee(): string | null {
    const employeeId = window.localStorage.getItem(this.EMPLOYEE_KEY);
    return employeeId ? JSON.parse(employeeId) : null;
  }
}
