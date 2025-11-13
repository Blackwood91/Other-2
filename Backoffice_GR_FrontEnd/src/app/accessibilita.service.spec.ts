import { TestBed } from '@angular/core/testing';
import { AccessibilitaService } from './accessibilita.service';

describe('AccessibilitaService', () => {
  let service: AccessibilitaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccessibilitaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});