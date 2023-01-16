import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, map, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginData } from '../models/login-data.model';
import { LoginResponse } from '../models/login-response.model';
import { PermissionsService } from './permissions.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private isLoggedIn: boolean = false;

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
    return this.tokenService.getEmployee() as string;
  }

  readFromLocalStorage(): void {
    const token = this.tokenService.getToken();
    if (token) {
      this.isLoggedIn = true;
    }
  }

  login(loginData: LoginData): Observable<boolean> {
    return this.http
      .post<LoginResponse>(
        `${environment.apiUrl}/authentication/login`,
        loginData,
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
          observe: 'response',
        }
      )
      .pipe(
        first(),
        tap(res => {
          const cookie = res.headers.get('Cookie') as string;
          const data = res.body as LoginResponse;
          this.tokenService.saveToken(cookie);
          console.log(data);
          this.tokenService.saveEmployee(data.id);
          this.isLoggedIn = true;
          this.permissionsService.setEmployeePermissions(data);
        }),
        map(() => true)
      );
  }

  logout(): void {
    this.isLoggedIn = false;
    this.tokenService.signOut();
  }
}
