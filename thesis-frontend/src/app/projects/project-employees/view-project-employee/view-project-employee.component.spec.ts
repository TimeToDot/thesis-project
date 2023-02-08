import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ViewProjectEmployeeComponent } from './view-project-employee.component';

describe('ViewProjectEmployeeComponent', () => {
  let component: ViewProjectEmployeeComponent;
  let fixture: ComponentFixture<ViewProjectEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        RouterTestingModule,
        ViewProjectEmployeeComponent,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewProjectEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
