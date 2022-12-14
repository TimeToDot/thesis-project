import { TestBed } from '@angular/core/testing';

import { ProjectApprovalsService } from './project-approvals.service';

describe('ProjectApprovalsService', () => {
  let service: ProjectApprovalsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectApprovalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
