import { TestBed } from '@angular/core/testing';

import { CanAddProjectGuard } from './can-add-project.guard';

describe('CanAddProjectGuard', () => {
  let guard: CanAddProjectGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAddProjectGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
