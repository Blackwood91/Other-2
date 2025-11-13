import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeseMediazioneComponent } from './spese-mediazione.component';

describe('SpeseMediazioneComponent', () => {
  let component: SpeseMediazioneComponent;
  let fixture: ComponentFixture<SpeseMediazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpeseMediazioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpeseMediazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
