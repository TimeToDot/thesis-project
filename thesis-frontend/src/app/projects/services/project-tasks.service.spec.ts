import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ProjectTasksService } from './project-tasks.service';

describe('ProjectTasksService', () => {
  let service: ProjectTasksService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(ProjectTasksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
