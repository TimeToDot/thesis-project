import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { AddProjectEmployeeComponent } from './add-project-employee.component';

describe('AddProjectEmployeeComponent', () => {
  let component: AddProjectEmployeeComponent;
  let fixture: ComponentFixture<AddProjectEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AddProjectEmployeeComponent,
        HttpClientTestingModule,
        NoopAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AddProjectEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
