import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoRichiesteIscrizioneComponent } from './elenco-richieste-iscrizione.component';

describe('ElencoRichiesteIscrizioneComponent', () => {
  let component: ElencoRichiesteIscrizioneComponent;
  let fixture: ComponentFixture<ElencoRichiesteIscrizioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoRichiesteIscrizioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoRichiesteIscrizioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
