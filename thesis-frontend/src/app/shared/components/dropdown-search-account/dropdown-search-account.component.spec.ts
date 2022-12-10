import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownSearchAccountComponent } from './dropdown-search-account.component';

describe('DropdownSearchAccountComponent', () => {
  let component: DropdownSearchAccountComponent;
  let fixture: ComponentFixture<DropdownSearchAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropdownSearchAccountComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DropdownSearchAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
