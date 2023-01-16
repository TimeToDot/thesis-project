import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewContactSettingsComponent } from './view-contact-settings.component';

describe('ViewContactSettingsComponent', () => {
  let component: ViewContactSettingsComponent;
  let fixture: ComponentFixture<ViewContactSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ViewContactSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewContactSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
