import { TestBed } from '@angular/core/testing';

import { CanAdminSettingsGuard } from './can-admin-settings.guard';

describe('CanAdminSettingsGuard', () => {
  let guard: CanAdminSettingsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CanAdminSettingsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
