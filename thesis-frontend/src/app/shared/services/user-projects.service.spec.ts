import { TestBed } from '@angular/core/testing';

import { UserProjectsService } from './user-projects.service';

describe('UserProjectsService', () => {
  let service: UserProjectsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserProjectsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
