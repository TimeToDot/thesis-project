import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly TOKEN_KEY = 'auth-token';

  constructor() {}

  signOut(): void {
    window.localStorage.clear();
  }

  saveToken(token: string): void {
    window.localStorage.removeItem(this.TOKEN_KEY);
    window.localStorage.setItem(this.TOKEN_KEY, JSON.stringify(token));
  }

  getToken(): string | null {
    const token = window.localStorage.getItem(this.TOKEN_KEY);
    return token ? JSON.parse(token) : null;
  }
}
