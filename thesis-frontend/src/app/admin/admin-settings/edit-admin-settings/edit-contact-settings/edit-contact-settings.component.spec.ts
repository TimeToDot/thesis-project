import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContactSettingsComponent } from './edit-contact-settings.component';

describe('EditContactSettingsComponent', () => {
  let component: EditContactSettingsComponent;
  let fixture: ComponentFixture<EditContactSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ EditContactSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditContactSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
