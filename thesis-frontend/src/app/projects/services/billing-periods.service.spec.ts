import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { BillingPeriodsService } from './billing-periods.service';

describe('BillingPeriodsService', () => {
  let service: BillingPeriodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(BillingPeriodsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
