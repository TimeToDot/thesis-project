import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAccountInfoComponent } from './view-account-info.component';

describe('ViewAccountInfoComponent', () => {
  let component: ViewAccountInfoComponent;
  let fixture: ComponentFixture<ViewAccountInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewAccountInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewAccountInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
