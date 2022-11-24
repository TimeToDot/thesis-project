import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ProjectPermissions } from '../../shared/model/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

import { CanAddProjectUserGuard } from './can-add-project-user.guard';

describe('CanAddProjectUserGuard', () => {
  let guard: CanAddProjectUserGuard;
  let permissionsService: PermissionsService;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAddProjectUserGuard);
    permissionsService = TestBed.inject(PermissionsService);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the authorized user to access add project user form page', () => {
    const mockPermissionsService = {
      getProjectPermissions: (index: string | null): ProjectPermissions => {
        return {
          id: '1',
          canReadProject: true,
          canManageTasks: true,
          canManageProjectUsers: true,
          canManageApprovals: true,
          canAdminProjects: true,
          canAddProjectUser: true,
        };
      },
    };

    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getProjectPermissions'
    ).and.callFake(mockPermissionsService.getProjectPermissions);

    guard.canActivate(routeMock, routeStateMock);

    expect(permissionsServiceSpy).toHaveBeenCalled();
  });

  it('should redirect an unauthorized user to the project dashboard route', () => {
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(routerParseUrlSpy).toHaveBeenCalledWith('/not-found');
  });
});
