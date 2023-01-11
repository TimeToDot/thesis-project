import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewProjectsInfoComponent } from './view-projects-info.component';

describe('ViewProjectsInfoComponent', () => {
  let component: ViewProjectsInfoComponent;
  let fixture: ComponentFixture<ViewProjectsInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ViewProjectsInfoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewProjectsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
