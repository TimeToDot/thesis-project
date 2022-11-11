import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedIn: boolean = false;

  redirectUrl: string | null = null;

  constructor() {
    this.readFromLocalStorage();
  }

  getLoggedInStatus(): boolean {
    return this.isLoggedIn;
  }

  readFromLocalStorage(): void {
    const isLogged = localStorage.getItem('user');
    if (isLogged) {
      this.isLoggedIn = JSON.parse(isLogged);
    }
  }

  login(): Observable<boolean> {
    return of(true).pipe(
      tap(() => {
        this.isLoggedIn = true;
        localStorage.setItem('user', JSON.stringify(this.isLoggedIn));
      })
    );
  }

  logout(): void {
    this.isLoggedIn = false;
    localStorage.setItem('user', JSON.stringify(this.isLoggedIn));
  }
}
