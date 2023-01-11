import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

import { LoginGuard } from './login.guard';

describe('LoginGuard', () => {
  let authService: AuthService;
  let guard: LoginGuard;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    authService = TestBed.inject(AuthService);
    guard = TestBed.inject(LoginGuard);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the unauthenticated employee to access login page', () => {
    const mockAuthService = {
      getLoggedInStatus: (): boolean => {
        return false;
      },
    };

    const authServiceSpy = spyOn(authService, 'getLoggedInStatus').and.callFake(
      mockAuthService.getLoggedInStatus
    );

    guard.canActivate(routeMock, routeStateMock);

    expect(authServiceSpy).toHaveBeenCalled();
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });

  it('should redirect an authenticated employee to the dashboard route', () => {
    const mockAuthService = {
      getLoggedInStatus: (): boolean => {
        return true;
      },
    };

    const authServiceSpy = spyOn(authService, 'getLoggedInStatus').and.callFake(
      mockAuthService.getLoggedInStatus
    );
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(authServiceSpy).toHaveBeenCalled();
    expect(routerParseUrlSpy).toHaveBeenCalledWith('/dashboard');
  });
});
