import { TestBed } from '@angular/core/testing';

import { GiustiziaService } from './giustizia.service';

describe('GiustiziaService', () => {
  let service: GiustiziaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GiustiziaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});