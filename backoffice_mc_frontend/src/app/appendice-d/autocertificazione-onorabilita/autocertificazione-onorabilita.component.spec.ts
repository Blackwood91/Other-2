import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutocertificazioneOnorabilitaComponent } from './autocertificazione-onorabilita.component';

describe('AutocertificazioneOnorabilitaComponent', () => {
  let component: AutocertificazioneOnorabilitaComponent;
  let fixture: ComponentFixture<AutocertificazioneOnorabilitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutocertificazioneOnorabilitaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AutocertificazioneOnorabilitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
