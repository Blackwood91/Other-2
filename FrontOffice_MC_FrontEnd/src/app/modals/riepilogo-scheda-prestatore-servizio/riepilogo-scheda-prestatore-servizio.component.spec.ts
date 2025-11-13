import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiepilogoSchedaPrestatoreServizioComponent } from './riepilogo-scheda-prestatore-servizio.component';

describe('RiepilogoSchedaPrestatoreServizioComponent', () => {
  let component: RiepilogoSchedaPrestatoreServizioComponent;
  let fixture: ComponentFixture<RiepilogoSchedaPrestatoreServizioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RiepilogoSchedaPrestatoreServizioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiepilogoSchedaPrestatoreServizioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
