import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LastTasksComponent } from './last-tasks.component';

describe('LastTasksComponent', () => {
  let component: LastTasksComponent;
  let fixture: ComponentFixture<LastTasksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ LastTasksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LastTasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
