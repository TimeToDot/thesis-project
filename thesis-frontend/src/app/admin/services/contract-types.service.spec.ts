import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ContractTypesService } from './contract-types.service';

describe('ContractTypesService', () => {
  let service: ContractTypesService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(ContractTypesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
