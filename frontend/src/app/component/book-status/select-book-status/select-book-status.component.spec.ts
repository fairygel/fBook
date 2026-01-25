import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectBookStatusComponent } from './select-book-status.component';

describe('SelectBookStatusComponent', () => {
  let component: SelectBookStatusComponent;
  let fixture: ComponentFixture<SelectBookStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectBookStatusComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SelectBookStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
