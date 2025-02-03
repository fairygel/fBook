import { TestBed } from '@angular/core/testing';

import { BookStatusService } from './book-status.service';

describe('BookStatusService', () => {
  let service: BookStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
