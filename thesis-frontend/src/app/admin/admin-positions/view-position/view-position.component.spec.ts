import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { ViewPositionComponent } from './view-position.component';

describe('ViewPositionComponent', () => {
  let component: ViewPositionComponent;
  let fixture: ComponentFixture<ViewPositionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule,
        RouterTestingModule,
        ViewPositionComponent,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
