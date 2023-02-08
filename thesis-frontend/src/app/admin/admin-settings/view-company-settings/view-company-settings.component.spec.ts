import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCompanySettingsComponent } from './view-company-settings.component';

describe('ViewCompanySettingsComponent', () => {
  let component: ViewCompanySettingsComponent;
  let fixture: ComponentFixture<ViewCompanySettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ViewCompanySettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewCompanySettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
