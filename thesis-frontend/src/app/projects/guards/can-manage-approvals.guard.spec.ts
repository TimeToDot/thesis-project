import { TestBed } from '@angular/core/testing';

import { CanManageApprovalsGuard } from './can-manage-approvals.guard';

describe('CanManageApprovalsGuard', () => {
  let guard: CanManageApprovalsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanManageApprovalsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
