import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ProjectUsersComponent } from './project-users.component';

describe('ProjectUsersComponent', () => {
  let component: ProjectUsersComponent;
  let fixture: ComponentFixture<ProjectUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectUsersComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
