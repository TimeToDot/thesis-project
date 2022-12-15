import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ApprovalTrackerComponent } from './approval-tracker.component';

describe('ApprovalTrackerComponent', () => {
  let component: ApprovalTrackerComponent;
  let fixture: ComponentFixture<ApprovalTrackerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ApprovalTrackerComponent,
        BrowserAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ApprovalTrackerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
