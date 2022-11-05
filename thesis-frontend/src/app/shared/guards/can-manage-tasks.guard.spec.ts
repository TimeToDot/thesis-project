import { TestBed } from '@angular/core/testing';

import { CanManageTasksGuard } from './can-manage-tasks.guard';

describe('CanManageTasksGuard', () => {
  let guard: CanManageTasksGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanManageTasksGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
