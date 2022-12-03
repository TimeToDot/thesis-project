import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { CreateProjectEmployeeComponent } from './create-project-employee.component';

describe('CreateProjectEmployeeComponent', () => {
  let component: CreateProjectEmployeeComponent;
  let fixture: ComponentFixture<CreateProjectEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateProjectEmployeeComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateProjectEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
