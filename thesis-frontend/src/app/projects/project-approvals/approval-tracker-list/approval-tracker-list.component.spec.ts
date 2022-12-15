import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ApprovalTrackerListComponent } from './approval-tracker-list.component';

describe('ApprovalTrackerListComponent', () => {
  let component: ApprovalTrackerListComponent;
  let fixture: ComponentFixture<ApprovalTrackerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ApprovalTrackerListComponent,
        BrowserAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ApprovalTrackerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
