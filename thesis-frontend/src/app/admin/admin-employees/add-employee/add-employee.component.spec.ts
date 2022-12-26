import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { AddEmployeeComponent } from './add-employee.component';

describe('AddEmployeeComponent', () => {
  let component: AddEmployeeComponent;
  let fixture: ComponentFixture<AddEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NoopAnimationsModule,
        AddEmployeeComponent,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AddEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
