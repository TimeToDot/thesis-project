import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPositionsComponent } from './admin-positions.component';

describe('AdminPositionsComponent', () => {
  let component: AdminPositionsComponent;
  let fixture: ComponentFixture<AdminPositionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ AdminPositionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPositionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
