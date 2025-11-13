import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RichiesteRegistrazioneUtenteComponent } from './richieste-registrazione-utente.component';

describe('RichiesteRegistrazioneUtenteComponent', () => {
  let component: RichiesteRegistrazioneUtenteComponent;
  let fixture: ComponentFixture<RichiesteRegistrazioneUtenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RichiesteRegistrazioneUtenteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RichiesteRegistrazioneUtenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
