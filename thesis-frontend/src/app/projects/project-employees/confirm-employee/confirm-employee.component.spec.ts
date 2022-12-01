import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ConfirmEmployeeComponent } from './confirm-employee.component';

describe('ConfirmEmployeeComponent', () => {
  let component: ConfirmEmployeeComponent;
  let fixture: ComponentFixture<ConfirmEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmEmployeeComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ConfirmEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
