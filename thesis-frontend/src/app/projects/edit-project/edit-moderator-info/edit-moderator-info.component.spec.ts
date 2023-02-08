import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditModeratorInfoComponent } from './edit-moderator-info.component';

describe('EditModeratorInfoComponent', () => {
  let component: EditModeratorInfoComponent;
  let fixture: ComponentFixture<EditModeratorInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditModeratorInfoComponent, HttpClientTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(EditModeratorInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
