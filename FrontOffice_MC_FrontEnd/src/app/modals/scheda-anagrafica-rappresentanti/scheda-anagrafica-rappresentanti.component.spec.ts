import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaAnagraficaRappresentantiComponent } from './scheda-anagrafica-rappresentanti.component';

describe('SchedaAnagraficaRappresentantiComponent', () => {
  let component: SchedaAnagraficaRappresentantiComponent;
  let fixture: ComponentFixture<SchedaAnagraficaRappresentantiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaAnagraficaRappresentantiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaAnagraficaRappresentantiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
