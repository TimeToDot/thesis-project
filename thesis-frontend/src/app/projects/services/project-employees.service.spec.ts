import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { ProjectEmployeesService } from './project-employees.service';

describe('ProjectEmployeesService', () => {
  let service: ProjectEmployeesService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(ProjectEmployeesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
