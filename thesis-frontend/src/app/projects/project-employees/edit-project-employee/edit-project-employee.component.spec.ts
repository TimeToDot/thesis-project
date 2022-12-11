import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';

import { EditProjectEmployeeComponent } from './edit-project-employee.component';

describe('EditProjectEmployeeComponent', () => {
  let component: EditProjectEmployeeComponent;
  let fixture: ComponentFixture<EditProjectEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditProjectEmployeeComponent, RouterModule],
    }).compileComponents();

    fixture = TestBed.createComponent(EditProjectEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
