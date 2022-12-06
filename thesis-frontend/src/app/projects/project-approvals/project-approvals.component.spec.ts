import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ProjectApprovalsComponent } from './project-approvals.component';

describe('ProjectApprovalsComponent', () => {
  let component: ProjectApprovalsComponent;
  let fixture: ComponentFixture<ProjectApprovalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectApprovalsComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectApprovalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
