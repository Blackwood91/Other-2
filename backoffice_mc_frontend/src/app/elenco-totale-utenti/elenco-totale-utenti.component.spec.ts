import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoTotaleUtentiComponent } from './elenco-totale-utenti.component';

describe('ElencoTotaleUtentiComponent', () => {
  let component: ElencoTotaleUtentiComponent;
  let fixture: ComponentFixture<ElencoTotaleUtentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoTotaleUtentiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoTotaleUtentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
