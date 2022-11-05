import { TestBed } from '@angular/core/testing';
import { CanAdminProjectGuard } from './can-admin-project.guard';

describe('CanAdminProjectGuard', () => {
  let guard: CanAdminProjectGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAdminProjectGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
