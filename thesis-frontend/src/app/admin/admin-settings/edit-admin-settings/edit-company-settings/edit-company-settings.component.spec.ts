import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCompanySettingsComponent } from './edit-company-settings.component';

describe('EditCompanySettingsComponent', () => {
  let component: EditCompanySettingsComponent;
  let fixture: ComponentFixture<EditCompanySettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ EditCompanySettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCompanySettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
