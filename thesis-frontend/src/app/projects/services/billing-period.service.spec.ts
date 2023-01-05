import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { BillingPeriodService } from './billing-period.service';

describe('BillingPeriodService', () => {
  let service: BillingPeriodService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(BillingPeriodService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
