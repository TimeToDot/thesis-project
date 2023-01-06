import { Injectable } from '@angular/core';
import { first, Observable, of, tap } from 'rxjs';
import { EmployeesService } from '../../admin/services/employees.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedIn: boolean = false;
  private loggedEmployeeId: string = '';

  redirectUrl: string | null = null;

  constructor(private employeesService: EmployeesService) {
    this.readFromLocalStorage();
  }

  getLoggedInStatus(): boolean {
    return this.isLoggedIn;
  }

  getLoggedEmployeeId(): string {
    return this.loggedEmployeeId;
  }

  readFromLocalStorage(): void {
    const isLogged = localStorage.getItem('isLoggedIn');
    if (isLogged) {
      this.isLoggedIn = JSON.parse(isLogged);
    }
    const employeeId = localStorage.getItem('employeeId');
    if (employeeId) {
      this.loggedEmployeeId = JSON.parse(employeeId);
    }
  }

  login(): Observable<boolean> {
    return of(true).pipe(
      tap(() => {
        this.isLoggedIn = true;
        localStorage.setItem('isLoggedIn', JSON.stringify(this.isLoggedIn));
        this.employeesService
          .getEmployee('1')
          .pipe(first())
          .subscribe(employee => {
            this.loggedEmployeeId = employee.id;
            localStorage.setItem(
              'employeeId',
              JSON.stringify(this.loggedEmployeeId)
            );
          });
      })
    );
  }

  logout(): void {
    this.isLoggedIn = false;
    this.loggedEmployeeId = '';
    localStorage.setItem('isLoggedIn', JSON.stringify(this.isLoggedIn));
    localStorage.setItem('employeeId', JSON.stringify(this.loggedEmployeeId));
  }
}
