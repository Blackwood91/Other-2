import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoSociPrestatoriServizioComponent } from './elenco-soci-prestatori-servizio.component';

describe('ElencoSociPrestatoriServizioComponent', () => {
  let component: ElencoSociPrestatoriServizioComponent;
  let fixture: ComponentFixture<ElencoSociPrestatoriServizioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoSociPrestatoriServizioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoSociPrestatoriServizioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
