import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGeneralSettingsComponent } from './view-general-settings.component';

describe('ViewGeneralSettingsComponent', () => {
  let component: ViewGeneralSettingsComponent;
  let fixture: ComponentFixture<ViewGeneralSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ViewGeneralSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewGeneralSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
