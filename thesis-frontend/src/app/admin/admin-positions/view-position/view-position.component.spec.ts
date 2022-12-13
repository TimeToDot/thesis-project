import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { ViewPositionComponent } from './view-position.component';

describe('ViewPositionComponent', () => {
  let component: ViewPositionComponent;
  let fixture: ComponentFixture<ViewPositionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ViewPositionComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
