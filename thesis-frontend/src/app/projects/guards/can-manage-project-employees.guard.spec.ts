import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ProjectPermissions } from '../model/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';
import { CanManageProjectEmployeesGuard } from './can-manage-project-employees.guard';

describe('CanManageProjectEmployeesGuard', () => {
  let guard: CanManageProjectEmployeesGuard;
  let permissionsService: PermissionsService;
  let routeMock: any = { snapshot: {} };
  let routeStateMock: any = { snapshot: {}, url: '/a/b/c' };
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanManageProjectEmployeesGuard);
    permissionsService = TestBed.inject(PermissionsService);
    router = TestBed.inject(Router);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow the authorized employee to access project employees page', () => {
    const mockPermissionsService = {
      getProjectPermissions: (index: string | null): ProjectPermissions => {
        return {
          id: '1',
          canReadProject: true,
          canManageTasks: true,
          canManageProjectEmployees: true,
          canManageApprovals: true,
          canAdminProjects: true,
          canAddProjectEmployee: true,
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

  it('should redirect the unauthorized employee to not found page', () => {
    const routerParseUrlSpy = spyOn(router, 'parseUrl');

    guard.canActivate(routeMock, routeStateMock);

    expect(routerParseUrlSpy).toHaveBeenCalledWith('/not-found');
  });
});
