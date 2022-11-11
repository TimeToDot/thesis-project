import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

import { AuthGuard } from './auth.guard';

describe('AuthGuard', () => {
  let authService: AuthService;
  let guard: AuthGuard;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    authService = TestBed.inject(AuthService);
    guard = TestBed.inject(AuthGuard);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should redirect an unauthenticated user to the login route', () => {
    const mockAuthService = {
      getLoggedInStatus: (): boolean => {
        return false;
      },
    };

    const authServiceSpy = spyOn(authService, 'getLoggedInStatus').and.callFake(
      mockAuthService.getLoggedInStatus
    );
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(authServiceSpy).toHaveBeenCalled();
    expect(routerParseUrlSpy).toHaveBeenCalledWith('/login');
  });

  it('should allow the authenticated user to access app', () => {
    spyOn(authService, 'getLoggedInStatus').and.returnValue(true);
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });
});
