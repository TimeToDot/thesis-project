import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectApprovalsComponent } from './project-approvals.component';

describe('ProjectApprovalsComponent', () => {
  let component: ProjectApprovalsComponent;
  let fixture: ComponentFixture<ProjectApprovalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectApprovalsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectApprovalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
