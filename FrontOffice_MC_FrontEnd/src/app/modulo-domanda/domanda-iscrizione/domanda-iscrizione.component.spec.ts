import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomandaIscrizioneComponent } from './domanda-iscrizione.component';

describe('DomandaIscrizioneComponent', () => {
  let component: DomandaIscrizioneComponent;
  let fixture: ComponentFixture<DomandaIscrizioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DomandaIscrizioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DomandaIscrizioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
