import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmploymentInfoComponent } from './view-employment-info.component';

describe('ViewEmploymentInfoComponent', () => {
  let component: ViewEmploymentInfoComponent;
  let fixture: ComponentFixture<ViewEmploymentInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewEmploymentInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewEmploymentInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
