import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectWageComponent } from './select-wage.component';

describe('SelectWageComponent', () => {
  let component: SelectWageComponent;
  let fixture: ComponentFixture<SelectWageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ SelectWageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectWageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
