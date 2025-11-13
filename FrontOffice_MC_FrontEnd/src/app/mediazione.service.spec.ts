import { TestBed } from '@angular/core/testing';

import { MediazioneService } from './mediazione.service';

describe('MediazioneService', () => {
  let service: MediazioneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MediazioneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
