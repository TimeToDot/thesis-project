import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ProjectApprovalsComponent } from './project-approvals.component';

describe('ProjectApprovalsComponent', () => {
  let component: ProjectApprovalsComponent;
  let fixture: ComponentFixture<ProjectApprovalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        ProjectApprovalsComponent,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectApprovalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
