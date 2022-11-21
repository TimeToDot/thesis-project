import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Permissions } from '../../shared/model/permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

import { CanAdminPositionsGuard } from './can-admin-positions.guard';

describe('CanAdminPositionsGuard', () => {
  let guard: CanAdminPositionsGuard;
  let permissionsService: PermissionsService;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAdminPositionsGuard);
    permissionsService = TestBed.inject(PermissionsService);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the authorized user to access admin positions page', () => {
    guard.canActivate(routeMock, routeStateMock);
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });

  it('should redirect an unauthorized user to the dashboard route', () => {
    const mockPermissionsService = {
      getUserPermissions: (): Permissions => {
        return {
          projects: [],
          canAdminUsers: false,
          canAdminSettings: false,
          canAdminPositions: false,
        };
      },
    };

    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getUserPermissions'
    ).and.callFake(mockPermissionsService.getUserPermissions);
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(permissionsServiceSpy).toHaveBeenCalled();
    expect(routerParseUrlSpy).toHaveBeenCalledWith('/not-found');
  });
});
