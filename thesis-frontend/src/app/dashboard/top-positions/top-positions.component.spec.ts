import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopPositionsComponent } from './top-positions.component';

describe('TopPositionsComponent', () => {
  let component: TopPositionsComponent;
  let fixture: ComponentFixture<TopPositionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ TopPositionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopPositionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
