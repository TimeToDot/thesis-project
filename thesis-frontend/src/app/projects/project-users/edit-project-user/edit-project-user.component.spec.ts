import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProjectUserComponent } from './edit-project-user.component';

describe('EditProjectUserComponent', () => {
  let component: EditProjectUserComponent;
  let fixture: ComponentFixture<EditProjectUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditProjectUserComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EditProjectUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
