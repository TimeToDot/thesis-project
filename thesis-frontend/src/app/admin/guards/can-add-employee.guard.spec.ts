import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Permissions } from '../../shared/models/permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

import { CanAddEmployeeGuard } from './can-add-employee.guard';

describe('CanAddEmployeeGuard', () => {
  let guard: CanAddEmployeeGuard;
  let permissionsService: PermissionsService;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAddEmployeeGuard);
    permissionsService = TestBed.inject(PermissionsService);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the authorized employee to access create employee form page', () => {
    guard.canActivate(routeMock, routeStateMock);
    expect(guard.canActivate(routeMock, routeStateMock)).toEqual(true);
  });

  it('should redirect an unauthorized employee to the dashboard route', () => {
    const mockPermissionsService = {
      getEmployeePermissions: (): Permissions => {
        return {
          projects: [],
          canAddEmployee: false,
          canAddProject: false,
          canAdminEmployees: false,
          canAdminSettings: false,
          canAdminPositions: false,
        };
      },
    };

    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getEmployeePermissions'
    ).and.callFake(mockPermissionsService.getEmployeePermissions);
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(permissionsServiceSpy).toHaveBeenCalled();
    expect(routerParseUrlSpy).toHaveBeenCalledWith('/not-found');
  });
});
