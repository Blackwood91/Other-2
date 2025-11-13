import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiornamentoAnagraficaComponent } from './aggiornamento-anagrafica.component';

describe('AggiornamentoAnagraficaComponent', () => {
  let component: AggiornamentoAnagraficaComponent;
  let fixture: ComponentFixture<AggiornamentoAnagraficaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiornamentoAnagraficaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiornamentoAnagraficaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
