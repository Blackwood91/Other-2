import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiornaUtentiComponent } from './aggiorna-utenti.component';

describe('AggiornaUtentiComponent', () => {
  let component: AggiornaUtentiComponent;
  let fixture: ComponentFixture<AggiornaUtentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiornaUtentiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiornaUtentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
