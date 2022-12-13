import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { AdminPositionsComponent } from './admin-positions.component';

describe('AdminPositionsComponent', () => {
  let component: AdminPositionsComponent;
  let fixture: ComponentFixture<AdminPositionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AdminPositionsComponent,
        BrowserAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminPositionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
