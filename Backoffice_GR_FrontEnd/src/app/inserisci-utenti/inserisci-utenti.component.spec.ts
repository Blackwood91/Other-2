import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserisciUtentiComponent } from './inserisci-utenti.component';

describe('InserisciUtentiComponent', () => {
  let component: InserisciUtentiComponent;
  let fixture: ComponentFixture<InserisciUtentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InserisciUtentiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InserisciUtentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
