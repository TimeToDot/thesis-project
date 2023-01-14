import { TestBed } from '@angular/core/testing';

import { TasksToRejectService } from './tasks-to-reject.service';

describe('TasksToRejectService', () => {
  let service: TasksToRejectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TasksToRejectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
