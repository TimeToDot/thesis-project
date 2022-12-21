import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ViewEmployeeComponent } from './view-employee.component';

describe('ViewEmployeeComponent', () => {
  let component: ViewEmployeeComponent;
  let fixture: ComponentFixture<ViewEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule,
        RouterTestingModule,
        ViewEmployeeComponent,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
