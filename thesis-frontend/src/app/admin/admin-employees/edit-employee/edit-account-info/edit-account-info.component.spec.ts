import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAccountInfoComponent } from './edit-account-info.component';

describe('EditAccountInfoComponent', () => {
  let component: EditAccountInfoComponent;
  let fixture: ComponentFixture<EditAccountInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditAccountInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EditAccountInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
