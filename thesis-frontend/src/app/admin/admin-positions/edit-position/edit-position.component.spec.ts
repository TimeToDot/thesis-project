import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { EditPositionComponent } from './edit-position.component';

describe('EditPositionComponent', () => {
  let component: EditPositionComponent;
  let fixture: ComponentFixture<EditPositionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditPositionComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(EditPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
