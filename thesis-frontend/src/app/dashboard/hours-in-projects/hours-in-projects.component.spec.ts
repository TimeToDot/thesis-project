import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoursInProjectsComponent } from './hours-in-projects.component';

describe('HoursInProjectsComponent', () => {
  let component: HoursInProjectsComponent;
  let fixture: ComponentFixture<HoursInProjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HoursInProjectsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HoursInProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
