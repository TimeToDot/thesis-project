import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { AddProjectEmployeeComponent } from './add-project-employee.component';

describe('AddProjectEmployeeComponent', () => {
  let component: AddProjectEmployeeComponent;
  let fixture: ComponentFixture<AddProjectEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddProjectEmployeeComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(AddProjectEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
