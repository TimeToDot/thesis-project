import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

import { AddPositionComponent } from './add-position.component';

describe('AddPositionComponent', () => {
  let component: AddPositionComponent;
  let fixture: ComponentFixture<AddPositionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AddPositionComponent,
        HttpClientTestingModule,
        NoopAnimationsModule,
        RouterTestingModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AddPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
