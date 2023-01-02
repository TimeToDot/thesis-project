import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { EmploymentInfoComponent } from './employment-info.component';

describe('EmploymentInfoComponent', () => {
  let component: EmploymentInfoComponent;
  let fixture: ComponentFixture<EmploymentInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        EmploymentInfoComponent,
        NoopAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EmploymentInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
