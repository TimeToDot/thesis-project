import { TestBed } from '@angular/core/testing';

import { CanManageProjectUsersGuard } from './can-manage-project-users.guard';

describe('CanManageProjectUsersGuard', () => {
  let guard: CanManageProjectUsersGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanManageProjectUsersGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
