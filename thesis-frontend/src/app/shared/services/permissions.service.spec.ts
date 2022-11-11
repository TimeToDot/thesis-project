import { TestBed } from '@angular/core/testing';

import { PermissionsService } from './permissions.service';

describe('PermissionsService', () => {
  let service: PermissionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PermissionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return user permissions', () => {
    expect(service.getUserPermissions()).toBeInstanceOf(Object);
  });

  it('should return project permissions', () => {
    const permissions = service.getProjectPermissions('1');
    expect(permissions).toBeInstanceOf(Object);
    expect(permissions?.canAdminProjects).toBeTrue();
    expect(permissions?.canManageApprovals).toBeTrue();
    expect(permissions?.canManageProjectUsers).toBeTrue();
    expect(permissions?.canManageTasks).toBeTrue();
    expect(permissions?.canReadProject).toBeTrue();
  });

  it('should not return project permissions', () => {
    const permissions = service.getProjectPermissions(null);
    expect(permissions).toBeUndefined();
  });
});
