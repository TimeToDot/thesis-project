import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAddressInfoComponent } from './view-address-info.component';

describe('ViewAddressInfoComponent', () => {
  let component: ViewAddressInfoComponent;
  let fixture: ComponentFixture<ViewAddressInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ViewAddressInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAddressInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
