import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEmploymentInfoComponent } from './edit-employment-info.component';

describe('EditEmploymentInfoComponent', () => {
  let component: EditEmploymentInfoComponent;
  let fixture: ComponentFixture<EditEmploymentInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ EditEmploymentInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditEmploymentInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
