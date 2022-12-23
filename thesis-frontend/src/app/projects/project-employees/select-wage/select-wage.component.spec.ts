import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { SelectWageComponent } from './select-wage.component';

describe('SelectWageComponent', () => {
  let component: SelectWageComponent;
  let fixture: ComponentFixture<SelectWageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoopAnimationsModule, SelectWageComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(SelectWageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
