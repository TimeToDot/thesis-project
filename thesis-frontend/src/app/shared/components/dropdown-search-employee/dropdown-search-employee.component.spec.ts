import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownSearchEmployeeComponent } from './dropdown-search-employee.component';

describe('DropdownSearchEmployeeComponent', () => {
  let component: DropdownSearchEmployeeComponent;
  let fixture: ComponentFixture<DropdownSearchEmployeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropdownSearchEmployeeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DropdownSearchEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
