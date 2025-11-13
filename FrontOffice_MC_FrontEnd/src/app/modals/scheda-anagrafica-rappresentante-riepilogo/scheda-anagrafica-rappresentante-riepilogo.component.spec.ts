import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaAnagraficaRappresentanteRiepilogoComponent } from './scheda-anagrafica-rappresentante-riepilogo.component';

describe('SchedaAnagraficaRappresentanteRiepilogoComponent', () => {
  let component: SchedaAnagraficaRappresentanteRiepilogoComponent;
  let fixture: ComponentFixture<SchedaAnagraficaRappresentanteRiepilogoComponent>;

  beforeEach(async () => { 
    await TestBed.configureTestingModule({
      declarations: [ SchedaAnagraficaRappresentanteRiepilogoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaAnagraficaRappresentanteRiepilogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
