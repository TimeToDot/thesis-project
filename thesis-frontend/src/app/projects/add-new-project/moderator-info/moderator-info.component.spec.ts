import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { ModeratorInfoComponent } from './moderator-info.component';

describe('ModeratorInfoComponent', () => {
  let component: ModeratorInfoComponent;
  let fixture: ComponentFixture<ModeratorInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModeratorInfoComponent, NoopAnimationsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ModeratorInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
