import { TestBed } from '@angular/core/testing';

import { CanAdminUsersGuard } from './can-admin-users.guard';

describe('CanAdminUsersGuard', () => {
  let guard: CanAdminUsersGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAdminUsersGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
