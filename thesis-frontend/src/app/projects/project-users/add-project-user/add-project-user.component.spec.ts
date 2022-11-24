import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { AddProjectUserComponent } from './add-project-user.component';

describe('AddProjectUserComponent', () => {
  let component: AddProjectUserComponent;
  let fixture: ComponentFixture<AddProjectUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddProjectUserComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(AddProjectUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
