import { TestBed } from '@angular/core/testing';

import { CanAdminProjectsGuard } from './can-admin-projects.guard';

describe('CanAdminProjectsGuard', () => {
  let guard: CanAdminProjectsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAdminProjectsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
