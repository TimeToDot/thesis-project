import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { AddNewProjectComponent } from './add-new-project.component';

describe('AddNewProjectComponent', () => {
  let component: AddNewProjectComponent;
  let fixture: ComponentFixture<AddNewProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewProjectComponent, NoopAnimationsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(AddNewProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
