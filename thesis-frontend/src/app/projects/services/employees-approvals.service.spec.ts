import { TestBed } from '@angular/core/testing';

import { EmployeesApprovalsService } from './employees-approvals.service';

describe('EmployeesApprovalsService', () => {
  let service: EmployeesApprovalsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeesApprovalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
