import { TestBed } from '@angular/core/testing';

import { CanReadProjectGuard } from './can-read-project.guard';

describe('CanReadProjectGuard', () => {
  let guard: CanReadProjectGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanReadProjectGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
