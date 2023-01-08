import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestsStatusComponent } from './requests-status.component';

describe('RequestsStatusComponent', () => {
  let component: RequestsStatusComponent;
  let fixture: ComponentFixture<RequestsStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ RequestsStatusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RequestsStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
