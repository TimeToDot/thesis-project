import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, map, Observable, tap } from 'rxjs';
import { LoginData } from '../models/login-data.model';
import { LoginResponse } from '../models/login-response.model';
import { PermissionsService } from './permissions.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedIn: boolean = false;
  private url: string = 'http://localhost:8080/thesis/api/authentication';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    observe: 'response',
  };

  redirectUrl: string | null = null;

  constructor(
    private http: HttpClient,
    private permissionsService: PermissionsService,
    private tokenService: TokenService
  ) {
    this.readFromLocalStorage();
  }

  getLoggedInStatus(): boolean {
    return this.isLoggedIn;
  }

  getLoggedEmployeeId(): string {
    return this.tokenService.getToken() as string;
  }

  readFromLocalStorage(): void {
    const token = this.tokenService.getToken();
    if (token) {
      this.isLoggedIn = true;
    }
  }

  login(loginData: LoginData): Observable<boolean> {
    return this.http
      .post<LoginResponse>(`${this.url}/login`, loginData, {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        observe: 'response',
      })
      .pipe(
        first(),
        tap(res => {
          console.log(res.headers.get('Cookie'));
          // this.tokenService.saveToken(res.body?.id);
          // this.isLoggedIn = true;
          // this.permissionsService.setEmployeePermissions(data);
        }),
        map(() => true)
      );
  }

  logout(): void {
    this.isLoggedIn = false;
    this.tokenService.signOut();
  }
}
