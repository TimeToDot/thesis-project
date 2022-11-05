import { TestBed } from '@angular/core/testing';

import { CanReadAdminGuard } from './can-read-admin.guard';

describe('CanReadAdminGuard', () => {
  let guard: CanReadAdminGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanReadAdminGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
