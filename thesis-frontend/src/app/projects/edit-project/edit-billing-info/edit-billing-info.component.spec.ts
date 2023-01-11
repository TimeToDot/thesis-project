import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBillingInfoComponent } from './edit-billing-info.component';

describe('EditBillingInfoComponent', () => {
  let component: EditBillingInfoComponent;
  let fixture: ComponentFixture<EditBillingInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditBillingInfoComponent, HttpClientTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(EditBillingInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
