import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ContactInfoComponent } from './contact-info.component';

describe('ContactInfoComponent', () => {
  let component: ContactInfoComponent;
  let fixture: ComponentFixture<ContactInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactInfoComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ContactInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});