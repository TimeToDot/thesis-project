import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ProjectPermissions } from '../../shared/model/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

import { CanManageTasksGuard } from './can-manage-tasks.guard';

describe('CanManageTasksGuard', () => {
  let guard: CanManageTasksGuard;
  let permissionsService: PermissionsService;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/a/b/c' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanManageTasksGuard);
    permissionsService = TestBed.inject(PermissionsService);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the authorized user to access project tasks page', () => {
    const mockPermissionsService = {
      getProjectPermissions: (index: string | null): ProjectPermissions => {
        return {
          id: '1',
          canReadProject: true,
          canManageTasks: true,
          canManageProjectUsers: true,
          canManageApprovals: true,
          canAdminProjects: true,
        };
      },
    };

    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getProjectPermissions'
    ).and.callFake(mockPermissionsService.getProjectPermissions);

    guard.canActivate(routeMock, routeStateMock);

    expect(permissionsServiceSpy).toHaveBeenCalledOnceWith('b');
  });

  it('should redirect the unauthorized user to not found page', () => {
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(routerParseUrlSpy).toHaveBeenCalledWith('/not-found');
  });
});
