import { Injectable } from '@angular/core';
import { delay, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn: boolean = false;
  redirectUrl: string | null = null;

  constructor() {
    this.readFromLocalStorage();
  }

  readFromLocalStorage(): void {
    const isLogged = localStorage.getItem('user');
    if (isLogged) {
      this.isLoggedIn = JSON.parse(isLogged);
    }
  }

  login(): Observable<boolean> {
    return of(true).pipe(
      delay(1000),
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
