import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGeneralSettingsComponent } from './edit-general-settings.component';

describe('EditGeneralSettingsComponent', () => {
  let component: EditGeneralSettingsComponent;
  let fixture: ComponentFixture<EditGeneralSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ EditGeneralSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditGeneralSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
